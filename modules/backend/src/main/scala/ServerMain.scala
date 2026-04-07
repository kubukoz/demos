package examples.smithy.server

import cats.effect._
import com.comcast.ip4s._
import fs2.Stream
import fs2.io.net.Network
import jsonrpclib.fs2._
import jsonrpclib.smithy4sinterop.ClientStub
import jsonrpclib.smithy4sinterop.ServerEndpoints
import jsonrpclib.CallId
import test._ // smithy4s-generated package

object ServerMain {

  // Reserving a method for cancelation.
  val cancelEndpoint = CancelTemplate.make[CallId]("$/cancel", identity, identity)

  // Implementing the generated interface
  class ServerImpl(client: TestClient[IO]) extends TestServer[IO] {

    def greet(name: String): IO[GreetOutput] =
      for {
        _ <- printErr(s"greet(name = $name)")
        timeResult <- client.getTime()
        _ <- printErr(s"greet: client time is ${timeResult.time}")
        response = GreetOutput(s"Server says: hello $name !")
        _ <- printErr(s"greet: responding: ${response.message}")
      } yield response

    def ping(ping: String): IO[Unit] =
      printErr(s"ping(ping = $ping)") >>
        client.pong(s"Returned to sender: $ping") >>
        printErr(s"ping: pong sent successfully")

  }

  def printErr(s: String): IO[Unit] = IO.consoleForIO.errorln(s)

  def run: IO[Unit] = {
    val run =
      Stream.resource(Network[IO].serverResource(port = Some(port"9999"))).flatMap { (_, server) =>
        server.map { client =>
          FS2Channel
            .stream[IO](cancelTemplate = Some(cancelEndpoint))
            .flatMap { channel =>
              Stream.eval(IO.fromEither(ClientStub(TestClient, channel))).flatMap { testClient =>
                Stream.eval(IO.fromEither(ServerEndpoints(new ServerImpl(testClient)))).flatMap {
                  se =>
                    channel.withEndpointsStream(se)
                }
              }
            }
            .flatMap { channel =>
              Stream
                .eval(IO.never)
                .concurrently(
                  client
                    .reads
                    .through(fs2.text.utf8.decode)
                    .through(fs2.text.lines)
                    .filter(_.nonEmpty)
                    .evalTap(line => printErr(s"<-- IN  $line"))
                    .map { line =>
                      import com.github.plokhotnyuk.jsoniter_scala.circe.JsoniterScalaCodec._
                      import com.github.plokhotnyuk.jsoniter_scala.core._
                      val json = readFromString[io.circe.Json](line)
                      io.circe
                        .Decoder[jsonrpclib.Message]
                        .apply(io.circe.HCursor.fromJson(json))
                        .left
                        .map(e => jsonrpclib.ProtocolError.ParseError(e.getMessage))
                    }
                    .through(channel.inputOrBounce)
                )
                .concurrently(
                  channel
                    .output
                    .map { msg =>
                      import com.github.plokhotnyuk.jsoniter_scala.circe.JsoniterScalaCodec._
                      import com.github.plokhotnyuk.jsoniter_scala.core._
                      val json = io.circe.Encoder[jsonrpclib.Message].apply(msg)
                      new String(writeToArray(json))
                    }
                    .evalTap(line => printErr(s"--> OUT $line"))
                    .map(_ + "\n")
                    .through(fs2.text.utf8.encode)
                    .through(client.writes)
                )
            }
        }.parJoinUnbounded
      }

    run.compile.drain
  }

}

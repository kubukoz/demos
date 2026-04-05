package examples.smithy.client

import cats.effect._
import scala.concurrent.duration._
import cats.syntax.all._
import fs2.io.process.Processes
import fs2.Stream
import jsonrpclib.fs2._
import jsonrpclib.smithy4sinterop.ClientStub
import jsonrpclib.smithy4sinterop.ServerEndpoints
import jsonrpclib.CallId
import test._
import demo.pdapiBindings
import scala.scalanative.unsafe.CFuncPtr
import scala.scalanative.unsafe.CFuncPtr3
import scala.scalanative.unsafe.Ptr
import demo.pdapiBindings.primitives.HTTPConnection
import demo.pdapiBindings.primitives.TCPConnection
import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*
import demo.pdapiBindings.primitives.PDNetErr
import fs2.Pipe
import cats.effect.std.Dispatcher
import fs2.Chunk

object SmithyClientMain {

  // Global state for the TCP open callback (CFuncPtr can't close over locals)
  private var _connDef: Either[Throwable, Ptr[TCPConnection]] => Unit = null

  // Reserving a method for cancelation.
  val cancelEndpoint = CancelTemplate.make[CallId]("$/cancel", identity, identity)

  type IOStream[A] = fs2.Stream[IO, A]

  def log(str: String): IO[Unit] =
    IO(logRaw(str))

  def logS(str: String): IOStream[Nothing] = Stream.exec(
    log(str)
  )

  def logS1(str: String): IOStream[Unit] = Stream.eval(
    log(str)
  )

  // Implementing the generated interface
  object Client extends TestClient[IO] {
    def pong(pong: String): IO[Unit] = log(s"Client received pong: $pong")

    def getTime(): IO[GetTimeOutput] = {
      val result = GetTimeOutput(System.currentTimeMillis().toString)
      log(s"Client getTime(), responding: ${result.time}").as(result)
    }

  }

  trait Socket {
    def incoming: fs2.Stream[IO, Byte]
    def outgoing: fs2.Pipe[IO, Byte, Unit]
  }

  def logRaw(s: String) = Zone(pdapiBindings.pd_log_error_raw(toCString(s)))

  // playdate api socket impl
  def mkSocket(host: String, port: Int): Resource[IO, Socket] = {
    val acquireConn: IO[Ptr[TCPConnection]] =
      IO.async[Ptr[TCPConnection]] { connDef =>
        IO {
          _connDef = connDef
          logRaw(s"Attempting to connect to TCP server at $host:$port...")

          val conn = Zone(pdapiBindings.pd_tcp_newConnection(toCString(host), port, 0))
          if (conn == null) {
            logRaw(s"pd_tcp_newConnection returned null for $host:$port")

            connDef(
              Left(new RuntimeException(s"pd_tcp_newConnection returned null for $host:$port"))
            )
          }

          val err = pdapiBindings.pd_tcp_open(
            conn,
            CFuncPtr3.fromScalaFunction { (conn: Ptr[TCPConnection], err: PDNetErr, _: Ptr[Byte]) =>
              logRaw(s"TCP open callback called with err code: ${err.code}")
              val result =
                if (!err.isOk)
                  Left(new RuntimeException(s"TCP open failed with error code: ${err.code}"))
                else
                  Right(conn)

              _connDef(result)
            },
            null,
          )
          if (!err.isOk) {
            logRaw(s"pd_tcp_open failed immediately with error code: ${err.code}")
            connDef(Left(new RuntimeException(s"pd_tcp_open failed with error code: ${err.code}")))
          }
          None
        }
      }
    // .timeout(5.seconds)

    acquireConn.toResource.map { conn =>
      new Socket {
        def incoming: Stream[IO, Byte] =
          if true then Stream.empty
          else
            logS("Setting TCP read timeout to 0 (non-blocking)") ++
              Stream.exec(IO(pdapiBindings.pd_tcp_setReadTimeout(conn, 0))) ++
              Stream
                .repeatEval {
                  IO {
                    val avail = pdapiBindings.pd_tcp_getBytesAvailable(conn)
                    logRaw(s"Checking TCP incoming data: $avail bytes available")
                    if (avail.toInt > 0) {
                      val toRead = math.min(avail.toLong, 4096L).toInt
                      val buf = stackalloc[Byte](4096)
                      val n = pdapiBindings.pd_tcp_read(conn, buf, toRead.toUInt)
                      if (n < 0)
                        throw new RuntimeException(s"TCP read error: $n")
                      else if (n > 0) {
                        val arr = new Array[Byte](n)
                        var i = 0
                        while (i < n) {
                          arr(i) = !(buf + i)
                          i += 1
                        }
                        Some(Chunk.array(arr))
                      } else
                        None
                    } else
                      None
                  }.flatTap {
                    case None => IO.sleep(10.millis) *> IO.cede
                    case _    => IO.cede
                  }
                }
                .unNone
                .unchunks

        def outgoing: Pipe[IO, Byte, Unit] =
          in =>
            in.chunks.evalMap { chunk =>
              IO {
                val arr = chunk.toArray
                val buf = stackalloc[Byte](arr.length)
                var i = 0
                while (i < arr.length) {
                  !(buf + i) = arr(i)
                  i += 1
                }
                val written = pdapiBindings.pd_tcp_write(conn, buf, arr.length.toUInt)
                if (written < 0)
                  logRaw(s"TCP write error: $written")
              }
            }
      }
    }
  }

  def run: IO[Unit] = {
    val run =
      for {
        _ <- logS1("Starting client")
        ////////////////////////////////////////////////////////
        /////// BOOTSTRAPPING
        ////////////////////////////////////////////////////////
        fs2Channel <- FS2Channel.stream[IO](cancelTemplate = cancelEndpoint.some)
        se <- Stream.eval(IO.fromEither(ServerEndpoints.apply[TestClientGen, IO](Client)))
        _ <- fs2Channel.withEndpointsStream(se)
        server: TestServer[IO] <- Stream.eval(IO.fromEither(ClientStub(TestServer, fs2Channel)))

        socket <- fs2.Stream.resource(mkSocket("192.168.10.69", 9999))
        _ <- Stream(())
          .concurrently(
            fs2Channel
              .output
              .map { msg =>
                import com.github.plokhotnyuk.jsoniter_scala.circe.JsoniterScalaCodec._
                import com.github.plokhotnyuk.jsoniter_scala.core._
                val json = io.circe.Encoder[jsonrpclib.Message].apply(msg)
                new String(writeToArray(json)) + "\n"
              }
              .through(fs2.text.utf8.encode)
              .through(socket.outgoing)
          )
          .concurrently(
            socket
              .incoming
              .through(fs2.text.utf8.decode)
              .through(fs2.text.lines)
              .filter(_.nonEmpty)
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
              .through(fs2Channel.inputOrBounce)
          )
        // .concurrently(rp.stderr.through(fs2.io.stderr[IO]))

        ////////////////////////////////////////////////////////
        /////// INTERACTION
        ////////////////////////////////////////////////////////
        // result1 <- Stream.eval(server.greet("Client"))
        // _ <- logS1(s"Client received $result1")
        // _ <- Stream.eval(server.ping("Ping"))
      } yield ()
    run.compile.drain.guarantee(log("Terminating client"))
  }

}

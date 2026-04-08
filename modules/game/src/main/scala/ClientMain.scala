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

  def logR(str: String): Resource[IO, Unit] =
    log(str).toResource

  // Implementing the generated interface
  class Client(onPong: String => IO[Unit]) extends TestClient[IO] {
    def pong(pong: String): IO[Unit] = log(s"Client received pong: $pong") *> onPong(pong)

    def getTime(): IO[GetTimeOutput] = {
      val result = GetTimeOutput(smithy4s.Timestamp.fromEpochMilli(System.currentTimeMillis()))
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
              logRaw(s"TCP open callback called with err: ${err.name} (${err.code})")
              val result =
                if (!err.isOk)
                  Left(new RuntimeException(s"TCP open failed: ${err.name} (${err.code})"))
                else
                  Right(conn)

              _connDef(result)
            },
            null,
          )
          if (!err.isOk) {
            logRaw(s"pd_tcp_open failed immediately: ${err.name} (${err.code})")
            connDef(Left(new RuntimeException(s"pd_tcp_open failed: ${err.name} (${err.code})")))
          }
          None
        }
      }
    // .timeout(5.seconds)

    acquireConn.toResource.map { conn =>
      new Socket {
        def incoming: Stream[IO, Byte] =
          Stream
            .repeatEval {
              IO {
                val avail = pdapiBindings.pd_tcp_getBytesAvailable(conn)
                if (avail.toInt > 0) {
                  val toRead = math.min(avail.toLong, 4096L).toInt
                  val arr = new Array[Byte](toRead)
                  val n = pdapiBindings.pd_tcp_read(conn, arr.at(0), toRead.toUInt)
                  if (n < 0)
                    throw new RuntimeException(s"TCP read error: $n")
                  else if (n > 0)
                    Some(Chunk.array(arr, 0, n))
                  else
                    None
                } else
                  None
              } <* IO.cede
            }
            .unNone
            .unchunks

        def outgoing: Pipe[IO, Byte, Unit] = {
          // Single-shot write. pd_tcp_write appears to queue the buffer
          // asynchronously; calling it again with the "remaining" bytes
          // collides with the in-flight queued data and corrupts the stream.
          def writeAll(arr: Array[Byte]): IO[Unit] =
            IO {
              val written = pdapiBindings.pd_tcp_write(conn, arr.at(0), arr.length.toUInt)
              if (written < 0) {
                val err = PDNetErr.fromInt(written)
                logRaw(s"TCP write error: ${err.name} (${err.code})")
              } else if (written != arr.length)
                logRaw(s"TCP write short: wrote $written of ${arr.length} bytes")
            }

          in =>
            in.chunks.evalMap { chunk =>
              writeAll(chunk.toArray)
            }
        }
      }
    }
  }

  def run(pongHandler: String => IO[Unit]): IO[Unit] = {
    val run =
      for {
        _ <- logR("Starting client")
        ////////////////////////////////////////////////////////
        /////// BOOTSTRAPPING
        ////////////////////////////////////////////////////////
        fs2Channel <- FS2Channel.resource[IO](cancelTemplate = cancelEndpoint.some)
        se <-
          ServerEndpoints.apply[TestClientGen, IO](new Client(pongHandler)).liftTo[IO].toResource
        _ <- fs2Channel.withEndpoints(se)
        server: TestServer[IO] <- ClientStub(TestServer, fs2Channel).liftTo[IO].toResource

        socket <- mkSocket("192.168.10.69", 9999)
        _ <-
          Stream
            .never[IO]
            .concurrently(
              fs2Channel
                .output
                .map { msg =>
                  val json = io.circe.Encoder[jsonrpclib.Message].apply(msg)
                  // Using circe's noSpaces instead of jsoniter's writeToArray
                  // to work around a ByteArrayAccess.setInt bug on 32-bit ARM
                  json.noSpaces + "\n"
                }
                .through(fs2.text.utf8.encode)
                .through(socket.outgoing)
            )
            .concurrently(
              socket
                .incoming
                .chunks
                // Bypassing fs2.text.utf8.decode — it crashes on 32-bit ARM
                .map(c => new String(c.toArray))
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
            .compile
            .drain
            .background

        //////////////////////////////////////////////////////
        ///// INTERACTION
        //////////////////////////////////////////////////////
        result1 <- server.greet("Client").toResource
        _ <- logR(s"greet response: $result1")
        _ <- server.ping("Ping").toResource
        _ <- logR("ping succeeded")
      } yield ()

    run.onFinalize(log("Terminating client")).useForever
  }

}

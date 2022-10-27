package hello

import cats.effect.IO
import cats.effect.Resource
import cats.effect.ResourceApp
import cats.implicits._
import com.comcast.ip4s._
import hello.ReportService
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import smithy4s.HintMask
import smithy4s.http.CodecAPI
import smithy4s.http4s.SimpleProtocolBuilder
import smithy4s.internals.InputOutput

object MyRestJsonBuilder
    extends SimpleProtocolBuilder[smithy4s.api.SimpleRestJson](
      // notable change
      CodecAPI.nativeStringsAndBlob(
        //
        smithy4s.http.json.codecs(
          smithy4s.api.SimpleRestJson.protocol.hintMask ++ HintMask(InputOutput)
        )
      )
    )

object Main extends ResourceApp.Forever {
  val impl: ReportService[IO] = new ReportService[IO] {
    def report(payload: RawPayload): IO[Unit] = IO.println(payload.value)
  }
  def run(args: List[String]): Resource[IO, Unit] =
    MyRestJsonBuilder
      .routes(impl)
      .resource
      .map(_.orNotFound)
      .flatMap(
        EmberServerBuilder
          .default[IO]
          .withHttpApp(_)
          .withPort(port"8080")
          .withHost(host"0.0.0.0")
          .build
      )
      .void
}

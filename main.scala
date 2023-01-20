//> using scala "2.13.10"
//> using lib "com.disneystreaming.smithy4s::smithy4s-http4s:0.17.2"
//> using lib "org.http4s::http4s-ember-server:0.23.18"
//> using lib "org.http4s::http4s-dsl:0.23.18"
import hello._
import cats.effect.IO
import hello.Request.InputCase
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.HttpApp
import cats.effect.IOApp
import org.http4s.websocket.WebSocketFrame
import smithy4s.Service
import org.http4s.HttpRoutes
import smithy4s.Endpoint
import org.http4s.Method
import org.http4s.Uri
import smithy4s.StreamingSchema
import org.http4s.server.websocket.WebSocketBuilder2
import scodec.bits.ByteVector
import org.http4s.dsl.io._
import org.http4s
import smithy4s.Document
import smithy4s.http.Metadata
import smithy4s.http.BodyPartial

object demo extends IOApp.Simple {
  import SmithyStreaming._

  val impl: WeatherStreamServiceGen[Streamed5] =
    new WeatherStreamServiceGen[Streamed5] {
      def getWeather(
          other: String
      ): Streamed[Request, Response, GetWeatherOutput] = {
        println("other: " + other)

        _.debug()
          .map { case InputCase(input) =>
            Left(
              Response.OutputCase(
                ResponseOutput(s"${input.city} is going to be hot!")
              )
            )
          }
          .take(5) ++ fs2.Stream.emit(
          Right(GetWeatherOutput("That's all folks!"))
        )
      }
    }

  import com.comcast.ip4s._

  def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHttpWebSocketApp {
      SmithyStreaming.make(impl)
    }
    .withHost(host"0.0.0.0")
    .withPort(port"8080")
    .withErrorHandler { case e =>
      IO.consoleForIO
        .printStackTrace(e)
        .as(http4s.Response[IO](status = InternalServerError))
    }
    .build
    .useForever
}

object SmithyStreaming {

  import cats.implicits._

  type Streamed[SI, SO, O] = fs2.Stream[IO, SI] => fs2.Stream[IO, Either[SO, O]]
  type Streamed5[I, E, O, SI, SO] = Streamed[SI, SO, O]

  def make[Alg[_[_, _, _, _, _]]](
      impl: Alg[Streamed5]
  )(wb: WebSocketBuilder2[IO])(implicit
      service: Service[Alg]
  ): HttpApp[IO] = {
    val capi = smithy4s.http.json.codecs()
    val cache = capi.createCache()
    val runner = service.toPolyFunction(impl)

    def endpointToRoute[I, E, O, SI, SO](
        e: Endpoint[service.Operation, I, E, O, SI, SO]
    ): HttpRoutes[IO] = {
      val opname = e.name

      val inputCodec: Metadata.PartialDecoder[I] =
        Metadata.PartialDecoder.fromSchema(e.input)

      val inputCodecJson: capi.Codec[I] =
        capi.compileCodec(e.input)

      val streamedInputCodec: capi.Codec[SI] = capi.compileCodec(
        e.streamedInput.asInstanceOf[StreamingSchema.Streamed[SI]].schema,
        cache
      )

      val streamedOutputCodec: capi.Codec[SO] = capi.compileCodec(
        e.streamedOutput.asInstanceOf[StreamingSchema.Streamed[SO]].schema,
        cache
      )
      val outputCodec: capi.Codec[O] = capi.compileCodec(e.output, cache)

      HttpRoutes.of { case req @ GET -> Root / `opname` =>
        val plainInput: I = inputCodec
          .decode(
            Metadata(
              query = req.uri.query.multiParams
            )
          )
          .flatMap(
            _.combineCatch {
              capi
                .decodeFromByteArrayPartial(
                  inputCodecJson,
                  "{}".getBytes()
                )
                .toTry
                .get
            }
          )
          .toTry
          .get

        val pipe: Streamed5[I, E, O, SI, SO] = runner(e.wrap(plainInput))

        wb.build(input =>
          input
            .evalMap { frame =>
              capi
                .decodeFromByteArray(
                  streamedInputCodec,
                  frame.data.toArray
                )
                .liftTo[IO]
            }
            .debug()
            .through(pipe)
            .debug()
            .map { case Left(o) =>
              WebSocketFrame.Binary(
                ByteVector(capi.writeToArray(streamedOutputCodec, o))
              )
              case Right(o) =>
                WebSocketFrame.Binary(
                  ByteVector(capi.writeToArray(outputCodec, o))
                )
            }
            .handleErrorWith { case e =>
              fs2.Stream.emit(WebSocketFrame.Text("Error: " + e.getMessage))
            }
        )
      }
    }

    service.endpoints.foldMapK { endpointToRoute(_) }.orNotFound
  }
}

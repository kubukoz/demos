import cats.effect.IOApp
import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import smithy4s.api.SimpleRestJson
import smithy4s.http4s.SimpleProtocolBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import hello._
import com.comcast.ip4s._
import org.http4s.server.middleware.Logger
import cats.effect.ResourceApp
import cats.effect.kernel.Resource
import cats.implicits._

object HelloServer extends ResourceApp.Forever:
  val impl = new WeatherService[IO]:
    def getWeather(city: String): IO[GetWeatherOutput] =
      IO.println(city) *>
        IO.pure(GetWeatherOutput("good weather in " + city))

  def run(args: List[String]): Resource[cats.effect.IO, Unit] =
    SimpleRestJsonBuilder(WeatherService)
      .routes(impl)
      .resource
      .flatMap { routes =>
        EmberServerBuilder
          .default[IO]
          .withHttpApp(
            Logger.httpApp(
              logHeaders = true,
              logBody = true,
              logAction = Some(IO.println(_: String))
            )(
              (routes <+> smithy4s.http4s.swagger
                .docs[IO](WeatherService)).orNotFound
            )
          )
          .withHost(host"localhost")
          .withPort(port"4000")
          .build
      }
      .void

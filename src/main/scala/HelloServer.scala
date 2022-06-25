import cats.effect.IOApp
import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import smithy4s.api.SimpleRestJson
import smithy4s.http4s.SimpleProtocolBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import hello._
import com.comcast.ip4s._

object HelloServer extends IOApp.Simple:
  val impl = new WeatherService[IO]:
    def getWeather(city: String): IO[GetWeatherOutput] =
      IO.pure(GetWeatherOutput("good weather in " + city))

  val run: IO[Unit] =
    SimpleRestJsonBuilder(WeatherService)
      .routes(impl)
      .resource
      .flatMap { routes =>
        EmberServerBuilder
          .default[IO]
          .withHttpApp(routes.orNotFound)
          .withHost(host"localhost")
          .withPort(port"4000")
          .build
      }
      .useForever

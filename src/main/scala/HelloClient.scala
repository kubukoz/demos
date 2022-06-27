import cats.effect.IO
import cats.effect.IOApp
import cats.implicits._
import hello.WeatherService
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits._
import smithy4s.http4s.SimpleRestJsonBuilder

object HelloClient extends IOApp.Simple:
  def run: IO[Unit] =
    EmberClientBuilder
      .default[IO]
      .build
      .flatMap { client =>
        SimpleRestJsonBuilder(WeatherService).clientResource(
          client,
          uri"http://localhost:4000"
        )
      }
      .use(_.getWeather("Warsaw"))
      .flatMap(IO.println(_))

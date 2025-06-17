package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp.Simple {

  def run =
    SimpleRestJsonBuilder
      .routes(
        new WeatherService[IO] {
          def getWeather(city: String): IO[GetWeatherOutput] = IO.pure(
            GetWeatherOutput("bad weather in " + city)
          )

          def postCity(city: String): IO[PostCityOutput] = IO.pure(
            PostCityOutput(
              "City " + city + " has been added"
            )
          )
        }
      )
      .resource
      .flatMap { routes =>
        EmberServerBuilder
          .default[IO]
          .withHttpApp(routes.orNotFound)
          .build
      }
      .evalMap(s => IO.println(s.addressIp4s))
      .useForever

}

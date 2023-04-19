package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import hello.WeatherService
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp.Simple {
  def run = SimpleRestJsonBuilder
    .routes(
      new WeatherService[IO] {
        def getWeather(city: String): IO[GetWeatherOutput] =
          IO.pure(GetWeatherOutput("bad weather in " + city))
      }
    )
    .resource
    .flatMap { routes =>
      EmberServerBuilder
        .default[IO]
        .withHttpApp(routes.orNotFound)
        .build
    }
    .evalMap { s => IO.println(s.addressIp4s) }
    .useForever
}

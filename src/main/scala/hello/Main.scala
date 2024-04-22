package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import hello.WeatherService
import cats.effect.IO
import cats.effect.IOApp

object Main extends IOApp.Simple {
  def serverDemo = SimpleRestJsonBuilder
    .routes(
      new WeatherService.Default[IO](IO.stub) {}: WeatherService[IO]
    )
    .resource
    .flatMap { routes =>
      EmberServerBuilder
        .default[IO]
        .withHttpApp(routes.orNotFound)
        .build
    }
    .use { server =>
      IO.unit
    }
    .debug()

  def run = serverDemo
}

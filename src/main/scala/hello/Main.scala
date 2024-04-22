package hello

import cats.effect.IO
import cats.effect.IOApp
import hello.WeatherService
import org.http4s.HttpApp
import org.http4s.Method
import org.http4s.Request
import org.http4s.Response
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder

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
      EmberClientBuilder.default[IO].build.use { client =>
        client
          .run(
            Request[IO](method = Method.POST, uri = server.baseUri / "weather")
          )
          .use { response =>
            response.bodyText.compile.string
              .debug(response.status.toString())
              .void
          }
      }
    }
    .void

  def clientDemo = SimpleRestJsonBuilder(WeatherService)
    .client(
      Client.fromHttpApp(HttpApp.pure[IO](Response[IO]()))
    )
    .resource
    .use(_.postWeather("NYC"))
    .debug()
    .attempt
    .void

  def run = serverDemo *> clientDemo
}

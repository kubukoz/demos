package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import cats.effect.IO
import cats.effect.IOApp
import hello.WeatherService
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.Request

object Main extends IOApp.Simple {
  def run = SimpleRestJsonBuilder
    .routes(
      new WeatherService[IO] {
        def getWeather(city: String): IO[GetWeatherOutput] =
          IO.pure(GetWeatherOutput("bad weather in " + city, MyCode.NICE))
      }
    )
    .resource
    .flatMap { routes =>
      EmberServerBuilder
        .default[IO]
        .withHttpApp(routes.orNotFound)
        .build
    }
    .flatMap { server =>
      EmberClientBuilder.default[IO].build.flatMap { c =>
        c.run(Request[IO](uri = server.baseUri / "weather" / "London"))
          .evalMap { response =>
            response.bodyText.compile.string.flatMap { body =>
              IO.println(s"""Status: ${response.status}
                          |Body: $body
                          |""".stripMargin)
            }
          }
      }
    }
    .use_
}

package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import cats.effect.IO
import cats.effect.IOApp
import cats.syntax.all.*
import org.http4s.HttpApp
import cats.data.Kleisli
import smithy4s.http4s.ServerEndpointMiddleware
import smithy4s.Endpoint
import smithy4s.Service
import smithy4s.Hints

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
      .middleware(
        new ServerEndpointMiddleware.Simple[IO] {
          def prepareWithHints(serviceHints: Hints, endpointHints: Hints)
            : HttpApp[IO] => HttpApp[IO] =
            _.onError { case e => Kleisli.liftF(IO.consoleForIO.printStackTrace(e)) }
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

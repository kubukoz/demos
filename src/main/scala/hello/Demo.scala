package hello

import smithy4s.http4s.SimpleRestJsonBuilder
import org.http4s.client.Client
import cats.effect.IO
import hello.WeatherService

object Demo {
  val b =
    SimpleRestJsonBuilder(WeatherServiceGen).client(??? : Client[IO])

  b
}

package hello

import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import hello.WeatherService
import cats.effect.IO
import cats.effect.IOApp
import smithy4s.json.Json

@main def demo = println(Json.writePrettyString(GetWeatherOutput("foo")))

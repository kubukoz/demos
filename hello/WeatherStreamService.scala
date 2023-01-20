package hello

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5

trait WeatherStreamServiceGen[F[_, _, _, _, _]] {
  self =>

  def getWeather(other: String): F[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response]

  def transform: Transformation.PartiallyApplied[WeatherStreamServiceGen[F]] = Transformation.of[WeatherStreamServiceGen[F]](this)
}

object WeatherStreamServiceGen extends Service.Mixin[WeatherStreamServiceGen, WeatherStreamServiceOperation] {

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val id: ShapeId = ShapeId("hello", "WeatherStreamService")

  val hints: Hints = Hints.empty

  val endpoints: List[WeatherStreamServiceGen.Endpoint[_, _, _, _, _]] = List(
    GetWeather,
  )

  val version: String = ""

  def endpoint[I, E, O, SI, SO](op: WeatherStreamServiceOperation[I, E, O, SI, SO]) = op.endpoint

  object reified extends WeatherStreamServiceGen[WeatherStreamServiceOperation] {
    def getWeather(other: String) = GetWeather(GetWeatherInput(other))
  }

  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: WeatherStreamServiceGen[P], f: PolyFunction5[P, P1]): WeatherStreamServiceGen[P1] = new Transformed(alg, f)

  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[WeatherStreamServiceOperation, P]): WeatherStreamServiceGen[P] = new Transformed(reified, f)
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: WeatherStreamServiceGen[P], f: PolyFunction5[P, P1]) extends WeatherStreamServiceGen[P1] {
    def getWeather(other: String) = f[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response](alg.getWeather(other))
  }

  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends Transformed[WeatherStreamServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]

  def toPolyFunction[P[_, _, _, _, _]](impl: WeatherStreamServiceGen[P]): PolyFunction5[WeatherStreamServiceOperation, P] = new PolyFunction5[WeatherStreamServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: WeatherStreamServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  case class GetWeather(input: GetWeatherInput) extends WeatherStreamServiceOperation[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response] {
    def run[F[_, _, _, _, _]](impl: WeatherStreamServiceGen[F]): F[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response] = impl.getWeather(input.other)
    def endpoint: (GetWeatherInput, Endpoint[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response]) = (input, GetWeather)
  }
  object GetWeather extends WeatherStreamServiceGen.Endpoint[GetWeatherInput, Nothing, GetWeatherOutput, Request, Response] {
    val id: ShapeId = ShapeId("hello", "GetWeather")
    val input: Schema[GetWeatherInput] = GetWeatherInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[GetWeatherOutput] = GetWeatherOutput.schema.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Request] = StreamingSchema("GetWeatherInput", Request.schema.addHints(smithy.api.Required()))
    val streamedOutput: StreamingSchema[Response] = StreamingSchema("GetWeatherOutput", Response.schema.addHints(smithy.api.Required()))
    val hints: Hints = Hints.empty
    def wrap(input: GetWeatherInput) = GetWeather(input)
  }
}

sealed trait WeatherStreamServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: WeatherStreamServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[WeatherStreamServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

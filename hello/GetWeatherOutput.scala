package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.constant

case class GetWeatherOutput()
object GetWeatherOutput extends ShapeTag.Companion[GetWeatherOutput] {
  val id: ShapeId = ShapeId("hello", "GetWeatherOutput")

  val hints: Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[GetWeatherOutput] = constant(GetWeatherOutput()).withId(id).addHints(hints)
}
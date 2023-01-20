package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class GetWeatherOutput(other: String)
object GetWeatherOutput extends ShapeTag.Companion[GetWeatherOutput] {
  val id: ShapeId = ShapeId("hello", "GetWeatherOutput")

  val hints: Hints = Hints(
    smithy.api.Output(),
  )

  implicit val schema: Schema[GetWeatherOutput] = struct(
    string.required[GetWeatherOutput]("other", _.other).addHints(smithy.api.Required()),
  ){
    GetWeatherOutput.apply
  }.withId(id).addHints(hints)
}
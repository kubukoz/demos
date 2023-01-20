package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class GetWeatherInput(other: String)
object GetWeatherInput extends ShapeTag.Companion[GetWeatherInput] {
  val id: ShapeId = ShapeId("hello", "GetWeatherInput")

  val hints: Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[GetWeatherInput] = struct(
    string.required[GetWeatherInput]("other", _.other).addHints(smithy.api.Required(), smithy.api.HttpQuery("other")),
  ){
    GetWeatherInput.apply
  }.withId(id).addHints(hints)
}
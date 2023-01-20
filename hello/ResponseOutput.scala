package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class ResponseOutput(weather: String)
object ResponseOutput extends ShapeTag.Companion[ResponseOutput] {
  val id: ShapeId = ShapeId("hello", "ResponseOutput")

  val hints: Hints = Hints.empty

  implicit val schema: Schema[ResponseOutput] = struct(
    string.required[ResponseOutput]("weather", _.weather).addHints(smithy.api.Required()),
  ){
    ResponseOutput.apply
  }.withId(id).addHints(hints)
}
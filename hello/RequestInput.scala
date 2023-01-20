package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class RequestInput(city: String)
object RequestInput extends ShapeTag.Companion[RequestInput] {
  val id: ShapeId = ShapeId("hello", "RequestInput")

  val hints: Hints = Hints.empty

  implicit val schema: Schema[RequestInput] = struct(
    string.required[RequestInput]("city", _.city).addHints(smithy.api.Required()),
  ){
    RequestInput.apply
  }.withId(id).addHints(hints)
}
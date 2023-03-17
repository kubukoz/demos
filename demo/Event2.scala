package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class Event2(name: String)
object Event2 extends ShapeTag.Companion[Event2] {
  val id: ShapeId = ShapeId("demo", "Event2")

  val hints: Hints = Hints(
    demo.Event(),
  )

  implicit val schema: Schema[Event2] = struct(
    string.required[Event2]("name", _.name).addHints(smithy.api.Required()),
  ){
    Event2.apply
  }.withId(id).addHints(hints)
}
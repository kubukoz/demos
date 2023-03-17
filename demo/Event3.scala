package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class Event3(name: String)
object Event3 extends ShapeTag.Companion[Event3] {
  val id: ShapeId = ShapeId("demo", "Event3")

  val hints: Hints = Hints(
    demo.Event(),
  )

  implicit val schema: Schema[Event3] = struct(
    string.required[Event3]("name", _.name).addHints(smithy.api.Required()),
  ){
    Event3.apply
  }.withId(id).addHints(hints)
}
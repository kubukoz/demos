package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class Event1(name: String)
object Event1 extends ShapeTag.Companion[Event1] {
  val id: ShapeId = ShapeId("demo", "Event1")

  val hints: Hints = Hints(
    demo.Event(),
  )

  implicit val schema: Schema[Event1] = struct(
    string.required[Event1]("name", _.name).addHints(smithy.api.Required()),
  ){
    Event1.apply
  }.withId(id).addHints(hints)
}
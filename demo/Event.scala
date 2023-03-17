package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.constant

case class Event()
object Event extends ShapeTag.Companion[Event] {
  val id: ShapeId = ShapeId("demo", "event")

  val hints: Hints = Hints(
    smithy.api.Trait(selector = None, structurallyExclusive = None, conflicts = None, breakingChanges = None),
  )

  implicit val schema: Schema[Event] = constant(Event()).withId(id).addHints(hints)
}
package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.constant

case class EventUnion()
object EventUnion extends ShapeTag.Companion[EventUnion] {
  val id: ShapeId = ShapeId("demo", "eventUnion")

  val hints: Hints = Hints.empty

  implicit val schema: Schema[EventUnion] = constant(EventUnion()).withId(id).addHints(hints)
}
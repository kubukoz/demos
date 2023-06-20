package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.struct

case class MyOperationInput(assignments: Option[Assignments] = None)
object MyOperationInput extends ShapeTag.Companion[MyOperationInput] {
  val id: ShapeId = ShapeId("demo", "MyOperationInput")

  val hints: Hints = Hints(
    smithy.api.Input(),
  )

  implicit val schema: Schema[MyOperationInput] = struct(
    Assignments.schema.optional[MyOperationInput]("assignments", _.assignments).addHints(smithy.api.HttpHeader("X-Assignments")),
  ){
    MyOperationInput.apply
  }.withId(id).addHints(hints)
}
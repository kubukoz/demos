package demo

import foo.bar.AssignmentsDecoded
import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.string

object Assignments extends Newtype[AssignmentsDecoded] {
  val id: ShapeId = ShapeId("demo", "Assignments")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[AssignmentsDecoded] = string.refined[AssignmentsDecoded](demo.ProtobufEncoded()).withId(id).addHints(hints)
  implicit val schema: Schema[Assignments] = bijection(underlyingSchema, asBijection)
}
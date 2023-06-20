package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.constant

case class ProtobufEncoded()
object ProtobufEncoded extends ShapeTag.Companion[ProtobufEncoded] {
  val id: ShapeId = ShapeId("demo", "protobufEncoded")

  val hints: Hints = Hints(
    smithy.api.Trait(selector = None, structurallyExclusive = None, conflicts = None, breakingChanges = None),
  )

  implicit val schema: Schema[ProtobufEncoded] = constant(ProtobufEncoded()).withId(id).addHints(hints)
}
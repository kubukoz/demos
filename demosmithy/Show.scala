package demosmithy

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class Show(id: String, _type: EntityType, showField: String)

object Show extends ShapeTag.Companion[Show] {
  val id: ShapeId = ShapeId("demosmithy", "Show")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: String, _type: EntityType, showField: String): Show = Show(id, _type, showField)

  implicit val schema: Schema[Show] = struct(
    string.required[Show]("id", _.id),
    EntityType.schema.required[Show]("type", _._type),
    string.required[Show]("showField", _.showField),
  )(make).withId(id).addHints(hints)
}

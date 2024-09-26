package demosmithy

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class Channel(id: String, _type: EntityType, channelField: String)

object Channel extends ShapeTag.Companion[Channel] {
  val id: ShapeId = ShapeId("demosmithy", "Channel")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: String, _type: EntityType, channelField: String): Channel = Channel(id, _type, channelField)

  implicit val schema: Schema[Channel] = struct(
    string.required[Channel]("id", _.id),
    EntityType.schema.required[Channel]("type", _._type),
    string.required[Channel]("channelField", _.channelField),
  )(make).withId(id).addHints(hints)
}

package demosmithy

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class ChannelLineup(id: String, _type: EntityType, lineupField: String)

object ChannelLineup extends ShapeTag.Companion[ChannelLineup] {
  val id: ShapeId = ShapeId("demosmithy", "ChannelLineup")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: String, _type: EntityType, lineupField: String): ChannelLineup = ChannelLineup(id, _type, lineupField)

  implicit val schema: Schema[ChannelLineup] = struct(
    string.required[ChannelLineup]("id", _.id),
    EntityType.schema.required[ChannelLineup]("type", _._type),
    string.required[ChannelLineup]("lineupField", _.lineupField),
  )(make).withId(id).addHints(hints)
}

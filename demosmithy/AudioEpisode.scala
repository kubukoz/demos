package demosmithy

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class AudioEpisode(id: String, _type: EntityType, audioField: String)

object AudioEpisode extends ShapeTag.Companion[AudioEpisode] {
  val id: ShapeId = ShapeId("demosmithy", "AudioEpisode")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: String, _type: EntityType, audioField: String): AudioEpisode = AudioEpisode(id, _type, audioField)

  implicit val schema: Schema[AudioEpisode] = struct(
    string.required[AudioEpisode]("id", _.id),
    EntityType.schema.required[AudioEpisode]("type", _._type),
    string.required[AudioEpisode]("audioField", _.audioField),
  )(make).withId(id).addHints(hints)
}

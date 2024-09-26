package demosmithy

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class VideoEpisode(id: String, _type: EntityType, videoField: String)

object VideoEpisode extends ShapeTag.Companion[VideoEpisode] {
  val id: ShapeId = ShapeId("demosmithy", "VideoEpisode")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: String, _type: EntityType, videoField: String): VideoEpisode = VideoEpisode(id, _type, videoField)

  implicit val schema: Schema[VideoEpisode] = struct(
    string.required[VideoEpisode]("id", _.id),
    EntityType.schema.required[VideoEpisode]("type", _._type),
    string.required[VideoEpisode]("videoField", _.videoField),
  )(make).withId(id).addHints(hints)
}

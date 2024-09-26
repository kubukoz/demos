package demosmithy

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

sealed abstract class EntityType(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = EntityType
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = EntityType
  @inline final def widen: EntityType = this
}
object EntityType extends Enumeration[EntityType] with ShapeTag.Companion[EntityType] {
  val id: ShapeId = ShapeId("demosmithy", "EntityType")

  val hints: Hints = Hints.empty

  case object CHANNEL_LINEAR extends EntityType("CHANNEL_LINEAR", "CHANNEL_LINEAR", 0, Hints.empty)
  case object CHANNEL_XTRA extends EntityType("CHANNEL_XTRA", "CHANNEL_XTRA", 1, Hints.empty)
  case object EPISODE_AUDIO extends EntityType("EPISODE_AUDIO", "EPISODE_AUDIO", 2, Hints.empty)
  case object EPISODE_VIDEO extends EntityType("EPISODE_VIDEO", "EPISODE_VIDEO", 3, Hints.empty)
  case object LINEUP extends EntityType("LINEUP", "LINEUP", 4, Hints.empty)
  case object SHOW extends EntityType("SHOW", "SHOW", 5, Hints.empty)
  case object SHOW_PODCAST extends EntityType("SHOW_PODCAST", "SHOW_PODCAST", 6, Hints.empty)

  val values: List[EntityType] = List(
    CHANNEL_LINEAR,
    CHANNEL_XTRA,
    EPISODE_AUDIO,
    EPISODE_VIDEO,
    LINEUP,
    SHOW,
    SHOW_PODCAST,
  )
  val tag: EnumTag[EntityType] = EnumTag.ClosedStringEnum
  implicit val schema: Schema[EntityType] = enumeration(tag, values).withId(id).addHints(hints)
}

package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class StreamViewType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: StreamViewType = this
}
object StreamViewType extends Enumeration[StreamViewType] with ShapeTag.Companion[StreamViewType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "StreamViewType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NEW_IMAGE"), name = Some(smithy.api.EnumConstantBodyName("NEW_IMAGE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("OLD_IMAGE"), name = Some(smithy.api.EnumConstantBodyName("OLD_IMAGE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NEW_AND_OLD_IMAGES"), name = Some(smithy.api.EnumConstantBodyName("NEW_AND_OLD_IMAGES")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("KEYS_ONLY"), name = Some(smithy.api.EnumConstantBodyName("KEYS_ONLY")), documentation = None, tags = None, deprecated = None))),
  )

  case object NEW_IMAGE extends StreamViewType("NEW_IMAGE", "NEW_IMAGE", 0)
  case object OLD_IMAGE extends StreamViewType("OLD_IMAGE", "OLD_IMAGE", 1)
  case object NEW_AND_OLD_IMAGES extends StreamViewType("NEW_AND_OLD_IMAGES", "NEW_AND_OLD_IMAGES", 2)
  case object KEYS_ONLY extends StreamViewType("KEYS_ONLY", "KEYS_ONLY", 3)

  val values: List[StreamViewType] = List(
    NEW_IMAGE,
    OLD_IMAGE,
    NEW_AND_OLD_IMAGES,
    KEYS_ONLY,
  )
  implicit val schema: Schema[StreamViewType] = enumeration(values).withId(id).addHints(hints)
}
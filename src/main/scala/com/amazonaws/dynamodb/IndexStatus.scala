package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class IndexStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: IndexStatus = this
}
object IndexStatus extends Enumeration[IndexStatus] with ShapeTag.Companion[IndexStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "IndexStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CREATING"), name = Some(smithy.api.EnumConstantBodyName("CREATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATING"), name = Some(smithy.api.EnumConstantBodyName("UPDATING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DELETING"), name = Some(smithy.api.EnumConstantBodyName("DELETING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ACTIVE"), name = Some(smithy.api.EnumConstantBodyName("ACTIVE")), documentation = None, tags = None, deprecated = None))),
  )

  case object CREATING extends IndexStatus("CREATING", "CREATING", 0)
  case object UPDATING extends IndexStatus("UPDATING", "UPDATING", 1)
  case object DELETING extends IndexStatus("DELETING", "DELETING", 2)
  case object ACTIVE extends IndexStatus("ACTIVE", "ACTIVE", 3)

  val values: List[IndexStatus] = List(
    CREATING,
    UPDATING,
    DELETING,
    ACTIVE,
  )
  implicit val schema: Schema[IndexStatus] = enumeration(values).withId(id).addHints(hints)
}
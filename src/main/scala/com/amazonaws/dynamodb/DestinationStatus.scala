package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class DestinationStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: DestinationStatus = this
}
object DestinationStatus extends Enumeration[DestinationStatus] with ShapeTag.Companion[DestinationStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DestinationStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLING"), name = Some(smithy.api.EnumConstantBodyName("ENABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ACTIVE"), name = Some(smithy.api.EnumConstantBodyName("ACTIVE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLING"), name = Some(smithy.api.EnumConstantBodyName("DISABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLE_FAILED"), name = Some(smithy.api.EnumConstantBodyName("ENABLE_FAILED")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLING extends DestinationStatus("ENABLING", "ENABLING", 0)
  case object ACTIVE extends DestinationStatus("ACTIVE", "ACTIVE", 1)
  case object DISABLING extends DestinationStatus("DISABLING", "DISABLING", 2)
  case object DISABLED extends DestinationStatus("DISABLED", "DISABLED", 3)
  case object ENABLE_FAILED extends DestinationStatus("ENABLE_FAILED", "ENABLE_FAILED", 4)

  val values: List[DestinationStatus] = List(
    ENABLING,
    ACTIVE,
    DISABLING,
    DISABLED,
    ENABLE_FAILED,
  )
  implicit val schema: Schema[DestinationStatus] = enumeration(values).withId(id).addHints(hints)
}
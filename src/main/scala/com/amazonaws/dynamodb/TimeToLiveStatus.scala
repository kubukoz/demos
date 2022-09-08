package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class TimeToLiveStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: TimeToLiveStatus = this
}
object TimeToLiveStatus extends Enumeration[TimeToLiveStatus] with ShapeTag.Companion[TimeToLiveStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TimeToLiveStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLING"), name = Some(smithy.api.EnumConstantBodyName("ENABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLING"), name = Some(smithy.api.EnumConstantBodyName("DISABLING")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLED"), name = Some(smithy.api.EnumConstantBodyName("ENABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLING extends TimeToLiveStatus("ENABLING", "ENABLING", 0)
  case object DISABLING extends TimeToLiveStatus("DISABLING", "DISABLING", 1)
  case object ENABLED extends TimeToLiveStatus("ENABLED", "ENABLED", 2)
  case object DISABLED extends TimeToLiveStatus("DISABLED", "DISABLED", 3)

  val values: List[TimeToLiveStatus] = List(
    ENABLING,
    DISABLING,
    ENABLED,
    DISABLED,
  )
  implicit val schema: Schema[TimeToLiveStatus] = enumeration(values).withId(id).addHints(hints)
}
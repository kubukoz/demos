package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class PointInTimeRecoveryStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: PointInTimeRecoveryStatus = this
}
object PointInTimeRecoveryStatus extends Enumeration[PointInTimeRecoveryStatus] with ShapeTag.Companion[PointInTimeRecoveryStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PointInTimeRecoveryStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLED"), name = Some(smithy.api.EnumConstantBodyName("ENABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLED extends PointInTimeRecoveryStatus("ENABLED", "ENABLED", 0)
  case object DISABLED extends PointInTimeRecoveryStatus("DISABLED", "DISABLED", 1)

  val values: List[PointInTimeRecoveryStatus] = List(
    ENABLED,
    DISABLED,
  )
  implicit val schema: Schema[PointInTimeRecoveryStatus] = enumeration(values).withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ContinuousBackupsStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ContinuousBackupsStatus = this
}
object ContinuousBackupsStatus extends Enumeration[ContinuousBackupsStatus] with ShapeTag.Companion[ContinuousBackupsStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContinuousBackupsStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ENABLED"), name = Some(smithy.api.EnumConstantBodyName("ENABLED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DISABLED"), name = Some(smithy.api.EnumConstantBodyName("DISABLED")), documentation = None, tags = None, deprecated = None))),
  )

  case object ENABLED extends ContinuousBackupsStatus("ENABLED", "ENABLED", 0)
  case object DISABLED extends ContinuousBackupsStatus("DISABLED", "DISABLED", 1)

  val values: List[ContinuousBackupsStatus] = List(
    ENABLED,
    DISABLED,
  )
  implicit val schema: Schema[ContinuousBackupsStatus] = enumeration(values).withId(id).addHints(hints)
}
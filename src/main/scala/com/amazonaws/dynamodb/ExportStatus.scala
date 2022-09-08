package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ExportStatus(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ExportStatus = this
}
object ExportStatus extends Enumeration[ExportStatus] with ShapeTag.Companion[ExportStatus] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportStatus")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("IN_PROGRESS"), name = Some(smithy.api.EnumConstantBodyName("IN_PROGRESS")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("COMPLETED"), name = Some(smithy.api.EnumConstantBodyName("COMPLETED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("FAILED"), name = Some(smithy.api.EnumConstantBodyName("FAILED")), documentation = None, tags = None, deprecated = None))),
  )

  case object IN_PROGRESS extends ExportStatus("IN_PROGRESS", "IN_PROGRESS", 0)
  case object COMPLETED extends ExportStatus("COMPLETED", "COMPLETED", 1)
  case object FAILED extends ExportStatus("FAILED", "FAILED", 2)

  val values: List[ExportStatus] = List(
    IN_PROGRESS,
    COMPLETED,
    FAILED,
  )
  implicit val schema: Schema[ExportStatus] = enumeration(values).withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ExportFormat(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ExportFormat = this
}
object ExportFormat extends Enumeration[ExportFormat] with ShapeTag.Companion[ExportFormat] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportFormat")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DYNAMODB_JSON"), name = Some(smithy.api.EnumConstantBodyName("DYNAMODB_JSON")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ION"), name = Some(smithy.api.EnumConstantBodyName("ION")), documentation = None, tags = None, deprecated = None))),
  )

  case object DYNAMODB_JSON extends ExportFormat("DYNAMODB_JSON", "DYNAMODB_JSON", 0)
  case object ION extends ExportFormat("ION", "ION", 1)

  val values: List[ExportFormat] = List(
    DYNAMODB_JSON,
    ION,
  )
  implicit val schema: Schema[ExportFormat] = enumeration(values).withId(id).addHints(hints)
}
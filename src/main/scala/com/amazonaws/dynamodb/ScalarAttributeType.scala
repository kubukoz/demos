package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ScalarAttributeType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ScalarAttributeType = this
}
object ScalarAttributeType extends Enumeration[ScalarAttributeType] with ShapeTag.Companion[ScalarAttributeType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ScalarAttributeType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("S"), name = Some(smithy.api.EnumConstantBodyName("S")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("N"), name = Some(smithy.api.EnumConstantBodyName("N")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("B"), name = Some(smithy.api.EnumConstantBodyName("B")), documentation = None, tags = None, deprecated = None))),
  )

  case object S extends ScalarAttributeType("S", "S", 0)
  case object N extends ScalarAttributeType("N", "N", 1)
  case object B extends ScalarAttributeType("B", "B", 2)

  val values: List[ScalarAttributeType] = List(
    S,
    N,
    B,
  )
  implicit val schema: Schema[ScalarAttributeType] = enumeration(values).withId(id).addHints(hints)
}
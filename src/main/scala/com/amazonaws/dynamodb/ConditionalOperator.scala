package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ConditionalOperator(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ConditionalOperator = this
}
object ConditionalOperator extends Enumeration[ConditionalOperator] with ShapeTag.Companion[ConditionalOperator] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ConditionalOperator")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AND"), name = Some(smithy.api.EnumConstantBodyName("AND")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("OR"), name = Some(smithy.api.EnumConstantBodyName("OR")), documentation = None, tags = None, deprecated = None))),
  )

  case object AND extends ConditionalOperator("AND", "AND", 0)
  case object OR extends ConditionalOperator("OR", "OR", 1)

  val values: List[ConditionalOperator] = List(
    AND,
    OR,
  )
  implicit val schema: Schema[ConditionalOperator] = enumeration(values).withId(id).addHints(hints)
}
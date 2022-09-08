package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ReturnValuesOnConditionCheckFailure(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ReturnValuesOnConditionCheckFailure = this
}
object ReturnValuesOnConditionCheckFailure extends Enumeration[ReturnValuesOnConditionCheckFailure] with ShapeTag.Companion[ReturnValuesOnConditionCheckFailure] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReturnValuesOnConditionCheckFailure")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL_OLD"), name = Some(smithy.api.EnumConstantBodyName("ALL_OLD")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NONE"), name = Some(smithy.api.EnumConstantBodyName("NONE")), documentation = None, tags = None, deprecated = None))),
  )

  case object ALL_OLD extends ReturnValuesOnConditionCheckFailure("ALL_OLD", "ALL_OLD", 0)
  case object NONE extends ReturnValuesOnConditionCheckFailure("NONE", "NONE", 1)

  val values: List[ReturnValuesOnConditionCheckFailure] = List(
    ALL_OLD,
    NONE,
  )
  implicit val schema: Schema[ReturnValuesOnConditionCheckFailure] = enumeration(values).withId(id).addHints(hints)
}
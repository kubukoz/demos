package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ReturnValue(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ReturnValue = this
}
object ReturnValue extends Enumeration[ReturnValue] with ShapeTag.Companion[ReturnValue] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReturnValue")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NONE"), name = Some(smithy.api.EnumConstantBodyName("NONE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL_OLD"), name = Some(smithy.api.EnumConstantBodyName("ALL_OLD")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATED_OLD"), name = Some(smithy.api.EnumConstantBodyName("UPDATED_OLD")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL_NEW"), name = Some(smithy.api.EnumConstantBodyName("ALL_NEW")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("UPDATED_NEW"), name = Some(smithy.api.EnumConstantBodyName("UPDATED_NEW")), documentation = None, tags = None, deprecated = None))),
  )

  case object NONE extends ReturnValue("NONE", "NONE", 0)
  case object ALL_OLD extends ReturnValue("ALL_OLD", "ALL_OLD", 1)
  case object UPDATED_OLD extends ReturnValue("UPDATED_OLD", "UPDATED_OLD", 2)
  case object ALL_NEW extends ReturnValue("ALL_NEW", "ALL_NEW", 3)
  case object UPDATED_NEW extends ReturnValue("UPDATED_NEW", "UPDATED_NEW", 4)

  val values: List[ReturnValue] = List(
    NONE,
    ALL_OLD,
    UPDATED_OLD,
    ALL_NEW,
    UPDATED_NEW,
  )
  implicit val schema: Schema[ReturnValue] = enumeration(values).withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class Select(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: Select = this
}
object Select extends Enumeration[Select] with ShapeTag.Companion[Select] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Select")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL_ATTRIBUTES"), name = Some(smithy.api.EnumConstantBodyName("ALL_ATTRIBUTES")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL_PROJECTED_ATTRIBUTES"), name = Some(smithy.api.EnumConstantBodyName("ALL_PROJECTED_ATTRIBUTES")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("SPECIFIC_ATTRIBUTES"), name = Some(smithy.api.EnumConstantBodyName("SPECIFIC_ATTRIBUTES")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("COUNT"), name = Some(smithy.api.EnumConstantBodyName("COUNT")), documentation = None, tags = None, deprecated = None))),
  )

  case object ALL_ATTRIBUTES extends Select("ALL_ATTRIBUTES", "ALL_ATTRIBUTES", 0)
  case object ALL_PROJECTED_ATTRIBUTES extends Select("ALL_PROJECTED_ATTRIBUTES", "ALL_PROJECTED_ATTRIBUTES", 1)
  case object SPECIFIC_ATTRIBUTES extends Select("SPECIFIC_ATTRIBUTES", "SPECIFIC_ATTRIBUTES", 2)
  case object COUNT extends Select("COUNT", "COUNT", 3)

  val values: List[Select] = List(
    ALL_ATTRIBUTES,
    ALL_PROJECTED_ATTRIBUTES,
    SPECIFIC_ATTRIBUTES,
    COUNT,
  )
  implicit val schema: Schema[Select] = enumeration(values).withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ComparisonOperator(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ComparisonOperator = this
}
object ComparisonOperator extends Enumeration[ComparisonOperator] with ShapeTag.Companion[ComparisonOperator] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ComparisonOperator")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("EQ"), name = Some(smithy.api.EnumConstantBodyName("EQ")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NE"), name = Some(smithy.api.EnumConstantBodyName("NE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("IN"), name = Some(smithy.api.EnumConstantBodyName("IN")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("LE"), name = Some(smithy.api.EnumConstantBodyName("LE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("LT"), name = Some(smithy.api.EnumConstantBodyName("LT")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("GE"), name = Some(smithy.api.EnumConstantBodyName("GE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("GT"), name = Some(smithy.api.EnumConstantBodyName("GT")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("BETWEEN"), name = Some(smithy.api.EnumConstantBodyName("BETWEEN")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NOT_NULL"), name = Some(smithy.api.EnumConstantBodyName("NOT_NULL")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NULL"), name = Some(smithy.api.EnumConstantBodyName("NULL")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("CONTAINS"), name = Some(smithy.api.EnumConstantBodyName("CONTAINS")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NOT_CONTAINS"), name = Some(smithy.api.EnumConstantBodyName("NOT_CONTAINS")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("BEGINS_WITH"), name = Some(smithy.api.EnumConstantBodyName("BEGINS_WITH")), documentation = None, tags = None, deprecated = None))),
  )

  case object EQ extends ComparisonOperator("EQ", "EQ", 0)
  case object NE extends ComparisonOperator("NE", "NE", 1)
  case object IN extends ComparisonOperator("IN", "IN", 2)
  case object LE extends ComparisonOperator("LE", "LE", 3)
  case object LT extends ComparisonOperator("LT", "LT", 4)
  case object GE extends ComparisonOperator("GE", "GE", 5)
  case object GT extends ComparisonOperator("GT", "GT", 6)
  case object BETWEEN extends ComparisonOperator("BETWEEN", "BETWEEN", 7)
  case object NOT_NULL extends ComparisonOperator("NOT_NULL", "NOT_NULL", 8)
  case object NULL extends ComparisonOperator("NULL", "NULL", 9)
  case object CONTAINS extends ComparisonOperator("CONTAINS", "CONTAINS", 10)
  case object NOT_CONTAINS extends ComparisonOperator("NOT_CONTAINS", "NOT_CONTAINS", 11)
  case object BEGINS_WITH extends ComparisonOperator("BEGINS_WITH", "BEGINS_WITH", 12)

  val values: List[ComparisonOperator] = List(
    EQ,
    NE,
    IN,
    LE,
    LT,
    GE,
    GT,
    BETWEEN,
    NOT_NULL,
    NULL,
    CONTAINS,
    NOT_CONTAINS,
    BEGINS_WITH,
  )
  implicit val schema: Schema[ComparisonOperator] = enumeration(values).withId(id).addHints(hints)
}
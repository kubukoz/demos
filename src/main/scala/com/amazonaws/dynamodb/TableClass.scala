package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class TableClass(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: TableClass = this
}
object TableClass extends Enumeration[TableClass] with ShapeTag.Companion[TableClass] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableClass")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("STANDARD"), name = Some(smithy.api.EnumConstantBodyName("STANDARD")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("STANDARD_INFREQUENT_ACCESS"), name = Some(smithy.api.EnumConstantBodyName("STANDARD_INFREQUENT_ACCESS")), documentation = None, tags = None, deprecated = None))),
  )

  case object STANDARD extends TableClass("STANDARD", "STANDARD", 0)
  case object STANDARD_INFREQUENT_ACCESS extends TableClass("STANDARD_INFREQUENT_ACCESS", "STANDARD_INFREQUENT_ACCESS", 1)

  val values: List[TableClass] = List(
    STANDARD,
    STANDARD_INFREQUENT_ACCESS,
  )
  implicit val schema: Schema[TableClass] = enumeration(values).withId(id).addHints(hints)
}
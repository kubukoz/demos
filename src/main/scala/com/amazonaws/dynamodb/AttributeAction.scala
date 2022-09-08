package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class AttributeAction(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: AttributeAction = this
}
object AttributeAction extends Enumeration[AttributeAction] with ShapeTag.Companion[AttributeAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeAction")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ADD"), name = Some(smithy.api.EnumConstantBodyName("ADD")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("PUT"), name = Some(smithy.api.EnumConstantBodyName("PUT")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("DELETE"), name = Some(smithy.api.EnumConstantBodyName("DELETE")), documentation = None, tags = None, deprecated = None))),
  )

  case object ADD extends AttributeAction("ADD", "ADD", 0)
  case object PUT extends AttributeAction("PUT", "PUT", 1)
  case object DELETE extends AttributeAction("DELETE", "DELETE", 2)

  val values: List[AttributeAction] = List(
    ADD,
    PUT,
    DELETE,
  )
  implicit val schema: Schema[AttributeAction] = enumeration(values).withId(id).addHints(hints)
}
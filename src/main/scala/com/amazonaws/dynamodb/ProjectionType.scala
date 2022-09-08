package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ProjectionType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ProjectionType = this
}
object ProjectionType extends Enumeration[ProjectionType] with ShapeTag.Companion[ProjectionType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ProjectionType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("ALL"), name = Some(smithy.api.EnumConstantBodyName("ALL")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("KEYS_ONLY"), name = Some(smithy.api.EnumConstantBodyName("KEYS_ONLY")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("INCLUDE"), name = Some(smithy.api.EnumConstantBodyName("INCLUDE")), documentation = None, tags = None, deprecated = None))),
  )

  case object ALL extends ProjectionType("ALL", "ALL", 0)
  case object KEYS_ONLY extends ProjectionType("KEYS_ONLY", "KEYS_ONLY", 1)
  case object INCLUDE extends ProjectionType("INCLUDE", "INCLUDE", 2)

  val values: List[ProjectionType] = List(
    ALL,
    KEYS_ONLY,
    INCLUDE,
  )
  implicit val schema: Schema[ProjectionType] = enumeration(values).withId(id).addHints(hints)
}
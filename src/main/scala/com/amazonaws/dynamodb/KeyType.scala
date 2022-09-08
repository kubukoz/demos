package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class KeyType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: KeyType = this
}
object KeyType extends Enumeration[KeyType] with ShapeTag.Companion[KeyType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KeyType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("HASH"), name = Some(smithy.api.EnumConstantBodyName("HASH")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("RANGE"), name = Some(smithy.api.EnumConstantBodyName("RANGE")), documentation = None, tags = None, deprecated = None))),
  )

  case object HASH extends KeyType("HASH", "HASH", 0)
  case object RANGE extends KeyType("RANGE", "RANGE", 1)

  val values: List[KeyType] = List(
    HASH,
    RANGE,
  )
  implicit val schema: Schema[KeyType] = enumeration(values).withId(id).addHints(hints)
}
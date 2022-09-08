package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class SSEType(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: SSEType = this
}
object SSEType extends Enumeration[SSEType] with ShapeTag.Companion[SSEType] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SSEType")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AES256"), name = Some(smithy.api.EnumConstantBodyName("AES256")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("KMS"), name = Some(smithy.api.EnumConstantBodyName("KMS")), documentation = None, tags = None, deprecated = None))),
  )

  case object AES256 extends SSEType("AES256", "AES256", 0)
  case object KMS extends SSEType("KMS", "KMS", 1)

  val values: List[SSEType] = List(
    AES256,
    KMS,
  )
  implicit val schema: Schema[SSEType] = enumeration(values).withId(id).addHints(hints)
}
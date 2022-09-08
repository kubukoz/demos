package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class S3SseAlgorithm(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: S3SseAlgorithm = this
}
object S3SseAlgorithm extends Enumeration[S3SseAlgorithm] with ShapeTag.Companion[S3SseAlgorithm] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "S3SseAlgorithm")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("AES256"), name = Some(smithy.api.EnumConstantBodyName("AES256")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("KMS"), name = Some(smithy.api.EnumConstantBodyName("KMS")), documentation = None, tags = None, deprecated = None))),
  )

  case object AES256 extends S3SseAlgorithm("AES256", "AES256", 0)
  case object KMS extends S3SseAlgorithm("KMS", "KMS", 1)

  val values: List[S3SseAlgorithm] = List(
    AES256,
    KMS,
  )
  implicit val schema: Schema[S3SseAlgorithm] = enumeration(values).withId(id).addHints(hints)
}
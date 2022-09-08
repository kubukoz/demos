package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ReturnItemCollectionMetrics(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: ReturnItemCollectionMetrics = this
}
object ReturnItemCollectionMetrics extends Enumeration[ReturnItemCollectionMetrics] with ShapeTag.Companion[ReturnItemCollectionMetrics] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReturnItemCollectionMetrics")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("SIZE"), name = Some(smithy.api.EnumConstantBodyName("SIZE")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("NONE"), name = Some(smithy.api.EnumConstantBodyName("NONE")), documentation = None, tags = None, deprecated = None))),
  )

  case object SIZE extends ReturnItemCollectionMetrics("SIZE", "SIZE", 0)
  case object NONE extends ReturnItemCollectionMetrics("NONE", "NONE", 1)

  val values: List[ReturnItemCollectionMetrics] = List(
    SIZE,
    NONE,
  )
  implicit val schema: Schema[ReturnItemCollectionMetrics] = enumeration(values).withId(id).addHints(hints)
}
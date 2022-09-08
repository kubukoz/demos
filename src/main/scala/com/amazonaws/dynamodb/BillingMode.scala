package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.enumeration

sealed abstract class BillingMode(_value: String, _name: String, _intValue: Int) extends Enumeration.Value {
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = Hints.empty
  @inline final def widen: BillingMode = this
}
object BillingMode extends Enumeration[BillingMode] with ShapeTag.Companion[BillingMode] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BillingMode")

  val hints : Hints = Hints(
    smithy.api.Enum(List(smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("PROVISIONED"), name = Some(smithy.api.EnumConstantBodyName("PROVISIONED")), documentation = None, tags = None, deprecated = None), smithy.api.EnumDefinition(value = smithy.api.NonEmptyString("PAY_PER_REQUEST"), name = Some(smithy.api.EnumConstantBodyName("PAY_PER_REQUEST")), documentation = None, tags = None, deprecated = None))),
  )

  case object PROVISIONED extends BillingMode("PROVISIONED", "PROVISIONED", 0)
  case object PAY_PER_REQUEST extends BillingMode("PAY_PER_REQUEST", "PAY_PER_REQUEST", 1)

  val values: List[BillingMode] = List(
    PROVISIONED,
    PAY_PER_REQUEST,
  )
  implicit val schema: Schema[BillingMode] = enumeration(values).withId(id).addHints(hints)
}
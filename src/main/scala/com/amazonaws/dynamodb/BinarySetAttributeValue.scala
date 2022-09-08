package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BinarySetAttributeValue extends Newtype[List[BinaryAttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BinarySetAttributeValue")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[BinaryAttributeValue]] = list(BinaryAttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[BinarySetAttributeValue] = bijection(underlyingSchema, asBijection)
}
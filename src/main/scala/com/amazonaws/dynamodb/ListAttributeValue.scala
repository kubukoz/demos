package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ListAttributeValue extends Newtype[List[AttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListAttributeValue")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[AttributeValue]] = list(AttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ListAttributeValue] = bijection(underlyingSchema, asBijection)
}
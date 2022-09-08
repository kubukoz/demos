package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ExpectedAttributeMap extends Newtype[Map[AttributeName,ExpectedAttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExpectedAttributeMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[AttributeName,ExpectedAttributeValue]] = map(AttributeName.schema, ExpectedAttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ExpectedAttributeMap] = bijection(underlyingSchema, asBijection)
}
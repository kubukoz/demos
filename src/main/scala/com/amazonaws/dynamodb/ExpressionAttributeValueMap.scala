package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ExpressionAttributeValueMap extends Newtype[Map[ExpressionAttributeValueVariable,AttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExpressionAttributeValueMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[ExpressionAttributeValueVariable,AttributeValue]] = map(ExpressionAttributeValueVariable.schema, AttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ExpressionAttributeValueMap] = bijection(underlyingSchema, asBijection)
}
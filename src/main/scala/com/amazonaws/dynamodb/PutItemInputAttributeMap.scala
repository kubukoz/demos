package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object PutItemInputAttributeMap extends Newtype[Map[AttributeName,AttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PutItemInputAttributeMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[AttributeName,AttributeValue]] = map(AttributeName.schema, AttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[PutItemInputAttributeMap] = bijection(underlyingSchema, asBijection)
}
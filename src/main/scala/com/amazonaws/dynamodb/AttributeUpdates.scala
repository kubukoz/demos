package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object AttributeUpdates extends Newtype[Map[AttributeName,AttributeValueUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeUpdates")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[AttributeName,AttributeValueUpdate]] = map(AttributeName.schema, AttributeValueUpdate.schema).withId(id).addHints(hints)
  implicit val schema : Schema[AttributeUpdates] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ExpressionAttributeNameMap extends Newtype[Map[ExpressionAttributeNameVariable,AttributeName]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExpressionAttributeNameMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[ExpressionAttributeNameVariable,AttributeName]] = map(ExpressionAttributeNameVariable.schema, AttributeName.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ExpressionAttributeNameMap] = bijection(underlyingSchema, asBijection)
}
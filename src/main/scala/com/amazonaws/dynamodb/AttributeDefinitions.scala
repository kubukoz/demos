package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object AttributeDefinitions extends Newtype[List[AttributeDefinition]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeDefinitions")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[AttributeDefinition]] = list(AttributeDefinition.schema).withId(id).addHints(hints)
  implicit val schema : Schema[AttributeDefinitions] = bijection(underlyingSchema, asBijection)
}
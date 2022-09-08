package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object KeySchema extends Newtype[List[KeySchemaElement]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KeySchema")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[KeySchemaElement]] = list(KeySchemaElement.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(2)))
  implicit val schema : Schema[KeySchema] = bijection(underlyingSchema, asBijection)
}
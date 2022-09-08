package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object LocalSecondaryIndexes extends Newtype[List[LocalSecondaryIndexInfo]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndexes")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[LocalSecondaryIndexInfo]] = list(LocalSecondaryIndexInfo.schema).withId(id).addHints(hints)
  implicit val schema : Schema[LocalSecondaryIndexes] = bijection(underlyingSchema, asBijection)
}
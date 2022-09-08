package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalSecondaryIndexes extends Newtype[List[GlobalSecondaryIndexInfo]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexes")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[GlobalSecondaryIndexInfo]] = list(GlobalSecondaryIndexInfo.schema).withId(id).addHints(hints)
  implicit val schema : Schema[GlobalSecondaryIndexes] = bijection(underlyingSchema, asBijection)
}
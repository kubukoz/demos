package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalSecondaryIndexUpdateList extends Newtype[List[GlobalSecondaryIndexUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexUpdateList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[GlobalSecondaryIndexUpdate]] = list(GlobalSecondaryIndexUpdate.schema).withId(id).addHints(hints)
  implicit val schema : Schema[GlobalSecondaryIndexUpdateList] = bijection(underlyingSchema, asBijection)
}
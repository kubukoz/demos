package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalSecondaryIndexList extends Newtype[List[GlobalSecondaryIndex]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[GlobalSecondaryIndex]] = list(GlobalSecondaryIndex.schema).withId(id).addHints(hints)
  implicit val schema : Schema[GlobalSecondaryIndexList] = bijection(underlyingSchema, asBijection)
}
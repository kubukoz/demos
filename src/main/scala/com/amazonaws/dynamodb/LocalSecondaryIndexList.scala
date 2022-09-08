package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object LocalSecondaryIndexList extends Newtype[List[LocalSecondaryIndex]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndexList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[LocalSecondaryIndex]] = list(LocalSecondaryIndex.schema).withId(id).addHints(hints)
  implicit val schema : Schema[LocalSecondaryIndexList] = bijection(underlyingSchema, asBijection)
}
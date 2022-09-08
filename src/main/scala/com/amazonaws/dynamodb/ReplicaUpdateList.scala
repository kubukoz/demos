package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaUpdateList extends Newtype[List[ReplicaUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaUpdateList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaUpdate]] = list(ReplicaUpdate.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaUpdateList] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaGlobalSecondaryIndexAutoScalingUpdateList extends Newtype[List[ReplicaGlobalSecondaryIndexAutoScalingUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexAutoScalingUpdateList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaGlobalSecondaryIndexAutoScalingUpdate]] = list(ReplicaGlobalSecondaryIndexAutoScalingUpdate.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaGlobalSecondaryIndexAutoScalingUpdateList] = bijection(underlyingSchema, asBijection)
}
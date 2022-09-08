package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaAutoScalingUpdateList extends Newtype[List[ReplicaAutoScalingUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaAutoScalingUpdateList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[ReplicaAutoScalingUpdate]] = list(ReplicaAutoScalingUpdate.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = None))
  implicit val schema : Schema[ReplicaAutoScalingUpdateList] = bijection(underlyingSchema, asBijection)
}
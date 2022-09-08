package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaGlobalSecondaryIndexAutoScalingDescriptionList extends Newtype[List[ReplicaGlobalSecondaryIndexAutoScalingDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexAutoScalingDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaGlobalSecondaryIndexAutoScalingDescription]] = list(ReplicaGlobalSecondaryIndexAutoScalingDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaGlobalSecondaryIndexAutoScalingDescriptionList] = bijection(underlyingSchema, asBijection)
}
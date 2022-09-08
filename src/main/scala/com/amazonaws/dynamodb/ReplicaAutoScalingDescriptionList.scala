package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaAutoScalingDescriptionList extends Newtype[List[ReplicaAutoScalingDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaAutoScalingDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaAutoScalingDescription]] = list(ReplicaAutoScalingDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaAutoScalingDescriptionList] = bijection(underlyingSchema, asBijection)
}
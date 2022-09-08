package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaGlobalSecondaryIndexDescriptionList extends Newtype[List[ReplicaGlobalSecondaryIndexDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaGlobalSecondaryIndexDescription]] = list(ReplicaGlobalSecondaryIndexDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaGlobalSecondaryIndexDescriptionList] = bijection(underlyingSchema, asBijection)
}
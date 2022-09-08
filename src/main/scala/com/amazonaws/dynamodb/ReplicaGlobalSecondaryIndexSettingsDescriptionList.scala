package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaGlobalSecondaryIndexSettingsDescriptionList extends Newtype[List[ReplicaGlobalSecondaryIndexSettingsDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexSettingsDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaGlobalSecondaryIndexSettingsDescription]] = list(ReplicaGlobalSecondaryIndexSettingsDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaGlobalSecondaryIndexSettingsDescriptionList] = bijection(underlyingSchema, asBijection)
}
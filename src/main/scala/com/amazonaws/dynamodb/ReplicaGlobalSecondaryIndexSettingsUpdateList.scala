package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaGlobalSecondaryIndexSettingsUpdateList extends Newtype[List[ReplicaGlobalSecondaryIndexSettingsUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexSettingsUpdateList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[ReplicaGlobalSecondaryIndexSettingsUpdate]] = list(ReplicaGlobalSecondaryIndexSettingsUpdate.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(20)))
  implicit val schema : Schema[ReplicaGlobalSecondaryIndexSettingsUpdateList] = bijection(underlyingSchema, asBijection)
}
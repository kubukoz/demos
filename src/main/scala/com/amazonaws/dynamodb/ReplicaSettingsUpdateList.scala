package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaSettingsUpdateList extends Newtype[List[ReplicaSettingsUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaSettingsUpdateList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[ReplicaSettingsUpdate]] = list(ReplicaSettingsUpdate.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(50)))
  implicit val schema : Schema[ReplicaSettingsUpdateList] = bijection(underlyingSchema, asBijection)
}
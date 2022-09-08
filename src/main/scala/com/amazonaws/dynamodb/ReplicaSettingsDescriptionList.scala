package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaSettingsDescriptionList extends Newtype[List[ReplicaSettingsDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaSettingsDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaSettingsDescription]] = list(ReplicaSettingsDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaSettingsDescriptionList] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ReplicaDescriptionList extends Newtype[List[ReplicaDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ReplicaDescription]] = list(ReplicaDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ReplicaDescriptionList] = bijection(underlyingSchema, asBijection)
}
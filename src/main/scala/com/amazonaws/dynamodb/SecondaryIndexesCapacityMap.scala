package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object SecondaryIndexesCapacityMap extends Newtype[Map[IndexName,Capacity]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SecondaryIndexesCapacityMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[IndexName,Capacity]] = map(IndexName.schema, Capacity.schema).withId(id).addHints(hints)
  implicit val schema : Schema[SecondaryIndexesCapacityMap] = bijection(underlyingSchema, asBijection)
}
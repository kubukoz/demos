package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ItemCollectionMetricsMultiple extends Newtype[List[ItemCollectionMetrics]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionMetricsMultiple")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ItemCollectionMetrics]] = list(ItemCollectionMetrics.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ItemCollectionMetricsMultiple] = bijection(underlyingSchema, asBijection)
}
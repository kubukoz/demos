package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ItemCollectionSizeEstimateRange extends Newtype[List[ItemCollectionSizeEstimateBound]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionSizeEstimateRange")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ItemCollectionSizeEstimateBound]] = list(ItemCollectionSizeEstimateBound.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ItemCollectionSizeEstimateRange] = bijection(underlyingSchema, asBijection)
}
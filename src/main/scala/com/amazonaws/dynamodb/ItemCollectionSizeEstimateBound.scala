package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.double
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ItemCollectionSizeEstimateBound extends Newtype[Double] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionSizeEstimateBound")
  val hints : Hints = Hints(
    smithy.api.Box(),
  )
  val underlyingSchema : Schema[Double] = double.withId(id).addHints(hints)
  implicit val schema : Schema[ItemCollectionSizeEstimateBound] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ConsumedCapacityMultiple extends Newtype[List[ConsumedCapacity]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ConsumedCapacityMultiple")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ConsumedCapacity]] = list(ConsumedCapacity.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ConsumedCapacityMultiple] = bijection(underlyingSchema, asBijection)
}
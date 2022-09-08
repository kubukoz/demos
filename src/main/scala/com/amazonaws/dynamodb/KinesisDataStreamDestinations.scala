package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object KinesisDataStreamDestinations extends Newtype[List[KinesisDataStreamDestination]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KinesisDataStreamDestinations")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[KinesisDataStreamDestination]] = list(KinesisDataStreamDestination.schema).withId(id).addHints(hints)
  implicit val schema : Schema[KinesisDataStreamDestinations] = bijection(underlyingSchema, asBijection)
}
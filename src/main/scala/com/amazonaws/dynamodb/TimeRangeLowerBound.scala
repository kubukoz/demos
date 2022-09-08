package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Timestamp
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.timestamp
import smithy4s.Newtype

object TimeRangeLowerBound extends Newtype[Timestamp] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TimeRangeLowerBound")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Timestamp] = timestamp.withId(id).addHints(hints)
  implicit val schema : Schema[TimeRangeLowerBound] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object WriteRequests extends Newtype[List[WriteRequest]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "WriteRequests")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[WriteRequest]] = list(WriteRequest.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(25)))
  implicit val schema : Schema[WriteRequests] = bijection(underlyingSchema, asBijection)
}
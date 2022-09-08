package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object PartiQLBatchResponse extends Newtype[List[BatchStatementResponse]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PartiQLBatchResponse")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[BatchStatementResponse]] = list(BatchStatementResponse.schema).withId(id).addHints(hints)
  implicit val schema : Schema[PartiQLBatchResponse] = bijection(underlyingSchema, asBijection)
}
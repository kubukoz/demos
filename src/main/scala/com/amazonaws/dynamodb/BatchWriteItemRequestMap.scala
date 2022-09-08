package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BatchWriteItemRequestMap extends Newtype[Map[TableName,List[WriteRequest]]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchWriteItemRequestMap")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[Map[TableName,List[WriteRequest]]] = map(TableName.schema, WriteRequests.underlyingSchema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(25)))
  implicit val schema : Schema[BatchWriteItemRequestMap] = bijection(underlyingSchema, asBijection)
}
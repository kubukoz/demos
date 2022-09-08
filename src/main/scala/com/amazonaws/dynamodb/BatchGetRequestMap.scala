package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BatchGetRequestMap extends Newtype[Map[TableName,KeysAndAttributes]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchGetRequestMap")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[Map[TableName,KeysAndAttributes]] = map(TableName.schema, KeysAndAttributes.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(100)))
  implicit val schema : Schema[BatchGetRequestMap] = bijection(underlyingSchema, asBijection)
}
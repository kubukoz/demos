package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BatchGetResponseMap extends Newtype[Map[TableName,List[Map[AttributeName,AttributeValue]]]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchGetResponseMap")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[TableName,List[Map[AttributeName,AttributeValue]]]] = map(TableName.schema, ItemList.underlyingSchema).withId(id).addHints(hints)
  implicit val schema : Schema[BatchGetResponseMap] = bijection(underlyingSchema, asBijection)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ItemCollectionMetricsPerTable extends Newtype[Map[TableName,List[ItemCollectionMetrics]]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionMetricsPerTable")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[Map[TableName,List[ItemCollectionMetrics]]] = map(TableName.schema, ItemCollectionMetricsMultiple.underlyingSchema).withId(id).addHints(hints)
  implicit val schema : Schema[ItemCollectionMetricsPerTable] = bijection(underlyingSchema, asBijection)
}
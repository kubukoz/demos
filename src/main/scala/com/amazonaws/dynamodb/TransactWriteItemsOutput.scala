package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactWriteItemsOutput(consumedCapacity: Option[List[ConsumedCapacity]] = None, itemCollectionMetrics: Option[Map[TableName,List[ItemCollectionMetrics]]] = None)
object TransactWriteItemsOutput extends ShapeTag.Companion[TransactWriteItemsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItemsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TransactWriteItemsOutput] = struct(
    ConsumedCapacityMultiple.underlyingSchema.optional[TransactWriteItemsOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the entire <code>TransactWriteItems</code> operation.\n            The values of the list are ordered according to the ordering of the\n                <code>TransactItems</code> request parameter. </p>")),
    ItemCollectionMetricsPerTable.underlyingSchema.optional[TransactWriteItemsOutput]("ItemCollectionMetrics", _.itemCollectionMetrics).addHints(smithy.api.Documentation("<p>A list of tables that were processed by <code>TransactWriteItems</code> and, for each\n            table, information about any item collections that were affected by individual\n                <code>UpdateItem</code>, <code>PutItem</code>, or <code>DeleteItem</code>\n            operations. </p>")),
  ){
    TransactWriteItemsOutput.apply
  }.withId(id).addHints(hints)
}
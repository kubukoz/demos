package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchWriteItemInput(requestItems: Map[TableName,List[WriteRequest]], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None)
object BatchWriteItemInput extends ShapeTag.Companion[BatchWriteItemInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchWriteItemInput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the input of a <code>BatchWriteItem</code> operation.</p>"),
  )

  implicit val schema: Schema[BatchWriteItemInput] = struct(
    BatchWriteItemRequestMap.underlyingSchema.required[BatchWriteItemInput]("RequestItems", _.requestItems).addHints(smithy.api.Documentation("<p>A map of one or more table names and, for each table, a list of operations to be\n            performed (<code>DeleteRequest</code> or <code>PutRequest</code>). Each element in the\n            map consists of the following:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>DeleteRequest</code> - Perform a <code>DeleteItem</code> operation on the\n                    specified item. The item to be deleted is identified by a <code>Key</code>\n                    subelement:</p>\n                <ul>\n                  <li>\n                        <p>\n                            <code>Key</code> - A map of primary key attribute values that uniquely\n                            identify the item. Each entry in this map consists of an attribute name\n                            and an attribute value. For each primary key, you must provide\n                                <i>all</i> of the key attributes. For example, with a\n                            simple primary key, you only need to provide a value for the partition\n                            key. For a composite primary key, you must provide values for\n                                <i>both</i> the partition key and the sort key.</p>\n                    </li>\n               </ul>\n            </li>\n            <li>\n                <p>\n                    <code>PutRequest</code> - Perform a <code>PutItem</code> operation on the\n                    specified item. The item to be put is identified by an <code>Item</code>\n                    subelement:</p>\n                <ul>\n                  <li>\n                        <p>\n                            <code>Item</code> - A map of attributes and their values. Each entry in\n                            this map consists of an attribute name and an attribute value. Attribute\n                            values must not be null; string and binary type attributes must have\n                            lengths greater than zero; and set type attributes must not be empty.\n                            Requests that contain empty values are rejected with a\n                                <code>ValidationException</code> exception.</p>\n                        <p>If you specify any attributes that are part of an index key, then the\n                            data types for those attributes must match those of the schema in the\n                            table\'s attribute definition.</p>\n                    </li>\n               </ul>\n            </li>\n         </ul>"), smithy.api.Required()),
    ReturnConsumedCapacity.schema.optional[BatchWriteItemInput]("ReturnConsumedCapacity", _.returnConsumedCapacity),
    ReturnItemCollectionMetrics.schema.optional[BatchWriteItemInput]("ReturnItemCollectionMetrics", _.returnItemCollectionMetrics).addHints(smithy.api.Documentation("<p>Determines whether item collection metrics are returned. If set to <code>SIZE</code>,\n            the response includes statistics about item collections, if any, that were modified\n            during the operation are returned in the response. If set to <code>NONE</code> (the\n            default), no statistics are returned.</p>")),
  ){
    BatchWriteItemInput.apply
  }.withId(id).addHints(hints)
}
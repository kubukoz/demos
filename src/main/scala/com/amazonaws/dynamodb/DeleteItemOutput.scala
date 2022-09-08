package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteItemOutput(attributes: Option[Map[AttributeName,AttributeValue]] = None, consumedCapacity: Option[ConsumedCapacity] = None, itemCollectionMetrics: Option[ItemCollectionMetrics] = None)
object DeleteItemOutput extends ShapeTag.Companion[DeleteItemOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteItemOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>DeleteItem</code> operation.</p>"),
  )

  implicit val schema: Schema[DeleteItemOutput] = struct(
    AttributeMap.underlyingSchema.optional[DeleteItemOutput]("Attributes", _.attributes).addHints(smithy.api.Documentation("<p>A map of attribute names to <code>AttributeValue</code> objects, representing the item\n            as it appeared before the <code>DeleteItem</code> operation. This map appears in the\n            response only if <code>ReturnValues</code> was specified as <code>ALL_OLD</code> in the\n            request.</p>")),
    ConsumedCapacity.schema.optional[DeleteItemOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the <code>DeleteItem</code> operation. The data\n            returned includes the total provisioned throughput consumed, along with statistics for\n            the table and any indexes involved in the operation. <code>ConsumedCapacity</code> is\n            only returned if the <code>ReturnConsumedCapacity</code> parameter was specified. For\n            more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html\">Provisioned Mode</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
    ItemCollectionMetrics.schema.optional[DeleteItemOutput]("ItemCollectionMetrics", _.itemCollectionMetrics).addHints(smithy.api.Documentation("<p>Information about item collections, if any, that were affected by the\n                <code>DeleteItem</code> operation. <code>ItemCollectionMetrics</code> is only\n            returned if the <code>ReturnItemCollectionMetrics</code> parameter was specified. If the\n            table does not have any local secondary indexes, this information is not returned in the\n            response.</p>\n        <p>Each <code>ItemCollectionMetrics</code> element consists of:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>ItemCollectionKey</code> - The partition key value of the item collection.\n                    This is the same as the partition key value of the item itself.</p>\n            </li>\n            <li>\n                <p>\n                    <code>SizeEstimateRangeGB</code> - An estimate of item collection size, in\n                    gigabytes. This value is a two-element array containing a lower bound and an\n                    upper bound for the estimate. The estimate includes the size of all the items in\n                    the table, plus the size of all attributes projected into all of the local\n                    secondary indexes on that table. Use this estimate to measure whether a local\n                    secondary index is approaching its size limit.</p>\n                <p>The estimate is subject to change over time; therefore, do not rely on the\n                    precision or accuracy of the estimate.</p>\n            </li>\n         </ul>")),
  ){
    DeleteItemOutput.apply
  }.withId(id).addHints(hints)
}
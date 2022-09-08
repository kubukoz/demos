package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateItemOutput(attributes: Option[Map[AttributeName,AttributeValue]] = None, consumedCapacity: Option[ConsumedCapacity] = None, itemCollectionMetrics: Option[ItemCollectionMetrics] = None)
object UpdateItemOutput extends ShapeTag.Companion[UpdateItemOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateItemOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of an <code>UpdateItem</code> operation.</p>"),
  )

  implicit val schema: Schema[UpdateItemOutput] = struct(
    AttributeMap.underlyingSchema.optional[UpdateItemOutput]("Attributes", _.attributes).addHints(smithy.api.Documentation("<p>A map of attribute values as they appear before or after the <code>UpdateItem</code>\n            operation, as determined by the <code>ReturnValues</code> parameter.</p>\n        <p>The <code>Attributes</code> map is only present if <code>ReturnValues</code> was\n            specified as something other than <code>NONE</code> in the request. Each element\n            represents one attribute.</p>")),
    ConsumedCapacity.schema.optional[UpdateItemOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the <code>UpdateItem</code> operation. The data\n            returned includes the total provisioned throughput consumed, along with statistics for\n            the table and any indexes involved in the operation. <code>ConsumedCapacity</code> is\n            only returned if the <code>ReturnConsumedCapacity</code> parameter was specified. For\n            more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html\">Provisioned Throughput</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
    ItemCollectionMetrics.schema.optional[UpdateItemOutput]("ItemCollectionMetrics", _.itemCollectionMetrics).addHints(smithy.api.Documentation("<p>Information about item collections, if any, that were affected by the\n                <code>UpdateItem</code> operation. <code>ItemCollectionMetrics</code> is only\n            returned if the <code>ReturnItemCollectionMetrics</code> parameter was specified. If the\n            table does not have any local secondary indexes, this information is not returned in the\n            response.</p>\n        <p>Each <code>ItemCollectionMetrics</code> element consists of:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>ItemCollectionKey</code> - The partition key value of the item collection.\n                    This is the same as the partition key value of the item itself.</p>\n            </li>\n            <li>\n                <p>\n                    <code>SizeEstimateRangeGB</code> - An estimate of item collection size, in\n                    gigabytes. This value is a two-element array containing a lower bound and an\n                    upper bound for the estimate. The estimate includes the size of all the items in\n                    the table, plus the size of all attributes projected into all of the local\n                    secondary indexes on that table. Use this estimate to measure whether a local\n                    secondary index is approaching its size limit.</p>\n                <p>The estimate is subject to change over time; therefore, do not rely on the\n                    precision or accuracy of the estimate.</p>\n            </li>\n         </ul>")),
  ){
    UpdateItemOutput.apply
  }.withId(id).addHints(hints)
}
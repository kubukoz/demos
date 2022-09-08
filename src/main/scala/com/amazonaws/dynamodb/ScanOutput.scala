package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ScanOutput(count: Int = 0, scannedCount: Int = 0, items: Option[List[Map[AttributeName,AttributeValue]]] = None, lastEvaluatedKey: Option[Map[AttributeName,AttributeValue]] = None, consumedCapacity: Option[ConsumedCapacity] = None)
object ScanOutput extends ShapeTag.Companion[ScanOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ScanOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>Scan</code> operation.</p>"),
  )

  implicit val schema: Schema[ScanOutput] = struct(
    int.required[ScanOutput]("Count", _.count).addHints(smithy.api.Documentation("<p>The number of items in the response.</p>\n        <p>If you set <code>ScanFilter</code> in the request, then <code>Count</code> is the\n            number of items returned after the filter was applied, and <code>ScannedCount</code> is\n            the number of matching items before the filter was applied.</p>\n        <p>If you did not use a filter in the request, then <code>Count</code> is the same as\n                <code>ScannedCount</code>.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    int.required[ScanOutput]("ScannedCount", _.scannedCount).addHints(smithy.api.Documentation("<p>The number of items evaluated, before any <code>ScanFilter</code> is applied. A high\n                <code>ScannedCount</code> value with few, or no, <code>Count</code> results\n            indicates an inefficient <code>Scan</code> operation. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/QueryAndScan.html#Count\">Count and\n                ScannedCount</a> in the <i>Amazon DynamoDB Developer\n            Guide</i>.</p>\n        <p>If you did not use a filter in the request, then <code>ScannedCount</code> is the same\n            as <code>Count</code>.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    ItemList.underlyingSchema.optional[ScanOutput]("Items", _.items).addHints(smithy.api.Documentation("<p>An array of item attributes that match the scan criteria. Each element in this array\n            consists of an attribute name and the value for that attribute.</p>")),
    Key.underlyingSchema.optional[ScanOutput]("LastEvaluatedKey", _.lastEvaluatedKey).addHints(smithy.api.Documentation("<p>The primary key of the item where the operation stopped, inclusive of the previous\n            result set. Use this value to start a new operation, excluding this value in the new\n            request.</p>\n        <p>If <code>LastEvaluatedKey</code> is empty, then the \"last page\" of results has been\n            processed and there is no more data to be retrieved.</p>\n        <p>If <code>LastEvaluatedKey</code> is not empty, it does not necessarily mean that there\n            is more data in the result set. The only way to know when you have reached the end of\n            the result set is when <code>LastEvaluatedKey</code> is empty.</p>")),
    ConsumedCapacity.schema.optional[ScanOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the <code>Scan</code> operation. The data returned\n            includes the total provisioned throughput consumed, along with statistics for the table\n            and any indexes involved in the operation. <code>ConsumedCapacity</code> is only\n            returned if the <code>ReturnConsumedCapacity</code> parameter was specified. For more\n            information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html\">Provisioned Throughput</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
  ){
    ScanOutput.apply
  }.withId(id).addHints(hints)
}
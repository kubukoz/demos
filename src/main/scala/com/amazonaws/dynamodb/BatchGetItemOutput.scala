package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchGetItemOutput(responses: Option[Map[TableName,List[Map[AttributeName,AttributeValue]]]] = None, unprocessedKeys: Option[Map[TableName,KeysAndAttributes]] = None, consumedCapacity: Option[List[ConsumedCapacity]] = None)
object BatchGetItemOutput extends ShapeTag.Companion[BatchGetItemOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchGetItemOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>BatchGetItem</code> operation.</p>"),
  )

  implicit val schema: Schema[BatchGetItemOutput] = struct(
    BatchGetResponseMap.underlyingSchema.optional[BatchGetItemOutput]("Responses", _.responses).addHints(smithy.api.Documentation("<p>A map of table name to a list of items. Each object in <code>Responses</code> consists\n            of a table name, along with a map of attribute data consisting of the data type and\n            attribute value.</p>")),
    BatchGetRequestMap.underlyingSchema.optional[BatchGetItemOutput]("UnprocessedKeys", _.unprocessedKeys).addHints(smithy.api.Documentation("<p>A map of tables and their respective keys that were not processed with the current\n            response. The <code>UnprocessedKeys</code> value is in the same form as\n                <code>RequestItems</code>, so the value can be provided directly to a subsequent\n                <code>BatchGetItem</code> operation. For more information, see\n                <code>RequestItems</code> in the Request Parameters section.</p>\n        <p>Each element consists of:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>Keys</code> - An array of primary key attribute values that define\n                    specific items in the table.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ProjectionExpression</code> - One or more attributes to be retrieved from\n                    the table or index. By default, all attributes are returned. If a requested\n                    attribute is not found, it does not appear in the result.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ConsistentRead</code> - The consistency of a read operation. If set to\n                        <code>true</code>, then a strongly consistent read is used; otherwise, an\n                    eventually consistent read is used.</p>\n            </li>\n         </ul>\n        <p>If there are no unprocessed keys remaining, the response contains an empty\n                <code>UnprocessedKeys</code> map.</p>")),
    ConsumedCapacityMultiple.underlyingSchema.optional[BatchGetItemOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The read capacity units consumed by the entire <code>BatchGetItem</code>\n            operation.</p>\n        <p>Each element consists of:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>TableName</code> - The table that consumed the provisioned\n                    throughput.</p>\n            </li>\n            <li>\n                <p>\n                    <code>CapacityUnits</code> - The total number of capacity units consumed.</p>\n            </li>\n         </ul>")),
  ){
    BatchGetItemOutput.apply
  }.withId(id).addHints(hints)
}
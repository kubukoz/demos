package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GetItemOutput(item: Option[Map[AttributeName,AttributeValue]] = None, consumedCapacity: Option[ConsumedCapacity] = None)
object GetItemOutput extends ShapeTag.Companion[GetItemOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GetItemOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>GetItem</code> operation.</p>"),
  )

  implicit val schema: Schema[GetItemOutput] = struct(
    AttributeMap.underlyingSchema.optional[GetItemOutput]("Item", _.item).addHints(smithy.api.Documentation("<p>A map of attribute names to <code>AttributeValue</code> objects, as specified by\n                <code>ProjectionExpression</code>.</p>")),
    ConsumedCapacity.schema.optional[GetItemOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the <code>GetItem</code> operation. The data returned\n            includes the total provisioned throughput consumed, along with statistics for the table\n            and any indexes involved in the operation. <code>ConsumedCapacity</code> is only\n            returned if the <code>ReturnConsumedCapacity</code> parameter was specified. For more\n            information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html\">Read/Write Capacity Mode</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
  ){
    GetItemOutput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExecuteStatementOutput(items: Option[List[Map[AttributeName,AttributeValue]]] = None, nextToken: Option[PartiQLNextToken] = None, consumedCapacity: Option[ConsumedCapacity] = None, lastEvaluatedKey: Option[Map[AttributeName,AttributeValue]] = None)
object ExecuteStatementOutput extends ShapeTag.Companion[ExecuteStatementOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteStatementOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExecuteStatementOutput] = struct(
    ItemList.underlyingSchema.optional[ExecuteStatementOutput]("Items", _.items).addHints(smithy.api.Documentation("<p>If a read operation was used, this property will contain the result of the read\n            operation; a map of attribute names and their values. For the write operations this\n            value will be empty.</p>")),
    PartiQLNextToken.schema.optional[ExecuteStatementOutput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>If the response of a read request exceeds the response payload limit DynamoDB will set\n            this value in the response. If set, you can use that this value in the subsequent\n            request to get the remaining results.</p>")),
    ConsumedCapacity.schema.optional[ExecuteStatementOutput]("ConsumedCapacity", _.consumedCapacity),
    Key.underlyingSchema.optional[ExecuteStatementOutput]("LastEvaluatedKey", _.lastEvaluatedKey).addHints(smithy.api.Documentation("<p>The primary key of the item where the operation stopped, inclusive of the previous\n            result set. Use this value to start a new operation, excluding this value in the new\n            request. If <code>LastEvaluatedKey</code> is empty, then the \"last page\" of results has\n            been processed and there is no more data to be retrieved. If\n                <code>LastEvaluatedKey</code> is not empty, it does not necessarily mean that there\n            is more data in the result set. The only way to know when you have reached the end of\n            the result set is when <code>LastEvaluatedKey</code> is empty. </p>")),
  ){
    ExecuteStatementOutput.apply
  }.withId(id).addHints(hints)
}
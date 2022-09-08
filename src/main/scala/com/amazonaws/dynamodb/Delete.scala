package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Delete(key: Map[AttributeName,AttributeValue], tableName: TableName, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, returnValuesOnConditionCheckFailure: Option[ReturnValuesOnConditionCheckFailure] = None)
object Delete extends ShapeTag.Companion[Delete] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Delete")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform a <code>DeleteItem</code> operation.</p>"),
  )

  implicit val schema: Schema[Delete] = struct(
    Key.underlyingSchema.required[Delete]("Key", _.key).addHints(smithy.api.Documentation("<p>The primary key of the item to be deleted. Each element consists of an attribute name\n            and a value for that attribute.</p>"), smithy.api.Required()),
    TableName.schema.required[Delete]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table in which the item to be deleted resides.</p>"), smithy.api.Required()),
    ConditionExpression.schema.optional[Delete]("ConditionExpression", _.conditionExpression).addHints(smithy.api.Documentation("<p>A condition that must be satisfied in order for a conditional delete to\n            succeed.</p>")),
    ExpressionAttributeNameMap.underlyingSchema.optional[Delete]("ExpressionAttributeNames", _.expressionAttributeNames).addHints(smithy.api.Documentation("<p>One or more substitution tokens for attribute names in an expression.</p>")),
    ExpressionAttributeValueMap.underlyingSchema.optional[Delete]("ExpressionAttributeValues", _.expressionAttributeValues).addHints(smithy.api.Documentation("<p>One or more values that can be substituted in an expression.</p>")),
    ReturnValuesOnConditionCheckFailure.schema.optional[Delete]("ReturnValuesOnConditionCheckFailure", _.returnValuesOnConditionCheckFailure).addHints(smithy.api.Documentation("<p>Use <code>ReturnValuesOnConditionCheckFailure</code> to get the item attributes if the\n                <code>Delete</code> condition fails. For\n                <code>ReturnValuesOnConditionCheckFailure</code>, the valid values are: NONE and\n            ALL_OLD.</p>")),
  ){
    Delete.apply
  }.withId(id).addHints(hints)
}
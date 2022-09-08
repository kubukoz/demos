package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Update(key: Map[AttributeName,AttributeValue], updateExpression: UpdateExpression, tableName: TableName, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, returnValuesOnConditionCheckFailure: Option[ReturnValuesOnConditionCheckFailure] = None)
object Update extends ShapeTag.Companion[Update] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Update")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform an <code>UpdateItem</code> operation.</p>"),
  )

  implicit val schema: Schema[Update] = struct(
    Key.underlyingSchema.required[Update]("Key", _.key).addHints(smithy.api.Documentation("<p>The primary key of the item to be updated. Each element consists of an attribute name\n            and a value for that attribute.</p>"), smithy.api.Required()),
    UpdateExpression.schema.required[Update]("UpdateExpression", _.updateExpression).addHints(smithy.api.Documentation("<p>An expression that defines one or more attributes to be updated, the action to be\n            performed on them, and new value(s) for them.</p>"), smithy.api.Required()),
    TableName.schema.required[Update]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table for the <code>UpdateItem</code> request.</p>"), smithy.api.Required()),
    ConditionExpression.schema.optional[Update]("ConditionExpression", _.conditionExpression).addHints(smithy.api.Documentation("<p>A condition that must be satisfied in order for a conditional update to\n            succeed.</p>")),
    ExpressionAttributeNameMap.underlyingSchema.optional[Update]("ExpressionAttributeNames", _.expressionAttributeNames).addHints(smithy.api.Documentation("<p>One or more substitution tokens for attribute names in an expression.</p>")),
    ExpressionAttributeValueMap.underlyingSchema.optional[Update]("ExpressionAttributeValues", _.expressionAttributeValues).addHints(smithy.api.Documentation("<p>One or more values that can be substituted in an expression.</p>")),
    ReturnValuesOnConditionCheckFailure.schema.optional[Update]("ReturnValuesOnConditionCheckFailure", _.returnValuesOnConditionCheckFailure).addHints(smithy.api.Documentation("<p>Use <code>ReturnValuesOnConditionCheckFailure</code> to get the item attributes if the\n                <code>Update</code> condition fails. For\n                <code>ReturnValuesOnConditionCheckFailure</code>, the valid values are: NONE,\n            ALL_OLD, UPDATED_OLD, ALL_NEW, UPDATED_NEW.</p>")),
  ){
    Update.apply
  }.withId(id).addHints(hints)
}
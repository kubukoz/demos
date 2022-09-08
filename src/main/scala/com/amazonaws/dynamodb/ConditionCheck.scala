package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ConditionCheck(key: Map[AttributeName,AttributeValue], tableName: TableName, conditionExpression: ConditionExpression, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, returnValuesOnConditionCheckFailure: Option[ReturnValuesOnConditionCheckFailure] = None)
object ConditionCheck extends ShapeTag.Companion[ConditionCheck] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ConditionCheck")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform a check that an item exists or to check the condition\n            of specific attributes of the item.</p>"),
  )

  implicit val schema: Schema[ConditionCheck] = struct(
    Key.underlyingSchema.required[ConditionCheck]("Key", _.key).addHints(smithy.api.Documentation("<p>The primary key of the item to be checked. Each element consists of an attribute name\n            and a value for that attribute.</p>"), smithy.api.Required()),
    TableName.schema.required[ConditionCheck]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table for the check item request.</p>"), smithy.api.Required()),
    ConditionExpression.schema.required[ConditionCheck]("ConditionExpression", _.conditionExpression).addHints(smithy.api.Documentation("<p>A condition that must be satisfied in order for a conditional update to\n            succeed.</p>"), smithy.api.Required()),
    ExpressionAttributeNameMap.underlyingSchema.optional[ConditionCheck]("ExpressionAttributeNames", _.expressionAttributeNames).addHints(smithy.api.Documentation("<p>One or more substitution tokens for attribute names in an expression.</p>")),
    ExpressionAttributeValueMap.underlyingSchema.optional[ConditionCheck]("ExpressionAttributeValues", _.expressionAttributeValues).addHints(smithy.api.Documentation("<p>One or more values that can be substituted in an expression.</p>")),
    ReturnValuesOnConditionCheckFailure.schema.optional[ConditionCheck]("ReturnValuesOnConditionCheckFailure", _.returnValuesOnConditionCheckFailure).addHints(smithy.api.Documentation("<p>Use <code>ReturnValuesOnConditionCheckFailure</code> to get the item attributes if the\n                <code>ConditionCheck</code> condition fails. For\n                <code>ReturnValuesOnConditionCheckFailure</code>, the valid values are: NONE and\n            ALL_OLD.</p>")),
  ){
    ConditionCheck.apply
  }.withId(id).addHints(hints)
}
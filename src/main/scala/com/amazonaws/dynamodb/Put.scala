package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Put(item: Map[AttributeName,AttributeValue], tableName: TableName, conditionExpression: Option[ConditionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None, expressionAttributeValues: Option[Map[ExpressionAttributeValueVariable,AttributeValue]] = None, returnValuesOnConditionCheckFailure: Option[ReturnValuesOnConditionCheckFailure] = None)
object Put extends ShapeTag.Companion[Put] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Put")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform a <code>PutItem</code> operation.</p>"),
  )

  implicit val schema: Schema[Put] = struct(
    PutItemInputAttributeMap.underlyingSchema.required[Put]("Item", _.item).addHints(smithy.api.Documentation("<p>A map of attribute name to attribute values, representing the primary key of the item\n            to be written by <code>PutItem</code>. All of the table\'s primary key attributes must be\n            specified, and their data types must match those of the table\'s key schema. If any\n            attributes are present in the item that are part of an index key schema for the table,\n            their types must match the index key schema. </p>"), smithy.api.Required()),
    TableName.schema.required[Put]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table in which to write the item.</p>"), smithy.api.Required()),
    ConditionExpression.schema.optional[Put]("ConditionExpression", _.conditionExpression).addHints(smithy.api.Documentation("<p>A condition that must be satisfied in order for a conditional update to\n            succeed.</p>")),
    ExpressionAttributeNameMap.underlyingSchema.optional[Put]("ExpressionAttributeNames", _.expressionAttributeNames).addHints(smithy.api.Documentation("<p>One or more substitution tokens for attribute names in an expression.</p>")),
    ExpressionAttributeValueMap.underlyingSchema.optional[Put]("ExpressionAttributeValues", _.expressionAttributeValues).addHints(smithy.api.Documentation("<p>One or more values that can be substituted in an expression.</p>")),
    ReturnValuesOnConditionCheckFailure.schema.optional[Put]("ReturnValuesOnConditionCheckFailure", _.returnValuesOnConditionCheckFailure).addHints(smithy.api.Documentation("<p>Use <code>ReturnValuesOnConditionCheckFailure</code> to get the item attributes if the\n                <code>Put</code> condition fails. For\n                <code>ReturnValuesOnConditionCheckFailure</code>, the valid values are: NONE and\n            ALL_OLD.</p>")),
  ){
    Put.apply
  }.withId(id).addHints(hints)
}
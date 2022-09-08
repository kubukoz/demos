package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Get(key: Map[AttributeName,AttributeValue], tableName: TableName, projectionExpression: Option[ProjectionExpression] = None, expressionAttributeNames: Option[Map[ExpressionAttributeNameVariable,AttributeName]] = None)
object Get extends ShapeTag.Companion[Get] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Get")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Specifies an item and related attribute values to retrieve in a\n                <code>TransactGetItem</code> object.</p>"),
  )

  implicit val schema: Schema[Get] = struct(
    Key.underlyingSchema.required[Get]("Key", _.key).addHints(smithy.api.Documentation("<p>A map of attribute names to <code>AttributeValue</code> objects that specifies the\n            primary key of the item to retrieve.</p>"), smithy.api.Required()),
    TableName.schema.required[Get]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table from which to retrieve the specified item.</p>"), smithy.api.Required()),
    ProjectionExpression.schema.optional[Get]("ProjectionExpression", _.projectionExpression).addHints(smithy.api.Documentation("<p>A string that identifies one or more attributes of the specified item to retrieve from\n            the table. The attributes in the expression must be separated by commas. If no attribute\n            names are specified, then all attributes of the specified item are returned. If any of\n            the requested attributes are not found, they do not appear in the result.</p>")),
    ExpressionAttributeNameMap.underlyingSchema.optional[Get]("ExpressionAttributeNames", _.expressionAttributeNames).addHints(smithy.api.Documentation("<p>One or more substitution tokens for attribute names in the ProjectionExpression\n            parameter.</p>")),
  ){
    Get.apply
  }.withId(id).addHints(hints)
}
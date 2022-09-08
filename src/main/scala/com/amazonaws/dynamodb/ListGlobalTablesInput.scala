package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListGlobalTablesInput(exclusiveStartGlobalTableName: Option[TableName] = None, limit: Option[PositiveIntegerObject] = None, regionName: Option[RegionName] = None)
object ListGlobalTablesInput extends ShapeTag.Companion[ListGlobalTablesInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListGlobalTablesInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListGlobalTablesInput] = struct(
    TableName.schema.optional[ListGlobalTablesInput]("ExclusiveStartGlobalTableName", _.exclusiveStartGlobalTableName).addHints(smithy.api.Documentation("<p>The first global table name that this operation will evaluate.</p>")),
    PositiveIntegerObject.schema.optional[ListGlobalTablesInput]("Limit", _.limit).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of table names to return, if the parameter is not specified\n            DynamoDB defaults to 100.</p>\n        <p>If the number of global tables DynamoDB finds reaches this limit, it stops the\n            operation and returns the table names collected up to that point, with a table name in\n            the <code>LastEvaluatedGlobalTableName</code> to apply in a subsequent operation to the\n                <code>ExclusiveStartGlobalTableName</code> parameter.</p>")),
    RegionName.schema.optional[ListGlobalTablesInput]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>Lists the global tables in a specific Region.</p>")),
  ){
    ListGlobalTablesInput.apply
  }.withId(id).addHints(hints)
}
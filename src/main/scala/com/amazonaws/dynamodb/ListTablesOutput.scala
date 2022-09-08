package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListTablesOutput(tableNames: Option[List[TableName]] = None, lastEvaluatedTableName: Option[TableName] = None)
object ListTablesOutput extends ShapeTag.Companion[ListTablesOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTablesOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>ListTables</code> operation.</p>"),
  )

  implicit val schema: Schema[ListTablesOutput] = struct(
    TableNameList.underlyingSchema.optional[ListTablesOutput]("TableNames", _.tableNames).addHints(smithy.api.Documentation("<p>The names of the tables associated with the current account at the current endpoint.\n            The maximum size of this array is 100.</p>\n        <p>If <code>LastEvaluatedTableName</code> also appears in the output, you can use this\n            value as the <code>ExclusiveStartTableName</code> parameter in a subsequent\n                <code>ListTables</code> request and obtain the next page of results.</p>")),
    TableName.schema.optional[ListTablesOutput]("LastEvaluatedTableName", _.lastEvaluatedTableName).addHints(smithy.api.Documentation("<p>The name of the last table in the current page of results. Use this value as the\n                <code>ExclusiveStartTableName</code> in a new request to obtain the next page of\n            results, until all the table names are returned.</p>\n        <p>If you do not receive a <code>LastEvaluatedTableName</code> value in the response,\n            this means that there are no more table names to be retrieved.</p>")),
  ){
    ListTablesOutput.apply
  }.withId(id).addHints(hints)
}
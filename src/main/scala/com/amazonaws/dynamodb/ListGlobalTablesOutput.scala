package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListGlobalTablesOutput(globalTables: Option[List[GlobalTable]] = None, lastEvaluatedGlobalTableName: Option[TableName] = None)
object ListGlobalTablesOutput extends ShapeTag.Companion[ListGlobalTablesOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListGlobalTablesOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListGlobalTablesOutput] = struct(
    GlobalTableList.underlyingSchema.optional[ListGlobalTablesOutput]("GlobalTables", _.globalTables).addHints(smithy.api.Documentation("<p>List of global table names.</p>")),
    TableName.schema.optional[ListGlobalTablesOutput]("LastEvaluatedGlobalTableName", _.lastEvaluatedGlobalTableName).addHints(smithy.api.Documentation("<p>Last evaluated global table name.</p>")),
  ){
    ListGlobalTablesOutput.apply
  }.withId(id).addHints(hints)
}
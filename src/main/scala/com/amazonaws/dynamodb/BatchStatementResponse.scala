package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchStatementResponse(error: Option[BatchStatementError] = None, tableName: Option[TableName] = None, item: Option[Map[AttributeName,AttributeValue]] = None)
object BatchStatementResponse extends ShapeTag.Companion[BatchStatementResponse] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchStatementResponse")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p> A PartiQL batch statement response.. </p>"),
  )

  implicit val schema: Schema[BatchStatementResponse] = struct(
    BatchStatementError.schema.optional[BatchStatementResponse]("Error", _.error).addHints(smithy.api.Documentation("<p> The error associated with a failed PartiQL batch statement. </p>")),
    TableName.schema.optional[BatchStatementResponse]("TableName", _.tableName).addHints(smithy.api.Documentation("<p> The table name associated with a failed PartiQL batch statement. </p>")),
    AttributeMap.underlyingSchema.optional[BatchStatementResponse]("Item", _.item).addHints(smithy.api.Documentation("<p> A DynamoDB item associated with a BatchStatementResponse </p>")),
  ){
    BatchStatementResponse.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchStatementError(code: Option[BatchStatementErrorCodeEnum] = None, message: Option[String] = None)
object BatchStatementError extends ShapeTag.Companion[BatchStatementError] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchStatementError")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p> An error associated with a statement in a PartiQL batch that was run. </p>"),
  )

  implicit val schema: Schema[BatchStatementError] = struct(
    BatchStatementErrorCodeEnum.schema.optional[BatchStatementError]("Code", _.code).addHints(smithy.api.Documentation("<p> The error code associated with the failed PartiQL batch statement. </p>")),
    string.optional[BatchStatementError]("Message", _.message).addHints(smithy.api.Documentation("<p> The error message associated with the PartiQL batch resposne. </p>")),
  ){
    BatchStatementError.apply
  }.withId(id).addHints(hints)
}
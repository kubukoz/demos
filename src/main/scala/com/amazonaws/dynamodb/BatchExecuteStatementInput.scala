package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchExecuteStatementInput(statements: List[BatchStatementRequest], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None)
object BatchExecuteStatementInput extends ShapeTag.Companion[BatchExecuteStatementInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchExecuteStatementInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[BatchExecuteStatementInput] = struct(
    PartiQLBatchRequest.underlyingSchema.required[BatchExecuteStatementInput]("Statements", _.statements).addHints(smithy.api.Documentation("<p>The list of PartiQL statements representing the batch to run.</p>"), smithy.api.Required()),
    ReturnConsumedCapacity.schema.optional[BatchExecuteStatementInput]("ReturnConsumedCapacity", _.returnConsumedCapacity),
  ){
    BatchExecuteStatementInput.apply
  }.withId(id).addHints(hints)
}
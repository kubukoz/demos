package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchExecuteStatementOutput(responses: Option[List[BatchStatementResponse]] = None, consumedCapacity: Option[List[ConsumedCapacity]] = None)
object BatchExecuteStatementOutput extends ShapeTag.Companion[BatchExecuteStatementOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchExecuteStatementOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[BatchExecuteStatementOutput] = struct(
    PartiQLBatchResponse.underlyingSchema.optional[BatchExecuteStatementOutput]("Responses", _.responses).addHints(smithy.api.Documentation("<p>The response to each PartiQL statement in the batch.</p>")),
    ConsumedCapacityMultiple.underlyingSchema.optional[BatchExecuteStatementOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the entire operation. The values of the list are\n            ordered according to the ordering of the statements.</p>")),
  ){
    BatchExecuteStatementOutput.apply
  }.withId(id).addHints(hints)
}
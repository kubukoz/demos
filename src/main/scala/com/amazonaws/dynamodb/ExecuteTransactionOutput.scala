package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExecuteTransactionOutput(responses: Option[List[ItemResponse]] = None, consumedCapacity: Option[List[ConsumedCapacity]] = None)
object ExecuteTransactionOutput extends ShapeTag.Companion[ExecuteTransactionOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteTransactionOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExecuteTransactionOutput] = struct(
    ItemResponseList.underlyingSchema.optional[ExecuteTransactionOutput]("Responses", _.responses).addHints(smithy.api.Documentation("<p>The response to a PartiQL transaction.</p>")),
    ConsumedCapacityMultiple.underlyingSchema.optional[ExecuteTransactionOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>The capacity units consumed by the entire operation. The values of the list are\n            ordered according to the ordering of the statements.</p>")),
  ){
    ExecuteTransactionOutput.apply
  }.withId(id).addHints(hints)
}
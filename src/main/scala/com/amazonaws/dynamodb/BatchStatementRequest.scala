package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BatchStatementRequest(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None)
object BatchStatementRequest extends ShapeTag.Companion[BatchStatementRequest] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BatchStatementRequest")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p> A PartiQL batch statement request. </p>"),
  )

  implicit val schema: Schema[BatchStatementRequest] = struct(
    PartiQLStatement.schema.required[BatchStatementRequest]("Statement", _.statement).addHints(smithy.api.Documentation("<p> A valid PartiQL statement. </p>"), smithy.api.Required()),
    PreparedStatementParameters.underlyingSchema.optional[BatchStatementRequest]("Parameters", _.parameters).addHints(smithy.api.Documentation("<p> The parameters associated with a PartiQL statement in the batch request. </p>")),
    ConsistentRead.schema.optional[BatchStatementRequest]("ConsistentRead", _.consistentRead).addHints(smithy.api.Box(), smithy.api.Documentation("<p> The read consistency of the PartiQL batch request. </p>")),
  ){
    BatchStatementRequest.apply
  }.withId(id).addHints(hints)
}
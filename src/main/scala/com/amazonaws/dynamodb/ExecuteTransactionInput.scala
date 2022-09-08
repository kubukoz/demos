package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExecuteTransactionInput(transactStatements: List[ParameterizedStatement], clientRequestToken: Option[ClientRequestToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None)
object ExecuteTransactionInput extends ShapeTag.Companion[ExecuteTransactionInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteTransactionInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExecuteTransactionInput] = struct(
    ParameterizedStatements.underlyingSchema.required[ExecuteTransactionInput]("TransactStatements", _.transactStatements).addHints(smithy.api.Documentation("<p>The list of PartiQL statements representing the transaction to run.</p>"), smithy.api.Required()),
    ClientRequestToken.schema.optional[ExecuteTransactionInput]("ClientRequestToken", _.clientRequestToken).addHints(smithy.api.Documentation("<p>Set this value to get remaining results, if <code>NextToken</code> was returned in the\n            statement response.</p>"), smithy.api.IdempotencyToken()),
    ReturnConsumedCapacity.schema.optional[ExecuteTransactionInput]("ReturnConsumedCapacity", _.returnConsumedCapacity).addHints(smithy.api.Documentation("<p>Determines the level of detail about either provisioned or on-demand throughput\n            consumption that is returned in the response. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_TransactGetItems.html\">TransactGetItems</a> and <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_TransactWriteItems.html\">TransactWriteItems</a>.</p>")),
  ){
    ExecuteTransactionInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExecuteStatementInput(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None, consistentRead: Option[ConsistentRead] = None, nextToken: Option[PartiQLNextToken] = None, returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, limit: Option[PositiveIntegerObject] = None)
object ExecuteStatementInput extends ShapeTag.Companion[ExecuteStatementInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExecuteStatementInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExecuteStatementInput] = struct(
    PartiQLStatement.schema.required[ExecuteStatementInput]("Statement", _.statement).addHints(smithy.api.Documentation("<p>The PartiQL statement representing the operation to run.</p>"), smithy.api.Required()),
    PreparedStatementParameters.underlyingSchema.optional[ExecuteStatementInput]("Parameters", _.parameters).addHints(smithy.api.Documentation("<p>The parameters for the PartiQL statement, if any.</p>")),
    ConsistentRead.schema.optional[ExecuteStatementInput]("ConsistentRead", _.consistentRead).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The consistency of a read operation. If set to <code>true</code>, then a strongly\n            consistent read is used; otherwise, an eventually consistent read is used.</p>")),
    PartiQLNextToken.schema.optional[ExecuteStatementInput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>Set this value to get remaining results, if <code>NextToken</code> was returned in the\n            statement response.</p>")),
    ReturnConsumedCapacity.schema.optional[ExecuteStatementInput]("ReturnConsumedCapacity", _.returnConsumedCapacity),
    PositiveIntegerObject.schema.optional[ExecuteStatementInput]("Limit", _.limit).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of items to evaluate (not necessarily the number of matching\n            items). If DynamoDB processes the number of items up to the limit while processing the\n            results, it stops the operation and returns the matching values up to that point, along\n            with a key in <code>LastEvaluatedKey</code> to apply in a subsequent operation so you\n            can pick up where you left off. Also, if the processed dataset size exceeds 1 MB before\n            DynamoDB reaches this limit, it stops the operation and returns the matching values up\n            to the limit, and a key in <code>LastEvaluatedKey</code> to apply in a subsequent\n            operation to continue the operation. </p>")),
  ){
    ExecuteStatementInput.apply
  }.withId(id).addHints(hints)
}
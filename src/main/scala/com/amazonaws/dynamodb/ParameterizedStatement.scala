package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ParameterizedStatement(statement: PartiQLStatement, parameters: Option[List[AttributeValue]] = None)
object ParameterizedStatement extends ShapeTag.Companion[ParameterizedStatement] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ParameterizedStatement")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p> Represents a PartiQL statment that uses parameters. </p>"),
  )

  implicit val schema: Schema[ParameterizedStatement] = struct(
    PartiQLStatement.schema.required[ParameterizedStatement]("Statement", _.statement).addHints(smithy.api.Documentation("<p> A PartiQL statment that uses parameters. </p>"), smithy.api.Required()),
    PreparedStatementParameters.underlyingSchema.optional[ParameterizedStatement]("Parameters", _.parameters).addHints(smithy.api.Documentation("<p> The parameter values. </p>")),
  ){
    ParameterizedStatement.apply
  }.withId(id).addHints(hints)
}
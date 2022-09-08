package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class FailureException(exceptionName: Option[ExceptionName] = None, exceptionDescription: Option[ExceptionDescription] = None)
object FailureException extends ShapeTag.Companion[FailureException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "FailureException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a failure a contributor insights operation.</p>"),
  )

  implicit val schema: Schema[FailureException] = struct(
    ExceptionName.schema.optional[FailureException]("ExceptionName", _.exceptionName).addHints(smithy.api.Documentation("<p>Exception name.</p>")),
    ExceptionDescription.schema.optional[FailureException]("ExceptionDescription", _.exceptionDescription).addHints(smithy.api.Documentation("<p>Description of the failure.</p>")),
  ){
    FailureException.apply
  }.withId(id).addHints(hints)
}
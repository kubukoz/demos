package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class IdempotentParameterMismatchException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object IdempotentParameterMismatchException extends ShapeTag.Companion[IdempotentParameterMismatchException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "IdempotentParameterMismatchException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>DynamoDB rejected the request because you retried a request with a\n            different payload but with an idempotent token that was already used.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[IdempotentParameterMismatchException] = struct(
    ErrorMessage.schema.optional[IdempotentParameterMismatchException]("Message", _.message),
  ){
    IdempotentParameterMismatchException.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactionConflictException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object TransactionConflictException extends ShapeTag.Companion[TransactionConflictException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactionConflictException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Operation was rejected because there is an ongoing transaction for the\n            item.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[TransactionConflictException] = struct(
    ErrorMessage.schema.optional[TransactionConflictException]("message", _.message),
  ){
    TransactionConflictException.apply
  }.withId(id).addHints(hints)
}
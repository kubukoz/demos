package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactionInProgressException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object TransactionInProgressException extends ShapeTag.Companion[TransactionInProgressException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactionInProgressException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The transaction with the given request token is already in progress.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[TransactionInProgressException] = struct(
    ErrorMessage.schema.optional[TransactionInProgressException]("Message", _.message),
  ){
    TransactionInProgressException.apply
  }.withId(id).addHints(hints)
}
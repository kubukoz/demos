package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class InvalidRestoreTimeException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object InvalidRestoreTimeException extends ShapeTag.Companion[InvalidRestoreTimeException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "InvalidRestoreTimeException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>An invalid restore time was specified. RestoreDateTime must be between\n            EarliestRestorableDateTime and LatestRestorableDateTime.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[InvalidRestoreTimeException] = struct(
    ErrorMessage.schema.optional[InvalidRestoreTimeException]("message", _.message),
  ){
    InvalidRestoreTimeException.apply
  }.withId(id).addHints(hints)
}
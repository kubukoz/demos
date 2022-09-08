package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ContinuousBackupsUnavailableException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ContinuousBackupsUnavailableException extends ShapeTag.Companion[ContinuousBackupsUnavailableException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContinuousBackupsUnavailableException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Backups have not yet been enabled for this table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ContinuousBackupsUnavailableException] = struct(
    ErrorMessage.schema.optional[ContinuousBackupsUnavailableException]("message", _.message),
  ){
    ContinuousBackupsUnavailableException.apply
  }.withId(id).addHints(hints)
}
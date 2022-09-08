package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class PointInTimeRecoveryUnavailableException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object PointInTimeRecoveryUnavailableException extends ShapeTag.Companion[PointInTimeRecoveryUnavailableException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PointInTimeRecoveryUnavailableException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Point in time recovery has not yet been enabled for this source table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[PointInTimeRecoveryUnavailableException] = struct(
    ErrorMessage.schema.optional[PointInTimeRecoveryUnavailableException]("message", _.message),
  ){
    PointInTimeRecoveryUnavailableException.apply
  }.withId(id).addHints(hints)
}
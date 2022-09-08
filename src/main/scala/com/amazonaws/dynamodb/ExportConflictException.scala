package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportConflictException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ExportConflictException extends ShapeTag.Companion[ExportConflictException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportConflictException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>There was a conflict when writing to the specified S3 bucket.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ExportConflictException] = struct(
    ErrorMessage.schema.optional[ExportConflictException]("message", _.message),
  ){
    ExportConflictException.apply
  }.withId(id).addHints(hints)
}
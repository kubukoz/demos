package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class InvalidExportTimeException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object InvalidExportTimeException extends ShapeTag.Companion[InvalidExportTimeException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "InvalidExportTimeException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified <code>ExportTime</code> is outside of the point in time recovery\n            window.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[InvalidExportTimeException] = struct(
    ErrorMessage.schema.optional[InvalidExportTimeException]("message", _.message),
  ){
    InvalidExportTimeException.apply
  }.withId(id).addHints(hints)
}
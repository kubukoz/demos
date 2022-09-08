package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ExportNotFoundException extends ShapeTag.Companion[ExportNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified export was not found.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ExportNotFoundException] = struct(
    ErrorMessage.schema.optional[ExportNotFoundException]("message", _.message),
  ){
    ExportNotFoundException.apply
  }.withId(id).addHints(hints)
}
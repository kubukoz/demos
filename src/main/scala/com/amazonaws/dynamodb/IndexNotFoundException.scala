package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class IndexNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object IndexNotFoundException extends ShapeTag.Companion[IndexNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "IndexNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The operation tried to access a nonexistent index.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[IndexNotFoundException] = struct(
    ErrorMessage.schema.optional[IndexNotFoundException]("message", _.message),
  ){
    IndexNotFoundException.apply
  }.withId(id).addHints(hints)
}
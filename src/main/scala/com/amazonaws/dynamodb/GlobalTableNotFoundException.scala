package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalTableNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object GlobalTableNotFoundException extends ShapeTag.Companion[GlobalTableNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified global table does not exist.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[GlobalTableNotFoundException] = struct(
    ErrorMessage.schema.optional[GlobalTableNotFoundException]("message", _.message),
  ){
    GlobalTableNotFoundException.apply
  }.withId(id).addHints(hints)
}
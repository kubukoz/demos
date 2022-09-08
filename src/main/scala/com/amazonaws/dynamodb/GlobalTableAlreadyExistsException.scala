package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalTableAlreadyExistsException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object GlobalTableAlreadyExistsException extends ShapeTag.Companion[GlobalTableAlreadyExistsException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableAlreadyExistsException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified global table already exists.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[GlobalTableAlreadyExistsException] = struct(
    ErrorMessage.schema.optional[GlobalTableAlreadyExistsException]("message", _.message),
  ){
    GlobalTableAlreadyExistsException.apply
  }.withId(id).addHints(hints)
}
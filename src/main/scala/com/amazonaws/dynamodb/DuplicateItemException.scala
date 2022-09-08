package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DuplicateItemException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object DuplicateItemException extends ShapeTag.Companion[DuplicateItemException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DuplicateItemException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p> There was an attempt to insert an item with the same primary key as an item that\n            already exists in the DynamoDB table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[DuplicateItemException] = struct(
    ErrorMessage.schema.optional[DuplicateItemException]("message", _.message),
  ){
    DuplicateItemException.apply
  }.withId(id).addHints(hints)
}
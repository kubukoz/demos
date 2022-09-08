package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TableAlreadyExistsException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object TableAlreadyExistsException extends ShapeTag.Companion[TableAlreadyExistsException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableAlreadyExistsException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>A target table with the specified name already exists. </p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[TableAlreadyExistsException] = struct(
    ErrorMessage.schema.optional[TableAlreadyExistsException]("message", _.message),
  ){
    TableAlreadyExistsException.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TableInUseException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object TableInUseException extends ShapeTag.Companion[TableInUseException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableInUseException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>A target table with the specified name is either being created or deleted.\n        </p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[TableInUseException] = struct(
    ErrorMessage.schema.optional[TableInUseException]("message", _.message),
  ){
    TableInUseException.apply
  }.withId(id).addHints(hints)
}
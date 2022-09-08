package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TableNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object TableNotFoundException extends ShapeTag.Companion[TableNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>A source table with the name <code>TableName</code> does not currently exist within\n            the subscriber\'s account or the subscriber is operating in the wrong Amazon Web Services Region.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[TableNotFoundException] = struct(
    ErrorMessage.schema.optional[TableNotFoundException]("message", _.message),
  ){
    TableNotFoundException.apply
  }.withId(id).addHints(hints)
}
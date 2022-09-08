package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ResourceInUseException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ResourceInUseException extends ShapeTag.Companion[ResourceInUseException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ResourceInUseException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The operation conflicts with the resource\'s availability. For example, you\n            attempted to recreate an existing table, or tried to delete a table currently in the\n                <code>CREATING</code> state.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ResourceInUseException] = struct(
    ErrorMessage.schema.optional[ResourceInUseException]("message", _.message).addHints(smithy.api.Documentation("<p>The resource which is being attempted to be changed is in use.</p>")),
  ){
    ResourceInUseException.apply
  }.withId(id).addHints(hints)
}
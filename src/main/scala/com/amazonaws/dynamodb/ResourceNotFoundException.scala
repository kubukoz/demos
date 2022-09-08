package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ResourceNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ResourceNotFoundException extends ShapeTag.Companion[ResourceNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ResourceNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The operation tried to access a nonexistent table or index. The resource might not\n            be specified correctly, or its status might not be <code>ACTIVE</code>.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ResourceNotFoundException] = struct(
    ErrorMessage.schema.optional[ResourceNotFoundException]("message", _.message).addHints(smithy.api.Documentation("<p>The resource which is being requested does not exist.</p>")),
  ){
    ResourceNotFoundException.apply
  }.withId(id).addHints(hints)
}
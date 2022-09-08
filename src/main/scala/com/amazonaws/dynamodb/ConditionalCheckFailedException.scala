package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ConditionalCheckFailedException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ConditionalCheckFailedException extends ShapeTag.Companion[ConditionalCheckFailedException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ConditionalCheckFailedException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>A condition specified in the operation could not be evaluated.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ConditionalCheckFailedException] = struct(
    ErrorMessage.schema.optional[ConditionalCheckFailedException]("message", _.message).addHints(smithy.api.Documentation("<p>The conditional request failed.</p>")),
  ){
    ConditionalCheckFailedException.apply
  }.withId(id).addHints(hints)
}
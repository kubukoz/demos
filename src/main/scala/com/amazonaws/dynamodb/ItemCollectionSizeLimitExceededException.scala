package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ItemCollectionSizeLimitExceededException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ItemCollectionSizeLimitExceededException extends ShapeTag.Companion[ItemCollectionSizeLimitExceededException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionSizeLimitExceededException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>An item collection is too large. This exception is only returned for tables that\n            have one or more local secondary indexes.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ItemCollectionSizeLimitExceededException] = struct(
    ErrorMessage.schema.optional[ItemCollectionSizeLimitExceededException]("message", _.message).addHints(smithy.api.Documentation("<p>The total size of an item collection has exceeded the maximum limit of 10\n            gigabytes.</p>")),
  ){
    ItemCollectionSizeLimitExceededException.apply
  }.withId(id).addHints(hints)
}
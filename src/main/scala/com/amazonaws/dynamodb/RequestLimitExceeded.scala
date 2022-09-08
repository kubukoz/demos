package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RequestLimitExceeded(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object RequestLimitExceeded extends ShapeTag.Companion[RequestLimitExceeded] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RequestLimitExceeded")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Throughput exceeds the current throughput quota for your account. Please contact\n                <a href=\"https://aws.amazon.com/support\">Amazon Web Services Support</a> to request a\n            quota increase.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[RequestLimitExceeded] = struct(
    ErrorMessage.schema.optional[RequestLimitExceeded]("message", _.message),
  ){
    RequestLimitExceeded.apply
  }.withId(id).addHints(hints)
}
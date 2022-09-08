package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ProvisionedThroughputExceededException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ProvisionedThroughputExceededException extends ShapeTag.Companion[ProvisionedThroughputExceededException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ProvisionedThroughputExceededException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Your request rate is too high. The Amazon Web Services SDKs for DynamoDB\n            automatically retry requests that receive this exception. Your request is eventually\n            successful, unless your retry queue is too large to finish. Reduce the frequency of\n            requests and use exponential backoff. For more information, go to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Programming.Errors.html#Programming.Errors.RetryAndBackoff\">Error Retries and Exponential Backoff</a> in the <i>Amazon DynamoDB Developer Guide</i>.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ProvisionedThroughputExceededException] = struct(
    ErrorMessage.schema.optional[ProvisionedThroughputExceededException]("message", _.message).addHints(smithy.api.Documentation("<p>You exceeded your maximum allowed provisioned throughput.</p>")),
  ){
    ProvisionedThroughputExceededException.apply
  }.withId(id).addHints(hints)
}
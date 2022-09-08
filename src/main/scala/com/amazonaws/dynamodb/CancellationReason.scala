package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CancellationReason(item: Option[Map[AttributeName,AttributeValue]] = None, code: Option[Code] = None, message: Option[ErrorMessage] = None)
object CancellationReason extends ShapeTag.Companion[CancellationReason] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CancellationReason")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>An ordered list of errors for each item in the request which caused the transaction to\n            get cancelled. The values of the list are ordered according to the ordering of the\n                <code>TransactWriteItems</code> request parameter. If no error occurred for the\n            associated item an error with a Null code and Null message will be present. </p>"),
  )

  implicit val schema: Schema[CancellationReason] = struct(
    AttributeMap.underlyingSchema.optional[CancellationReason]("Item", _.item).addHints(smithy.api.Documentation("<p>Item in the request which caused the transaction to get cancelled.</p>")),
    Code.schema.optional[CancellationReason]("Code", _.code).addHints(smithy.api.Documentation("<p>Status code for the result of the cancelled transaction.</p>")),
    ErrorMessage.schema.optional[CancellationReason]("Message", _.message).addHints(smithy.api.Documentation("<p>Cancellation reason message description.</p>")),
  ){
    CancellationReason.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactGetItemsInput(transactItems: List[TransactGetItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None)
object TransactGetItemsInput extends ShapeTag.Companion[TransactGetItemsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactGetItemsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TransactGetItemsInput] = struct(
    TransactGetItemList.underlyingSchema.required[TransactGetItemsInput]("TransactItems", _.transactItems).addHints(smithy.api.Documentation("<p>An ordered array of up to 25 <code>TransactGetItem</code> objects, each of which\n            contains a <code>Get</code> structure.</p>"), smithy.api.Required()),
    ReturnConsumedCapacity.schema.optional[TransactGetItemsInput]("ReturnConsumedCapacity", _.returnConsumedCapacity).addHints(smithy.api.Documentation("<p>A value of <code>TOTAL</code> causes consumed capacity information to be returned, and\n            a value of <code>NONE</code> prevents that information from being returned. No other\n            value is valid.</p>")),
  ){
    TransactGetItemsInput.apply
  }.withId(id).addHints(hints)
}
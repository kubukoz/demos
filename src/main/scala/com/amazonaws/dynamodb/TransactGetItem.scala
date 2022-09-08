package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactGetItem(get: Get)
object TransactGetItem extends ShapeTag.Companion[TransactGetItem] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactGetItem")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Specifies an item to be retrieved as part of the transaction.</p>"),
  )

  implicit val schema: Schema[TransactGetItem] = struct(
    Get.schema.required[TransactGetItem]("Get", _.get).addHints(smithy.api.Documentation("<p>Contains the primary key that identifies the item to get, together with the name of\n            the table that contains the item, and optionally the specific attributes of the item to\n            retrieve.</p>"), smithy.api.Required()),
  ){
    TransactGetItem.apply
  }.withId(id).addHints(hints)
}
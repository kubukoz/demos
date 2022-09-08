package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactWriteItem(conditionCheck: Option[ConditionCheck] = None, put: Option[Put] = None, delete: Option[Delete] = None, update: Option[Update] = None)
object TransactWriteItem extends ShapeTag.Companion[TransactWriteItem] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItem")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>A list of requests that can perform update, put, delete, or check operations on\n            multiple items in one or more tables atomically.</p>"),
  )

  implicit val schema: Schema[TransactWriteItem] = struct(
    ConditionCheck.schema.optional[TransactWriteItem]("ConditionCheck", _.conditionCheck).addHints(smithy.api.Documentation("<p>A request to perform a check item operation.</p>")),
    Put.schema.optional[TransactWriteItem]("Put", _.put).addHints(smithy.api.Documentation("<p>A request to perform a <code>PutItem</code> operation.</p>")),
    Delete.schema.optional[TransactWriteItem]("Delete", _.delete).addHints(smithy.api.Documentation("<p>A request to perform a <code>DeleteItem</code> operation.</p>")),
    Update.schema.optional[TransactWriteItem]("Update", _.update).addHints(smithy.api.Documentation("<p>A request to perform an <code>UpdateItem</code> operation.</p>")),
  ){
    TransactWriteItem.apply
  }.withId(id).addHints(hints)
}
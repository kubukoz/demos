package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object TransactWriteItemList extends Newtype[List[TransactWriteItem]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItemList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[TransactWriteItem]] = list(TransactWriteItem.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(25)))
  implicit val schema : Schema[TransactWriteItemList] = bijection(underlyingSchema, asBijection)
}
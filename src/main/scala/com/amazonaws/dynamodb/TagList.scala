package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object TagList extends Newtype[List[Tag]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TagList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[Tag]] = list(Tag.schema).withId(id).addHints(hints)
  implicit val schema : Schema[TagList] = bijection(underlyingSchema, asBijection)
}
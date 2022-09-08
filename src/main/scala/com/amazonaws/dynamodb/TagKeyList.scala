package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object TagKeyList extends Newtype[List[TagKeyString]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TagKeyList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[TagKeyString]] = list(TagKeyString.schema).withId(id).addHints(hints)
  implicit val schema : Schema[TagKeyList] = bijection(underlyingSchema, asBijection)
}
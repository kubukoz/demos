package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object LocalSecondaryIndexDescriptionList extends Newtype[List[LocalSecondaryIndexDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndexDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[LocalSecondaryIndexDescription]] = list(LocalSecondaryIndexDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[LocalSecondaryIndexDescriptionList] = bijection(underlyingSchema, asBijection)
}
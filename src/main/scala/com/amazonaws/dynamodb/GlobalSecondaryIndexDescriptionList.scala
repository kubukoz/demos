package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalSecondaryIndexDescriptionList extends Newtype[List[GlobalSecondaryIndexDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[GlobalSecondaryIndexDescription]] = list(GlobalSecondaryIndexDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[GlobalSecondaryIndexDescriptionList] = bijection(underlyingSchema, asBijection)
}
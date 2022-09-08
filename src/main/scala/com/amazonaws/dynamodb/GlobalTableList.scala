package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalTableList extends Newtype[List[GlobalTable]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[GlobalTable]] = list(GlobalTable.schema).withId(id).addHints(hints)
  implicit val schema : Schema[GlobalTableList] = bijection(underlyingSchema, asBijection)
}
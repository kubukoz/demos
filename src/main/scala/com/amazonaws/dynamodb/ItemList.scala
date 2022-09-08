package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ItemList extends Newtype[List[Map[AttributeName,AttributeValue]]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[Map[AttributeName,AttributeValue]]] = list(AttributeMap.underlyingSchema).withId(id).addHints(hints)
  implicit val schema : Schema[ItemList] = bijection(underlyingSchema, asBijection)
}
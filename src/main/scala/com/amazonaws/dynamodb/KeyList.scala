package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object KeyList extends Newtype[List[Map[AttributeName,AttributeValue]]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KeyList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[Map[AttributeName,AttributeValue]]] = list(Key.underlyingSchema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(100)))
  implicit val schema : Schema[KeyList] = bijection(underlyingSchema, asBijection)
}
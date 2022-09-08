package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object StringSetAttributeValue extends Newtype[List[StringAttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "StringSetAttributeValue")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[StringAttributeValue]] = list(StringAttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[StringSetAttributeValue] = bijection(underlyingSchema, asBijection)
}
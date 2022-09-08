package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object NumberSetAttributeValue extends Newtype[List[NumberAttributeValue]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "NumberSetAttributeValue")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[NumberAttributeValue]] = list(NumberAttributeValue.schema).withId(id).addHints(hints)
  implicit val schema : Schema[NumberSetAttributeValue] = bijection(underlyingSchema, asBijection)
}
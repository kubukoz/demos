package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ResourceArnString extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ResourceArnString")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(1283)))
  implicit val schema : Schema[ResourceArnString] = bijection(underlyingSchema, asBijection)
}
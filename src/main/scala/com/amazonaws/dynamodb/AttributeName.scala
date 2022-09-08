package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object AttributeName extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeName")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(0), max = Some(65535)))
  implicit val schema : Schema[AttributeName] = bijection(underlyingSchema, asBijection)
}
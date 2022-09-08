package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object IndexName extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "IndexName")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(3), max = Some(255))).validated(smithy.api.Pattern("^[a-zA-Z0-9_.-]+$"))
  implicit val schema : Schema[IndexName] = bijection(underlyingSchema, asBijection)
}
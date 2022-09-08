package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object Code extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Code")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints)
  implicit val schema : Schema[Code] = bijection(underlyingSchema, asBijection)
}
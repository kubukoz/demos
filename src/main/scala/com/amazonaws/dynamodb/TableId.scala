package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object TableId extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableId")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Pattern("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"))
  implicit val schema : Schema[TableId] = bijection(underlyingSchema, asBijection)
}
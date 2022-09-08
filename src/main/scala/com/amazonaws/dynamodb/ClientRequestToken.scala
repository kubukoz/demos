package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ClientRequestToken extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ClientRequestToken")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(36)))
  implicit val schema : Schema[ClientRequestToken] = bijection(underlyingSchema, asBijection)
}
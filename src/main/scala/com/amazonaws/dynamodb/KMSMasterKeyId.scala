package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object KMSMasterKeyId extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KMSMasterKeyId")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints)
  implicit val schema : Schema[KMSMasterKeyId] = bijection(underlyingSchema, asBijection)
}
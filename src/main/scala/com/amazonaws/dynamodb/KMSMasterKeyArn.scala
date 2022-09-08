package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object KMSMasterKeyArn extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KMSMasterKeyArn")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints)
  implicit val schema : Schema[KMSMasterKeyArn] = bijection(underlyingSchema, asBijection)
}
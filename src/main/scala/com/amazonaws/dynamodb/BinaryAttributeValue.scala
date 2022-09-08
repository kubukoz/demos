package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.ByteArray
import smithy4s.Hints
import smithy4s.schema.Schema.bytes
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BinaryAttributeValue extends Newtype[ByteArray] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BinaryAttributeValue")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[ByteArray] = bytes.withId(id).addHints(hints)
  implicit val schema : Schema[BinaryAttributeValue] = bijection(underlyingSchema, asBijection)
}
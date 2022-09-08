package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.long
import smithy4s.Newtype

object ItemCount extends Newtype[Long] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCount")
  val hints : Hints = Hints(
    smithy.api.Box(),
  )
  val underlyingSchema : Schema[Long] = long.withId(id).addHints(hints).validated(smithy.api.Range(min = Some(scala.math.BigDecimal(0.0)), max = None))
  implicit val schema : Schema[ItemCount] = bijection(underlyingSchema, asBijection)
}
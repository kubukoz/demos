package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ListExportsMaxLimit extends Newtype[Int] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListExportsMaxLimit")
  val hints : Hints = Hints(
    smithy.api.Box(),
  )
  val underlyingSchema : Schema[Int] = int.withId(id).addHints(hints).validated(smithy.api.Range(min = Some(scala.math.BigDecimal(1.0)), max = Some(scala.math.BigDecimal(25.0))))
  implicit val schema : Schema[ListExportsMaxLimit] = bijection(underlyingSchema, asBijection)
}
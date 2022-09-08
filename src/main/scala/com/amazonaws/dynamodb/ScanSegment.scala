package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ScanSegment extends Newtype[Int] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ScanSegment")
  val hints : Hints = Hints(
    smithy.api.Box(),
  )
  val underlyingSchema : Schema[Int] = int.withId(id).addHints(hints).validated(smithy.api.Range(min = Some(scala.math.BigDecimal(0.0)), max = Some(scala.math.BigDecimal(999999.0))))
  implicit val schema : Schema[ScanSegment] = bijection(underlyingSchema, asBijection)
}
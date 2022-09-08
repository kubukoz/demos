package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ListContributorInsightsLimit extends Newtype[Int] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListContributorInsightsLimit")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[Int] = int.withId(id).addHints(hints).validated(smithy.api.Range(min = None, max = Some(scala.math.BigDecimal(100.0))))
  implicit val schema : Schema[ListContributorInsightsLimit] = bijection(underlyingSchema, asBijection)
}
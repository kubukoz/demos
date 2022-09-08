package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ContributorInsightsRule extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContributorInsightsRule")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Pattern("^[A-Za-z0-9][A-Za-z0-9\\-\\_\\.]{0,126}[A-Za-z0-9]$"))
  implicit val schema : Schema[ContributorInsightsRule] = bijection(underlyingSchema, asBijection)
}
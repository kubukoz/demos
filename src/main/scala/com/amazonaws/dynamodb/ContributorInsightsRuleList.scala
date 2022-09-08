package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ContributorInsightsRuleList extends Newtype[List[ContributorInsightsRule]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContributorInsightsRuleList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ContributorInsightsRule]] = list(ContributorInsightsRule.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ContributorInsightsRuleList] = bijection(underlyingSchema, asBijection)
}
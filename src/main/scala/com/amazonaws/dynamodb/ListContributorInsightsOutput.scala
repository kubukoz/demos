package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListContributorInsightsOutput(contributorInsightsSummaries: Option[List[ContributorInsightsSummary]] = None, nextToken: Option[NextTokenString] = None)
object ListContributorInsightsOutput extends ShapeTag.Companion[ListContributorInsightsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListContributorInsightsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListContributorInsightsOutput] = struct(
    ContributorInsightsSummaries.underlyingSchema.optional[ListContributorInsightsOutput]("ContributorInsightsSummaries", _.contributorInsightsSummaries).addHints(smithy.api.Documentation("<p>A list of ContributorInsightsSummary.</p>")),
    NextTokenString.schema.optional[ListContributorInsightsOutput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>A token to go to the next page if there is one.</p>")),
  ){
    ListContributorInsightsOutput.apply
  }.withId(id).addHints(hints)
}
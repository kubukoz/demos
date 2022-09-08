package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ContributorInsightsSummary(tableName: Option[TableName] = None, indexName: Option[IndexName] = None, contributorInsightsStatus: Option[ContributorInsightsStatus] = None)
object ContributorInsightsSummary extends ShapeTag.Companion[ContributorInsightsSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContributorInsightsSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a Contributor Insights summary entry.</p>"),
  )

  implicit val schema: Schema[ContributorInsightsSummary] = struct(
    TableName.schema.optional[ContributorInsightsSummary]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table associated with the summary.</p>")),
    IndexName.schema.optional[ContributorInsightsSummary]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>Name of the index associated with the summary, if any.</p>")),
    ContributorInsightsStatus.schema.optional[ContributorInsightsSummary]("ContributorInsightsStatus", _.contributorInsightsStatus).addHints(smithy.api.Documentation("<p>Describes the current status for contributor insights for the given table and index,\n            if applicable.</p>")),
  ){
    ContributorInsightsSummary.apply
  }.withId(id).addHints(hints)
}
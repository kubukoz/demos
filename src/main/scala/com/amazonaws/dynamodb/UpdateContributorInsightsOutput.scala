package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateContributorInsightsOutput(tableName: Option[TableName] = None, indexName: Option[IndexName] = None, contributorInsightsStatus: Option[ContributorInsightsStatus] = None)
object UpdateContributorInsightsOutput extends ShapeTag.Companion[UpdateContributorInsightsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContributorInsightsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateContributorInsightsOutput] = struct(
    TableName.schema.optional[UpdateContributorInsightsOutput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>")),
    IndexName.schema.optional[UpdateContributorInsightsOutput]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index, if applicable.</p>")),
    ContributorInsightsStatus.schema.optional[UpdateContributorInsightsOutput]("ContributorInsightsStatus", _.contributorInsightsStatus).addHints(smithy.api.Documentation("<p>The status of contributor insights</p>")),
  ){
    UpdateContributorInsightsOutput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeContributorInsightsOutput(tableName: Option[TableName] = None, indexName: Option[IndexName] = None, contributorInsightsRuleList: Option[List[ContributorInsightsRule]] = None, contributorInsightsStatus: Option[ContributorInsightsStatus] = None, lastUpdateDateTime: Option[LastUpdateDateTime] = None, failureException: Option[FailureException] = None)
object DescribeContributorInsightsOutput extends ShapeTag.Companion[DescribeContributorInsightsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContributorInsightsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeContributorInsightsOutput] = struct(
    TableName.schema.optional[DescribeContributorInsightsOutput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table being described.</p>")),
    IndexName.schema.optional[DescribeContributorInsightsOutput]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index being described.</p>")),
    ContributorInsightsRuleList.underlyingSchema.optional[DescribeContributorInsightsOutput]("ContributorInsightsRuleList", _.contributorInsightsRuleList).addHints(smithy.api.Documentation("<p>List of names of the associated contributor insights rules.</p>")),
    ContributorInsightsStatus.schema.optional[DescribeContributorInsightsOutput]("ContributorInsightsStatus", _.contributorInsightsStatus).addHints(smithy.api.Documentation("<p>Current status of contributor insights.</p>")),
    LastUpdateDateTime.schema.optional[DescribeContributorInsightsOutput]("LastUpdateDateTime", _.lastUpdateDateTime).addHints(smithy.api.Documentation("<p>Timestamp of the last time the status was changed.</p>")),
    FailureException.schema.optional[DescribeContributorInsightsOutput]("FailureException", _.failureException).addHints(smithy.api.Documentation("<p>Returns information about the last failure that was encountered.</p>\n        <p>The most common exceptions for a FAILED status are:</p>\n        <ul>\n            <li>\n                <p>LimitExceededException - Per-account Amazon CloudWatch Contributor Insights\n                    rule limit reached. Please disable Contributor Insights for other tables/indexes\n                    OR disable Contributor Insights rules before retrying.</p>\n            </li>\n            <li>\n                <p>AccessDeniedException - Amazon CloudWatch Contributor Insights rules cannot be\n                    modified due to insufficient permissions.</p>\n            </li>\n            <li>\n                <p>AccessDeniedException - Failed to create service-linked role for Contributor\n                    Insights due to insufficient permissions.</p>\n            </li>\n            <li>\n                <p>InternalServerError - Failed to create Amazon CloudWatch Contributor Insights\n                    rules. Please retry request.</p>\n            </li>\n         </ul>")),
  ){
    DescribeContributorInsightsOutput.apply
  }.withId(id).addHints(hints)
}
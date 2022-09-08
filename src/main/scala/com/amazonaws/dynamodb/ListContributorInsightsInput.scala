package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListContributorInsightsInput(maxResults: ListContributorInsightsLimit = com.amazonaws.dynamodb.ListContributorInsightsLimit(0), tableName: Option[TableName] = None, nextToken: Option[NextTokenString] = None)
object ListContributorInsightsInput extends ShapeTag.Companion[ListContributorInsightsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListContributorInsightsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListContributorInsightsInput] = struct(
    ListContributorInsightsLimit.schema.required[ListContributorInsightsInput]("MaxResults", _.maxResults).addHints(smithy.api.Documentation("<p>Maximum number of results to return per page.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    TableName.schema.optional[ListContributorInsightsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>")),
    NextTokenString.schema.optional[ListContributorInsightsInput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>A token to for the desired page, if there is one.</p>")),
  ){
    ListContributorInsightsInput.apply
  }.withId(id).addHints(hints)
}
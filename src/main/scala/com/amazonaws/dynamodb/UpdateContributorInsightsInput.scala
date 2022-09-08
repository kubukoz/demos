package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateContributorInsightsInput(tableName: TableName, contributorInsightsAction: ContributorInsightsAction, indexName: Option[IndexName] = None)
object UpdateContributorInsightsInput extends ShapeTag.Companion[UpdateContributorInsightsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContributorInsightsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateContributorInsightsInput] = struct(
    TableName.schema.required[UpdateContributorInsightsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>"), smithy.api.Required()),
    ContributorInsightsAction.schema.required[UpdateContributorInsightsInput]("ContributorInsightsAction", _.contributorInsightsAction).addHints(smithy.api.Documentation("<p>Represents the contributor insights action.</p>"), smithy.api.Required()),
    IndexName.schema.optional[UpdateContributorInsightsInput]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The global secondary index name, if applicable.</p>")),
  ){
    UpdateContributorInsightsInput.apply
  }.withId(id).addHints(hints)
}
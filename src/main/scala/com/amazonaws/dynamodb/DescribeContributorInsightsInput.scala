package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeContributorInsightsInput(tableName: TableName, indexName: Option[IndexName] = None)
object DescribeContributorInsightsInput extends ShapeTag.Companion[DescribeContributorInsightsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContributorInsightsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeContributorInsightsInput] = struct(
    TableName.schema.required[DescribeContributorInsightsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table to describe.</p>"), smithy.api.Required()),
    IndexName.schema.optional[DescribeContributorInsightsInput]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index to describe, if applicable.</p>")),
  ){
    DescribeContributorInsightsInput.apply
  }.withId(id).addHints(hints)
}
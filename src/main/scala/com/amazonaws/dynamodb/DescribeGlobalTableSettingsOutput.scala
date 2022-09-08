package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeGlobalTableSettingsOutput(globalTableName: Option[TableName] = None, replicaSettings: Option[List[ReplicaSettingsDescription]] = None)
object DescribeGlobalTableSettingsOutput extends ShapeTag.Companion[DescribeGlobalTableSettingsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableSettingsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeGlobalTableSettingsOutput] = struct(
    TableName.schema.optional[DescribeGlobalTableSettingsOutput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The name of the global table.</p>")),
    ReplicaSettingsDescriptionList.underlyingSchema.optional[DescribeGlobalTableSettingsOutput]("ReplicaSettings", _.replicaSettings).addHints(smithy.api.Documentation("<p>The Region-specific settings for the global table.</p>")),
  ){
    DescribeGlobalTableSettingsOutput.apply
  }.withId(id).addHints(hints)
}
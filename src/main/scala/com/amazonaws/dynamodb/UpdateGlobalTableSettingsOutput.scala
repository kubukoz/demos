package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateGlobalTableSettingsOutput(globalTableName: Option[TableName] = None, replicaSettings: Option[List[ReplicaSettingsDescription]] = None)
object UpdateGlobalTableSettingsOutput extends ShapeTag.Companion[UpdateGlobalTableSettingsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableSettingsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateGlobalTableSettingsOutput] = struct(
    TableName.schema.optional[UpdateGlobalTableSettingsOutput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The name of the global table.</p>")),
    ReplicaSettingsDescriptionList.underlyingSchema.optional[UpdateGlobalTableSettingsOutput]("ReplicaSettings", _.replicaSettings).addHints(smithy.api.Documentation("<p>The Region-specific settings for the global table.</p>")),
  ){
    UpdateGlobalTableSettingsOutput.apply
  }.withId(id).addHints(hints)
}
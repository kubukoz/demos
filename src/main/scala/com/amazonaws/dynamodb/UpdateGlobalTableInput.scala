package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateGlobalTableInput(globalTableName: TableName, replicaUpdates: List[ReplicaUpdate])
object UpdateGlobalTableInput extends ShapeTag.Companion[UpdateGlobalTableInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateGlobalTableInput] = struct(
    TableName.schema.required[UpdateGlobalTableInput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The global table name.</p>"), smithy.api.Required()),
    ReplicaUpdateList.underlyingSchema.required[UpdateGlobalTableInput]("ReplicaUpdates", _.replicaUpdates).addHints(smithy.api.Documentation("<p>A list of Regions that should be added or removed from the global table.</p>"), smithy.api.Required()),
  ){
    UpdateGlobalTableInput.apply
  }.withId(id).addHints(hints)
}
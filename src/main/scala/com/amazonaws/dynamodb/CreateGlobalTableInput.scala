package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateGlobalTableInput(globalTableName: TableName, replicationGroup: List[Replica])
object CreateGlobalTableInput extends ShapeTag.Companion[CreateGlobalTableInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateGlobalTableInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[CreateGlobalTableInput] = struct(
    TableName.schema.required[CreateGlobalTableInput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The global table name.</p>"), smithy.api.Required()),
    ReplicaList.underlyingSchema.required[CreateGlobalTableInput]("ReplicationGroup", _.replicationGroup).addHints(smithy.api.Documentation("<p>The Regions where the global table needs to be created.</p>"), smithy.api.Required()),
  ){
    CreateGlobalTableInput.apply
  }.withId(id).addHints(hints)
}
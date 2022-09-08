package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalTable(globalTableName: Option[TableName] = None, replicationGroup: Option[List[Replica]] = None)
object GlobalTable extends ShapeTag.Companion[GlobalTable] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTable")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a global table.</p>"),
  )

  implicit val schema: Schema[GlobalTable] = struct(
    TableName.schema.optional[GlobalTable]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The global table name.</p>")),
    ReplicaList.underlyingSchema.optional[GlobalTable]("ReplicationGroup", _.replicationGroup).addHints(smithy.api.Documentation("<p>The Regions where the global table has replicas.</p>")),
  ){
    GlobalTable.apply
  }.withId(id).addHints(hints)
}
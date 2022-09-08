package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateTableReplicaAutoScalingOutput(tableAutoScalingDescription: Option[TableAutoScalingDescription] = None)
object UpdateTableReplicaAutoScalingOutput extends ShapeTag.Companion[UpdateTableReplicaAutoScalingOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableReplicaAutoScalingOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateTableReplicaAutoScalingOutput] = struct(
    TableAutoScalingDescription.schema.optional[UpdateTableReplicaAutoScalingOutput]("TableAutoScalingDescription", _.tableAutoScalingDescription).addHints(smithy.api.Documentation("<p>Returns information about the auto scaling settings of a table with replicas.</p>")),
  ){
    UpdateTableReplicaAutoScalingOutput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTableReplicaAutoScalingOutput(tableAutoScalingDescription: Option[TableAutoScalingDescription] = None)
object DescribeTableReplicaAutoScalingOutput extends ShapeTag.Companion[DescribeTableReplicaAutoScalingOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableReplicaAutoScalingOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeTableReplicaAutoScalingOutput] = struct(
    TableAutoScalingDescription.schema.optional[DescribeTableReplicaAutoScalingOutput]("TableAutoScalingDescription", _.tableAutoScalingDescription).addHints(smithy.api.Documentation("<p>Represents the auto scaling properties of the table.</p>")),
  ){
    DescribeTableReplicaAutoScalingOutput.apply
  }.withId(id).addHints(hints)
}
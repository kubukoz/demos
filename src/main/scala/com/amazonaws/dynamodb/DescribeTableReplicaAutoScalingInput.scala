package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTableReplicaAutoScalingInput(tableName: TableName)
object DescribeTableReplicaAutoScalingInput extends ShapeTag.Companion[DescribeTableReplicaAutoScalingInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableReplicaAutoScalingInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeTableReplicaAutoScalingInput] = struct(
    TableName.schema.required[DescribeTableReplicaAutoScalingInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>"), smithy.api.Required()),
  ){
    DescribeTableReplicaAutoScalingInput.apply
  }.withId(id).addHints(hints)
}
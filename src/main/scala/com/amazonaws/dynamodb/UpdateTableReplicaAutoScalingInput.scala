package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateTableReplicaAutoScalingInput(tableName: TableName, globalSecondaryIndexUpdates: Option[List[GlobalSecondaryIndexAutoScalingUpdate]] = None, provisionedWriteCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None, replicaUpdates: Option[List[ReplicaAutoScalingUpdate]] = None)
object UpdateTableReplicaAutoScalingInput extends ShapeTag.Companion[UpdateTableReplicaAutoScalingInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableReplicaAutoScalingInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateTableReplicaAutoScalingInput] = struct(
    TableName.schema.required[UpdateTableReplicaAutoScalingInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the global table to be updated.</p>"), smithy.api.Required()),
    GlobalSecondaryIndexAutoScalingUpdateList.underlyingSchema.optional[UpdateTableReplicaAutoScalingInput]("GlobalSecondaryIndexUpdates", _.globalSecondaryIndexUpdates).addHints(smithy.api.Documentation("<p>Represents the auto scaling settings of the global secondary indexes of the replica to\n            be updated.</p>")),
    AutoScalingSettingsUpdate.schema.optional[UpdateTableReplicaAutoScalingInput]("ProvisionedWriteCapacityAutoScalingUpdate", _.provisionedWriteCapacityAutoScalingUpdate),
    ReplicaAutoScalingUpdateList.underlyingSchema.optional[UpdateTableReplicaAutoScalingInput]("ReplicaUpdates", _.replicaUpdates).addHints(smithy.api.Documentation("<p>Represents the auto scaling settings of replicas of the table that will be\n            modified.</p>")),
  ){
    UpdateTableReplicaAutoScalingInput.apply
  }.withId(id).addHints(hints)
}
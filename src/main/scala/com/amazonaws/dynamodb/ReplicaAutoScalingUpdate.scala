package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaAutoScalingUpdate(regionName: RegionName, replicaGlobalSecondaryIndexUpdates: Option[List[ReplicaGlobalSecondaryIndexAutoScalingUpdate]] = None, replicaProvisionedReadCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None)
object ReplicaAutoScalingUpdate extends ShapeTag.Companion[ReplicaAutoScalingUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaAutoScalingUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings of a replica that will be modified.</p>"),
  )

  implicit val schema: Schema[ReplicaAutoScalingUpdate] = struct(
    RegionName.schema.required[ReplicaAutoScalingUpdate]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the replica exists.</p>"), smithy.api.Required()),
    ReplicaGlobalSecondaryIndexAutoScalingUpdateList.underlyingSchema.optional[ReplicaAutoScalingUpdate]("ReplicaGlobalSecondaryIndexUpdates", _.replicaGlobalSecondaryIndexUpdates).addHints(smithy.api.Documentation("<p>Represents the auto scaling settings of global secondary indexes that will be\n            modified.</p>")),
    AutoScalingSettingsUpdate.schema.optional[ReplicaAutoScalingUpdate]("ReplicaProvisionedReadCapacityAutoScalingUpdate", _.replicaProvisionedReadCapacityAutoScalingUpdate),
  ){
    ReplicaAutoScalingUpdate.apply
  }.withId(id).addHints(hints)
}
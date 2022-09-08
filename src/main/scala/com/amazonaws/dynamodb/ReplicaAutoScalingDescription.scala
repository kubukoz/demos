package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaAutoScalingDescription(regionName: Option[RegionName] = None, globalSecondaryIndexes: Option[List[ReplicaGlobalSecondaryIndexAutoScalingDescription]] = None, replicaProvisionedReadCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, replicaProvisionedWriteCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, replicaStatus: Option[ReplicaStatus] = None)
object ReplicaAutoScalingDescription extends ShapeTag.Companion[ReplicaAutoScalingDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaAutoScalingDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings of the replica.</p>"),
  )

  implicit val schema: Schema[ReplicaAutoScalingDescription] = struct(
    RegionName.schema.optional[ReplicaAutoScalingDescription]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the replica exists.</p>")),
    ReplicaGlobalSecondaryIndexAutoScalingDescriptionList.underlyingSchema.optional[ReplicaAutoScalingDescription]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>Replica-specific global secondary index auto scaling settings.</p>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaAutoScalingDescription]("ReplicaProvisionedReadCapacityAutoScalingSettings", _.replicaProvisionedReadCapacityAutoScalingSettings),
    AutoScalingSettingsDescription.schema.optional[ReplicaAutoScalingDescription]("ReplicaProvisionedWriteCapacityAutoScalingSettings", _.replicaProvisionedWriteCapacityAutoScalingSettings),
    ReplicaStatus.schema.optional[ReplicaAutoScalingDescription]("ReplicaStatus", _.replicaStatus).addHints(smithy.api.Documentation("<p>The current state of the replica:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The replica is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The replica is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The replica is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The replica is ready for use.</p>\n            </li>\n         </ul>")),
  ){
    ReplicaAutoScalingDescription.apply
  }.withId(id).addHints(hints)
}
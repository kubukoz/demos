package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndexAutoScalingDescription(indexName: Option[IndexName] = None, indexStatus: Option[IndexStatus] = None, provisionedReadCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, provisionedWriteCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None)
object ReplicaGlobalSecondaryIndexAutoScalingDescription extends ShapeTag.Companion[ReplicaGlobalSecondaryIndexAutoScalingDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexAutoScalingDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling configuration for a replica global secondary index.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndexAutoScalingDescription] = struct(
    IndexName.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingDescription]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>")),
    IndexStatus.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingDescription]("IndexStatus", _.indexStatus).addHints(smithy.api.Documentation("<p>The current state of the replica global secondary index:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The index is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The index is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The index is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The index is ready for use.</p>\n            </li>\n         </ul>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingDescription]("ProvisionedReadCapacityAutoScalingSettings", _.provisionedReadCapacityAutoScalingSettings),
    AutoScalingSettingsDescription.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingDescription]("ProvisionedWriteCapacityAutoScalingSettings", _.provisionedWriteCapacityAutoScalingSettings),
  ){
    ReplicaGlobalSecondaryIndexAutoScalingDescription.apply
  }.withId(id).addHints(hints)
}
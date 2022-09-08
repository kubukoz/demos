package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndexSettingsDescription(indexName: IndexName, indexStatus: Option[IndexStatus] = None, provisionedReadCapacityUnits: Option[PositiveLongObject] = None, provisionedReadCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, provisionedWriteCapacityUnits: Option[PositiveLongObject] = None, provisionedWriteCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None)
object ReplicaGlobalSecondaryIndexSettingsDescription extends ShapeTag.Companion[ReplicaGlobalSecondaryIndexSettingsDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexSettingsDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a global secondary index.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndexSettingsDescription] = struct(
    IndexName.schema.required[ReplicaGlobalSecondaryIndexSettingsDescription]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index. The name must be unique among all other\n            indexes on this table.</p>"), smithy.api.Required()),
    IndexStatus.schema.optional[ReplicaGlobalSecondaryIndexSettingsDescription]("IndexStatus", _.indexStatus).addHints(smithy.api.Documentation("<p> The current status of the global secondary index:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The global secondary index is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The global secondary index is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The global secondary index is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The global secondary index is ready for use.</p>\n            </li>\n         </ul>")),
    PositiveLongObject.schema.optional[ReplicaGlobalSecondaryIndexSettingsDescription]("ProvisionedReadCapacityUnits", _.provisionedReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>.</p>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaGlobalSecondaryIndexSettingsDescription]("ProvisionedReadCapacityAutoScalingSettings", _.provisionedReadCapacityAutoScalingSettings).addHints(smithy.api.Documentation("<p>Auto scaling settings for a global secondary index replica\'s read capacity\n            units.</p>")),
    PositiveLongObject.schema.optional[ReplicaGlobalSecondaryIndexSettingsDescription]("ProvisionedWriteCapacityUnits", _.provisionedWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException</code>.</p>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaGlobalSecondaryIndexSettingsDescription]("ProvisionedWriteCapacityAutoScalingSettings", _.provisionedWriteCapacityAutoScalingSettings).addHints(smithy.api.Documentation("<p>Auto scaling settings for a global secondary index replica\'s write capacity\n            units.</p>")),
  ){
    ReplicaGlobalSecondaryIndexSettingsDescription.apply
  }.withId(id).addHints(hints)
}
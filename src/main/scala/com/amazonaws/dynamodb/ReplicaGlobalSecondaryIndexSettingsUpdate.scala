package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndexSettingsUpdate(indexName: IndexName, provisionedReadCapacityUnits: Option[PositiveLongObject] = None, provisionedReadCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None)
object ReplicaGlobalSecondaryIndexSettingsUpdate extends ShapeTag.Companion[ReplicaGlobalSecondaryIndexSettingsUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexSettingsUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings of a global secondary index for a global table that will be\n            modified.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndexSettingsUpdate] = struct(
    IndexName.schema.required[ReplicaGlobalSecondaryIndexSettingsUpdate]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index. The name must be unique among all other\n            indexes on this table.</p>"), smithy.api.Required()),
    PositiveLongObject.schema.optional[ReplicaGlobalSecondaryIndexSettingsUpdate]("ProvisionedReadCapacityUnits", _.provisionedReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>.</p>")),
    AutoScalingSettingsUpdate.schema.optional[ReplicaGlobalSecondaryIndexSettingsUpdate]("ProvisionedReadCapacityAutoScalingSettingsUpdate", _.provisionedReadCapacityAutoScalingSettingsUpdate).addHints(smithy.api.Documentation("<p>Auto scaling settings for managing a global secondary index replica\'s read capacity\n            units.</p>")),
  ){
    ReplicaGlobalSecondaryIndexSettingsUpdate.apply
  }.withId(id).addHints(hints)
}
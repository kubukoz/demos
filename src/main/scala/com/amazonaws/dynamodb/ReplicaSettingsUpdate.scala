package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaSettingsUpdate(regionName: RegionName, replicaProvisionedReadCapacityUnits: Option[PositiveLongObject] = None, replicaProvisionedReadCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None, replicaGlobalSecondaryIndexSettingsUpdate: Option[List[ReplicaGlobalSecondaryIndexSettingsUpdate]] = None, replicaTableClass: Option[TableClass] = None)
object ReplicaSettingsUpdate extends ShapeTag.Companion[ReplicaSettingsUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaSettingsUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings for a global table in a Region that will be modified.</p>"),
  )

  implicit val schema: Schema[ReplicaSettingsUpdate] = struct(
    RegionName.schema.required[ReplicaSettingsUpdate]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region of the replica to be added.</p>"), smithy.api.Required()),
    PositiveLongObject.schema.optional[ReplicaSettingsUpdate]("ReplicaProvisionedReadCapacityUnits", _.replicaProvisionedReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput\">Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB\n                Developer Guide</i>. </p>")),
    AutoScalingSettingsUpdate.schema.optional[ReplicaSettingsUpdate]("ReplicaProvisionedReadCapacityAutoScalingSettingsUpdate", _.replicaProvisionedReadCapacityAutoScalingSettingsUpdate).addHints(smithy.api.Documentation("<p>Auto scaling settings for managing a global table replica\'s read capacity\n            units.</p>")),
    ReplicaGlobalSecondaryIndexSettingsUpdateList.underlyingSchema.optional[ReplicaSettingsUpdate]("ReplicaGlobalSecondaryIndexSettingsUpdate", _.replicaGlobalSecondaryIndexSettingsUpdate).addHints(smithy.api.Documentation("<p>Represents the settings of a global secondary index for a global table that will be\n            modified.</p>")),
    TableClass.schema.optional[ReplicaSettingsUpdate]("ReplicaTableClass", _.replicaTableClass).addHints(smithy.api.Documentation("<p>Replica-specific table class. If not specified, uses the source table\'s\n            table class.</p>")),
  ){
    ReplicaSettingsUpdate.apply
  }.withId(id).addHints(hints)
}
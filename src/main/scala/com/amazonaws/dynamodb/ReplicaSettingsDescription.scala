package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaSettingsDescription(regionName: RegionName, replicaStatus: Option[ReplicaStatus] = None, replicaBillingModeSummary: Option[BillingModeSummary] = None, replicaProvisionedReadCapacityUnits: Option[NonNegativeLongObject] = None, replicaProvisionedReadCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, replicaProvisionedWriteCapacityUnits: Option[NonNegativeLongObject] = None, replicaProvisionedWriteCapacityAutoScalingSettings: Option[AutoScalingSettingsDescription] = None, replicaGlobalSecondaryIndexSettings: Option[List[ReplicaGlobalSecondaryIndexSettingsDescription]] = None, replicaTableClassSummary: Option[TableClassSummary] = None)
object ReplicaSettingsDescription extends ShapeTag.Companion[ReplicaSettingsDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaSettingsDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a replica.</p>"),
  )

  implicit val schema: Schema[ReplicaSettingsDescription] = struct(
    RegionName.schema.required[ReplicaSettingsDescription]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region name of the replica.</p>"), smithy.api.Required()),
    ReplicaStatus.schema.optional[ReplicaSettingsDescription]("ReplicaStatus", _.replicaStatus).addHints(smithy.api.Documentation("<p>The current state of the Region:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The Region is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The Region is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The Region is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The Region is ready for use.</p>\n            </li>\n         </ul>")),
    BillingModeSummary.schema.optional[ReplicaSettingsDescription]("ReplicaBillingModeSummary", _.replicaBillingModeSummary).addHints(smithy.api.Documentation("<p>The read/write capacity mode of the replica.</p>")),
    NonNegativeLongObject.schema.optional[ReplicaSettingsDescription]("ReplicaProvisionedReadCapacityUnits", _.replicaProvisionedReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput\">Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB\n                Developer Guide</i>. </p>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaSettingsDescription]("ReplicaProvisionedReadCapacityAutoScalingSettings", _.replicaProvisionedReadCapacityAutoScalingSettings).addHints(smithy.api.Documentation("<p>Auto scaling settings for a global table replica\'s read capacity units.</p>")),
    NonNegativeLongObject.schema.optional[ReplicaSettingsDescription]("ReplicaProvisionedWriteCapacityUnits", _.replicaProvisionedWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException</code>. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput\">Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB\n                Developer Guide</i>.</p>")),
    AutoScalingSettingsDescription.schema.optional[ReplicaSettingsDescription]("ReplicaProvisionedWriteCapacityAutoScalingSettings", _.replicaProvisionedWriteCapacityAutoScalingSettings).addHints(smithy.api.Documentation("<p>Auto scaling settings for a global table replica\'s write capacity units.</p>")),
    ReplicaGlobalSecondaryIndexSettingsDescriptionList.underlyingSchema.optional[ReplicaSettingsDescription]("ReplicaGlobalSecondaryIndexSettings", _.replicaGlobalSecondaryIndexSettings).addHints(smithy.api.Documentation("<p>Replica global secondary index settings for the global table.</p>")),
    TableClassSummary.schema.optional[ReplicaSettingsDescription]("ReplicaTableClassSummary", _.replicaTableClassSummary),
  ){
    ReplicaSettingsDescription.apply
  }.withId(id).addHints(hints)
}
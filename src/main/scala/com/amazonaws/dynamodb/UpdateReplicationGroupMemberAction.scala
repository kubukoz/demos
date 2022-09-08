package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateReplicationGroupMemberAction(regionName: RegionName, kMSMasterKeyId: Option[KMSMasterKeyId] = None, provisionedThroughputOverride: Option[ProvisionedThroughputOverride] = None, globalSecondaryIndexes: Option[List[ReplicaGlobalSecondaryIndex]] = None, tableClassOverride: Option[TableClass] = None)
object UpdateReplicationGroupMemberAction extends ShapeTag.Companion[UpdateReplicationGroupMemberAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateReplicationGroupMemberAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a replica to be modified.</p>"),
  )

  implicit val schema: Schema[UpdateReplicationGroupMemberAction] = struct(
    RegionName.schema.required[UpdateReplicationGroupMemberAction]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the replica exists.</p>"), smithy.api.Required()),
    KMSMasterKeyId.schema.optional[UpdateReplicationGroupMemberAction]("KMSMasterKeyId", _.kMSMasterKeyId).addHints(smithy.api.Documentation("<p>The KMS key of the replica that should be used\n            for KMS encryption. To specify a key, use its key ID, Amazon Resource\n            Name (ARN), alias name, or alias ARN. Note that you should only provide this parameter\n            if the key is different from the default DynamoDB KMS key\n                <code>alias/aws/dynamodb</code>.</p>")),
    ProvisionedThroughputOverride.schema.optional[UpdateReplicationGroupMemberAction]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Replica-specific provisioned throughput. If not specified, uses the source table\'s\n            provisioned throughput settings.</p>")),
    ReplicaGlobalSecondaryIndexList.underlyingSchema.optional[UpdateReplicationGroupMemberAction]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>Replica-specific global secondary index settings.</p>")),
    TableClass.schema.optional[UpdateReplicationGroupMemberAction]("TableClassOverride", _.tableClassOverride).addHints(smithy.api.Documentation("<p>Replica-specific table class. If not specified, uses the source table\'s\n            table class.</p>")),
  ){
    UpdateReplicationGroupMemberAction.apply
  }.withId(id).addHints(hints)
}
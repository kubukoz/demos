package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateReplicationGroupMemberAction(regionName: RegionName, kMSMasterKeyId: Option[KMSMasterKeyId] = None, provisionedThroughputOverride: Option[ProvisionedThroughputOverride] = None, globalSecondaryIndexes: Option[List[ReplicaGlobalSecondaryIndex]] = None, tableClassOverride: Option[TableClass] = None)
object CreateReplicationGroupMemberAction extends ShapeTag.Companion[CreateReplicationGroupMemberAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateReplicationGroupMemberAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a replica to be created.</p>"),
  )

  implicit val schema: Schema[CreateReplicationGroupMemberAction] = struct(
    RegionName.schema.required[CreateReplicationGroupMemberAction]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the new replica will be created.</p>"), smithy.api.Required()),
    KMSMasterKeyId.schema.optional[CreateReplicationGroupMemberAction]("KMSMasterKeyId", _.kMSMasterKeyId).addHints(smithy.api.Documentation("<p>The KMS key that should be used for KMS encryption in\n            the new replica. To specify a key, use its key ID, Amazon Resource Name (ARN), alias\n            name, or alias ARN. Note that you should only provide this parameter if the key is\n            different from the default DynamoDB KMS key\n            <code>alias/aws/dynamodb</code>.</p>")),
    ProvisionedThroughputOverride.schema.optional[CreateReplicationGroupMemberAction]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Replica-specific provisioned throughput. If not specified, uses the source table\'s\n            provisioned throughput settings.</p>")),
    ReplicaGlobalSecondaryIndexList.underlyingSchema.optional[CreateReplicationGroupMemberAction]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>Replica-specific global secondary index settings.</p>")),
    TableClass.schema.optional[CreateReplicationGroupMemberAction]("TableClassOverride", _.tableClassOverride).addHints(smithy.api.Documentation("<p>Replica-specific table class. If not specified, uses the source table\'s\n            table class.</p>")),
  ){
    CreateReplicationGroupMemberAction.apply
  }.withId(id).addHints(hints)
}
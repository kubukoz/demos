package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaDescription(regionName: Option[RegionName] = None, replicaStatus: Option[ReplicaStatus] = None, replicaStatusDescription: Option[ReplicaStatusDescription] = None, replicaStatusPercentProgress: Option[ReplicaStatusPercentProgress] = None, kMSMasterKeyId: Option[KMSMasterKeyId] = None, provisionedThroughputOverride: Option[ProvisionedThroughputOverride] = None, globalSecondaryIndexes: Option[List[ReplicaGlobalSecondaryIndexDescription]] = None, replicaInaccessibleDateTime: Option[Date] = None, replicaTableClassSummary: Option[TableClassSummary] = None)
object ReplicaDescription extends ShapeTag.Companion[ReplicaDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the details of the replica.</p>"),
  )

  implicit val schema: Schema[ReplicaDescription] = struct(
    RegionName.schema.optional[ReplicaDescription]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The name of the Region.</p>")),
    ReplicaStatus.schema.optional[ReplicaDescription]("ReplicaStatus", _.replicaStatus).addHints(smithy.api.Documentation("<p>The current state of the replica:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The replica is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The replica is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The replica is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The replica is ready for use.</p>\n            </li>\n            <li>\n                <p>\n                    <code>REGION_DISABLED</code> - The replica is inaccessible because the Amazon Web Services Region has been disabled.</p>\n                <note>\n                    <p>If the Amazon Web Services Region remains inaccessible for more than 20\n                        hours, DynamoDB will remove this replica from the replication\n                        group. The replica will not be deleted and replication will stop from and to\n                        this region.</p>\n                </note>\n            </li>\n            <li>\n                <p>\n                  <code>INACCESSIBLE_ENCRYPTION_CREDENTIALS </code> - The KMS key\n                    used to encrypt the table is inaccessible.</p>\n                <note>\n                    <p>If the KMS key remains inaccessible for more than 20 hours,\n                            DynamoDB will remove this replica from the replication group.\n                        The replica will not be deleted and replication will stop from and to this\n                        region.</p>\n                </note>\n            </li>\n         </ul>")),
    ReplicaStatusDescription.schema.optional[ReplicaDescription]("ReplicaStatusDescription", _.replicaStatusDescription).addHints(smithy.api.Documentation("<p>Detailed information about the replica status.</p>")),
    ReplicaStatusPercentProgress.schema.optional[ReplicaDescription]("ReplicaStatusPercentProgress", _.replicaStatusPercentProgress).addHints(smithy.api.Documentation("<p>Specifies the progress of a Create, Update, or Delete action on the replica as a\n            percentage.</p>")),
    KMSMasterKeyId.schema.optional[ReplicaDescription]("KMSMasterKeyId", _.kMSMasterKeyId).addHints(smithy.api.Documentation("<p>The KMS key of the replica that will be used for\n                KMS encryption.</p>")),
    ProvisionedThroughputOverride.schema.optional[ReplicaDescription]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Replica-specific provisioned throughput. If not described, uses the source table\'s\n            provisioned throughput settings.</p>")),
    ReplicaGlobalSecondaryIndexDescriptionList.underlyingSchema.optional[ReplicaDescription]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>Replica-specific global secondary index settings.</p>")),
    Date.schema.optional[ReplicaDescription]("ReplicaInaccessibleDateTime", _.replicaInaccessibleDateTime).addHints(smithy.api.Documentation("<p>The time at which the replica was first detected as inaccessible. To determine cause\n            of inaccessibility check the <code>ReplicaStatus</code> property.</p>")),
    TableClassSummary.schema.optional[ReplicaDescription]("ReplicaTableClassSummary", _.replicaTableClassSummary),
  ){
    ReplicaDescription.apply
  }.withId(id).addHints(hints)
}
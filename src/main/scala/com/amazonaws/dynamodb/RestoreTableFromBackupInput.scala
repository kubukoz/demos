package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RestoreTableFromBackupInput(targetTableName: TableName, backupArn: BackupArn, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None)
object RestoreTableFromBackupInput extends ShapeTag.Companion[RestoreTableFromBackupInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableFromBackupInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[RestoreTableFromBackupInput] = struct(
    TableName.schema.required[RestoreTableFromBackupInput]("TargetTableName", _.targetTableName).addHints(smithy.api.Documentation("<p>The name of the new table to which the backup must be restored.</p>"), smithy.api.Required()),
    BackupArn.schema.required[RestoreTableFromBackupInput]("BackupArn", _.backupArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) associated with the backup.</p>"), smithy.api.Required()),
    BillingMode.schema.optional[RestoreTableFromBackupInput]("BillingModeOverride", _.billingModeOverride).addHints(smithy.api.Documentation("<p>The billing mode of the restored table.</p>")),
    GlobalSecondaryIndexList.underlyingSchema.optional[RestoreTableFromBackupInput]("GlobalSecondaryIndexOverride", _.globalSecondaryIndexOverride).addHints(smithy.api.Documentation("<p>List of global secondary indexes for the restored table. The indexes provided should\n            match existing secondary indexes. You can choose to exclude some or all of the indexes\n            at the time of restore.</p>")),
    LocalSecondaryIndexList.underlyingSchema.optional[RestoreTableFromBackupInput]("LocalSecondaryIndexOverride", _.localSecondaryIndexOverride).addHints(smithy.api.Documentation("<p>List of local secondary indexes for the restored table. The indexes provided should\n            match existing secondary indexes. You can choose to exclude some or all of the indexes\n            at the time of restore.</p>")),
    ProvisionedThroughput.schema.optional[RestoreTableFromBackupInput]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Provisioned throughput settings for the restored table.</p>")),
    SSESpecification.schema.optional[RestoreTableFromBackupInput]("SSESpecificationOverride", _.sSESpecificationOverride).addHints(smithy.api.Documentation("<p>The new server-side encryption settings for the restored table.</p>")),
  ){
    RestoreTableFromBackupInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BackupSummary(tableName: Option[TableName] = None, tableId: Option[TableId] = None, tableArn: Option[TableArn] = None, backupArn: Option[BackupArn] = None, backupName: Option[BackupName] = None, backupCreationDateTime: Option[BackupCreationDateTime] = None, backupExpiryDateTime: Option[Date] = None, backupStatus: Option[BackupStatus] = None, backupType: Option[BackupType] = None, backupSizeBytes: Option[BackupSizeBytes] = None)
object BackupSummary extends ShapeTag.Companion[BackupSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains details for the backup.</p>"),
  )

  implicit val schema: Schema[BackupSummary] = struct(
    TableName.schema.optional[BackupSummary]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table.</p>")),
    TableId.schema.optional[BackupSummary]("TableId", _.tableId).addHints(smithy.api.Documentation("<p>Unique identifier for the table.</p>")),
    TableArn.schema.optional[BackupSummary]("TableArn", _.tableArn).addHints(smithy.api.Documentation("<p>ARN associated with the table.</p>")),
    BackupArn.schema.optional[BackupSummary]("BackupArn", _.backupArn).addHints(smithy.api.Documentation("<p>ARN associated with the backup.</p>")),
    BackupName.schema.optional[BackupSummary]("BackupName", _.backupName).addHints(smithy.api.Documentation("<p>Name of the specified backup.</p>")),
    BackupCreationDateTime.schema.optional[BackupSummary]("BackupCreationDateTime", _.backupCreationDateTime).addHints(smithy.api.Documentation("<p>Time at which the backup was created.</p>")),
    Date.schema.optional[BackupSummary]("BackupExpiryDateTime", _.backupExpiryDateTime).addHints(smithy.api.Documentation("<p>Time at which the automatic on-demand backup created by DynamoDB will\n            expire. This <code>SYSTEM</code> on-demand backup expires automatically 35 days after\n            its creation.</p>")),
    BackupStatus.schema.optional[BackupSummary]("BackupStatus", _.backupStatus).addHints(smithy.api.Documentation("<p>Backup can be in one of the following states: CREATING, ACTIVE, DELETED.</p>")),
    BackupType.schema.optional[BackupSummary]("BackupType", _.backupType).addHints(smithy.api.Documentation("<p>BackupType:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>USER</code> - You create and manage these using the on-demand backup\n                    feature.</p>\n            </li>\n            <li>\n                <p>\n                    <code>SYSTEM</code> - If you delete a table with point-in-time recovery enabled,\n                    a <code>SYSTEM</code> backup is automatically created and is retained for 35\n                    days (at no additional cost). System backups allow you to restore the deleted\n                    table to the state it was in just before the point of deletion. </p>\n            </li>\n            <li>\n                <p>\n                    <code>AWS_BACKUP</code> - On-demand backup created by you from Backup service.</p>\n            </li>\n         </ul>")),
    BackupSizeBytes.schema.optional[BackupSummary]("BackupSizeBytes", _.backupSizeBytes).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Size of the backup in bytes.</p>")),
  ){
    BackupSummary.apply
  }.withId(id).addHints(hints)
}
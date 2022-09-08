package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BackupDetails(backupArn: BackupArn, backupName: BackupName, backupStatus: BackupStatus, backupType: BackupType, backupCreationDateTime: BackupCreationDateTime, backupSizeBytes: Option[BackupSizeBytes] = None, backupExpiryDateTime: Option[Date] = None)
object BackupDetails extends ShapeTag.Companion[BackupDetails] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupDetails")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the details of the backup created for the table.</p>"),
  )

  implicit val schema: Schema[BackupDetails] = struct(
    BackupArn.schema.required[BackupDetails]("BackupArn", _.backupArn).addHints(smithy.api.Documentation("<p>ARN associated with the backup.</p>"), smithy.api.Required()),
    BackupName.schema.required[BackupDetails]("BackupName", _.backupName).addHints(smithy.api.Documentation("<p>Name of the requested backup.</p>"), smithy.api.Required()),
    BackupStatus.schema.required[BackupDetails]("BackupStatus", _.backupStatus).addHints(smithy.api.Documentation("<p>Backup can be in one of the following states: CREATING, ACTIVE, DELETED. </p>"), smithy.api.Required()),
    BackupType.schema.required[BackupDetails]("BackupType", _.backupType).addHints(smithy.api.Documentation("<p>BackupType:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>USER</code> - You create and manage these using the on-demand backup\n                    feature.</p>\n            </li>\n            <li>\n                <p>\n                    <code>SYSTEM</code> - If you delete a table with point-in-time recovery enabled,\n                    a <code>SYSTEM</code> backup is automatically created and is retained for 35\n                    days (at no additional cost). System backups allow you to restore the deleted\n                    table to the state it was in just before the point of deletion. </p>\n            </li>\n            <li>\n                <p>\n                    <code>AWS_BACKUP</code> - On-demand backup created by you from Backup service.</p>\n            </li>\n         </ul>"), smithy.api.Required()),
    BackupCreationDateTime.schema.required[BackupDetails]("BackupCreationDateTime", _.backupCreationDateTime).addHints(smithy.api.Documentation("<p>Time at which the backup was created. This is the request time of the backup. </p>"), smithy.api.Required()),
    BackupSizeBytes.schema.optional[BackupDetails]("BackupSizeBytes", _.backupSizeBytes).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Size of the backup in bytes. DynamoDB updates this value approximately every six hours. \n         Recent changes might not be reflected in this value.</p>")),
    Date.schema.optional[BackupDetails]("BackupExpiryDateTime", _.backupExpiryDateTime).addHints(smithy.api.Documentation("<p>Time at which the automatic on-demand backup created by DynamoDB will\n            expire. This <code>SYSTEM</code> on-demand backup expires automatically 35 days after\n            its creation.</p>")),
  ){
    BackupDetails.apply
  }.withId(id).addHints(hints)
}
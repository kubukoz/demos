package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListBackupsInput(tableName: Option[TableName] = None, limit: Option[BackupsInputLimit] = None, timeRangeLowerBound: Option[TimeRangeLowerBound] = None, timeRangeUpperBound: Option[TimeRangeUpperBound] = None, exclusiveStartBackupArn: Option[BackupArn] = None, backupType: Option[BackupTypeFilter] = None)
object ListBackupsInput extends ShapeTag.Companion[ListBackupsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListBackupsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListBackupsInput] = struct(
    TableName.schema.optional[ListBackupsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The backups from the table specified by <code>TableName</code> are listed. </p>")),
    BackupsInputLimit.schema.optional[ListBackupsInput]("Limit", _.limit).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Maximum number of backups to return at once.</p>")),
    TimeRangeLowerBound.schema.optional[ListBackupsInput]("TimeRangeLowerBound", _.timeRangeLowerBound).addHints(smithy.api.Documentation("<p>Only backups created after this time are listed. <code>TimeRangeLowerBound</code> is\n            inclusive.</p>")),
    TimeRangeUpperBound.schema.optional[ListBackupsInput]("TimeRangeUpperBound", _.timeRangeUpperBound).addHints(smithy.api.Documentation("<p>Only backups created before this time are listed. <code>TimeRangeUpperBound</code> is\n            exclusive. </p>")),
    BackupArn.schema.optional[ListBackupsInput]("ExclusiveStartBackupArn", _.exclusiveStartBackupArn).addHints(smithy.api.Documentation("<p>\n            <code>LastEvaluatedBackupArn</code> is the Amazon Resource Name (ARN) of the backup last\n            evaluated when the current page of results was returned, inclusive of the current page\n            of results. This value may be specified as the <code>ExclusiveStartBackupArn</code> of a\n            new <code>ListBackups</code> operation in order to fetch the next page of results.\n        </p>")),
    BackupTypeFilter.schema.optional[ListBackupsInput]("BackupType", _.backupType).addHints(smithy.api.Documentation("<p>The backups from the table specified by <code>BackupType</code> are listed.</p>\n        <p>Where <code>BackupType</code> can be:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>USER</code> - On-demand backup created by you. (The default setting if no other backup types are specified.)</p>\n            </li>\n            <li>\n                <p>\n                    <code>SYSTEM</code> - On-demand backup automatically created by DynamoDB.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ALL</code> - All types of on-demand backups (USER and SYSTEM).</p>\n            </li>\n         </ul>")),
  ){
    ListBackupsInput.apply
  }.withId(id).addHints(hints)
}
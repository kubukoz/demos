package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListBackupsOutput(backupSummaries: Option[List[BackupSummary]] = None, lastEvaluatedBackupArn: Option[BackupArn] = None)
object ListBackupsOutput extends ShapeTag.Companion[ListBackupsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListBackupsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListBackupsOutput] = struct(
    BackupSummaries.underlyingSchema.optional[ListBackupsOutput]("BackupSummaries", _.backupSummaries).addHints(smithy.api.Documentation("<p>List of <code>BackupSummary</code> objects.</p>")),
    BackupArn.schema.optional[ListBackupsOutput]("LastEvaluatedBackupArn", _.lastEvaluatedBackupArn).addHints(smithy.api.Documentation("<p> The ARN of the backup last evaluated when the current page of results was returned,\n            inclusive of the current page of results. This value may be specified as the\n                <code>ExclusiveStartBackupArn</code> of a new <code>ListBackups</code> operation in\n            order to fetch the next page of results. </p>\n        <p> If <code>LastEvaluatedBackupArn</code> is empty, then the last page of results has\n            been processed and there are no more results to be retrieved. </p>\n        <p> If <code>LastEvaluatedBackupArn</code> is not empty, this may or may not indicate\n            that there is more data to be returned. All results are guaranteed to have been returned\n            if and only if no value for <code>LastEvaluatedBackupArn</code> is returned. </p>")),
  ){
    ListBackupsOutput.apply
  }.withId(id).addHints(hints)
}
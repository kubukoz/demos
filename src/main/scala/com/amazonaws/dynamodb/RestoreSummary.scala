package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RestoreSummary(restoreDateTime: Date, restoreInProgress: RestoreInProgress, sourceBackupArn: Option[BackupArn] = None, sourceTableArn: Option[TableArn] = None)
object RestoreSummary extends ShapeTag.Companion[RestoreSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains details for the restore.</p>"),
  )

  implicit val schema: Schema[RestoreSummary] = struct(
    Date.schema.required[RestoreSummary]("RestoreDateTime", _.restoreDateTime).addHints(smithy.api.Documentation("<p>Point in time or source backup time.</p>"), smithy.api.Required()),
    RestoreInProgress.schema.required[RestoreSummary]("RestoreInProgress", _.restoreInProgress).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>Indicates if a restore is in progress or not.</p>")),
    BackupArn.schema.optional[RestoreSummary]("SourceBackupArn", _.sourceBackupArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) of the backup from which the table was restored.</p>")),
    TableArn.schema.optional[RestoreSummary]("SourceTableArn", _.sourceTableArn).addHints(smithy.api.Documentation("<p>The ARN of the source table of the backup that is being restored.</p>")),
  ){
    RestoreSummary.apply
  }.withId(id).addHints(hints)
}
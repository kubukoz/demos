package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ArchivalSummary(archivalDateTime: Option[Date] = None, archivalReason: Option[ArchivalReason] = None, archivalBackupArn: Option[BackupArn] = None)
object ArchivalSummary extends ShapeTag.Companion[ArchivalSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ArchivalSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains details of a table archival operation.</p>"),
  )

  implicit val schema: Schema[ArchivalSummary] = struct(
    Date.schema.optional[ArchivalSummary]("ArchivalDateTime", _.archivalDateTime).addHints(smithy.api.Documentation("<p>The date and time when table archival was initiated by DynamoDB, in UNIX epoch time\n            format.</p>")),
    ArchivalReason.schema.optional[ArchivalSummary]("ArchivalReason", _.archivalReason).addHints(smithy.api.Documentation("<p>The reason DynamoDB archived the table. Currently, the only possible value is:</p>\n\n        <ul>\n            <li>\n                <p>\n                  <code>INACCESSIBLE_ENCRYPTION_CREDENTIALS</code> - The table was archived due\n                    to the table\'s KMS key being inaccessible for more than seven\n                    days. An On-Demand backup was created at the archival time.</p>\n            </li>\n         </ul>")),
    BackupArn.schema.optional[ArchivalSummary]("ArchivalBackupArn", _.archivalBackupArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) of the backup the table was archived to, when\n            applicable in the archival reason. If you wish to restore this backup to the same table\n            name, you will need to delete the original table.</p>")),
  ){
    ArchivalSummary.apply
  }.withId(id).addHints(hints)
}
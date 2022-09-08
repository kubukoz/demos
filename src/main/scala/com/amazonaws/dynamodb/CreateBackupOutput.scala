package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateBackupOutput(backupDetails: Option[BackupDetails] = None)
object CreateBackupOutput extends ShapeTag.Companion[CreateBackupOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateBackupOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[CreateBackupOutput] = struct(
    BackupDetails.schema.optional[CreateBackupOutput]("BackupDetails", _.backupDetails).addHints(smithy.api.Documentation("<p>Contains the details of the backup created for the table.</p>")),
  ){
    CreateBackupOutput.apply
  }.withId(id).addHints(hints)
}
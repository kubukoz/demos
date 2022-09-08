package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteBackupOutput(backupDescription: Option[BackupDescription] = None)
object DeleteBackupOutput extends ShapeTag.Companion[DeleteBackupOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteBackupOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DeleteBackupOutput] = struct(
    BackupDescription.schema.optional[DeleteBackupOutput]("BackupDescription", _.backupDescription).addHints(smithy.api.Documentation("<p>Contains the description of the backup created for the table.</p>")),
  ){
    DeleteBackupOutput.apply
  }.withId(id).addHints(hints)
}
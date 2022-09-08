package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteBackupInput(backupArn: BackupArn)
object DeleteBackupInput extends ShapeTag.Companion[DeleteBackupInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteBackupInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DeleteBackupInput] = struct(
    BackupArn.schema.required[DeleteBackupInput]("BackupArn", _.backupArn).addHints(smithy.api.Documentation("<p>The ARN associated with the backup.</p>"), smithy.api.Required()),
  ){
    DeleteBackupInput.apply
  }.withId(id).addHints(hints)
}
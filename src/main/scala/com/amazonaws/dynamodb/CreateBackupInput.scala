package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateBackupInput(tableName: TableName, backupName: BackupName)
object CreateBackupInput extends ShapeTag.Companion[CreateBackupInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateBackupInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[CreateBackupInput] = struct(
    TableName.schema.required[CreateBackupInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>"), smithy.api.Required()),
    BackupName.schema.required[CreateBackupInput]("BackupName", _.backupName).addHints(smithy.api.Documentation("<p>Specified name for the backup.</p>"), smithy.api.Required()),
  ){
    CreateBackupInput.apply
  }.withId(id).addHints(hints)
}
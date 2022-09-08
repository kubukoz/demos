package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeBackupInput(backupArn: BackupArn)
object DescribeBackupInput extends ShapeTag.Companion[DescribeBackupInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeBackupInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeBackupInput] = struct(
    BackupArn.schema.required[DescribeBackupInput]("BackupArn", _.backupArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) associated with the backup.</p>"), smithy.api.Required()),
  ){
    DescribeBackupInput.apply
  }.withId(id).addHints(hints)
}
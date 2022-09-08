package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeBackupOutput(backupDescription: Option[BackupDescription] = None)
object DescribeBackupOutput extends ShapeTag.Companion[DescribeBackupOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeBackupOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeBackupOutput] = struct(
    BackupDescription.schema.optional[DescribeBackupOutput]("BackupDescription", _.backupDescription).addHints(smithy.api.Documentation("<p>Contains the description of the backup created for the table.</p>")),
  ){
    DescribeBackupOutput.apply
  }.withId(id).addHints(hints)
}
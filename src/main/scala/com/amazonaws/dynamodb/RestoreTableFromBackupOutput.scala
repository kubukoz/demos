package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RestoreTableFromBackupOutput(tableDescription: Option[TableDescription] = None)
object RestoreTableFromBackupOutput extends ShapeTag.Companion[RestoreTableFromBackupOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableFromBackupOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[RestoreTableFromBackupOutput] = struct(
    TableDescription.schema.optional[RestoreTableFromBackupOutput]("TableDescription", _.tableDescription).addHints(smithy.api.Documentation("<p>The description of the table created from an existing backup.</p>")),
  ){
    RestoreTableFromBackupOutput.apply
  }.withId(id).addHints(hints)
}
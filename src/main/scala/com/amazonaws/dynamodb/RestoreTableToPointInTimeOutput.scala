package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RestoreTableToPointInTimeOutput(tableDescription: Option[TableDescription] = None)
object RestoreTableToPointInTimeOutput extends ShapeTag.Companion[RestoreTableToPointInTimeOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableToPointInTimeOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[RestoreTableToPointInTimeOutput] = struct(
    TableDescription.schema.optional[RestoreTableToPointInTimeOutput]("TableDescription", _.tableDescription).addHints(smithy.api.Documentation("<p>Represents the properties of a table.</p>")),
  ){
    RestoreTableToPointInTimeOutput.apply
  }.withId(id).addHints(hints)
}
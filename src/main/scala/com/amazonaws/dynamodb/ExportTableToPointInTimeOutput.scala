package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportTableToPointInTimeOutput(exportDescription: Option[ExportDescription] = None)
object ExportTableToPointInTimeOutput extends ShapeTag.Companion[ExportTableToPointInTimeOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportTableToPointInTimeOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ExportTableToPointInTimeOutput] = struct(
    ExportDescription.schema.optional[ExportTableToPointInTimeOutput]("ExportDescription", _.exportDescription).addHints(smithy.api.Documentation("<p>Contains a description of the table export.</p>")),
  ){
    ExportTableToPointInTimeOutput.apply
  }.withId(id).addHints(hints)
}
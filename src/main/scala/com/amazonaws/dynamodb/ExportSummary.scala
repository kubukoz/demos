package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ExportSummary(exportArn: Option[ExportArn] = None, exportStatus: Option[ExportStatus] = None)
object ExportSummary extends ShapeTag.Companion[ExportSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Summary information about an export task.</p>"),
  )

  implicit val schema: Schema[ExportSummary] = struct(
    ExportArn.schema.optional[ExportSummary]("ExportArn", _.exportArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) of the export.</p>")),
    ExportStatus.schema.optional[ExportSummary]("ExportStatus", _.exportStatus).addHints(smithy.api.Documentation("<p>Export can be in one of the following states: IN_PROGRESS, COMPLETED, or\n            FAILED.</p>")),
  ){
    ExportSummary.apply
  }.withId(id).addHints(hints)
}
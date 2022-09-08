package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeExportOutput(exportDescription: Option[ExportDescription] = None)
object DescribeExportOutput extends ShapeTag.Companion[DescribeExportOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeExportOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeExportOutput] = struct(
    ExportDescription.schema.optional[DescribeExportOutput]("ExportDescription", _.exportDescription).addHints(smithy.api.Documentation("<p>Represents the properties of the export.</p>")),
  ){
    DescribeExportOutput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeExportInput(exportArn: ExportArn)
object DescribeExportInput extends ShapeTag.Companion[DescribeExportInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeExportInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeExportInput] = struct(
    ExportArn.schema.required[DescribeExportInput]("ExportArn", _.exportArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) associated with the export.</p>"), smithy.api.Required()),
  ){
    DescribeExportInput.apply
  }.withId(id).addHints(hints)
}
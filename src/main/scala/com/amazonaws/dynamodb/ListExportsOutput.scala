package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListExportsOutput(exportSummaries: Option[List[ExportSummary]] = None, nextToken: Option[ExportNextToken] = None)
object ListExportsOutput extends ShapeTag.Companion[ListExportsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListExportsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListExportsOutput] = struct(
    ExportSummaries.underlyingSchema.optional[ListExportsOutput]("ExportSummaries", _.exportSummaries).addHints(smithy.api.Documentation("<p>A list of <code>ExportSummary</code> objects.</p>")),
    ExportNextToken.schema.optional[ListExportsOutput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>If this value is returned, there are additional results to be displayed. To retrieve\n            them, call <code>ListExports</code> again, with <code>NextToken</code> set to this\n            value.</p>")),
  ){
    ListExportsOutput.apply
  }.withId(id).addHints(hints)
}
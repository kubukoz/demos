package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListExportsInput(tableArn: Option[TableArn] = None, maxResults: Option[ListExportsMaxLimit] = None, nextToken: Option[ExportNextToken] = None)
object ListExportsInput extends ShapeTag.Companion[ListExportsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListExportsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListExportsInput] = struct(
    TableArn.schema.optional[ListExportsInput]("TableArn", _.tableArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) associated with the exported table.</p>")),
    ListExportsMaxLimit.schema.optional[ListExportsInput]("MaxResults", _.maxResults).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Maximum number of results to return per page.</p>")),
    ExportNextToken.schema.optional[ListExportsInput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>An optional string that, if supplied, must be copied from the output of a previous\n            call to <code>ListExports</code>. When provided in this manner, the API fetches the next\n            page of results.</p>")),
  ){
    ListExportsInput.apply
  }.withId(id).addHints(hints)
}
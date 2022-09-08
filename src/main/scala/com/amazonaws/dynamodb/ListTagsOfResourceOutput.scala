package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListTagsOfResourceOutput(tags: Option[List[Tag]] = None, nextToken: Option[NextTokenString] = None)
object ListTagsOfResourceOutput extends ShapeTag.Companion[ListTagsOfResourceOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTagsOfResourceOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListTagsOfResourceOutput] = struct(
    TagList.underlyingSchema.optional[ListTagsOfResourceOutput]("Tags", _.tags).addHints(smithy.api.Documentation("<p>The tags currently associated with the Amazon DynamoDB resource.</p>")),
    NextTokenString.schema.optional[ListTagsOfResourceOutput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>If this value is returned, there are additional results to be displayed. To retrieve\n            them, call ListTagsOfResource again, with NextToken set to this value.</p>")),
  ){
    ListTagsOfResourceOutput.apply
  }.withId(id).addHints(hints)
}
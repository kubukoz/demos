package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ListTagsOfResourceInput(resourceArn: ResourceArnString, nextToken: Option[NextTokenString] = None)
object ListTagsOfResourceInput extends ShapeTag.Companion[ListTagsOfResourceInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ListTagsOfResourceInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[ListTagsOfResourceInput] = struct(
    ResourceArnString.schema.required[ListTagsOfResourceInput]("ResourceArn", _.resourceArn).addHints(smithy.api.Documentation("<p>The Amazon DynamoDB resource with tags to be listed. This value is an Amazon Resource\n            Name (ARN).</p>"), smithy.api.Required()),
    NextTokenString.schema.optional[ListTagsOfResourceInput]("NextToken", _.nextToken).addHints(smithy.api.Documentation("<p>An optional string that, if supplied, must be copied from the output of a previous\n            call to ListTagOfResource. When provided in this manner, this API fetches the next page\n            of results.</p>")),
  ){
    ListTagsOfResourceInput.apply
  }.withId(id).addHints(hints)
}
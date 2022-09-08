package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TagResourceInput(resourceArn: ResourceArnString, tags: List[Tag])
object TagResourceInput extends ShapeTag.Companion[TagResourceInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TagResourceInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TagResourceInput] = struct(
    ResourceArnString.schema.required[TagResourceInput]("ResourceArn", _.resourceArn).addHints(smithy.api.Documentation("<p>Identifies the Amazon DynamoDB resource to which tags should be added. This value is\n            an Amazon Resource Name (ARN).</p>"), smithy.api.Required()),
    TagList.underlyingSchema.required[TagResourceInput]("Tags", _.tags).addHints(smithy.api.Documentation("<p>The tags to be assigned to the Amazon DynamoDB resource.</p>"), smithy.api.Required()),
  ){
    TagResourceInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UntagResourceInput(resourceArn: ResourceArnString, tagKeys: List[TagKeyString])
object UntagResourceInput extends ShapeTag.Companion[UntagResourceInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UntagResourceInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UntagResourceInput] = struct(
    ResourceArnString.schema.required[UntagResourceInput]("ResourceArn", _.resourceArn).addHints(smithy.api.Documentation("<p>The DynamoDB resource that the tags will be removed from. This value is an Amazon\n            Resource Name (ARN).</p>"), smithy.api.Required()),
    TagKeyList.underlyingSchema.required[UntagResourceInput]("TagKeys", _.tagKeys).addHints(smithy.api.Documentation("<p>A list of tag keys. Existing tags of the resource whose keys are members of this list\n            will be removed from the DynamoDB resource.</p>"), smithy.api.Required()),
  ){
    UntagResourceInput.apply
  }.withId(id).addHints(hints)
}
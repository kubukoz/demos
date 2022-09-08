package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Tag(key: TagKeyString, value: TagValueString)
object Tag extends ShapeTag.Companion[Tag] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Tag")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Describes a tag. A tag is a key-value pair. You can add up to 50 tags to a single\n            DynamoDB table. </p>\n        <p>Amazon Web Services-assigned tag names and values are automatically assigned the\n                <code>aws:</code> prefix, which the user cannot assign. Amazon Web Services-assigned\n            tag names do not count towards the tag limit of 50. User-assigned tag names have the\n            prefix <code>user:</code> in the Cost Allocation Report. You cannot backdate the\n            application of a tag.</p>\n        <p>For an overview on tagging DynamoDB resources, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Tagging.html\">Tagging\n                for DynamoDB</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>"),
  )

  implicit val schema: Schema[Tag] = struct(
    TagKeyString.schema.required[Tag]("Key", _.key).addHints(smithy.api.Documentation("<p>The key of the tag. Tag keys are case sensitive. Each DynamoDB table can\n            only have up to one tag with the same key. If you try to add an existing tag (same key),\n            the existing tag value will be updated to the new value.</p>"), smithy.api.Required()),
    TagValueString.schema.required[Tag]("Value", _.value).addHints(smithy.api.Documentation("<p>The value of the tag. Tag values are case-sensitive and can be null.</p>"), smithy.api.Required()),
  ){
    Tag.apply
  }.withId(id).addHints(hints)
}
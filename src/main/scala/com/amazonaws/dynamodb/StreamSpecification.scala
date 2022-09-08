package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class StreamSpecification(streamEnabled: StreamEnabled, streamViewType: Option[StreamViewType] = None)
object StreamSpecification extends ShapeTag.Companion[StreamSpecification] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "StreamSpecification")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the DynamoDB Streams configuration for a table in DynamoDB.</p>"),
  )

  implicit val schema: Schema[StreamSpecification] = struct(
    StreamEnabled.schema.required[StreamSpecification]("StreamEnabled", _.streamEnabled).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>Indicates whether DynamoDB Streams is enabled (true) or disabled (false) on the\n            table.</p>")),
    StreamViewType.schema.optional[StreamSpecification]("StreamViewType", _.streamViewType).addHints(smithy.api.Documentation("<p> When an item in the table is modified, <code>StreamViewType</code> determines what\n            information is written to the stream for this table. Valid values for\n                <code>StreamViewType</code> are:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>KEYS_ONLY</code> - Only the key attributes of the modified item are\n                    written to the stream.</p>\n            </li>\n            <li>\n                <p>\n                    <code>NEW_IMAGE</code> - The entire item, as it appears after it was modified,\n                    is written to the stream.</p>\n            </li>\n            <li>\n                <p>\n                    <code>OLD_IMAGE</code> - The entire item, as it appeared before it was modified,\n                    is written to the stream.</p>\n            </li>\n            <li>\n                <p>\n                    <code>NEW_AND_OLD_IMAGES</code> - Both the new and the old item images of the\n                    item are written to the stream.</p>\n            </li>\n         </ul>")),
  ){
    StreamSpecification.apply
  }.withId(id).addHints(hints)
}
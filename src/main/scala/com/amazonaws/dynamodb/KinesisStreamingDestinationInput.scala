package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class KinesisStreamingDestinationInput(tableName: TableName, streamArn: StreamArn)
object KinesisStreamingDestinationInput extends ShapeTag.Companion[KinesisStreamingDestinationInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KinesisStreamingDestinationInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[KinesisStreamingDestinationInput] = struct(
    TableName.schema.required[KinesisStreamingDestinationInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the DynamoDB table.</p>"), smithy.api.Required()),
    StreamArn.schema.required[KinesisStreamingDestinationInput]("StreamArn", _.streamArn).addHints(smithy.api.Documentation("<p>The ARN for a Kinesis data stream.</p>"), smithy.api.Required()),
  ){
    KinesisStreamingDestinationInput.apply
  }.withId(id).addHints(hints)
}
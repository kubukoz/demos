package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class KinesisStreamingDestinationOutput(tableName: Option[TableName] = None, streamArn: Option[StreamArn] = None, destinationStatus: Option[DestinationStatus] = None)
object KinesisStreamingDestinationOutput extends ShapeTag.Companion[KinesisStreamingDestinationOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KinesisStreamingDestinationOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[KinesisStreamingDestinationOutput] = struct(
    TableName.schema.optional[KinesisStreamingDestinationOutput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table being modified.</p>")),
    StreamArn.schema.optional[KinesisStreamingDestinationOutput]("StreamArn", _.streamArn).addHints(smithy.api.Documentation("<p>The ARN for the specific Kinesis data stream.</p>")),
    DestinationStatus.schema.optional[KinesisStreamingDestinationOutput]("DestinationStatus", _.destinationStatus).addHints(smithy.api.Documentation("<p>The current status of the replication.</p>")),
  ){
    KinesisStreamingDestinationOutput.apply
  }.withId(id).addHints(hints)
}
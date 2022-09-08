package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class KinesisDataStreamDestination(streamArn: Option[StreamArn] = None, destinationStatus: Option[DestinationStatus] = None, destinationStatusDescription: Option[String] = None)
object KinesisDataStreamDestination extends ShapeTag.Companion[KinesisDataStreamDestination] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KinesisDataStreamDestination")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Describes a Kinesis data stream destination.</p>"),
  )

  implicit val schema: Schema[KinesisDataStreamDestination] = struct(
    StreamArn.schema.optional[KinesisDataStreamDestination]("StreamArn", _.streamArn).addHints(smithy.api.Documentation("<p>The ARN for a specific Kinesis data stream.</p>")),
    DestinationStatus.schema.optional[KinesisDataStreamDestination]("DestinationStatus", _.destinationStatus).addHints(smithy.api.Documentation("<p>The current status of replication.</p>")),
    string.optional[KinesisDataStreamDestination]("DestinationStatusDescription", _.destinationStatusDescription).addHints(smithy.api.Documentation("<p>The human-readable string that corresponds to the replica status.</p>")),
  ){
    KinesisDataStreamDestination.apply
  }.withId(id).addHints(hints)
}
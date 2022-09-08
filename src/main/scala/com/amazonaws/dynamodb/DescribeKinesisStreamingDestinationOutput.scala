package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeKinesisStreamingDestinationOutput(tableName: Option[TableName] = None, kinesisDataStreamDestinations: Option[List[KinesisDataStreamDestination]] = None)
object DescribeKinesisStreamingDestinationOutput extends ShapeTag.Companion[DescribeKinesisStreamingDestinationOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeKinesisStreamingDestinationOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeKinesisStreamingDestinationOutput] = struct(
    TableName.schema.optional[DescribeKinesisStreamingDestinationOutput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table being described.</p>")),
    KinesisDataStreamDestinations.underlyingSchema.optional[DescribeKinesisStreamingDestinationOutput]("KinesisDataStreamDestinations", _.kinesisDataStreamDestinations).addHints(smithy.api.Documentation("<p>The list of replica structures for the table being described.</p>")),
  ){
    DescribeKinesisStreamingDestinationOutput.apply
  }.withId(id).addHints(hints)
}
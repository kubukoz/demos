package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeKinesisStreamingDestinationInput(tableName: TableName)
object DescribeKinesisStreamingDestinationInput extends ShapeTag.Companion[DescribeKinesisStreamingDestinationInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeKinesisStreamingDestinationInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeKinesisStreamingDestinationInput] = struct(
    TableName.schema.required[DescribeKinesisStreamingDestinationInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table being described.</p>"), smithy.api.Required()),
  ){
    DescribeKinesisStreamingDestinationInput.apply
  }.withId(id).addHints(hints)
}
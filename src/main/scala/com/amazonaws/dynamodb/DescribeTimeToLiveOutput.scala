package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTimeToLiveOutput(timeToLiveDescription: Option[TimeToLiveDescription] = None)
object DescribeTimeToLiveOutput extends ShapeTag.Companion[DescribeTimeToLiveOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTimeToLiveOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeTimeToLiveOutput] = struct(
    TimeToLiveDescription.schema.optional[DescribeTimeToLiveOutput]("TimeToLiveDescription", _.timeToLiveDescription).addHints(smithy.api.Documentation("<p></p>")),
  ){
    DescribeTimeToLiveOutput.apply
  }.withId(id).addHints(hints)
}
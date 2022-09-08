package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeGlobalTableOutput(globalTableDescription: Option[GlobalTableDescription] = None)
object DescribeGlobalTableOutput extends ShapeTag.Companion[DescribeGlobalTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeGlobalTableOutput] = struct(
    GlobalTableDescription.schema.optional[DescribeGlobalTableOutput]("GlobalTableDescription", _.globalTableDescription).addHints(smithy.api.Documentation("<p>Contains the details of the global table.</p>")),
  ){
    DescribeGlobalTableOutput.apply
  }.withId(id).addHints(hints)
}
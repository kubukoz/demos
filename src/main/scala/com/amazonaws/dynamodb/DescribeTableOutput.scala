package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTableOutput(table: Option[TableDescription] = None)
object DescribeTableOutput extends ShapeTag.Companion[DescribeTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>DescribeTable</code> operation.</p>"),
  )

  implicit val schema: Schema[DescribeTableOutput] = struct(
    TableDescription.schema.optional[DescribeTableOutput]("Table", _.table).addHints(smithy.api.Documentation("<p>The properties of the table.</p>")),
  ){
    DescribeTableOutput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTableInput(tableName: TableName)
object DescribeTableInput extends ShapeTag.Companion[DescribeTableInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTableInput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the input of a <code>DescribeTable</code> operation.</p>"),
  )

  implicit val schema: Schema[DescribeTableInput] = struct(
    TableName.schema.required[DescribeTableInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table to describe.</p>"), smithy.api.Required()),
  ){
    DescribeTableInput.apply
  }.withId(id).addHints(hints)
}
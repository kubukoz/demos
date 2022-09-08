package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeTimeToLiveInput(tableName: TableName)
object DescribeTimeToLiveInput extends ShapeTag.Companion[DescribeTimeToLiveInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeTimeToLiveInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeTimeToLiveInput] = struct(
    TableName.schema.required[DescribeTimeToLiveInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table to be described.</p>"), smithy.api.Required()),
  ){
    DescribeTimeToLiveInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeGlobalTableInput(globalTableName: TableName)
object DescribeGlobalTableInput extends ShapeTag.Companion[DescribeGlobalTableInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeGlobalTableInput] = struct(
    TableName.schema.required[DescribeGlobalTableInput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The name of the global table.</p>"), smithy.api.Required()),
  ){
    DescribeGlobalTableInput.apply
  }.withId(id).addHints(hints)
}
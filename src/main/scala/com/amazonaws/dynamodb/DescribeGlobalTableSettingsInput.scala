package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeGlobalTableSettingsInput(globalTableName: TableName)
object DescribeGlobalTableSettingsInput extends ShapeTag.Companion[DescribeGlobalTableSettingsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeGlobalTableSettingsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeGlobalTableSettingsInput] = struct(
    TableName.schema.required[DescribeGlobalTableSettingsInput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The name of the global table to describe.</p>"), smithy.api.Required()),
  ){
    DescribeGlobalTableSettingsInput.apply
  }.withId(id).addHints(hints)
}
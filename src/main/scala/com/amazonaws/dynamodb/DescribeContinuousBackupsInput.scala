package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeContinuousBackupsInput(tableName: TableName)
object DescribeContinuousBackupsInput extends ShapeTag.Companion[DescribeContinuousBackupsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContinuousBackupsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeContinuousBackupsInput] = struct(
    TableName.schema.required[DescribeContinuousBackupsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>Name of the table for which the customer wants to check the continuous backups and\n            point in time recovery settings.</p>"), smithy.api.Required()),
  ){
    DescribeContinuousBackupsInput.apply
  }.withId(id).addHints(hints)
}
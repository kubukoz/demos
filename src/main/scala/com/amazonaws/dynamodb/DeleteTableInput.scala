package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteTableInput(tableName: TableName)
object DeleteTableInput extends ShapeTag.Companion[DeleteTableInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteTableInput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the input of a <code>DeleteTable</code> operation.</p>"),
  )

  implicit val schema: Schema[DeleteTableInput] = struct(
    TableName.schema.required[DeleteTableInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table to delete.</p>"), smithy.api.Required()),
  ){
    DeleteTableInput.apply
  }.withId(id).addHints(hints)
}
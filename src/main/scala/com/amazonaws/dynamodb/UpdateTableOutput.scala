package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateTableOutput(tableDescription: Option[TableDescription] = None)
object UpdateTableOutput extends ShapeTag.Companion[UpdateTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTableOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of an <code>UpdateTable</code> operation.</p>"),
  )

  implicit val schema: Schema[UpdateTableOutput] = struct(
    TableDescription.schema.optional[UpdateTableOutput]("TableDescription", _.tableDescription).addHints(smithy.api.Documentation("<p>Represents the properties of the table.</p>")),
  ){
    UpdateTableOutput.apply
  }.withId(id).addHints(hints)
}
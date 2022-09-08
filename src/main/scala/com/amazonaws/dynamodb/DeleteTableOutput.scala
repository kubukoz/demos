package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteTableOutput(tableDescription: Option[TableDescription] = None)
object DeleteTableOutput extends ShapeTag.Companion[DeleteTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteTableOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>DeleteTable</code> operation.</p>"),
  )

  implicit val schema: Schema[DeleteTableOutput] = struct(
    TableDescription.schema.optional[DeleteTableOutput]("TableDescription", _.tableDescription).addHints(smithy.api.Documentation("<p>Represents the properties of a table.</p>")),
  ){
    DeleteTableOutput.apply
  }.withId(id).addHints(hints)
}
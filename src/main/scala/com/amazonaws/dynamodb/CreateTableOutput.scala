package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateTableOutput(tableDescription: Option[TableDescription] = None)
object CreateTableOutput extends ShapeTag.Companion[CreateTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateTableOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>CreateTable</code> operation.</p>"),
  )

  implicit val schema: Schema[CreateTableOutput] = struct(
    TableDescription.schema.optional[CreateTableOutput]("TableDescription", _.tableDescription).addHints(smithy.api.Documentation("<p>Represents the properties of the table.</p>")),
  ){
    CreateTableOutput.apply
  }.withId(id).addHints(hints)
}
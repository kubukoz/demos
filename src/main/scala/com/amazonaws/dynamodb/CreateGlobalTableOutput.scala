package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateGlobalTableOutput(globalTableDescription: Option[GlobalTableDescription] = None)
object CreateGlobalTableOutput extends ShapeTag.Companion[CreateGlobalTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateGlobalTableOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[CreateGlobalTableOutput] = struct(
    GlobalTableDescription.schema.optional[CreateGlobalTableOutput]("GlobalTableDescription", _.globalTableDescription).addHints(smithy.api.Documentation("<p>Contains the details of the global table.</p>")),
  ){
    CreateGlobalTableOutput.apply
  }.withId(id).addHints(hints)
}
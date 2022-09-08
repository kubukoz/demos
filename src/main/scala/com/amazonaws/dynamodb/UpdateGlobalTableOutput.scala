package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateGlobalTableOutput(globalTableDescription: Option[GlobalTableDescription] = None)
object UpdateGlobalTableOutput extends ShapeTag.Companion[UpdateGlobalTableOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateGlobalTableOutput] = struct(
    GlobalTableDescription.schema.optional[UpdateGlobalTableOutput]("GlobalTableDescription", _.globalTableDescription).addHints(smithy.api.Documentation("<p>Contains the details of the global table.</p>")),
  ){
    UpdateGlobalTableOutput.apply
  }.withId(id).addHints(hints)
}
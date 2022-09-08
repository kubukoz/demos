package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateContinuousBackupsOutput(continuousBackupsDescription: Option[ContinuousBackupsDescription] = None)
object UpdateContinuousBackupsOutput extends ShapeTag.Companion[UpdateContinuousBackupsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContinuousBackupsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateContinuousBackupsOutput] = struct(
    ContinuousBackupsDescription.schema.optional[UpdateContinuousBackupsOutput]("ContinuousBackupsDescription", _.continuousBackupsDescription).addHints(smithy.api.Documentation("<p>Represents the continuous backups and point in time recovery settings on the\n            table.</p>")),
  ){
    UpdateContinuousBackupsOutput.apply
  }.withId(id).addHints(hints)
}
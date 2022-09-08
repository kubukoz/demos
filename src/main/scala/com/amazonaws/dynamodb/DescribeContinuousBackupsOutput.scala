package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeContinuousBackupsOutput(continuousBackupsDescription: Option[ContinuousBackupsDescription] = None)
object DescribeContinuousBackupsOutput extends ShapeTag.Companion[DescribeContinuousBackupsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeContinuousBackupsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[DescribeContinuousBackupsOutput] = struct(
    ContinuousBackupsDescription.schema.optional[DescribeContinuousBackupsOutput]("ContinuousBackupsDescription", _.continuousBackupsDescription).addHints(smithy.api.Documentation("<p>Represents the continuous backups and point in time recovery settings on the\n            table.</p>")),
  ){
    DescribeContinuousBackupsOutput.apply
  }.withId(id).addHints(hints)
}
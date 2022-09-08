package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ContinuousBackupsDescription(continuousBackupsStatus: ContinuousBackupsStatus, pointInTimeRecoveryDescription: Option[PointInTimeRecoveryDescription] = None)
object ContinuousBackupsDescription extends ShapeTag.Companion[ContinuousBackupsDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ContinuousBackupsDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the continuous backups and point in time recovery settings on the\n            table.</p>"),
  )

  implicit val schema: Schema[ContinuousBackupsDescription] = struct(
    ContinuousBackupsStatus.schema.required[ContinuousBackupsDescription]("ContinuousBackupsStatus", _.continuousBackupsStatus).addHints(smithy.api.Documentation("<p>\n            <code>ContinuousBackupsStatus</code> can be one of the following states: ENABLED,\n            DISABLED</p>"), smithy.api.Required()),
    PointInTimeRecoveryDescription.schema.optional[ContinuousBackupsDescription]("PointInTimeRecoveryDescription", _.pointInTimeRecoveryDescription).addHints(smithy.api.Documentation("<p>The description of the point in time recovery settings applied to the table.</p>")),
  ){
    ContinuousBackupsDescription.apply
  }.withId(id).addHints(hints)
}
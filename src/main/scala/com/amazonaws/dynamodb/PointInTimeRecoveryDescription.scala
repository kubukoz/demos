package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class PointInTimeRecoveryDescription(pointInTimeRecoveryStatus: Option[PointInTimeRecoveryStatus] = None, earliestRestorableDateTime: Option[Date] = None, latestRestorableDateTime: Option[Date] = None)
object PointInTimeRecoveryDescription extends ShapeTag.Companion[PointInTimeRecoveryDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PointInTimeRecoveryDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The description of the point in time settings applied to the table.</p>"),
  )

  implicit val schema: Schema[PointInTimeRecoveryDescription] = struct(
    PointInTimeRecoveryStatus.schema.optional[PointInTimeRecoveryDescription]("PointInTimeRecoveryStatus", _.pointInTimeRecoveryStatus).addHints(smithy.api.Documentation("<p>The current state of point in time recovery:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>ENABLED</code> - Point in time recovery is enabled.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DISABLED</code> - Point in time recovery is disabled.</p>\n            </li>\n         </ul>")),
    Date.schema.optional[PointInTimeRecoveryDescription]("EarliestRestorableDateTime", _.earliestRestorableDateTime).addHints(smithy.api.Documentation("<p>Specifies the earliest point in time you can restore your table to. You can restore\n            your table to any point in time during the last 35 days. </p>")),
    Date.schema.optional[PointInTimeRecoveryDescription]("LatestRestorableDateTime", _.latestRestorableDateTime).addHints(smithy.api.Documentation("<p>\n            <code>LatestRestorableDateTime</code> is typically 5 minutes before the current time.\n        </p>")),
  ){
    PointInTimeRecoveryDescription.apply
  }.withId(id).addHints(hints)
}
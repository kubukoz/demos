package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class PointInTimeRecoverySpecification(pointInTimeRecoveryEnabled: BooleanObject)
object PointInTimeRecoverySpecification extends ShapeTag.Companion[PointInTimeRecoverySpecification] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PointInTimeRecoverySpecification")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings used to enable point in time recovery.</p>"),
  )

  implicit val schema: Schema[PointInTimeRecoverySpecification] = struct(
    BooleanObject.schema.required[PointInTimeRecoverySpecification]("PointInTimeRecoveryEnabled", _.pointInTimeRecoveryEnabled).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>Indicates whether point in time recovery is enabled (true) or disabled (false) on the\n            table.</p>")),
  ){
    PointInTimeRecoverySpecification.apply
  }.withId(id).addHints(hints)
}
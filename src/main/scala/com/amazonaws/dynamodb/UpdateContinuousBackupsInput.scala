package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateContinuousBackupsInput(tableName: TableName, pointInTimeRecoverySpecification: PointInTimeRecoverySpecification)
object UpdateContinuousBackupsInput extends ShapeTag.Companion[UpdateContinuousBackupsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateContinuousBackupsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateContinuousBackupsInput] = struct(
    TableName.schema.required[UpdateContinuousBackupsInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>"), smithy.api.Required()),
    PointInTimeRecoverySpecification.schema.required[UpdateContinuousBackupsInput]("PointInTimeRecoverySpecification", _.pointInTimeRecoverySpecification).addHints(smithy.api.Documentation("<p>Represents the settings used to enable point in time recovery.</p>"), smithy.api.Required()),
  ){
    UpdateContinuousBackupsInput.apply
  }.withId(id).addHints(hints)
}
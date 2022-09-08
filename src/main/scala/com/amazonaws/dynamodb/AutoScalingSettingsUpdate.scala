package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class AutoScalingSettingsUpdate(minimumUnits: Option[PositiveLongObject] = None, maximumUnits: Option[PositiveLongObject] = None, autoScalingDisabled: Option[BooleanObject] = None, autoScalingRoleArn: Option[AutoScalingRoleArn] = None, scalingPolicyUpdate: Option[AutoScalingPolicyUpdate] = None)
object AutoScalingSettingsUpdate extends ShapeTag.Companion[AutoScalingSettingsUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingSettingsUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings to be modified for a global table or global\n            secondary index.</p>"),
  )

  implicit val schema: Schema[AutoScalingSettingsUpdate] = struct(
    PositiveLongObject.schema.optional[AutoScalingSettingsUpdate]("MinimumUnits", _.minimumUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The minimum capacity units that a global table or global secondary index should be\n            scaled down to.</p>")),
    PositiveLongObject.schema.optional[AutoScalingSettingsUpdate]("MaximumUnits", _.maximumUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum capacity units that a global table or global secondary index should be\n            scaled up to.</p>")),
    BooleanObject.schema.optional[AutoScalingSettingsUpdate]("AutoScalingDisabled", _.autoScalingDisabled).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Disabled auto scaling for this global table or global secondary index.</p>")),
    AutoScalingRoleArn.schema.optional[AutoScalingSettingsUpdate]("AutoScalingRoleArn", _.autoScalingRoleArn).addHints(smithy.api.Documentation("<p>Role ARN used for configuring auto scaling policy.</p>")),
    AutoScalingPolicyUpdate.schema.optional[AutoScalingSettingsUpdate]("ScalingPolicyUpdate", _.scalingPolicyUpdate).addHints(smithy.api.Documentation("<p>The scaling policy to apply for scaling target global table or global secondary index\n            capacity units.</p>")),
  ){
    AutoScalingSettingsUpdate.apply
  }.withId(id).addHints(hints)
}
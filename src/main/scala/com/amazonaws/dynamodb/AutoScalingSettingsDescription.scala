package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class AutoScalingSettingsDescription(minimumUnits: Option[PositiveLongObject] = None, maximumUnits: Option[PositiveLongObject] = None, autoScalingDisabled: Option[BooleanObject] = None, autoScalingRoleArn: Option[String] = None, scalingPolicies: Option[List[AutoScalingPolicyDescription]] = None)
object AutoScalingSettingsDescription extends ShapeTag.Companion[AutoScalingSettingsDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingSettingsDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings for a global table or global secondary\n            index.</p>"),
  )

  implicit val schema: Schema[AutoScalingSettingsDescription] = struct(
    PositiveLongObject.schema.optional[AutoScalingSettingsDescription]("MinimumUnits", _.minimumUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The minimum capacity units that a global table or global secondary index should be\n            scaled down to.</p>")),
    PositiveLongObject.schema.optional[AutoScalingSettingsDescription]("MaximumUnits", _.maximumUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum capacity units that a global table or global secondary index should be\n            scaled up to.</p>")),
    BooleanObject.schema.optional[AutoScalingSettingsDescription]("AutoScalingDisabled", _.autoScalingDisabled).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Disabled auto scaling for this global table or global secondary index.</p>")),
    string.optional[AutoScalingSettingsDescription]("AutoScalingRoleArn", _.autoScalingRoleArn).addHints(smithy.api.Documentation("<p>Role ARN used for configuring the auto scaling policy.</p>")),
    AutoScalingPolicyDescriptionList.underlyingSchema.optional[AutoScalingSettingsDescription]("ScalingPolicies", _.scalingPolicies).addHints(smithy.api.Documentation("<p>Information about the scaling policies.</p>")),
  ){
    AutoScalingSettingsDescription.apply
  }.withId(id).addHints(hints)
}
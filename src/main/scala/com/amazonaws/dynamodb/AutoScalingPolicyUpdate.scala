package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class AutoScalingPolicyUpdate(targetTrackingScalingPolicyConfiguration: AutoScalingTargetTrackingScalingPolicyConfigurationUpdate, policyName: Option[AutoScalingPolicyName] = None)
object AutoScalingPolicyUpdate extends ShapeTag.Companion[AutoScalingPolicyUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingPolicyUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling policy to be modified.</p>"),
  )

  implicit val schema: Schema[AutoScalingPolicyUpdate] = struct(
    AutoScalingTargetTrackingScalingPolicyConfigurationUpdate.schema.required[AutoScalingPolicyUpdate]("TargetTrackingScalingPolicyConfiguration", _.targetTrackingScalingPolicyConfiguration).addHints(smithy.api.Documentation("<p>Represents a target tracking scaling policy configuration.</p>"), smithy.api.Required()),
    AutoScalingPolicyName.schema.optional[AutoScalingPolicyUpdate]("PolicyName", _.policyName).addHints(smithy.api.Documentation("<p>The name of the scaling policy.</p>")),
  ){
    AutoScalingPolicyUpdate.apply
  }.withId(id).addHints(hints)
}
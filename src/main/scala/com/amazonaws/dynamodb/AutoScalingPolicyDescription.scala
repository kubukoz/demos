package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class AutoScalingPolicyDescription(policyName: Option[AutoScalingPolicyName] = None, targetTrackingScalingPolicyConfiguration: Option[AutoScalingTargetTrackingScalingPolicyConfigurationDescription] = None)
object AutoScalingPolicyDescription extends ShapeTag.Companion[AutoScalingPolicyDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingPolicyDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of the scaling policy.</p>"),
  )

  implicit val schema: Schema[AutoScalingPolicyDescription] = struct(
    AutoScalingPolicyName.schema.optional[AutoScalingPolicyDescription]("PolicyName", _.policyName).addHints(smithy.api.Documentation("<p>The name of the scaling policy.</p>")),
    AutoScalingTargetTrackingScalingPolicyConfigurationDescription.schema.optional[AutoScalingPolicyDescription]("TargetTrackingScalingPolicyConfiguration", _.targetTrackingScalingPolicyConfiguration).addHints(smithy.api.Documentation("<p>Represents a target tracking scaling policy configuration.</p>")),
  ){
    AutoScalingPolicyDescription.apply
  }.withId(id).addHints(hints)
}
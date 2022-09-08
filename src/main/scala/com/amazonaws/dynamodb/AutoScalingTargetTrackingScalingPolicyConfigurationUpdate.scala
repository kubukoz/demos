package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.schema.Schema.double
import smithy4s.ShapeTag

case class AutoScalingTargetTrackingScalingPolicyConfigurationUpdate(targetValue: Double, disableScaleIn: Option[BooleanObject] = None, scaleInCooldown: Option[IntegerObject] = None, scaleOutCooldown: Option[IntegerObject] = None)
object AutoScalingTargetTrackingScalingPolicyConfigurationUpdate extends ShapeTag.Companion[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingTargetTrackingScalingPolicyConfigurationUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings of a target tracking scaling policy that will be\n            modified.</p>"),
  )

  implicit val schema: Schema[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate] = struct(
    double.required[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate]("TargetValue", _.targetValue).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>The target value for the metric. The range is 8.515920e-109 to 1.174271e+108 (Base 10)\n            or 2e-360 to 2e360 (Base 2).</p>")),
    BooleanObject.schema.optional[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate]("DisableScaleIn", _.disableScaleIn).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Indicates whether scale in by the target tracking policy is disabled. If the value is\n            true, scale in is disabled and the target tracking policy won\'t remove capacity from the\n            scalable resource. Otherwise, scale in is enabled and the target tracking policy can\n            remove capacity from the scalable resource. The default value is false.</p>")),
    IntegerObject.schema.optional[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate]("ScaleInCooldown", _.scaleInCooldown).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The amount of time, in seconds, after a scale in activity completes before another\n            scale in activity can start. The cooldown period is used to block subsequent scale in\n            requests until it has expired. You should scale in conservatively to protect your\n            application\'s availability. However, if another alarm triggers a scale out policy during\n            the cooldown period after a scale-in, application auto scaling scales out your scalable\n            target immediately. </p>")),
    IntegerObject.schema.optional[AutoScalingTargetTrackingScalingPolicyConfigurationUpdate]("ScaleOutCooldown", _.scaleOutCooldown).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The amount of time, in seconds, after a scale out activity completes before another\n            scale out activity can start. While the cooldown period is in effect, the capacity that\n            has been added by the previous scale out event that initiated the cooldown is calculated\n            as part of the desired capacity for the next scale out. You should continuously (but not\n            excessively) scale out.</p>")),
  ){
    AutoScalingTargetTrackingScalingPolicyConfigurationUpdate.apply
  }.withId(id).addHints(hints)
}
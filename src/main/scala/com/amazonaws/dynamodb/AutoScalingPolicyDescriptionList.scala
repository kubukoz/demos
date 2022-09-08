package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object AutoScalingPolicyDescriptionList extends Newtype[List[AutoScalingPolicyDescription]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingPolicyDescriptionList")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[AutoScalingPolicyDescription]] = list(AutoScalingPolicyDescription.schema).withId(id).addHints(hints)
  implicit val schema : Schema[AutoScalingPolicyDescriptionList] = bijection(underlyingSchema, asBijection)
}
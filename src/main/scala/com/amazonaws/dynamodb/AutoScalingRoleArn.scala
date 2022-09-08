package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object AutoScalingRoleArn extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AutoScalingRoleArn")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(1600))).validated(smithy.api.Pattern("^[\\u0020-\\uD7FF\\uE000-\\uFFFD\\uD800\\uDC00-\\uDBFF\\uDFFF\\r\\n\\t]*$"))
  implicit val schema : Schema[AutoScalingRoleArn] = bijection(underlyingSchema, asBijection)
}
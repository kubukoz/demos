package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.constant
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.ShapeTag

case class DescribeLimitsInput()
object DescribeLimitsInput extends ShapeTag.Companion[DescribeLimitsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeLimitsInput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the input of a <code>DescribeLimits</code> operation. Has no\n            content.</p>"),
  )

  implicit val schema: Schema[DescribeLimitsInput] = constant(DescribeLimitsInput()).withId(id).addHints(hints)
}
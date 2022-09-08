package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.int
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object IntegerObject extends Newtype[Int] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "IntegerObject")
  val hints : Hints = Hints(
    smithy.api.Box(),
  )
  val underlyingSchema : Schema[Int] = int.withId(id).addHints(hints)
  implicit val schema : Schema[IntegerObject] = bijection(underlyingSchema, asBijection)
}
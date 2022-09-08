package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object Endpoints extends Newtype[List[Endpoint]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Endpoints")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[Endpoint]] = list(Endpoint.schema).withId(id).addHints(hints)
  implicit val schema : Schema[Endpoints] = bijection(underlyingSchema, asBijection)
}
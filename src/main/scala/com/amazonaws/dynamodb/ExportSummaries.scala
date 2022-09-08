package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object ExportSummaries extends Newtype[List[ExportSummary]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ExportSummaries")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[ExportSummary]] = list(ExportSummary.schema).withId(id).addHints(hints)
  implicit val schema : Schema[ExportSummaries] = bijection(underlyingSchema, asBijection)
}
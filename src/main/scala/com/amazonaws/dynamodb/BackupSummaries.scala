package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BackupSummaries extends Newtype[List[BackupSummary]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupSummaries")
  val hints : Hints = Hints.empty
  val underlyingSchema : Schema[List[BackupSummary]] = list(BackupSummary.schema).withId(id).addHints(hints)
  implicit val schema : Schema[BackupSummaries] = bijection(underlyingSchema, asBijection)
}
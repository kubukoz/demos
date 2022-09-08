package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.schema.Schema.list
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object GlobalTableGlobalSecondaryIndexSettingsUpdateList extends Newtype[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableGlobalSecondaryIndexSettingsUpdateList")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] = list(GlobalTableGlobalSecondaryIndexSettingsUpdate.schema).withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1), max = Some(20)))
  implicit val schema : Schema[GlobalTableGlobalSecondaryIndexSettingsUpdateList] = bijection(underlyingSchema, asBijection)
}
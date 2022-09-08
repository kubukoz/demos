package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.Newtype

object BackupArn extends Newtype[String] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupArn")
  val hints : Hints = Hints()
  val underlyingSchema : Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(37), max = Some(1024)))
  implicit val schema : Schema[BackupArn] = bijection(underlyingSchema, asBijection)
}
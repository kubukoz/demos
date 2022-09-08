package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteGlobalSecondaryIndexAction(indexName: IndexName)
object DeleteGlobalSecondaryIndexAction extends ShapeTag.Companion[DeleteGlobalSecondaryIndexAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteGlobalSecondaryIndexAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a global secondary index to be deleted from an existing table.</p>"),
  )

  implicit val schema: Schema[DeleteGlobalSecondaryIndexAction] = struct(
    IndexName.schema.required[DeleteGlobalSecondaryIndexAction]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index to be deleted.</p>"), smithy.api.Required()),
  ){
    DeleteGlobalSecondaryIndexAction.apply
  }.withId(id).addHints(hints)
}
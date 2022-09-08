package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalSecondaryIndexUpdate(update: Option[UpdateGlobalSecondaryIndexAction] = None, create: Option[CreateGlobalSecondaryIndexAction] = None, delete: Option[DeleteGlobalSecondaryIndexAction] = None)
object GlobalSecondaryIndexUpdate extends ShapeTag.Companion[GlobalSecondaryIndexUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents one of the following:</p>\n        <ul>\n            <li>\n                <p>A new global secondary index to be added to an existing table.</p>\n            </li>\n            <li>\n                <p>New provisioned throughput parameters for an existing global secondary\n                    index.</p>\n            </li>\n            <li>\n                <p>An existing global secondary index to be removed from an existing\n                    table.</p>\n            </li>\n         </ul>"),
  )

  implicit val schema: Schema[GlobalSecondaryIndexUpdate] = struct(
    UpdateGlobalSecondaryIndexAction.schema.optional[GlobalSecondaryIndexUpdate]("Update", _.update).addHints(smithy.api.Documentation("<p>The name of an existing global secondary index, along with new provisioned throughput\n            settings to be applied to that index.</p>")),
    CreateGlobalSecondaryIndexAction.schema.optional[GlobalSecondaryIndexUpdate]("Create", _.create).addHints(smithy.api.Documentation("<p>The parameters required for creating a global secondary index on an existing\n            table:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>IndexName </code>\n                </p>\n            </li>\n            <li>\n                <p>\n                    <code>KeySchema </code>\n                </p>\n            </li>\n            <li>\n                <p>\n                    <code>AttributeDefinitions </code>\n                </p>\n            </li>\n            <li>\n                <p>\n                    <code>Projection </code>\n                </p>\n            </li>\n            <li>\n                <p>\n                    <code>ProvisionedThroughput </code>\n                </p>\n            </li>\n         </ul>")),
    DeleteGlobalSecondaryIndexAction.schema.optional[GlobalSecondaryIndexUpdate]("Delete", _.delete).addHints(smithy.api.Documentation("<p>The name of an existing global secondary index to be removed.</p>")),
  ){
    GlobalSecondaryIndexUpdate.apply
  }.withId(id).addHints(hints)
}
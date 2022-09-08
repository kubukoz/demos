package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateGlobalSecondaryIndexAction(indexName: IndexName, keySchema: List[KeySchemaElement], projection: Projection, provisionedThroughput: Option[ProvisionedThroughput] = None)
object CreateGlobalSecondaryIndexAction extends ShapeTag.Companion[CreateGlobalSecondaryIndexAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateGlobalSecondaryIndexAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a new global secondary index to be added to an existing table.</p>"),
  )

  implicit val schema: Schema[CreateGlobalSecondaryIndexAction] = struct(
    IndexName.schema.required[CreateGlobalSecondaryIndexAction]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index to be created.</p>"), smithy.api.Required()),
    KeySchema.underlyingSchema.required[CreateGlobalSecondaryIndexAction]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The key schema for the global secondary index.</p>"), smithy.api.Required()),
    Projection.schema.required[CreateGlobalSecondaryIndexAction]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into an index. These\n            are in addition to the primary key attributes and index key attributes, which are\n            automatically projected.</p>"), smithy.api.Required()),
    ProvisionedThroughput.schema.optional[CreateGlobalSecondaryIndexAction]("ProvisionedThroughput", _.provisionedThroughput).addHints(smithy.api.Documentation("<p>Represents the provisioned throughput settings for the specified global secondary\n            index.</p>\n        <p>For current minimum and maximum provisioned throughput values, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
  ){
    CreateGlobalSecondaryIndexAction.apply
  }.withId(id).addHints(hints)
}
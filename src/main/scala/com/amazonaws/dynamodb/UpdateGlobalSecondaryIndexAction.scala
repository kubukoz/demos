package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateGlobalSecondaryIndexAction(indexName: IndexName, provisionedThroughput: ProvisionedThroughput)
object UpdateGlobalSecondaryIndexAction extends ShapeTag.Companion[UpdateGlobalSecondaryIndexAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalSecondaryIndexAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the new provisioned throughput settings to be applied to a global secondary\n            index.</p>"),
  )

  implicit val schema: Schema[UpdateGlobalSecondaryIndexAction] = struct(
    IndexName.schema.required[UpdateGlobalSecondaryIndexAction]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index to be updated.</p>"), smithy.api.Required()),
    ProvisionedThroughput.schema.required[UpdateGlobalSecondaryIndexAction]("ProvisionedThroughput", _.provisionedThroughput).addHints(smithy.api.Documentation("<p>Represents the provisioned throughput settings for the specified global secondary\n            index.</p>\n        <p>For current minimum and maximum provisioned throughput values, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>"), smithy.api.Required()),
  ){
    UpdateGlobalSecondaryIndexAction.apply
  }.withId(id).addHints(hints)
}
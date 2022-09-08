package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalTableGlobalSecondaryIndexSettingsUpdate(indexName: IndexName, provisionedWriteCapacityUnits: Option[PositiveLongObject] = None, provisionedWriteCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None)
object GlobalTableGlobalSecondaryIndexSettingsUpdate extends ShapeTag.Companion[GlobalTableGlobalSecondaryIndexSettingsUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableGlobalSecondaryIndexSettingsUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings of a global secondary index for a global table that will be\n            modified.</p>"),
  )

  implicit val schema: Schema[GlobalTableGlobalSecondaryIndexSettingsUpdate] = struct(
    IndexName.schema.required[GlobalTableGlobalSecondaryIndexSettingsUpdate]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index. The name must be unique among all other\n            indexes on this table.</p>"), smithy.api.Required()),
    PositiveLongObject.schema.optional[GlobalTableGlobalSecondaryIndexSettingsUpdate]("ProvisionedWriteCapacityUnits", _.provisionedWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException.</code>\n         </p>")),
    AutoScalingSettingsUpdate.schema.optional[GlobalTableGlobalSecondaryIndexSettingsUpdate]("ProvisionedWriteCapacityAutoScalingSettingsUpdate", _.provisionedWriteCapacityAutoScalingSettingsUpdate).addHints(smithy.api.Documentation("<p>Auto scaling settings for managing a global secondary index\'s write capacity\n            units.</p>")),
  ){
    GlobalTableGlobalSecondaryIndexSettingsUpdate.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalSecondaryIndexAutoScalingUpdate(indexName: Option[IndexName] = None, provisionedWriteCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None)
object GlobalSecondaryIndexAutoScalingUpdate extends ShapeTag.Companion[GlobalSecondaryIndexAutoScalingUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexAutoScalingUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings of a global secondary index for a global table\n            that will be modified.</p>"),
  )

  implicit val schema: Schema[GlobalSecondaryIndexAutoScalingUpdate] = struct(
    IndexName.schema.optional[GlobalSecondaryIndexAutoScalingUpdate]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>")),
    AutoScalingSettingsUpdate.schema.optional[GlobalSecondaryIndexAutoScalingUpdate]("ProvisionedWriteCapacityAutoScalingUpdate", _.provisionedWriteCapacityAutoScalingUpdate),
  ){
    GlobalSecondaryIndexAutoScalingUpdate.apply
  }.withId(id).addHints(hints)
}
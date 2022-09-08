package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndexAutoScalingUpdate(indexName: Option[IndexName] = None, provisionedReadCapacityAutoScalingUpdate: Option[AutoScalingSettingsUpdate] = None)
object ReplicaGlobalSecondaryIndexAutoScalingUpdate extends ShapeTag.Companion[ReplicaGlobalSecondaryIndexAutoScalingUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexAutoScalingUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling settings of a global secondary index for a replica that\n            will be modified.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndexAutoScalingUpdate] = struct(
    IndexName.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingUpdate]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>")),
    AutoScalingSettingsUpdate.schema.optional[ReplicaGlobalSecondaryIndexAutoScalingUpdate]("ProvisionedReadCapacityAutoScalingUpdate", _.provisionedReadCapacityAutoScalingUpdate),
  ){
    ReplicaGlobalSecondaryIndexAutoScalingUpdate.apply
  }.withId(id).addHints(hints)
}
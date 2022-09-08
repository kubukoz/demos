package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndexDescription(indexName: Option[IndexName] = None, provisionedThroughputOverride: Option[ProvisionedThroughputOverride] = None)
object ReplicaGlobalSecondaryIndexDescription extends ShapeTag.Companion[ReplicaGlobalSecondaryIndexDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndexDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a replica global secondary index.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndexDescription] = struct(
    IndexName.schema.optional[ReplicaGlobalSecondaryIndexDescription]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>")),
    ProvisionedThroughputOverride.schema.optional[ReplicaGlobalSecondaryIndexDescription]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>If not described, uses the source table GSI\'s read capacity settings.</p>")),
  ){
    ReplicaGlobalSecondaryIndexDescription.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaGlobalSecondaryIndex(indexName: IndexName, provisionedThroughputOverride: Option[ProvisionedThroughputOverride] = None)
object ReplicaGlobalSecondaryIndex extends ShapeTag.Companion[ReplicaGlobalSecondaryIndex] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaGlobalSecondaryIndex")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a replica global secondary index.</p>"),
  )

  implicit val schema: Schema[ReplicaGlobalSecondaryIndex] = struct(
    IndexName.schema.required[ReplicaGlobalSecondaryIndex]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>"), smithy.api.Required()),
    ProvisionedThroughputOverride.schema.optional[ReplicaGlobalSecondaryIndex]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Replica table GSI-specific provisioned throughput. If not specified, uses the source\n            table GSI\'s read capacity settings.</p>")),
  ){
    ReplicaGlobalSecondaryIndex.apply
  }.withId(id).addHints(hints)
}
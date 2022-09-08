package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ProvisionedThroughputOverride(readCapacityUnits: Option[PositiveLongObject] = None)
object ProvisionedThroughputOverride extends ShapeTag.Companion[ProvisionedThroughputOverride] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ProvisionedThroughputOverride")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Replica-specific provisioned throughput settings. If not specified, uses the source\n            table\'s provisioned throughput settings.</p>"),
  )

  implicit val schema: Schema[ProvisionedThroughputOverride] = struct(
    PositiveLongObject.schema.optional[ProvisionedThroughputOverride]("ReadCapacityUnits", _.readCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Replica-specific read capacity units. If not specified, uses the source table\'s read\n            capacity settings.</p>")),
  ){
    ProvisionedThroughputOverride.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Capacity(readCapacityUnits: Option[ConsumedCapacityUnits] = None, writeCapacityUnits: Option[ConsumedCapacityUnits] = None, capacityUnits: Option[ConsumedCapacityUnits] = None)
object Capacity extends ShapeTag.Companion[Capacity] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Capacity")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the amount of provisioned throughput capacity consumed on a table or an\n            index.</p>"),
  )

  implicit val schema: Schema[Capacity] = struct(
    ConsumedCapacityUnits.schema.optional[Capacity]("ReadCapacityUnits", _.readCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of read capacity units consumed on a table or an index.</p>")),
    ConsumedCapacityUnits.schema.optional[Capacity]("WriteCapacityUnits", _.writeCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of write capacity units consumed on a table or an index.</p>")),
    ConsumedCapacityUnits.schema.optional[Capacity]("CapacityUnits", _.capacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of capacity units consumed on a table or an index.</p>")),
  ){
    Capacity.apply
  }.withId(id).addHints(hints)
}
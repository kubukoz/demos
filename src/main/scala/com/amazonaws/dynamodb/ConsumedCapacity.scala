package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ConsumedCapacity(tableName: Option[TableName] = None, capacityUnits: Option[ConsumedCapacityUnits] = None, readCapacityUnits: Option[ConsumedCapacityUnits] = None, writeCapacityUnits: Option[ConsumedCapacityUnits] = None, table: Option[Capacity] = None, localSecondaryIndexes: Option[Map[IndexName,Capacity]] = None, globalSecondaryIndexes: Option[Map[IndexName,Capacity]] = None)
object ConsumedCapacity extends ShapeTag.Companion[ConsumedCapacity] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ConsumedCapacity")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The capacity units consumed by an operation. The data returned includes the total\n            provisioned throughput consumed, along with statistics for the table and any indexes\n            involved in the operation. <code>ConsumedCapacity</code> is only returned if the request\n            asked for it. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ProvisionedThroughputIntro.html\">Provisioned Throughput</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>"),
  )

  implicit val schema: Schema[ConsumedCapacity] = struct(
    TableName.schema.optional[ConsumedCapacity]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table that was affected by the operation.</p>")),
    ConsumedCapacityUnits.schema.optional[ConsumedCapacity]("CapacityUnits", _.capacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of capacity units consumed by the operation.</p>")),
    ConsumedCapacityUnits.schema.optional[ConsumedCapacity]("ReadCapacityUnits", _.readCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of read capacity units consumed by the operation.</p>")),
    ConsumedCapacityUnits.schema.optional[ConsumedCapacity]("WriteCapacityUnits", _.writeCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The total number of write capacity units consumed by the operation.</p>")),
    Capacity.schema.optional[ConsumedCapacity]("Table", _.table).addHints(smithy.api.Documentation("<p>The amount of throughput consumed on the table affected by the operation.</p>")),
    SecondaryIndexesCapacityMap.underlyingSchema.optional[ConsumedCapacity]("LocalSecondaryIndexes", _.localSecondaryIndexes).addHints(smithy.api.Documentation("<p>The amount of throughput consumed on each local index affected by the\n            operation.</p>")),
    SecondaryIndexesCapacityMap.underlyingSchema.optional[ConsumedCapacity]("GlobalSecondaryIndexes", _.globalSecondaryIndexes).addHints(smithy.api.Documentation("<p>The amount of throughput consumed on each global index affected by the\n            operation.</p>")),
  ){
    ConsumedCapacity.apply
  }.withId(id).addHints(hints)
}
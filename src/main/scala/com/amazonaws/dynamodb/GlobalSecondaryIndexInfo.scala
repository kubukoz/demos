package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalSecondaryIndexInfo(indexName: Option[IndexName] = None, keySchema: Option[List[KeySchemaElement]] = None, projection: Option[Projection] = None, provisionedThroughput: Option[ProvisionedThroughput] = None)
object GlobalSecondaryIndexInfo extends ShapeTag.Companion[GlobalSecondaryIndexInfo] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndexInfo")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a global secondary index for the table when the backup\n            was created.</p>"),
  )

  implicit val schema: Schema[GlobalSecondaryIndexInfo] = struct(
    IndexName.schema.optional[GlobalSecondaryIndexInfo]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index.</p>")),
    KeySchema.underlyingSchema.optional[GlobalSecondaryIndexInfo]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The complete key schema for a global secondary index, which consists of one or more\n            pairs of attribute names and key types:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of an internal hash function to evenly distribute data items across\n                partitions, based on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with\n                the same partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>")),
    Projection.schema.optional[GlobalSecondaryIndexInfo]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into the global\n            secondary index. These are in addition to the primary key attributes and index key\n            attributes, which are automatically projected. </p>")),
    ProvisionedThroughput.schema.optional[GlobalSecondaryIndexInfo]("ProvisionedThroughput", _.provisionedThroughput).addHints(smithy.api.Documentation("<p>Represents the provisioned throughput settings for the specified global secondary\n            index. </p>")),
  ){
    GlobalSecondaryIndexInfo.apply
  }.withId(id).addHints(hints)
}
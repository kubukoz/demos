package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class LocalSecondaryIndexInfo(indexName: Option[IndexName] = None, keySchema: Option[List[KeySchemaElement]] = None, projection: Option[Projection] = None)
object LocalSecondaryIndexInfo extends ShapeTag.Companion[LocalSecondaryIndexInfo] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndexInfo")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a local secondary index for the table when the backup was\n            created.</p>"),
  )

  implicit val schema: Schema[LocalSecondaryIndexInfo] = struct(
    IndexName.schema.optional[LocalSecondaryIndexInfo]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>Represents the name of the local secondary index.</p>")),
    KeySchema.underlyingSchema.optional[LocalSecondaryIndexInfo]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The complete key schema for a local secondary index, which consists of one or more\n            pairs of attribute names and key types:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of\n                an internal hash function to evenly distribute data items across partitions, based\n                on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with the same\n                partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>")),
    Projection.schema.optional[LocalSecondaryIndexInfo]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into the global\n            secondary index. These are in addition to the primary key attributes and index key\n            attributes, which are automatically projected. </p>")),
  ){
    LocalSecondaryIndexInfo.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class LocalSecondaryIndex(indexName: IndexName, keySchema: List[KeySchemaElement], projection: Projection)
object LocalSecondaryIndex extends ShapeTag.Companion[LocalSecondaryIndex] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndex")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a local secondary index.</p>"),
  )

  implicit val schema: Schema[LocalSecondaryIndex] = struct(
    IndexName.schema.required[LocalSecondaryIndex]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the local secondary index. The name must be unique among all other indexes\n            on this table.</p>"), smithy.api.Required()),
    KeySchema.underlyingSchema.required[LocalSecondaryIndex]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The complete key schema for the local secondary index, consisting of one or more pairs\n            of attribute names and key types:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of\n                an internal hash function to evenly distribute data items across partitions, based\n                on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with the same\n                partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>"), smithy.api.Required()),
    Projection.schema.required[LocalSecondaryIndex]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into the local\n            secondary index. These are in addition to the primary key attributes and index key\n            attributes, which are automatically projected. </p>"), smithy.api.Required()),
  ){
    LocalSecondaryIndex.apply
  }.withId(id).addHints(hints)
}
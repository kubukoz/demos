package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag
import smithy4s.schema.Schema.long

case class LocalSecondaryIndexDescription(indexSizeBytes: Long = 0, itemCount: Long = 0, indexName: Option[IndexName] = None, keySchema: Option[List[KeySchemaElement]] = None, projection: Option[Projection] = None, indexArn: Option[String] = None)
object LocalSecondaryIndexDescription extends ShapeTag.Companion[LocalSecondaryIndexDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LocalSecondaryIndexDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a local secondary index.</p>"),
  )

  implicit val schema: Schema[LocalSecondaryIndexDescription] = struct(
    long.required[LocalSecondaryIndexDescription]("IndexSizeBytes", _.indexSizeBytes).addHints(smithy.api.Documentation("<p>The total size of the specified index, in bytes. DynamoDB updates this value\n            approximately every six hours. Recent changes might not be reflected in this\n            value.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    long.required[LocalSecondaryIndexDescription]("ItemCount", _.itemCount).addHints(smithy.api.Documentation("<p>The number of items in the specified index. DynamoDB updates this value\n            approximately every six hours. Recent changes might not be reflected in this\n            value.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    IndexName.schema.optional[LocalSecondaryIndexDescription]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>Represents the name of the local secondary index.</p>")),
    KeySchema.underlyingSchema.optional[LocalSecondaryIndexDescription]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The complete key schema for the local secondary index, consisting of one or more pairs\n            of attribute names and key types:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of\n                an internal hash function to evenly distribute data items across partitions, based\n                on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with the same\n                partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>")),
    Projection.schema.optional[LocalSecondaryIndexDescription]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into the global\n            secondary index. These are in addition to the primary key attributes and index key\n            attributes, which are automatically projected. </p>")),
    string.optional[LocalSecondaryIndexDescription]("IndexArn", _.indexArn).addHints(smithy.api.Documentation("<p>The Amazon Resource Name (ARN) that uniquely identifies the index.</p>")),
  ){
    LocalSecondaryIndexDescription.apply
  }.withId(id).addHints(hints)
}
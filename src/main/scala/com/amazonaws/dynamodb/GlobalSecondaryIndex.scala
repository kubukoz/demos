package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalSecondaryIndex(indexName: IndexName, keySchema: List[KeySchemaElement], projection: Projection, provisionedThroughput: Option[ProvisionedThroughput] = None)
object GlobalSecondaryIndex extends ShapeTag.Companion[GlobalSecondaryIndex] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalSecondaryIndex")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a global secondary index.</p>"),
  )

  implicit val schema: Schema[GlobalSecondaryIndex] = struct(
    IndexName.schema.required[GlobalSecondaryIndex]("IndexName", _.indexName).addHints(smithy.api.Documentation("<p>The name of the global secondary index. The name must be unique among all other\n            indexes on this table.</p>"), smithy.api.Required()),
    KeySchema.underlyingSchema.required[GlobalSecondaryIndex]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>The complete key schema for a global secondary index, which consists of one or more\n            pairs of attribute names and key types:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of\n                an internal hash function to evenly distribute data items across partitions, based\n                on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with the same\n                partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>"), smithy.api.Required()),
    Projection.schema.required[GlobalSecondaryIndex]("Projection", _.projection).addHints(smithy.api.Documentation("<p>Represents attributes that are copied (projected) from the table into the global\n            secondary index. These are in addition to the primary key attributes and index key\n            attributes, which are automatically projected. </p>"), smithy.api.Required()),
    ProvisionedThroughput.schema.optional[GlobalSecondaryIndex]("ProvisionedThroughput", _.provisionedThroughput).addHints(smithy.api.Documentation("<p>Represents the provisioned throughput settings for the specified global secondary\n            index.</p>\n        <p>For current minimum and maximum provisioned throughput values, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
  ){
    GlobalSecondaryIndex.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class KeySchemaElement(attributeName: KeySchemaAttributeName, keyType: KeyType)
object KeySchemaElement extends ShapeTag.Companion[KeySchemaElement] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "KeySchemaElement")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents <i>a single element</i> of a key schema. A key schema\n            specifies the attributes that make up the primary key of a table, or the key attributes\n            of an index.</p>\n        <p>A <code>KeySchemaElement</code> represents exactly one attribute of the primary key.\n            For example, a simple primary key would be represented by one\n                <code>KeySchemaElement</code> (for the partition key). A composite primary key would\n            require one <code>KeySchemaElement</code> for the partition key, and another\n                <code>KeySchemaElement</code> for the sort key.</p>\n        <p>A <code>KeySchemaElement</code> must be a scalar, top-level attribute (not a nested\n            attribute). The data type must be one of String, Number, or Binary. The attribute cannot\n            be nested within a List or a Map.</p>"),
  )

  implicit val schema: Schema[KeySchemaElement] = struct(
    KeySchemaAttributeName.schema.required[KeySchemaElement]("AttributeName", _.attributeName).addHints(smithy.api.Documentation("<p>The name of a key attribute.</p>"), smithy.api.Required()),
    KeyType.schema.required[KeySchemaElement]("KeyType", _.keyType).addHints(smithy.api.Documentation("<p>The role that this key attribute will assume:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>HASH</code> - partition key</p>\n            </li>\n            <li>\n                <p>\n                    <code>RANGE</code> - sort key</p>\n            </li>\n         </ul>\n        <note>\n            <p>The partition key of an item is also known as its <i>hash\n                    attribute</i>. The term \"hash attribute\" derives from DynamoDB\'s usage of an internal hash function to evenly distribute data items across\n                partitions, based on their partition key values.</p>\n            <p>The sort key of an item is also known as its <i>range attribute</i>.\n                The term \"range attribute\" derives from the way DynamoDB stores items with\n                the same partition key physically close together, in sorted order by the sort key\n                value.</p>\n        </note>"), smithy.api.Required()),
  ){
    KeySchemaElement.apply
  }.withId(id).addHints(hints)
}
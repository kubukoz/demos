package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class AttributeDefinition(attributeName: KeySchemaAttributeName, attributeType: ScalarAttributeType)
object AttributeDefinition extends ShapeTag.Companion[AttributeDefinition] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "AttributeDefinition")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents an attribute for describing the key schema for the table and\n            indexes.</p>"),
  )

  implicit val schema: Schema[AttributeDefinition] = struct(
    KeySchemaAttributeName.schema.required[AttributeDefinition]("AttributeName", _.attributeName).addHints(smithy.api.Documentation("<p>A name for the attribute.</p>"), smithy.api.Required()),
    ScalarAttributeType.schema.required[AttributeDefinition]("AttributeType", _.attributeType).addHints(smithy.api.Documentation("<p>The data type for the attribute, where:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>S</code> - the attribute is of type String</p>\n            </li>\n            <li>\n                <p>\n                    <code>N</code> - the attribute is of type Number</p>\n            </li>\n            <li>\n                <p>\n                    <code>B</code> - the attribute is of type Binary</p>\n            </li>\n         </ul>"), smithy.api.Required()),
  ){
    AttributeDefinition.apply
  }.withId(id).addHints(hints)
}
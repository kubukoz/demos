package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class PutRequest(item: Map[AttributeName,AttributeValue])
object PutRequest extends ShapeTag.Companion[PutRequest] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "PutRequest")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform a <code>PutItem</code> operation on an item.</p>"),
  )

  implicit val schema: Schema[PutRequest] = struct(
    PutItemInputAttributeMap.underlyingSchema.required[PutRequest]("Item", _.item).addHints(smithy.api.Documentation("<p>A map of attribute name to attribute values, representing the primary key of an item\n            to be processed by <code>PutItem</code>. All of the table\'s primary key attributes must\n            be specified, and their data types must match those of the table\'s key schema. If any\n            attributes are present in the item that are part of an index key schema for the table,\n            their types must match the index key schema.</p>"), smithy.api.Required()),
  ){
    PutRequest.apply
  }.withId(id).addHints(hints)
}
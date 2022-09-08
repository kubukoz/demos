package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteRequest(key: Map[AttributeName,AttributeValue])
object DeleteRequest extends ShapeTag.Companion[DeleteRequest] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteRequest")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a request to perform a <code>DeleteItem</code> operation on an item.</p>"),
  )

  implicit val schema: Schema[DeleteRequest] = struct(
    Key.underlyingSchema.required[DeleteRequest]("Key", _.key).addHints(smithy.api.Documentation("<p>A map of attribute name to attribute values, representing the primary key of the item\n            to delete. All of the table\'s primary key attributes must be specified, and their data\n            types must match those of the table\'s key schema.</p>"), smithy.api.Required()),
  ){
    DeleteRequest.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ItemResponse(item: Option[Map[AttributeName,AttributeValue]] = None)
object ItemResponse extends ShapeTag.Companion[ItemResponse] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemResponse")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Details for the requested item.</p>"),
  )

  implicit val schema: Schema[ItemResponse] = struct(
    AttributeMap.underlyingSchema.optional[ItemResponse]("Item", _.item).addHints(smithy.api.Documentation("<p>Map of attribute data consisting of the data type and attribute value.</p>")),
  ){
    ItemResponse.apply
  }.withId(id).addHints(hints)
}
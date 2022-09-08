package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactGetItemsOutput(consumedCapacity: Option[List[ConsumedCapacity]] = None, responses: Option[List[ItemResponse]] = None)
object TransactGetItemsOutput extends ShapeTag.Companion[TransactGetItemsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactGetItemsOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TransactGetItemsOutput] = struct(
    ConsumedCapacityMultiple.underlyingSchema.optional[TransactGetItemsOutput]("ConsumedCapacity", _.consumedCapacity).addHints(smithy.api.Documentation("<p>If the <i>ReturnConsumedCapacity</i> value was <code>TOTAL</code>, this\n            is an array of <code>ConsumedCapacity</code> objects, one for each table addressed by\n                <code>TransactGetItem</code> objects in the <i>TransactItems</i>\n            parameter. These <code>ConsumedCapacity</code> objects report the read-capacity units\n            consumed by the <code>TransactGetItems</code> call in that table.</p>")),
    ItemResponseList.underlyingSchema.optional[TransactGetItemsOutput]("Responses", _.responses).addHints(smithy.api.Documentation("<p>An ordered array of up to 25 <code>ItemResponse</code> objects, each of which\n            corresponds to the <code>TransactGetItem</code> object in the same position in the\n                <i>TransactItems</i> array. Each <code>ItemResponse</code> object\n            contains a Map of the name-value pairs that are the projected attributes of the\n            requested item.</p>\n        <p>If a requested item could not be retrieved, the corresponding\n                <code>ItemResponse</code> object is Null, or if the requested item has no projected\n            attributes, the corresponding <code>ItemResponse</code> object is an empty Map. </p>")),
  ){
    TransactGetItemsOutput.apply
  }.withId(id).addHints(hints)
}
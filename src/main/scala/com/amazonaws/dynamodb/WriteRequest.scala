package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class WriteRequest(putRequest: Option[PutRequest] = None, deleteRequest: Option[DeleteRequest] = None)
object WriteRequest extends ShapeTag.Companion[WriteRequest] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "WriteRequest")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents an operation to perform - either <code>DeleteItem</code> or\n                <code>PutItem</code>. You can only request one of these operations, not both, in a\n            single <code>WriteRequest</code>. If you do need to perform both of these operations,\n            you need to provide two separate <code>WriteRequest</code> objects.</p>"),
  )

  implicit val schema: Schema[WriteRequest] = struct(
    PutRequest.schema.optional[WriteRequest]("PutRequest", _.putRequest).addHints(smithy.api.Documentation("<p>A request to perform a <code>PutItem</code> operation.</p>")),
    DeleteRequest.schema.optional[WriteRequest]("DeleteRequest", _.deleteRequest).addHints(smithy.api.Documentation("<p>A request to perform a <code>DeleteItem</code> operation.</p>")),
  ){
    WriteRequest.apply
  }.withId(id).addHints(hints)
}
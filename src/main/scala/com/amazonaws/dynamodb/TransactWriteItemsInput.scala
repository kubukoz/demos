package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TransactWriteItemsInput(transactItems: List[TransactWriteItem], returnConsumedCapacity: Option[ReturnConsumedCapacity] = None, returnItemCollectionMetrics: Option[ReturnItemCollectionMetrics] = None, clientRequestToken: Option[ClientRequestToken] = None)
object TransactWriteItemsInput extends ShapeTag.Companion[TransactWriteItemsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TransactWriteItemsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[TransactWriteItemsInput] = struct(
    TransactWriteItemList.underlyingSchema.required[TransactWriteItemsInput]("TransactItems", _.transactItems).addHints(smithy.api.Documentation("<p>An ordered array of up to 25 <code>TransactWriteItem</code> objects, each of which\n            contains a <code>ConditionCheck</code>, <code>Put</code>, <code>Update</code>, or\n                <code>Delete</code> object. These can operate on items in different tables, but the\n            tables must reside in the same Amazon Web Services account and Region, and no two of them\n            can operate on the same item. </p>"), smithy.api.Required()),
    ReturnConsumedCapacity.schema.optional[TransactWriteItemsInput]("ReturnConsumedCapacity", _.returnConsumedCapacity),
    ReturnItemCollectionMetrics.schema.optional[TransactWriteItemsInput]("ReturnItemCollectionMetrics", _.returnItemCollectionMetrics).addHints(smithy.api.Documentation("<p>Determines whether item collection metrics are returned. If set to <code>SIZE</code>,\n            the response includes statistics about item collections (if any), that were modified\n            during the operation and are returned in the response. If set to <code>NONE</code> (the\n            default), no statistics are returned. </p>")),
    ClientRequestToken.schema.optional[TransactWriteItemsInput]("ClientRequestToken", _.clientRequestToken).addHints(smithy.api.Documentation("<p>Providing a <code>ClientRequestToken</code> makes the call to\n                <code>TransactWriteItems</code> idempotent, meaning that multiple identical calls\n            have the same effect as one single call.</p>\n        <p>Although multiple identical calls using the same client request token produce the same\n            result on the server (no side effects), the responses to the calls might not be the\n            same. If the <code>ReturnConsumedCapacity></code> parameter is set, then the initial\n                <code>TransactWriteItems</code> call returns the amount of write capacity units\n            consumed in making the changes. Subsequent <code>TransactWriteItems</code> calls with\n            the same client token return the number of read capacity units consumed in reading the\n            item.</p>\n        <p>A client request token is valid for 10 minutes after the first request that uses it is\n            completed. After 10 minutes, any request with the same client token is treated as a new\n            request. Do not resubmit the same request with the same client token for more than 10\n            minutes, or the result might not be idempotent.</p>\n        <p>If you submit a request with the same client token but a change in other parameters\n            within the 10-minute idempotency window, DynamoDB returns an\n                <code>IdempotentParameterMismatch</code> exception.</p>"), smithy.api.IdempotencyToken()),
  ){
    TransactWriteItemsInput.apply
  }.withId(id).addHints(hints)
}
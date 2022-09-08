package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ItemCollectionMetrics(itemCollectionKey: Option[Map[AttributeName,AttributeValue]] = None, sizeEstimateRangeGB: Option[List[ItemCollectionSizeEstimateBound]] = None)
object ItemCollectionMetrics extends ShapeTag.Companion[ItemCollectionMetrics] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ItemCollectionMetrics")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Information about item collections, if any, that were affected by the operation.\n                <code>ItemCollectionMetrics</code> is only returned if the request asked for it. If\n            the table does not have any local secondary indexes, this information is not returned in\n            the response.</p>"),
  )

  implicit val schema: Schema[ItemCollectionMetrics] = struct(
    ItemCollectionKeyAttributeMap.underlyingSchema.optional[ItemCollectionMetrics]("ItemCollectionKey", _.itemCollectionKey).addHints(smithy.api.Documentation("<p>The partition key value of the item collection. This value is the same as the\n            partition key value of the item.</p>")),
    ItemCollectionSizeEstimateRange.underlyingSchema.optional[ItemCollectionMetrics]("SizeEstimateRangeGB", _.sizeEstimateRangeGB).addHints(smithy.api.Documentation("<p>An estimate of item collection size, in gigabytes. This value is a two-element array\n            containing a lower bound and an upper bound for the estimate. The estimate includes the\n            size of all the items in the table, plus the size of all attributes projected into all\n            of the local secondary indexes on that table. Use this estimate to measure whether a\n            local secondary index is approaching its size limit.</p>\n        <p>The estimate is subject to change over time; therefore, do not rely on the precision\n            or accuracy of the estimate.</p>")),
  ){
    ItemCollectionMetrics.apply
  }.withId(id).addHints(hints)
}
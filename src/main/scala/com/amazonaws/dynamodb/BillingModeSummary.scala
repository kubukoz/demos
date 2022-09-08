package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BillingModeSummary(billingMode: Option[BillingMode] = None, lastUpdateToPayPerRequestDateTime: Option[Date] = None)
object BillingModeSummary extends ShapeTag.Companion[BillingModeSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BillingModeSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the details for the read/write capacity mode.</p>"),
  )

  implicit val schema: Schema[BillingModeSummary] = struct(
    BillingMode.schema.optional[BillingModeSummary]("BillingMode", _.billingMode).addHints(smithy.api.Documentation("<p>Controls how you are charged for read and write throughput and how you manage\n            capacity. This setting can be changed later.</p>\n        <ul>\n            <li>\n                <p>\n                    <code>PROVISIONED</code> - Sets the read/write capacity mode to\n                        <code>PROVISIONED</code>. We recommend using <code>PROVISIONED</code> for\n                    predictable workloads.</p>\n            </li>\n            <li>\n                <p>\n                    <code>PAY_PER_REQUEST</code> - Sets the read/write capacity mode to\n                        <code>PAY_PER_REQUEST</code>. We recommend using\n                        <code>PAY_PER_REQUEST</code> for unpredictable workloads. </p>\n            </li>\n         </ul>")),
    Date.schema.optional[BillingModeSummary]("LastUpdateToPayPerRequestDateTime", _.lastUpdateToPayPerRequestDateTime).addHints(smithy.api.Documentation("<p>Represents the time when <code>PAY_PER_REQUEST</code> was last set as the read/write\n            capacity mode.</p>")),
  ){
    BillingModeSummary.apply
  }.withId(id).addHints(hints)
}
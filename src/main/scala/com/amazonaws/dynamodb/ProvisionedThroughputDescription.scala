package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ProvisionedThroughputDescription(lastIncreaseDateTime: Option[Date] = None, lastDecreaseDateTime: Option[Date] = None, numberOfDecreasesToday: Option[PositiveLongObject] = None, readCapacityUnits: Option[NonNegativeLongObject] = None, writeCapacityUnits: Option[NonNegativeLongObject] = None)
object ProvisionedThroughputDescription extends ShapeTag.Companion[ProvisionedThroughputDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ProvisionedThroughputDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the provisioned throughput settings for the table, consisting of read and\n            write capacity units, along with data about increases and decreases.</p>"),
  )

  implicit val schema: Schema[ProvisionedThroughputDescription] = struct(
    Date.schema.optional[ProvisionedThroughputDescription]("LastIncreaseDateTime", _.lastIncreaseDateTime).addHints(smithy.api.Documentation("<p>The date and time of the last provisioned throughput increase for this table.</p>")),
    Date.schema.optional[ProvisionedThroughputDescription]("LastDecreaseDateTime", _.lastDecreaseDateTime).addHints(smithy.api.Documentation("<p>The date and time of the last provisioned throughput decrease for this table.</p>")),
    PositiveLongObject.schema.optional[ProvisionedThroughputDescription]("NumberOfDecreasesToday", _.numberOfDecreasesToday).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The number of provisioned throughput decreases for this table during this UTC calendar\n            day. For current maximums on provisioned throughput decreases, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>")),
    NonNegativeLongObject.schema.optional[ProvisionedThroughputDescription]("ReadCapacityUnits", _.readCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>. Eventually consistent reads require less\n            effort than strongly consistent reads, so a setting of 50 <code>ReadCapacityUnits</code>\n            per second provides 100 eventually consistent <code>ReadCapacityUnits</code> per\n            second.</p>")),
    NonNegativeLongObject.schema.optional[ProvisionedThroughputDescription]("WriteCapacityUnits", _.writeCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException</code>.</p>")),
  ){
    ProvisionedThroughputDescription.apply
  }.withId(id).addHints(hints)
}
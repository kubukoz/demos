package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag
import smithy4s.schema.Schema.long

case class SourceTableDetails(tableName: TableName, tableId: TableId, keySchema: List[KeySchemaElement], tableCreationDateTime: TableCreationDateTime, provisionedThroughput: ProvisionedThroughput, tableSizeBytes: Long = 0, tableArn: Option[TableArn] = None, itemCount: Option[ItemCount] = None, billingMode: Option[BillingMode] = None)
object SourceTableDetails extends ShapeTag.Companion[SourceTableDetails] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "SourceTableDetails")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains the details of the table when the backup was created. </p>"),
  )

  implicit val schema: Schema[SourceTableDetails] = struct(
    TableName.schema.required[SourceTableDetails]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table for which the backup was created. </p>"), smithy.api.Required()),
    TableId.schema.required[SourceTableDetails]("TableId", _.tableId).addHints(smithy.api.Documentation("<p>Unique identifier for the table for which the backup was created. </p>"), smithy.api.Required()),
    KeySchema.underlyingSchema.required[SourceTableDetails]("KeySchema", _.keySchema).addHints(smithy.api.Documentation("<p>Schema of the table. </p>"), smithy.api.Required()),
    TableCreationDateTime.schema.required[SourceTableDetails]("TableCreationDateTime", _.tableCreationDateTime).addHints(smithy.api.Documentation("<p>Time when the source table was created. </p>"), smithy.api.Required()),
    ProvisionedThroughput.schema.required[SourceTableDetails]("ProvisionedThroughput", _.provisionedThroughput).addHints(smithy.api.Documentation("<p>Read IOPs and Write IOPS on the table when the backup was created.</p>"), smithy.api.Required()),
    long.required[SourceTableDetails]("TableSizeBytes", _.tableSizeBytes).addHints(smithy.api.Documentation("<p>Size of the table in bytes. Note that this is an approximate value.</p>"), smithy.api.Default(smithy4s.Document.fromDouble(0.0))),
    TableArn.schema.optional[SourceTableDetails]("TableArn", _.tableArn).addHints(smithy.api.Documentation("<p>ARN of the table for which backup was created. </p>")),
    ItemCount.schema.optional[SourceTableDetails]("ItemCount", _.itemCount).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Number of items in the table. Note that this is an approximate value. </p>")),
    BillingMode.schema.optional[SourceTableDetails]("BillingMode", _.billingMode).addHints(smithy.api.Documentation("<p>Controls how you are charged for read and write throughput and how you manage\n            capacity. This setting can be changed later.</p>\n        <ul>\n            <li>\n                <p>\n                    <code>PROVISIONED</code> - Sets the read/write capacity mode to\n                        <code>PROVISIONED</code>. We recommend using <code>PROVISIONED</code> for\n                    predictable workloads.</p>\n            </li>\n            <li>\n                <p>\n                    <code>PAY_PER_REQUEST</code> - Sets the read/write capacity mode to\n                        <code>PAY_PER_REQUEST</code>. We recommend using\n                        <code>PAY_PER_REQUEST</code> for unpredictable workloads. </p>\n            </li>\n         </ul>")),
  ){
    SourceTableDetails.apply
  }.withId(id).addHints(hints)
}
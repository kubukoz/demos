package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class RestoreTableToPointInTimeInput(targetTableName: TableName, sourceTableArn: Option[TableArn] = None, sourceTableName: Option[TableName] = None, useLatestRestorableTime: Option[BooleanObject] = None, restoreDateTime: Option[Date] = None, billingModeOverride: Option[BillingMode] = None, globalSecondaryIndexOverride: Option[List[GlobalSecondaryIndex]] = None, localSecondaryIndexOverride: Option[List[LocalSecondaryIndex]] = None, provisionedThroughputOverride: Option[ProvisionedThroughput] = None, sSESpecificationOverride: Option[SSESpecification] = None)
object RestoreTableToPointInTimeInput extends ShapeTag.Companion[RestoreTableToPointInTimeInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "RestoreTableToPointInTimeInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[RestoreTableToPointInTimeInput] = struct(
    TableName.schema.required[RestoreTableToPointInTimeInput]("TargetTableName", _.targetTableName).addHints(smithy.api.Documentation("<p>The name of the new table to which it must be restored to.</p>"), smithy.api.Required()),
    TableArn.schema.optional[RestoreTableToPointInTimeInput]("SourceTableArn", _.sourceTableArn).addHints(smithy.api.Documentation("<p>The DynamoDB table that will be restored. This value is an Amazon Resource Name\n            (ARN).</p>")),
    TableName.schema.optional[RestoreTableToPointInTimeInput]("SourceTableName", _.sourceTableName).addHints(smithy.api.Documentation("<p>Name of the source table that is being restored.</p>")),
    BooleanObject.schema.optional[RestoreTableToPointInTimeInput]("UseLatestRestorableTime", _.useLatestRestorableTime).addHints(smithy.api.Box(), smithy.api.Documentation("<p>Restore the table to the latest possible time. <code>LatestRestorableDateTime</code>\n            is typically 5 minutes before the current time. </p>")),
    Date.schema.optional[RestoreTableToPointInTimeInput]("RestoreDateTime", _.restoreDateTime).addHints(smithy.api.Documentation("<p>Time in the past to restore the table to.</p>")),
    BillingMode.schema.optional[RestoreTableToPointInTimeInput]("BillingModeOverride", _.billingModeOverride).addHints(smithy.api.Documentation("<p>The billing mode of the restored table.</p>")),
    GlobalSecondaryIndexList.underlyingSchema.optional[RestoreTableToPointInTimeInput]("GlobalSecondaryIndexOverride", _.globalSecondaryIndexOverride).addHints(smithy.api.Documentation("<p>List of global secondary indexes for the restored table. The indexes provided should\n            match existing secondary indexes. You can choose to exclude some or all of the indexes\n            at the time of restore.</p>")),
    LocalSecondaryIndexList.underlyingSchema.optional[RestoreTableToPointInTimeInput]("LocalSecondaryIndexOverride", _.localSecondaryIndexOverride).addHints(smithy.api.Documentation("<p>List of local secondary indexes for the restored table. The indexes provided should\n            match existing secondary indexes. You can choose to exclude some or all of the indexes\n            at the time of restore.</p>")),
    ProvisionedThroughput.schema.optional[RestoreTableToPointInTimeInput]("ProvisionedThroughputOverride", _.provisionedThroughputOverride).addHints(smithy.api.Documentation("<p>Provisioned throughput settings for the restored table.</p>")),
    SSESpecification.schema.optional[RestoreTableToPointInTimeInput]("SSESpecificationOverride", _.sSESpecificationOverride).addHints(smithy.api.Documentation("<p>The new server-side encryption settings for the restored table.</p>")),
  ){
    RestoreTableToPointInTimeInput.apply
  }.withId(id).addHints(hints)
}
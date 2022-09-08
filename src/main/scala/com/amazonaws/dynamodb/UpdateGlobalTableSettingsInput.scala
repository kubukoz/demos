package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateGlobalTableSettingsInput(globalTableName: TableName, globalTableBillingMode: Option[BillingMode] = None, globalTableProvisionedWriteCapacityUnits: Option[PositiveLongObject] = None, globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate: Option[AutoScalingSettingsUpdate] = None, globalTableGlobalSecondaryIndexSettingsUpdate: Option[List[GlobalTableGlobalSecondaryIndexSettingsUpdate]] = None, replicaSettingsUpdate: Option[List[ReplicaSettingsUpdate]] = None)
object UpdateGlobalTableSettingsInput extends ShapeTag.Companion[UpdateGlobalTableSettingsInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateGlobalTableSettingsInput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateGlobalTableSettingsInput] = struct(
    TableName.schema.required[UpdateGlobalTableSettingsInput]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The name of the global table</p>"), smithy.api.Required()),
    BillingMode.schema.optional[UpdateGlobalTableSettingsInput]("GlobalTableBillingMode", _.globalTableBillingMode).addHints(smithy.api.Documentation("<p>The billing mode of the global table. If <code>GlobalTableBillingMode</code> is not\n            specified, the global table defaults to <code>PROVISIONED</code> capacity billing\n            mode.</p>\n        <ul>\n            <li>\n                <p>\n                    <code>PROVISIONED</code> - We recommend using <code>PROVISIONED</code> for\n                    predictable workloads. <code>PROVISIONED</code> sets the billing mode to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.ReadWriteCapacityMode.html#HowItWorks.ProvisionedThroughput.Manual\">Provisioned Mode</a>.</p>\n            </li>\n            <li>\n                <p>\n                    <code>PAY_PER_REQUEST</code> - We recommend using <code>PAY_PER_REQUEST</code>\n                    for unpredictable workloads. <code>PAY_PER_REQUEST</code> sets the billing mode\n                    to <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.ReadWriteCapacityMode.html#HowItWorks.OnDemand\">On-Demand Mode</a>. </p>\n            </li>\n         </ul>")),
    PositiveLongObject.schema.optional[UpdateGlobalTableSettingsInput]("GlobalTableProvisionedWriteCapacityUnits", _.globalTableProvisionedWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException.</code>\n         </p>")),
    AutoScalingSettingsUpdate.schema.optional[UpdateGlobalTableSettingsInput]("GlobalTableProvisionedWriteCapacityAutoScalingSettingsUpdate", _.globalTableProvisionedWriteCapacityAutoScalingSettingsUpdate).addHints(smithy.api.Documentation("<p>Auto scaling settings for managing provisioned write capacity for the global\n            table.</p>")),
    GlobalTableGlobalSecondaryIndexSettingsUpdateList.underlyingSchema.optional[UpdateGlobalTableSettingsInput]("GlobalTableGlobalSecondaryIndexSettingsUpdate", _.globalTableGlobalSecondaryIndexSettingsUpdate).addHints(smithy.api.Documentation("<p>Represents the settings of a global secondary index for a global table that will be\n            modified.</p>")),
    ReplicaSettingsUpdateList.underlyingSchema.optional[UpdateGlobalTableSettingsInput]("ReplicaSettingsUpdate", _.replicaSettingsUpdate).addHints(smithy.api.Documentation("<p>Represents the settings for a global table in a Region that will be modified.</p>")),
  ){
    UpdateGlobalTableSettingsInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ProvisionedThroughput(readCapacityUnits: PositiveLongObject, writeCapacityUnits: PositiveLongObject)
object ProvisionedThroughput extends ShapeTag.Companion[ProvisionedThroughput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ProvisionedThroughput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the provisioned throughput settings for a specified table or index. The\n            settings can be modified using the <code>UpdateTable</code> operation.</p>\n        <p>For current minimum and maximum provisioned throughput values, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Limits.html\">Service,\n                Account, and Table Quotas</a> in the <i>Amazon DynamoDB Developer\n                Guide</i>.</p>"),
  )

  implicit val schema: Schema[ProvisionedThroughput] = struct(
    PositiveLongObject.schema.required[ProvisionedThroughput]("ReadCapacityUnits", _.readCapacityUnits).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>The maximum number of strongly consistent reads consumed per second before DynamoDB\n            returns a <code>ThrottlingException</code>. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput\">Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB\n                Developer Guide</i>.</p>\n        <p>If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to\n            0.</p>")),
    PositiveLongObject.schema.required[ProvisionedThroughput]("WriteCapacityUnits", _.writeCapacityUnits).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>The maximum number of writes consumed per second before DynamoDB returns a\n                <code>ThrottlingException</code>. For more information, see <a href=\"https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithTables.html#ProvisionedThroughput\">Specifying Read and Write Requirements</a> in the <i>Amazon DynamoDB\n                Developer Guide</i>.</p>\n        <p>If read/write capacity mode is <code>PAY_PER_REQUEST</code> the value is set to\n            0.</p>")),
  ){
    ProvisionedThroughput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DescribeLimitsOutput(accountMaxReadCapacityUnits: Option[PositiveLongObject] = None, accountMaxWriteCapacityUnits: Option[PositiveLongObject] = None, tableMaxReadCapacityUnits: Option[PositiveLongObject] = None, tableMaxWriteCapacityUnits: Option[PositiveLongObject] = None)
object DescribeLimitsOutput extends ShapeTag.Companion[DescribeLimitsOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DescribeLimitsOutput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the output of a <code>DescribeLimits</code> operation.</p>"),
  )

  implicit val schema: Schema[DescribeLimitsOutput] = struct(
    PositiveLongObject.schema.optional[DescribeLimitsOutput]("AccountMaxReadCapacityUnits", _.accountMaxReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum total read capacity units that your account allows you to provision across\n            all of your tables in this Region.</p>")),
    PositiveLongObject.schema.optional[DescribeLimitsOutput]("AccountMaxWriteCapacityUnits", _.accountMaxWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum total write capacity units that your account allows you to provision\n            across all of your tables in this Region.</p>")),
    PositiveLongObject.schema.optional[DescribeLimitsOutput]("TableMaxReadCapacityUnits", _.tableMaxReadCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum read capacity units that your account allows you to provision for a new\n            table that you are creating in this Region, including the read capacity units\n            provisioned for its global secondary indexes (GSIs).</p>")),
    PositiveLongObject.schema.optional[DescribeLimitsOutput]("TableMaxWriteCapacityUnits", _.tableMaxWriteCapacityUnits).addHints(smithy.api.Box(), smithy.api.Documentation("<p>The maximum write capacity units that your account allows you to provision for a new\n            table that you are creating in this Region, including the write capacity units\n            provisioned for its global secondary indexes (GSIs).</p>")),
  ){
    DescribeLimitsOutput.apply
  }.withId(id).addHints(hints)
}
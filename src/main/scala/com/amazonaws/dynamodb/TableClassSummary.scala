package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TableClassSummary(tableClass: Option[TableClass] = None, lastUpdateDateTime: Option[Date] = None)
object TableClassSummary extends ShapeTag.Companion[TableClassSummary] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableClassSummary")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains details of the table class.</p>"),
  )

  implicit val schema: Schema[TableClassSummary] = struct(
    TableClass.schema.optional[TableClassSummary]("TableClass", _.tableClass).addHints(smithy.api.Documentation("<p>The table class of the specified table. Valid values are <code>STANDARD</code> and\n                <code>STANDARD_INFREQUENT_ACCESS</code>.</p>")),
    Date.schema.optional[TableClassSummary]("LastUpdateDateTime", _.lastUpdateDateTime).addHints(smithy.api.Documentation("<p>The date and time at which the table class was last updated.</p>")),
  ){
    TableClassSummary.apply
  }.withId(id).addHints(hints)
}
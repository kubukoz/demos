package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TableAutoScalingDescription(tableName: Option[TableName] = None, tableStatus: Option[TableStatus] = None, replicas: Option[List[ReplicaAutoScalingDescription]] = None)
object TableAutoScalingDescription extends ShapeTag.Companion[TableAutoScalingDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TableAutoScalingDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the auto scaling configuration for a global table.</p>"),
  )

  implicit val schema: Schema[TableAutoScalingDescription] = struct(
    TableName.schema.optional[TableAutoScalingDescription]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table.</p>")),
    TableStatus.schema.optional[TableAutoScalingDescription]("TableStatus", _.tableStatus).addHints(smithy.api.Documentation("<p>The current state of the table:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The table is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The table is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The table is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The table is ready for use.</p>\n            </li>\n         </ul>")),
    ReplicaAutoScalingDescriptionList.underlyingSchema.optional[TableAutoScalingDescription]("Replicas", _.replicas).addHints(smithy.api.Documentation("<p>Represents replicas of the global table.</p>")),
  ){
    TableAutoScalingDescription.apply
  }.withId(id).addHints(hints)
}
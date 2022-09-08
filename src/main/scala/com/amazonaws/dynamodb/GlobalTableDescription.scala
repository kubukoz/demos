package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class GlobalTableDescription(replicationGroup: Option[List[ReplicaDescription]] = None, globalTableArn: Option[GlobalTableArnString] = None, creationDateTime: Option[Date] = None, globalTableStatus: Option[GlobalTableStatus] = None, globalTableName: Option[TableName] = None)
object GlobalTableDescription extends ShapeTag.Companion[GlobalTableDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "GlobalTableDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Contains details about the global table.</p>"),
  )

  implicit val schema: Schema[GlobalTableDescription] = struct(
    ReplicaDescriptionList.underlyingSchema.optional[GlobalTableDescription]("ReplicationGroup", _.replicationGroup).addHints(smithy.api.Documentation("<p>The Regions where the global table has replicas.</p>")),
    GlobalTableArnString.schema.optional[GlobalTableDescription]("GlobalTableArn", _.globalTableArn).addHints(smithy.api.Documentation("<p>The unique identifier of the global table.</p>")),
    Date.schema.optional[GlobalTableDescription]("CreationDateTime", _.creationDateTime).addHints(smithy.api.Documentation("<p>The creation time of the global table.</p>")),
    GlobalTableStatus.schema.optional[GlobalTableDescription]("GlobalTableStatus", _.globalTableStatus).addHints(smithy.api.Documentation("<p>The current state of the global table:</p>\n        <ul>\n            <li>\n                <p>\n                    <code>CREATING</code> - The global table is being created.</p>\n            </li>\n            <li>\n                <p>\n                    <code>UPDATING</code> - The global table is being updated.</p>\n            </li>\n            <li>\n                <p>\n                    <code>DELETING</code> - The global table is being deleted.</p>\n            </li>\n            <li>\n                <p>\n                    <code>ACTIVE</code> - The global table is ready for use.</p>\n            </li>\n         </ul>")),
    TableName.schema.optional[GlobalTableDescription]("GlobalTableName", _.globalTableName).addHints(smithy.api.Documentation("<p>The global table name.</p>")),
  ){
    GlobalTableDescription.apply
  }.withId(id).addHints(hints)
}
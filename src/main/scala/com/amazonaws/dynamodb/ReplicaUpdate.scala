package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaUpdate(create: Option[CreateReplicaAction] = None, delete: Option[DeleteReplicaAction] = None)
object ReplicaUpdate extends ShapeTag.Companion[ReplicaUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents one of the following:</p>\n        <ul>\n            <li>\n                <p>A new replica to be added to an existing global table.</p>\n            </li>\n            <li>\n                <p>New parameters for an existing replica.</p>\n            </li>\n            <li>\n                <p>An existing replica to be removed from an existing global table.</p>\n            </li>\n         </ul>"),
  )

  implicit val schema: Schema[ReplicaUpdate] = struct(
    CreateReplicaAction.schema.optional[ReplicaUpdate]("Create", _.create).addHints(smithy.api.Documentation("<p>The parameters required for creating a replica on an existing global table.</p>")),
    DeleteReplicaAction.schema.optional[ReplicaUpdate]("Delete", _.delete).addHints(smithy.api.Documentation("<p>The name of the existing replica to be removed.</p>")),
  ){
    ReplicaUpdate.apply
  }.withId(id).addHints(hints)
}
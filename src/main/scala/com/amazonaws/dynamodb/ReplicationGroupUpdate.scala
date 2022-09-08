package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicationGroupUpdate(create: Option[CreateReplicationGroupMemberAction] = None, update: Option[UpdateReplicationGroupMemberAction] = None, delete: Option[DeleteReplicationGroupMemberAction] = None)
object ReplicationGroupUpdate extends ShapeTag.Companion[ReplicationGroupUpdate] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicationGroupUpdate")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents one of the following:</p>\n        <ul>\n            <li>\n                <p>A new replica to be added to an existing regional table or global table. This\n                    request invokes the <code>CreateTableReplica</code> action in the destination\n                    Region.</p>\n            </li>\n            <li>\n                <p>New parameters for an existing replica. This request invokes the\n                        <code>UpdateTable</code> action in the destination Region.</p>\n            </li>\n            <li>\n                <p>An existing replica to be deleted. The request invokes the\n                        <code>DeleteTableReplica</code> action in the destination Region, deleting\n                    the replica and all if its items in the destination Region.</p>\n            </li>\n         </ul>\n        <note>\n            <p>When you manually remove a table or global table replica, you do not \n                automatically remove any associated scalable targets, scaling policies, or \n                CloudWatch alarms.</p>\n        </note>"),
  )

  implicit val schema: Schema[ReplicationGroupUpdate] = struct(
    CreateReplicationGroupMemberAction.schema.optional[ReplicationGroupUpdate]("Create", _.create).addHints(smithy.api.Documentation("<p>The parameters required for creating a replica for the table.</p>")),
    UpdateReplicationGroupMemberAction.schema.optional[ReplicationGroupUpdate]("Update", _.update).addHints(smithy.api.Documentation("<p>The parameters required for updating a replica for the table.</p>")),
    DeleteReplicationGroupMemberAction.schema.optional[ReplicationGroupUpdate]("Delete", _.delete).addHints(smithy.api.Documentation("<p>The parameters required for deleting a replica for the table.</p>")),
  ){
    ReplicationGroupUpdate.apply
  }.withId(id).addHints(hints)
}
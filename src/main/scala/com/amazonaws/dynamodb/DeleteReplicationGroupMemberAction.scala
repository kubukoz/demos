package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteReplicationGroupMemberAction(regionName: RegionName)
object DeleteReplicationGroupMemberAction extends ShapeTag.Companion[DeleteReplicationGroupMemberAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteReplicationGroupMemberAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a replica to be deleted.</p>"),
  )

  implicit val schema: Schema[DeleteReplicationGroupMemberAction] = struct(
    RegionName.schema.required[DeleteReplicationGroupMemberAction]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the replica exists.</p>"), smithy.api.Required()),
  ){
    DeleteReplicationGroupMemberAction.apply
  }.withId(id).addHints(hints)
}
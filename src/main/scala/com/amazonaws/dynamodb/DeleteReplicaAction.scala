package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class DeleteReplicaAction(regionName: RegionName)
object DeleteReplicaAction extends ShapeTag.Companion[DeleteReplicaAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "DeleteReplicaAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a replica to be removed.</p>"),
  )

  implicit val schema: Schema[DeleteReplicaAction] = struct(
    RegionName.schema.required[DeleteReplicaAction]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region of the replica to be removed.</p>"), smithy.api.Required()),
  ){
    DeleteReplicaAction.apply
  }.withId(id).addHints(hints)
}
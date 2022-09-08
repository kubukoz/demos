package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class CreateReplicaAction(regionName: RegionName)
object CreateReplicaAction extends ShapeTag.Companion[CreateReplicaAction] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "CreateReplicaAction")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents a replica to be added.</p>"),
  )

  implicit val schema: Schema[CreateReplicaAction] = struct(
    RegionName.schema.required[CreateReplicaAction]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region of the replica to be added.</p>"), smithy.api.Required()),
  ){
    CreateReplicaAction.apply
  }.withId(id).addHints(hints)
}
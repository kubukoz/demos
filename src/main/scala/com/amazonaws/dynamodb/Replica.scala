package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class Replica(regionName: Option[RegionName] = None)
object Replica extends ShapeTag.Companion[Replica] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Replica")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the properties of a replica.</p>"),
  )

  implicit val schema: Schema[Replica] = struct(
    RegionName.schema.optional[Replica]("RegionName", _.regionName).addHints(smithy.api.Documentation("<p>The Region where the replica needs to be created.</p>")),
  ){
    Replica.apply
  }.withId(id).addHints(hints)
}
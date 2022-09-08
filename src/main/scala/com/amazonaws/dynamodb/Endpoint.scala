package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.schema.Schema.string
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag
import smithy4s.schema.Schema.long

case class Endpoint(address: String, cachePeriodInMinutes: Long)
object Endpoint extends ShapeTag.Companion[Endpoint] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "Endpoint")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>An endpoint information details.</p>"),
  )

  implicit val schema: Schema[Endpoint] = struct(
    string.required[Endpoint]("Address", _.address).addHints(smithy.api.Documentation("<p>IP address of the endpoint.</p>"), smithy.api.Required()),
    long.required[Endpoint]("CachePeriodInMinutes", _.cachePeriodInMinutes).addHints(smithy.api.Documentation("<p>Endpoint cache time to live (TTL) value.</p>"), smithy.api.Required()),
  ){
    Endpoint.apply
  }.withId(id).addHints(hints)
}
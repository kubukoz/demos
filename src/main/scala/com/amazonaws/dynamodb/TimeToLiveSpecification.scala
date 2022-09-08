package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TimeToLiveSpecification(enabled: TimeToLiveEnabled, attributeName: TimeToLiveAttributeName)
object TimeToLiveSpecification extends ShapeTag.Companion[TimeToLiveSpecification] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TimeToLiveSpecification")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the settings used to enable or disable Time to Live (TTL) for the specified\n            table.</p>"),
  )

  implicit val schema: Schema[TimeToLiveSpecification] = struct(
    TimeToLiveEnabled.schema.required[TimeToLiveSpecification]("Enabled", _.enabled).addHints(smithy.api.Box(), smithy.api.Required(), smithy.api.Documentation("<p>Indicates whether TTL is to be enabled (true) or disabled (false) on the table.</p>")),
    TimeToLiveAttributeName.schema.required[TimeToLiveSpecification]("AttributeName", _.attributeName).addHints(smithy.api.Documentation("<p>The name of the TTL attribute used to store the expiration time for items in the\n            table.</p>"), smithy.api.Required()),
  ){
    TimeToLiveSpecification.apply
  }.withId(id).addHints(hints)
}
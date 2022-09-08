package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class TimeToLiveDescription(timeToLiveStatus: Option[TimeToLiveStatus] = None, attributeName: Option[TimeToLiveAttributeName] = None)
object TimeToLiveDescription extends ShapeTag.Companion[TimeToLiveDescription] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "TimeToLiveDescription")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The description of the Time to Live (TTL) status on the specified table. </p>"),
  )

  implicit val schema: Schema[TimeToLiveDescription] = struct(
    TimeToLiveStatus.schema.optional[TimeToLiveDescription]("TimeToLiveStatus", _.timeToLiveStatus).addHints(smithy.api.Documentation("<p> The TTL status for the table.</p>")),
    TimeToLiveAttributeName.schema.optional[TimeToLiveDescription]("AttributeName", _.attributeName).addHints(smithy.api.Documentation("<p> The name of the TTL attribute for items in the table.</p>")),
  ){
    TimeToLiveDescription.apply
  }.withId(id).addHints(hints)
}
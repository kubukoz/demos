package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateTimeToLiveOutput(timeToLiveSpecification: Option[TimeToLiveSpecification] = None)
object UpdateTimeToLiveOutput extends ShapeTag.Companion[UpdateTimeToLiveOutput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTimeToLiveOutput")

  val hints : Hints = Hints.empty

  implicit val schema: Schema[UpdateTimeToLiveOutput] = struct(
    TimeToLiveSpecification.schema.optional[UpdateTimeToLiveOutput]("TimeToLiveSpecification", _.timeToLiveSpecification).addHints(smithy.api.Documentation("<p>Represents the output of an <code>UpdateTimeToLive</code> operation.</p>")),
  ){
    UpdateTimeToLiveOutput.apply
  }.withId(id).addHints(hints)
}
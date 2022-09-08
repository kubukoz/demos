package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class UpdateTimeToLiveInput(tableName: TableName, timeToLiveSpecification: TimeToLiveSpecification)
object UpdateTimeToLiveInput extends ShapeTag.Companion[UpdateTimeToLiveInput] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "UpdateTimeToLiveInput")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Represents the input of an <code>UpdateTimeToLive</code> operation.</p>"),
  )

  implicit val schema: Schema[UpdateTimeToLiveInput] = struct(
    TableName.schema.required[UpdateTimeToLiveInput]("TableName", _.tableName).addHints(smithy.api.Documentation("<p>The name of the table to be configured.</p>"), smithy.api.Required()),
    TimeToLiveSpecification.schema.required[UpdateTimeToLiveInput]("TimeToLiveSpecification", _.timeToLiveSpecification).addHints(smithy.api.Documentation("<p>Represents the settings used to enable or disable Time to Live for the specified\n            table.</p>"), smithy.api.Required()),
  ){
    UpdateTimeToLiveInput.apply
  }.withId(id).addHints(hints)
}
package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ReplicaNotFoundException extends ShapeTag.Companion[ReplicaNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified replica is no longer part of the global table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ReplicaNotFoundException] = struct(
    ErrorMessage.schema.optional[ReplicaNotFoundException]("message", _.message),
  ){
    ReplicaNotFoundException.apply
  }.withId(id).addHints(hints)
}
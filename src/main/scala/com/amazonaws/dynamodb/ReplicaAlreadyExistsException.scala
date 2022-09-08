package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class ReplicaAlreadyExistsException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object ReplicaAlreadyExistsException extends ShapeTag.Companion[ReplicaAlreadyExistsException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "ReplicaAlreadyExistsException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>The specified replica is already part of the global table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[ReplicaAlreadyExistsException] = struct(
    ErrorMessage.schema.optional[ReplicaAlreadyExistsException]("message", _.message),
  ){
    ReplicaAlreadyExistsException.apply
  }.withId(id).addHints(hints)
}
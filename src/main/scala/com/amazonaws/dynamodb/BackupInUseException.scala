package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BackupInUseException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object BackupInUseException extends ShapeTag.Companion[BackupInUseException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupInUseException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>There is another ongoing conflicting backup control plane operation on the table.\n            The backup is either being created, deleted or restored to a table.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[BackupInUseException] = struct(
    ErrorMessage.schema.optional[BackupInUseException]("message", _.message),
  ){
    BackupInUseException.apply
  }.withId(id).addHints(hints)
}
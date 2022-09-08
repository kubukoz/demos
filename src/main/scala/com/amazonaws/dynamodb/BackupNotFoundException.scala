package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class BackupNotFoundException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object BackupNotFoundException extends ShapeTag.Companion[BackupNotFoundException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "BackupNotFoundException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>Backup not found for the given BackupARN. </p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[BackupNotFoundException] = struct(
    ErrorMessage.schema.optional[BackupNotFoundException]("message", _.message),
  ){
    BackupNotFoundException.apply
  }.withId(id).addHints(hints)
}
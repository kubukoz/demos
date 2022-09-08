package com.amazonaws.dynamodb

import smithy4s.Schema
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Schema.struct
import smithy4s.ShapeTag

case class LimitExceededException(message: Option[ErrorMessage] = None) extends Throwable {
  override def getMessage(): String = message.map(_.value).orNull
}
object LimitExceededException extends ShapeTag.Companion[LimitExceededException] {
  val id: ShapeId = ShapeId("com.amazonaws.dynamodb", "LimitExceededException")

  val hints : Hints = Hints(
    smithy.api.Documentation("<p>There is no limit to the number of daily on-demand backups that can be taken. </p>\n        <p>Up to 500 simultaneous table operations are allowed per account. These operations\n            include <code>CreateTable</code>, <code>UpdateTable</code>,\n                <code>DeleteTable</code>,<code>UpdateTimeToLive</code>,\n                <code>RestoreTableFromBackup</code>, and <code>RestoreTableToPointInTime</code>. </p>\n        <p>The only exception is when you are creating a table with one or more secondary\n            indexes. You can have up to 250 such requests running at a time; however, if the table or\n            index specifications are complex, DynamoDB might temporarily reduce the number\n            of concurrent operations.</p>\n        <p>There is a soft account quota of 2,500 tables.</p>"),
    smithy.api.Error.CLIENT.widen,
  )

  implicit val schema: Schema[LimitExceededException] = struct(
    ErrorMessage.schema.optional[LimitExceededException]("message", _.message).addHints(smithy.api.Documentation("<p>Too many operations for a given subscriber.</p>")),
  ){
    LimitExceededException.apply
  }.withId(id).addHints(hints)
}
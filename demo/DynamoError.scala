package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

case class DynamoError(name: String, age: Option[Int] = None) extends Throwable {
}
object DynamoError extends ShapeTag.Companion[DynamoError] {
  val id: ShapeId = ShapeId("demo", "DynamoError")

  val hints: Hints = Hints(
    smithy.api.Error.SERVER.widen,
    smithy.api.HttpError(500),
  )

  implicit val schema: Schema[DynamoError] = struct(
    string.required[DynamoError]("name", _.name).addHints(smithy.api.Required()),
    int.optional[DynamoError]("age", _.age),
  ){
    DynamoError.apply
  }.withId(id).addHints(hints)
}
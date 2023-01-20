package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union

sealed trait Request extends scala.Product with scala.Serializable {
  @inline final def widen: Request = this
}
object Request extends ShapeTag.Companion[Request] {
  val id: ShapeId = ShapeId("hello", "Request")

  val hints: Hints = Hints(
    alloy.Untagged(),
    smithy.api.Streaming(),
  )

  case class InputCase(input: RequestInput) extends Request

  object InputCase {
    val hints: Hints = Hints.empty
    val schema: Schema[InputCase] = bijection(RequestInput.schema.addHints(hints), InputCase(_), _.input)
    val alt = schema.oneOf[Request]("input")
  }

  implicit val schema: Schema[Request] = union(
    InputCase.alt,
  ){
    case c: InputCase => InputCase.alt(c)
  }.withId(id).addHints(hints)
}
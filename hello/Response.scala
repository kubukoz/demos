package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union

sealed trait Response extends scala.Product with scala.Serializable {
  @inline final def widen: Response = this
}
object Response extends ShapeTag.Companion[Response] {
  val id: ShapeId = ShapeId("hello", "Response")

  val hints: Hints = Hints(
    alloy.Untagged(),
    smithy.api.Streaming(),
  )

  case class OutputCase(output: ResponseOutput) extends Response

  object OutputCase {
    val hints: Hints = Hints.empty
    val schema: Schema[OutputCase] = bijection(ResponseOutput.schema.addHints(hints), OutputCase(_), _.output)
    val alt = schema.oneOf[Response]("output")
  }

  implicit val schema: Schema[Response] = union(
    OutputCase.alt,
  ){
    case c: OutputCase => OutputCase.alt(c)
  }.withId(id).addHints(hints)
}
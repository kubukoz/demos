package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class MyHelloOld(f1: String, f2: Int, f3: Option[String] = None, f4: Option[Int] = None)

object MyHelloOld extends ShapeTag.Companion[MyHelloOld] {
  val id: ShapeId = ShapeId("hello", "MyHelloOld")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(f1: String, f2: Int, f3: Option[String], f4: Option[Int]): MyHelloOld = MyHelloOld(f1, f2, f3, f4)

  implicit val schema: Schema[MyHelloOld] = struct(
    string.required[MyHelloOld]("f1", _.f1),
    int.required[MyHelloOld]("f2", _.f2),
    string.optional[MyHelloOld]("f3", _.f3),
    int.optional[MyHelloOld]("f4", _.f4),
  )(make).withId(id).addHints(hints)
}

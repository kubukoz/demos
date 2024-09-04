//> using dep com.disneystreaming.smithy4s::smithy4s-core:0.18.23
package hellonew

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

// no default arguments on the constructor in bincompat-friendly mode
// (auxiliary constructors would not work then)
final case class MyHello(
  f1: String,
  f2: Int,
  f3: Option[String],
  f4: Option[Int],
) {

  // setters
  def withF1(f1: String): MyHello = copy(f1 = f1)
  def withF2(f2: Int): MyHello = copy(f2 = f2)
  def withF3(f3: Option[String]): MyHello = copy(f3 = f3)
  def withF4(f4: Option[Int]): MyHello = copy(f4 = f4)

  private def copy(
    f1: String = this.f1,
    f2: Int = this.f2,
    f3: Option[String] = this.f3,
    f4: Option[Int] = this.f4,
  ): MyHello = new MyHello(f1, f2, f3, f4)
}

object MyHello extends ShapeTag.Companion[MyHello] {
  val id: ShapeId = ShapeId("hello", "MyHello")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(
    f1: String,
    f2: Int,
    f3: Option[String],
    f4: Option[Int],
  ): MyHello = apply(f1, f2, f3, f4)

  def apply(f1: String, f2: Int, f3: Option[String] = None, f4: Option[Int] = None): MyHello = new MyHello(f1, f2, f3, f4)

  implicit val schema: Schema[MyHello] = struct(
    string.required[MyHello]("f1", _.f1),
    int.required[MyHello]("f2", _.f2),
    string.optional[MyHello]("f3", _.f3),
    int.optional[MyHello]("f4", _.f4),
  )(make).withId(id).addHints(hints)

}

object Demo extends App {
  println(MyHello("a", 1).withF3(Some("b")).withF4(Some(42)))
  println(new MyHello("a", 1, None, None).withF3(Some("b")).withF4(Some(42)))
}

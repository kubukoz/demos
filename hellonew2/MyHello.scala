//> using dep com.disneystreaming.smithy4s::smithy4s-core:0.18.23
package hellonew

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.boolean
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

// constructor becomes private
// aux constructor added matching the old shape
final class MyHello private (
  val f1: String,
  val f2: Int,
  val f3: Option[String],
  val f5: Option[Boolean],
  val f4: Option[Int],
) {

  // f5 omitted due to magic trait
  def this(
    f1: String,
    f2: Int,
    f3: Option[String] = None,
    f4: Option[Int] = None,
  ) = this(f1, f2, f3, None, f4)

  def withF1(f1: String): MyHello = copy(f1 = f1)
  def withF2(f2: Int): MyHello = copy(f2 = f2)
  def withF3(f3: Option[String]): MyHello = copy(f3 = f3)
  def withF5(f5: Option[Boolean]): MyHello = copy(f5 = f5)
  def withF4(f4: Option[Int]): MyHello = copy(f4 = f4)

  private def copy(
    f1: String = this.f1,
    f2: Int = this.f2,
    f3: Option[String] = this.f3,
    f5: Option[Boolean] = this.f5,
    f4: Option[Int] = this.f4,
  ): MyHello = new MyHello(f1, f2, f3, f5, f4)

  override def toString(): String = s"MyHello(f1 = $f1, f2 = $f2, f3 = $f3, f5 = $f5, f4 = $f4)"

  override def equals(x: Any): Boolean =
    x match {
      case that: MyHello =>
        (this eq that) || (f1 == that.f1 &&
          f2 == that.f2 &&
          f3 == that.f3 &&
          f5 == that.f5 &&
          f4 == that.f4)
      case _ => false
    }

  override def hashCode(): Int =
    31 * (31 * (31 * (31 * f1.hashCode + f2.hashCode) + f3.hashCode) + f5.hashCode) + f4.hashCode
}

object MyHello extends ShapeTag.Companion[MyHello] {
  val id: ShapeId = ShapeId("hello", "MyHello")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(
    f1: String,
    f2: Int,
    f3: Option[String],
    f5: Option[Boolean],
    f4: Option[Int],
  ): MyHello = new MyHello(f1, f2, f3, f5, f4)

  // f5 omitted due to magic trait
  // so this looks just like before
  def apply(f1: String, f2: Int, f3: Option[String] = None, f4: Option[Int] = None): MyHello =
    new MyHello(f1, f2, f3, None, f4)

  implicit val schema: Schema[MyHello] = struct(
    string.required[MyHello]("f1", _.f1),
    int.required[MyHello]("f2", _.f2),
    string.optional[MyHello]("f3", _.f3),
    boolean.optional[MyHello]("f5", _.f5),
    int.optional[MyHello]("f4", _.f4),
  )(make).withId(id).addHints(hints)

}

object Demo extends App {
  println(MyHello("a", 1).withF3(Some("b")).withF4(Some(42)).f5)
}

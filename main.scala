//> using lib "org.typelevel::cats-core:2.9.0"
//> using plugin "org.polyvariant:::better-tostring:0.3.17"
//> using option "-no-indent"

import scala.collection.immutable.ListMap
import scala.compiletime._
import scala.deriving.Mirror
import scala.reflect.TypeTest

trait Copy[T] {

  /** Transform all pieces of this structure using the given function.
    */
  def copyWith(t: T)(f: Mod): T
}

type AnyTypeTest[T] = TypeTest[Any, T]

object Copy {

  inline given derived[T: AnyTypeTest]: Copy[T] = summonFrom {
    case m: Mirror.SumOf[T] =>
      new Copy[T] {
        lazy val instances = summonAll[Tuple.Map[m.MirroredElemTypes, Copy]].toArray

        override def copyWith(t: T)(f: Mod): T = {
          val instance = instances(m.ordinal(t)).asInstanceOf[Copy[Any]]

          instance.copyWith(t)(f).asInstanceOf[T]

        }

      }

    case m: Mirror.ProductOf[T] =>
      new Copy[T] {
        lazy val instances = summonAll[Tuple.Map[m.MirroredElemTypes, Copy]].toArray

        override def copyWith(t: T)(f: Mod): T = {
          // for each member of the product, call copyWith on it
          // then reconstruct the instance of T
          // finally, apply f to the whole thing.

          val values =
            m.fromProduct(t.asInstanceOf[Product]).asInstanceOf[Product].productIterator.toArray

          val newValues = instances.zip(values).map { (copy, value) =>
            val c = copy.asInstanceOf[Copy[Any]]
            c.copyWith(value)(f)
          }

          f(
            m.fromProduct(Tuple.fromArray(newValues))
          )

        }

      }

    case _ =>
      // default instance: no mirror
      new Copy[T] {
        override def copyWith(t: T)(f: Mod): T = f(t)
      }
  }

  given [T: Copy: AnyTypeTest]: Copy[List[T]] with {
    override def copyWith(t: List[T])(f: Mod): List[T] = t.map(_.copyWith(f))
  }

  given [K: Copy, V: Copy]: Copy[ListMap[K, V]] with {

    override def copyWith(t: ListMap[K, V])(f: Mod): ListMap[K, V] = t.map { case (k, v) =>
      (k.copyWith(f), v.copyWith(f))
    }

  }

}

extension [T: Copy](t: T) def copyWith(f: Mod): T = summon[Copy[T]].copyWith(t)(f)

trait Mod {
  def apply[U: AnyTypeTest](a: U): U
}

object Mod {

  def collect[T: AnyTypeTest](f: T => T): Mod =
    new Mod {

      override def apply[U: AnyTypeTest](a: U): U =
        a match {
          case t: T =>
            f(t) match {
              case b: U => b
              case _    => a // return unmodified
            }
          case _ => a
        }

    }

}

// demo
// demo
// demo
// demo
// demo
// demo
// demo

case class Identifier(name: String)

enum MyStructure {
  case Simple(s: String)
  case JustIdent(i: Identifier)
  case Nested(n: NestedStructure)
}

enum NestedStructure {
  case AnotherIdent(i: Identifier)
}

enum RecIdent {
  case More(id: String, ri: RecIdent)
  case Just(id: String, i: Identifier)
}

def censorIdentifiers[T: Copy](t: T): T = t
  .copyWith {
    Mod.collect[Identifier] { id =>
      id.copy(name = s"""${Console.GREEN}modified:${Console.RESET} ${id.name}""")
    }
  }

@main def main(): Unit = {
  given Copy[RecIdent] = Copy.derived

  println(
    censorIdentifiers(
      RecIdent.More("id2", RecIdent.Just("id2", Identifier("foo"))): RecIdent
    )
  )

  println(
    censorIdentifiers(
      MyStructure.JustIdent(Identifier("baz"))
    )
  )
}

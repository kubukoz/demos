//> using lib "org.typelevel::cats-core:2.9.0"
//> using lib "dev.optics::monocle-core:3.2.0"
//> using option "-no-indent"
import cats.implicits.*
import cats.Applicative

import scala.collection.immutable.ListMap
import scala.compiletime._
import scala.deriving.Mirror
import scala.reflect.ClassTag

object Copy {

  inline given derived[T: ClassTag]: Copy[T] = summonFrom {
    case m: Mirror.SumOf[T] =>
      new Copy[T] {

        override def make[Of: ClassTag]: Traversal[T, Of] =
          new Traversal[T, Of] {

            private val defo = defaultInstance[T]

            private lazy val childTraversals: Array[Traversal[Any, Of]] =
              summonAll[Tuple.Map[m.MirroredElemTypes, Copy]]
                .toArray
                .map(_.asInstanceOf[Copy[Any]].make[Of])

            override def modify(
              f: Of => Of
            )(
              t: T
            ): T =
              defo.modifyAll(
                childTraversals(m.ordinal(t)).modify(f)(t).asInstanceOf[T]
              )(f)

          }

      }

    case m: Mirror.ProductOf[T] =>
      new Copy[T] {

        override def make[Of: ClassTag]: Traversal[T, Of] =
          new Traversal[T, Of] {

            private val defo = defaultInstance[T]

            private lazy val childTraversals =
              summonAll[Tuple.Map[m.MirroredElemTypes, Copy]]
                .toArray
                .map(_.asInstanceOf[Copy[Any]].make[Of])
                .zipWithIndex

            override def modify(f: Of => Of)(s: T): T =
              defo.modifyAll {
                m
                  .fromProduct {
                    Tuple.fromArray {
                      childTraversals
                        .map { case (childInstance, index) =>
                          val childField = s.asInstanceOf[Product].productElement(index)
                          childInstance.modify(f)(childField)
                        }
                    }
                  }
              }(f)

          }

      }

    case _ =>
      // default instance: no mirror
      defaultInstance[T]
  }

  def defaultInstance[T: ClassTag]: Copy[T] =
    new Copy[T] {

      override def make[Of: ClassTag]: Traversal[T, Of] =
        new Traversal[T, Of] {

          override def modify(f: Of => Of)(s: T): T =
            s match {
              case of: Of =>
                f(of) match {
                  case t: T => t
                  case _    => s // by some miracle the result wasn't a T
                }
              case _ => s
            }

        }

    }

  given [T: Copy]: Copy[List[T]] with {

    override def make[Of: ClassTag]: Traversal[List[T], Of] = {
      lazy val child = summon[Copy[T]].make[Of]

      new Traversal[List[T], Of] {
        override def modify(
          f: Of => Of
        )(
          s: List[T]
        ): List[T] = s.map(child.modify(f))
      }
    }

  }

  given [K: Copy, V: Copy]: Copy[ListMap[K, V]] with {

    override def make[Of: ClassTag]: Traversal[ListMap[K, V], Of] =
      new Traversal[ListMap[K, V], Of] {
        private val kC = summon[Copy[K]].make[Of]
        private val vC = summon[Copy[V]].make[Of]

        override def modify(
          f: Of => Of
        )(
          s: ListMap[K, V]
        ): ListMap[K, V] = s
          .map { case (k, v) => (kC.modify(f)(k), vC.modify(f)(v)) }

      }

  }

}

extension [T: Copy](t: T) {
  def modifyAll[Of: ClassTag](f: Of => Of): T = summon[Copy[T]].modifyAll(t)(f)
}

trait Copy[T] {
  def make[Of: ClassTag]: Traversal[T, Of]
  def modifyAll[Of: ClassTag](t: T)(f: Of => Of): T = make[Of].modify(f)(t)
}

trait Traversal[S, T] {
  def modify(f: T => T)(s: S): S
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

def censorIdentifiers[T: Copy](t: T): T = t.modifyAll[Any] {
  case id: Identifier => id.copy(name = Console.GREEN + "modified: " + Console.RESET + id.name)
  case other          => other
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

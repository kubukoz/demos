//> using scala "3.3.0"
import scala.deriving._
import scala.compiletime._

trait Eq[A] {
  def eqv(a: A, b: A): Boolean
}

object Eq {
  given Eq[String] = _.toLowerCase() == _.toLowerCase()
  given Eq[Int] = _ == _
  given Eq[Boolean] = _ == _

  inline def derived[A](using M: Mirror.Of[A]): Eq[A] =
    M match {
      case m: Mirror.ProductOf[A] =>
        val eqInstances = summonAll[Tuple.Map[M.MirroredElemTypes, Eq]]

        (a, b) =>
          a.asInstanceOf[Product]
            .productIterator
            .zip(b.asInstanceOf[Product].productIterator)
            .zip(eqInstances.toList.asInstanceOf[List[Eq[Any]]])
            .forall { case ((x, y), eq) => eq.eqv(x, y) }
    }

}

case class Person(name: String, age: Int) derives Eq

@main def demo =
  println(summon[Eq[Person]].eqv(Person("John", 30), Person("John", 30)))
  println(summon[Eq[Person]].eqv(Person("John", 30), Person("john", 30)))
  println(summon[Eq[Person]].eqv(Person("John Paul II", 2137), Person("john", 2137)))

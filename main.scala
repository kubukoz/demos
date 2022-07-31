//> using scala "2.13.8"
//> using plugin "org.typelevel:::kind-projector:0.13.2"
//> using lib "org.typelevel::cats-core:2.8.0"
//> using lib "org.typelevel::cats-tagless-core:0.14.0"

import cats.FlatMap
import cats.~>
import cats.implicits._
import cats.Id
import cats.data.Nested
import cats.tagless.FunctorK
import cats.tagless.Trivial

case class Suspended[Alg[_[_]], F[_], A](effect: Alg[F] => F[A])

trait SuspendK[Alg[_[_]]] extends FunctorK[Alg] {
  def suspendK[F[_], G[_]](fk: Suspended[Alg, F, *] ~> G): Alg[G]

  def mapK[F[_], G[_]](af: Alg[F])(fk: F ~> G): Alg[G] = suspendK(
    new (Suspended[Alg, F, *] ~> G) {
      def apply[A](fa: Suspended[Alg, F, A]): G[A] = fk(fa.effect(af))
    }
  )

  def unsuspend[F[_]](af: Alg[F]): Alg[F] = suspendK(
    new (Suspended[Alg, F, *] ~> F) {
      def apply[A](fa: Suspended[Alg, F, A]): F[A] = fa.effect(af)
    }
  )

  def deferK[F[_]: FlatMap, G[_]](fa: F[Alg[G]]): Alg[Nested[F, G, *]] =
    suspendK {
      new (Suspended[Alg, G, *] ~> Nested[F, G, *]) {
        def apply[A](fk: Suspended[Alg, G, A]): Nested[F, G, A] =
          fa.map(fk.effect(_)).nested
      }
    }

  def deferKId[F[_]: FlatMap](fa: F[Alg[F]]): Alg[F] =
    suspendK {
      new (Suspended[Alg, F, *] ~> F) {
        def apply[A](fk: Suspended[Alg, F, A]): F[A] =
          fa.flatMap(fk.effect(_))
      }
    }
}

trait MyAlg[F[_]] {
  def foo(s: String): F[Int]
  def bar(i: Int): F[String]
}

object MyAlg {

  implicit val suspendKMyAlg: SuspendK[MyAlg] =
    new SuspendK[MyAlg] {

      def suspendK[F[_], G[_]](fk: Suspended[MyAlg, F, *] ~> G): MyAlg[G] =
        new MyAlg[G] {
          def foo(s: String): G[Int] = fk.apply(Suspended(_.foo(s)))

          def bar(i: Int): G[String] = fk.apply(Suspended(_.bar(i)))

        }
    }

}

object Main extends App {

  val myAlgOptional: Option[MyAlg[Option]] = Some(new MyAlg[Option] {
    def foo(s: String): Option[Int] = s.length().some

    def bar(i: Int): Option[String] = i.toString().some
  })

  val alg: MyAlg[Option] = MyAlg.suspendKMyAlg.deferKId(myAlgOptional)

  println(alg.bar(42))

}

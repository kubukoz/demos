//> using scala "2.13.7"
//> using lib "com.disneystreaming::weaver-scalacheck::0.7.13"
//> using lib "com.disneystreaming::weaver-cats::0.7.13"
//> using lib "com.disneystreaming::weaver-discipline::0.7.13"
//> using lib "org.typelevel::cats-laws:2.8.0"
//> using plugin "org.typelevel:::kind-projector:0.13.2"
import weaver._
import weaver.discipline._

import cats.implicits._
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.ApplicativeTests
import cats.Applicative
import cats.kernel.Semigroup

object ApplicativeEitherTests extends FunSuite with Discipline {

  // default applicative
  checkAll(
    "Applicative[Either[Int, *]]",
    ApplicativeTests[Either[Int, *]]
      .applicative[Int, Int, Int]
  )

  locally {
    implicit def eitherApplicativeMerging[E: Semigroup]
        : Applicative[Either[E, *]] =
      new Applicative[Either[E, *]] {

        def ap[A, B](ff: Either[E, A => B])(fa: Either[E, A]): Either[E, B] =
          (ff, fa) match {
            case (Left(e), Left(e2))  => Left(e |+| e2)
            case (Left(e), _)         => Left(e)
            case (_, Left(e))         => Left(e)
            case (Right(f), Right(a)) => Right(f(a))
          }

        def pure[A](x: A): Either[E, A] = Right(x)

      }

    checkAll(
      "ApplicativeDrugi[Either[Int, *]]",
      ApplicativeTests[Either[Int, *]](eitherApplicativeMerging[Int])
        .applicative[Int, Int, Int]
    )
  }
}

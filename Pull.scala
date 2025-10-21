import cats.free.Free
import cats.effect.IO
import cats.syntax.all.*
import cats.Monad
import cats.data.NonEmptyList
import cats.Functor
import cats.~>

opaque type Pull[A, Out] = Free[PullAlg[A, *], Out]

enum PullAlg[A, Out] {
  case Output[A, R](as: NonEmptyList[A], next: R) extends PullAlg[A, R]
  case Lift[A, Out](fa: IO[Out]) extends PullAlg[A, Out]
  case Uncons[A, A2, R](stream: Pull[A, Unit], r: Option[(NonEmptyList[A], Pull[A, Unit])] => R) extends PullAlg[A2, R]
}

object PullAlg {

  given [Emit]: Functor[PullAlg[Emit, *]] =
    new {
      def map[A, B](fa: PullAlg[Emit, A])(f: A => B): PullAlg[Emit, B] =
        fa match {
          case Lift(fa)          => Lift(fa.map(f))
          case Output(as, next)  => Output(as, f(next))
          case Uncons(stream, k) => Uncons(stream, k.andThen(f))
        }
    }

}

object Pull {
  import PullAlg.*

  given [A]: Monad[Pull[A, *]] = Free.catsFreeMonadForFree

  val done: Pull[Nothing, Unit] = succeed(())

  def output1[A](item: A): Pull[A, Unit] = output(NonEmptyList.one(item))
  def output[A](items: NonEmptyList[A]): Pull[A, Unit] = Free.liftF(Output(items, ()))

  def liftF[A](fa: IO[A]): Pull[Nothing, A] = Free.liftF(Lift(fa))

  def succeed[A](a: A): Pull[Nothing, A] = liftF(a.pure[IO])

  def uncons[A](stream: Pull[A, Unit]): Pull[Nothing, Option[(NonEmptyList[A], Pull[A, Unit])]] = Free
    .liftF(PullAlg.Uncons(stream, identity))

  extension [Emit, Out](fa: Pull[Emit, Out]) {

    def covary[B >: Emit]: Pull[B, Out] = fa.mapK(new (PullAlg[Emit, *] ~> PullAlg[B, *]) {
      def apply[A](fa: PullAlg[Emit, A]): PullAlg[B, A] =
        fa match {
          case Lift(fa)          => Lift(fa)
          case Output(as, next)  => Output(as, next)
          case Uncons(stream, r) => Uncons(stream, r)
        }
    })

  }

  def unravel[A, B](s: Pull[A, B]): IO[Either[(NonEmptyList[A], Pull[A, B]), B]] =
    s.resume match {
      case Right(value)                                   => value.asRight.pure[IO]
      case Left(moar: PullAlg[A, Free[PullAlg[A, *], B]]) =>
        moar match {
          case Output(as, next) => (as, next).asLeft.pure[IO]
          case Lift(f)          => f.flatMap(unravel)
          case Uncons(s, k)     =>
            unravel(s)
              .map(_.left.toOption)
              // this part probably deals with the interpretation of the provided chunk, up to the next layer
              // e.g. if we were doing uncons.flatMap(handle)
              // this is going to be the next layer of handle
              .map(k)
              .flatMap(unravel)
        }
    }

}

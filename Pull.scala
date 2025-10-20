import cats.effect.IO
import cats.syntax.all.*
import cats.Monad
import cats.StackSafeMonad
import cats.data.NonEmptyList

sealed trait Pull[+A, +Out] {
  def asPull: Pull[A, Out] = this

  def flatMap[AA >: A, B](f: Out => Pull[AA, B]): Pull[AA, B] = Pull.Bind(this, f)
}

object Pull {
  val done: Pull[Nothing, Unit] = succeed(())

  def output1[A](item: A): Pull[A, Unit] = Output(NonEmptyList.of(item))
  def output[A](items: NonEmptyList[A]): Pull[A, Unit] = Output(items)

  def liftF[A](fa: IO[A]): Pull[Nothing, A] = Lift(fa)

  def succeed[A](a: A): Pull[Nothing, A] = liftF(a.pure[IO])

  final case class Uncons[A](stream: Pull[A, Unit]) extends Pull[Nothing, Option[(NonEmptyList[A], Pull[A, Unit])]]
  final case class Output[A](items: NonEmptyList[A]) extends Pull[A, Unit]
  final case class Bind[A, B, C](source: Pull[A, B], f: B => Pull[A, C]) extends Pull[A, C]
  final case class Lift[A](fa: IO[A]) extends Pull[Nothing, A]

  def unravel[A, B](s: Pull[A, B]): IO[Either[(NonEmptyList[A], Pull[A, B]), B]] =
    s match {
      case Bind(lhs, f) =>
        unravel(lhs).flatMap {
          case Right(result)       => unravel(f(result))
          case Left((chunk, rest)) => (chunk -> rest.flatMap(f)).asLeft.pure[IO]
        }
      case Output(vs) => IO.pure((vs -> Stream.empty.pull.echo).asLeft)
      case Lift(fa)   => fa.map(_.asRight)
      case Uncons(v)  => unravel(v).map(_.left.toOption.asRight)
    }

  given [T]: Monad[Pull[T, *]] =
    new StackSafeMonad[Pull[T, *]] {
      def pure[A](x: A): Pull[T, A] = Pull.succeed(x)
      def flatMap[A, B](fa: Pull[T, A])(f: A => Pull[T, B]): Pull[T, B] = fa.flatMap(f)
    }

}

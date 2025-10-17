//> using scala 3.7.3
//> using dep org.typelevel::cats-effect:3.6.3
import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.all.*

sealed trait Stream[A] {
  def take(n: Int): Stream[A] = Stream.Take(this, n)
  def evalMap[B](f: A => IO[B]): Stream[B] = Stream.EvalMap(this, f)
  def compile: Stream.Compile[A] = Stream.Compile(this)
}

object Stream {
  def apply[A](as: A*): Stream[A] = Stream.Chunk(as.toVector)

  trait Compile[A] {
    def drain: IO[Unit]
    def toList: IO[List[A]]
  }

  private object Compile {

    def apply[A](stream: Stream[A]): Compile[A] =
      new Compile[A] {
        def drain: IO[Unit] = {
          def loop[A](s: Stream[A], onItem: A => IO[Unit]): IO[Unit] =
            s match {
              case Chunk(items)       => items.traverse_(onItem)
              case EvalMap(stream, f) => loop(stream, f >=> onItem)
              case Take(stream, n)    =>
                IO.ref(0).flatMap { count =>
                  loop(
                    stream,
                    a =>
                      count.updateAndGet(_ + 1).flatMap {
                        case c if c <= n => onItem(a)
                        case _           => IO.unit
                      },
                  )
                }

            }

          loop(stream, Function.const(IO.unit))
        }
        def toList: IO[List[A]] = ???
      }

  }

  final case class Take[A](stream: Stream[A], n: Int) extends Stream[A]
  final case class EvalMap[A, B](stream: Stream[A], f: A => IO[B]) extends Stream[B]
  final case class Chunk[A](elements: Vector[A]) extends Stream[A]

}

object Demo extends IOApp.Simple {

  def run: IO[Unit] =
    Stream(1, 2, 3, 4, 5)
      .take(3)
      .evalMap(n => IO(println(s"Processing $n")).as(n + 1))
      .compile
      .drain

}

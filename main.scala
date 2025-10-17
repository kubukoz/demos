//> using scala 3.7.3
//> using dep org.typelevel::cats-effect:3.6.3
//> using option -no-indent
import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.all.*

sealed trait Stream[A] {
  def take(n: Int): Stream[A] = Stream.FilterIndex(this, _ < n)
  def drop(n: Int): Stream[A] = Stream.FilterIndex(this, _ >= n)
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
        private def loop[A](s: Stream[A], onItem: A => IO[Unit]): IO[Unit] =
          s match {
            case Chunk(items)              => items.traverse_(onItem)
            case EvalMap(stream, f)        => loop(stream, f >=> onItem)
            case FilterIndex(stream, cond) =>
              IO.ref(0).flatMap { count =>
                loop(
                  stream,
                  a =>
                    count.updateAndGet(_ + 1).flatMap {
                      case c if cond(c) => onItem(a)
                      case _            => IO.unit
                    },
                )
              }
          }

        def drain: IO[Unit] = loop(stream, _ => IO.unit)

        def toList: IO[List[A]] = IO
          .ref(Vector.empty[A])
          .flatTap { ref =>
            loop(stream, item => ref.update(_ :+ item))
          }
          .flatMap(_.get)
          .map(_.toList)
      }

  }

  final case class FilterIndex[A](stream: Stream[A], n: Int => Boolean) extends Stream[A]
  final case class EvalMap[A, B](stream: Stream[A], f: A => IO[B]) extends Stream[B]
  final case class Chunk[A](elements: Vector[A]) extends Stream[A]

}

object Demo extends IOApp.Simple {

  def run: IO[Unit] = Stream(1, 2, 3, 4, 5)
    .evalMap(n => IO(println(s"1: Processing $n")).as(n * 10))
    .take(3)
    .drop(1)
    .evalMap(n => IO(println(s"2: Processing $n")).as(n * 10))
    .compile
    .toList
    .flatMap(IO.println)

}

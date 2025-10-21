//> using scala 3.7.3
//> using dep org.typelevel::cats-effect:3.6.3
//> using dep org.typelevel::cats-free:2.13.0
//> using dep co.fs2::fs2-core:3.12.2
//> using option -no-indent
//> using option -Wunused:all
//> using option -Wvalue-discard
//> using option -Wnonunit-statement
//> using option -Xkind-projector
import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.all.*
import cats.data.NonEmptyList
import cats.data.Chain

opaque type Stream[A] = Pull[A, Unit]

private enum TakeDecision {
  case Keep
  case Reject
  case RejectNext
}

import Stream.stream

extension [A](self: Stream[A]) {
  def append(another: => Stream[A]): Stream[A] = (self.pull.echo >> another.pull.echo).stream
  def ++ = append

  def take(n: Int): Stream[A] = zipWithIndex
    .takeWhile_ {
      case (_, index) if index == n => TakeDecision.RejectNext
      case (_, index) if index < n  => TakeDecision.Keep
      case _                        => TakeDecision.Reject
    }
    .map(_._1)

  private def takeWhile_(cond: A => TakeDecision): Stream[A] =
    pull
      .uncons1
      .covary[A]
      .flatMap {
        _.traverse_ { (item, rest) =>
          val c = cond(item)
          c match {
            case TakeDecision.Keep       => Pull.output1(item) *> rest.takeWhile_(cond).pull.echo
            case TakeDecision.RejectNext => Pull.output1(item)
            case TakeDecision.Reject     => Pull.done.covary[A]
          }
        }
      }
      .stream

  def chunks: Stream[NonEmptyList[A]] =
    pull
      .uncons
      .covary[NonEmptyList[A]]
      .flatMap {
        _.traverse_((item, rest) => Pull.output1(item) *> rest.chunks.pull.echo)
      }
      .stream

  def drain[B]: Stream[B] = pull.uncons.covary[B].flatMap {
    _.traverse_(_._2.drain)
  }

  def unchunks[B](
    using ev: A <:< NonEmptyList[B]
  ): Stream[B] = flatMap(Stream.emits)

  def zip[B](rhs: Stream[B]): Stream[(A, B)] = {
    import Stream.stream

    self
      .pull
      .uncons1
      .covary[(A, B)]
      .flatMap {
        case None                       => Pull.done.covary[(A, B)]
        case Some((leftItem, leftTail)) =>
          rhs.pull.uncons1.covary[(A, B)].flatMap {
            case None                         => Pull.done.covary[(A, B)]
            case Some((rightItem, rightTail)) =>
              Pull.output1((leftItem, rightItem)).covary[(A, B)] *>
                leftTail.zip(rightTail).pull.echo
          }
      }
      .stream
  }

  def zipWithIndex: Stream[(A, Int)] = zip(Stream.iterate(0)(_ + 1))

  def drop(n: Int): Stream[A] = zipWithIndex.dropWhile(_._2 < n).map(_._1)

  def dropWhile(cond: A => Boolean): Stream[A] = pull.uncons1.covary[A].flatMap {
    _.traverse_ { (item, rest) =>
      if cond(item) then rest.dropWhile(cond).stream
      else
        Pull.output1(item).covary[A] *> rest
    }
  }

  def evalMap[B](f: A => IO[B]): Stream[B] = flatMap(f.andThen(Stream.eval))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    pull
      .uncons1
      .covary[B]
      .flatMap {
        _.traverse_ { (item, rest) =>
          f(item).pull.echo *> rest.flatMap(f).pull.echo
        }
      }
      .stream

  def map[B](f: A => B): Stream[B] = evalMap(f.andThen(IO.pure))

  def compile: Stream.Compile[A] = new Stream.Compile(self)
  def pull: Stream.ToPull[A] = new Stream.ToPull(self)

}

object Stream {
  def apply[A](a1: A, rest: A*): Stream[A] = Pull.output(NonEmptyList(a1, rest.toList))
  def emit[A](a: A): Stream[A] = emits(NonEmptyList.of(a))
  def emits[A](as: NonEmptyList[A]): Stream[A] = Pull.output(as)
  def empty[A]: Stream[A] = Pull.done.covary[A]

  def eval[A](fa: IO[A]): Stream[A] = Pull.liftF(fa).covary[A].flatMap(Pull.output1).stream
  def iterate[A](init: A)(f: A => A): Stream[A] = Pull.output1(init).stream ++ iterate(f(init))(f)

  extension [A](pull: Pull[A, Unit]) {
    def stream: Stream[A] = pull
  }

  class Compile[A](stream: Stream[A]) {

    def drain: IO[Unit] = stream.tailRecM(
      Pull
        .unravel(_)
        .map(_.leftMap(_._2))
    )

    def toList: IO[List[A]] = IO
      .ref(Chain.empty[A])
      .flatTap { r =>
        stream.evalMap(v => r.update(_.append(v))).compile.drain
      }
      .flatMap(_.get)
      .map(_.toList)

  }

  class ToPull[A](stream: Stream[A]) {
    inline def echo: Pull[A, Unit] = stream

    def uncons1: Pull[Nothing, Option[(A, Stream[A])]] = uncons.map {
      _.map { (hChunk, t) =>
        val rest = hChunk.tail.toNel.fold(Stream.empty)(Stream.emits)

        (hChunk.head, rest.append(t))
      }
    }

    def uncons: Pull[Nothing, Option[(NonEmptyList[A], Stream[A])]] = Pull.uncons(stream)

  }

}

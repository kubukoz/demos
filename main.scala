//> using scala 3.7.3
//> using dep org.typelevel::cats-effect:3.6.3
//> using dep co.fs2::fs2-core:3.12.2
//> using option -no-indent
//> using option -Wunused:all
//> using option -Wvalue-discard
//> using option -Wnonunit-statement
//> using option -Xkind-projector
import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.all.*
import cats.Monad
import cats.StackSafeMonad
import Stream.Pull
import cats.data.NonEmptyList
import cats.data.Chain

opaque type Stream[+A] = Stream.Pull[A, Unit]

private enum TakeDecision {
  case Keep
  case Reject
  case RejectNext
}

extension [A](self: Stream[A]) {

  def append(another: => Stream[A]): Stream[A] = self.asPull >> another
  def ++ = append

  def take(n: Int): Stream[A] = zipWithIndex
    .takeWhile_ {
      case (_, index) if index == n => TakeDecision.RejectNext
      case (_, index) if index < n  => TakeDecision.Keep
      case _                        => TakeDecision.Reject
    }
    .map(_._1)

  private def takeWhile_(cond: A => TakeDecision): Stream[A] = pull.uncons1.asPull.flatMap {
    _.traverse_ { (item, rest) =>
      val c = cond(item)
      c match {
        case TakeDecision.Keep       => Pull.output1(item) *> rest.takeWhile_(cond)
        case TakeDecision.RejectNext => Pull.output1(item)
        case TakeDecision.Reject     => Pull.done
      }
    }
  }

  def chunks: Stream[NonEmptyList[A]] = pull.uncons.flatMap {
    _.traverse_((item, rest) => Pull.output1(item) *> rest.chunks.pull.echo)
  }

  def drain: Stream[Nothing] = pull.uncons.flatMap {
    _.traverse_(_._2.drain)
  }

  def unchunks[B](
    using ev: A <:< NonEmptyList[B]
  ): Stream[B] = flatMap(Stream.emits)

  def zip[B](rhs: Stream[B]): Stream[(A, B)] =
    self
      .pull
      .uncons1
      .flatMap {
        case None                       => Pull.done
        case Some((leftItem, leftTail)) =>
          rhs.pull.uncons1.flatMap {
            case None                         => Pull.done
            case Some((rightItem, rightTail)) =>
              Pull.output1((leftItem, rightItem)) *>
                leftTail.zip(rightTail)
          }
      }
      .stream

  def zipWithIndex: Stream[(A, Int)] = zip(Stream.iterate(0)(_ + 1))

  def drop(n: Int): Stream[A] = zipWithIndex.dropWhile(_._2 < n).map(_._1)

  def dropWhile(cond: A => Boolean): Stream[A] = pull.uncons1.asPull.flatMap {
    _.traverse_ { (item, rest) =>
      if cond(item) then rest.dropWhile(cond)
      else
        Pull.output1(item) *> rest
    }
  }

  def evalMap[B](f: A => IO[B]): Stream[B] = flatMap(f.andThen(Stream.eval))

  def flatMap[B](f: A => Stream[B]): Stream[B] = pull.uncons1.flatMap {
    _.traverse_ { (item, rest) =>
      f(item) ++ rest.flatMap(f)
    }
  }

  def map[B](f: A => B): Stream[B] = evalMap(f.andThen(IO.pure))

  def compile: Stream.Compile[A] = new Stream.Compile(self)
  def pull: Stream.ToPull[A] = new Stream.ToPull(self)

}

object Stream {
  def apply[A](a1: A, rest: A*): Stream[A] = Pull.Output(NonEmptyList(a1, rest.toList))
  def emit[A](a: A): Stream[A] = emits(NonEmptyList.of(a))
  def emits[A](as: NonEmptyList[A]): Stream[A] = Pull.Output(as)
  def empty[A]: Stream[A] = Pull.done

  def eval[A](fa: IO[A]): Stream[A] = Pull.liftF(fa).flatMap(Pull.output1)
  def iterate[A](init: A)(f: A => A): Stream[A] = Pull.output1(init) ++ iterate(f(init))(f)

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
    def echo: Pull[A, Unit] = stream

    def uncons1: Pull[Nothing, Option[(A, Stream[A])]] = uncons.map {
      _.map { (hChunk, t) =>
        val rest = hChunk.tail.toNel.fold(Stream.empty)(Stream.emits)

        (hChunk.head, rest.append(t))
      }
    }

    def uncons: Pull[Nothing, Option[(NonEmptyList[A], Stream[A])]] = Pull.Uncons(stream)

  }

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

    final case class Uncons[A](stream: Pull[A, Unit]) extends Pull[Nothing, Option[(NonEmptyList[A], Stream[A])]]
    final case class Output[A](items: NonEmptyList[A]) extends Pull[A, Unit]
    final case class Bind[A, B, C](source: Pull[A, B], f: B => Pull[A, C]) extends Pull[A, C]
    final case class Lift[A](fa: IO[A]) extends Pull[Nothing, A]

    private[Stream] def unravel[A, B](s: Pull[A, B]): IO[Either[(NonEmptyList[A], Pull[A, B]), B]] =
      s match {
        case Bind(lhs, f) =>
          unravel(lhs).flatMap {
            case Right(result)       => unravel(f(result))
            case Left((chunk, rest)) => (chunk -> rest.flatMap(f)).asLeft.pure[IO]
          }
        case Output(vs) => IO.pure((vs -> Stream.empty).asLeft)
        case Lift(fa)   => fa.map(_.asRight)
        case Uncons(v)  => unravel(v).map(_.left.toOption.asRight)
      }

    object pullaws {

      case class Eqv[A](lhs: A, rhs: A)

      extension [A](a: A) {

        def <->[B](
          another: B
        )(
          using B <:< A
        ) = Eqv(a, another)

      }

      def unconsOutputIsPure[A](as: NonEmptyList[A]) = Uncons(Output(as)).asPull <-> succeed((as, done).some)
      def unconsLiftUnit(fa: IO[Unit]) = Uncons(Lift(fa)).asPull <-> Lift(fa.as(none))

      // monad laws
      def monadAssociativity[S, A, B, C](lhs: Pull[S, A], f1: A => Pull[S, B], f2: B => Pull[S, C]) =
        Bind(Bind(lhs, f1), f2).asPull <-> Bind(lhs, a => Bind(f1(a), f2))
      def monadFlatmapIdentity[S, A](lhs: Pull[S, A]) = Bind(lhs, Pull.succeed(_)).asPull <-> lhs
      def bindSucceed[S, A, B](a: A, f: A => Pull[S, B]) = Bind(succeed(a), f).asPull <-> f(a)

      // kinda useless I guess, but still true
      def bindEval[S, A, B](fa: IO[A], f: A => Pull[S, B]) =
        Bind(Lift(fa), f).asPull <->
          Bind(Lift(fa.map(f)), identity)

      // just showing this is indeed a free monad in disguise
      def pullFlatmapIsFree[A, B](fa: IO[A], f: A => IO[B]) =
        Bind(Lift(fa), a => Lift(f(a))).asPull <->
          Lift(fa.flatMap(f))

      def unconsBindOutput[S, A, B](ss: NonEmptyList[S], f: Unit => Pull[S, Unit]) =
        Uncons(Bind(Output(ss), f)).asPull <->
          succeed(Some(ss, f(())))

    }

    extension [A](pull: Pull[A, Unit]) {
      def stream: Stream[A] = pull
    }

    given [T]: Monad[Pull[T, *]] =
      new StackSafeMonad[Pull[T, *]] {
        def pure[A](x: A): Pull[T, A] = Pull.succeed(x)
        def flatMap[A, B](fa: Pull[T, A])(f: A => Pull[T, B]): Pull[T, B] = fa.flatMap(f)
      }

  }

}

object Demo extends IOApp.Simple {

  def run: IO[Unit] = /* IO.ref(List[Int]()).flatMap { seen =>
    Stream
      .iterate(1)(_ + 1)
      .take(5)
      .append(Stream(6, 7, 8, 9, 10).evalMap(it => seen.update(_ :+ it).as(it)))
      .evalMap(n => IO(println(s"1: Processing $n")).as(n * 10))
      .take(6)
      .drop(1)
      // .evalMap(n => IO(println(s"2: Processing $n")).as(n * 10))
      .compile
      .toList
      .debug()
     *> seen.get.flatMap { seen =>
        IO.println(s"seen items from second stream: $seen")
      }
  } */
    (Stream(1, 2, 3, 4, 5) ++ Stream
      .eval(IO.unit)
      .drain ++
      Stream(1) ++
      Stream(5, 6, 7, 8)).chunks.unchunks.compile.toList.flatMap(IO.println)

  // Stream
  //   .eval(IO.unit)
  //   .pull
  //   .uncons
  //   .flatMap(_ => Pull.done)
  //   .stream
  //   .compile
  //   .drain
  // Stream.iterate(0)(_ + 1).take(5).evalMap(IO.println(_)).compile.drain

}

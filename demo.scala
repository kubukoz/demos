//> using dep org.typelevel::cats-core::2.12.0
//> using dep org.typelevel::cats-free::2.12.0
//> using option -Ykind-projector
//> using option -source:future-migration
//> using option -rewrite
import cats.~>
import cats.free.Free
import cats.Monad
import cats.syntax.all.*
import cats.data.State

trait MyAlg[F[_]] {
  def create(s: String): F[Int]
  def consume(i: Int): F[Unit]
}

object MyAlg {

  def apply[F[_]](
    implicit F: MyAlg[F]
  ): MyAlg[F] = F

}

enum MyOp[A] {
  case Create(s: String) extends MyOp[Int]
  case Consume(i: Int) extends MyOp[Unit]
}

given MyAlg[Free[MyOp, *]] =
  new MyAlg[Free[MyOp, *]] {
    def create(s: String): Free[MyOp, Int] = Free.liftF(MyOp.Create(s))
    def consume(i: Int): Free[MyOp, Unit] = Free.liftF(MyOp.Consume(i))
  }

def program[F[_]: MyAlg: Monad] =
  for
    i <- MyAlg[F].create("hello")
    _ = println("received " + i)
    _ <- MyAlg[F].consume(i)
  yield ()

// framework
def exec[F[_], A](p: Free[F, A])(handlers: PartialFunction[F[?], ?]*): A =
  p
    .foldMap(
      new (F ~> State[List[PartialFunction[F[?], ?]], *]) {

        def apply[X](fa: F[X]): State[List[PartialFunction[F[?], ?]], X] = State {
          case Nil => sys.error(s"Handlers exhausted: $fa")
          case one :: more =>
            one.lift(fa) match {
              case Some(v) => (more, v.asInstanceOf[X])
              case None    => sys.error(s"Handler not defined: $fa")
            }

        }

      }
    )
    .runA(
      handlers.toList
    )
    .value

@main def run = println {
  import MyOp.*

  exec(program[Free[MyOp, *]])(
    { case Create("hello") => 42 },
    { case Consume(42) => () },
  )
}

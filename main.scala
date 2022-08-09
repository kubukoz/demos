//> using scala "3.1.3"

trait Request {
  type Out
}

object demo {

  def foo(r: Request)(v: r.Out) = ()

  val get: Request { type Out = String } = ???
  val put: Request { type Out = Unit } = ???

  foo(get)(())
}

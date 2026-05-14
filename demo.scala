import library.internal.{AAAA as A1, BBBB as B1, CCCC as C1, DDDD as D1, EEEE as E1, FFFF as F1, GGGG as G1, HHHH as H1}
import library.internal.{X, *}

@main def demo = {
  null.asInstanceOf[A1].asInstanceOf[B1].asInstanceOf[C1].asInstanceOf[D1]
  null.asInstanceOf[E1].asInstanceOf[F1].asInstanceOf[G1].asInstanceOf[H1]
  null.asInstanceOf[X]
}

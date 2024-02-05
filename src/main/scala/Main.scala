package demo

import scala.scalanative.unsafe.exported

object Main {

  class Foo(i: Int)

  @exported("foo")
  def foo(n: Int): Int = {
    Array.fill[Foo](n)(new Foo(n))
    42
  }

}

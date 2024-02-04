package demo

import scala.scalanative.unsafe.exported

object Main {

  @exported("foo")
  def foo(n: Int): Int = {
    Array.ofDim[Int](n)
    42
  }

}

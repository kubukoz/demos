package demo

import cats.effect.IO
import weaver.*

object DemoTest extends SimpleIOSuite {

  test("this should fail rather than hang") {
    IO.unit *> IO.raiseError(new Throwable("woop woop")).as(success)
  }
}

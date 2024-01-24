package demo

import cats.effect.IO
import weaver.*

object DemoTest extends SimpleIOSuite {

  test("envelope serialization round trip") {
    for deserializer <- IO.unit
    _ <- IO.raiseError(new Throwable("woop woop")).as(success)
    yield expect(true)
  }
}

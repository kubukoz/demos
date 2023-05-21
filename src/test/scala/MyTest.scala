import zio.test.Assertion._

import zio.test._

object MyTest extends ZIOSpecDefault {
  def spec = test("example")(assertTrue(true))
}

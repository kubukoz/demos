import weaver._

import cats.effect.IO

object DemoTestWeaver extends SimpleIOSuite {

  pureTest("demo") {
    throw new NoClassDefFoundError("fake exception")
  }
}

//> using scala "2.13.7"
//> using repository "sonatype:snapshots"
//> using lib "dev.zio::zio-test:1.0.14"
//> using lib "dev.zio::zio-test-sbt:1.0.14"
//> using lib "dev.zio::zio-test-magnolia:1.0.14"

import zio.test.Assertion._
import zio.test._

import zio.test.magnolia.diff._
import zio.test.diff.Diff

case class BClass(targetValue: Int, anotherValue: String)
case class AClass(b: BClass, anotherValue: String)
case class MyCaseClass(a: AClass, anotherValue: String)

object MyTest extends DefaultRunnableSpec {
  val actual = MyCaseClass(AClass(BClass(1, "test3"), "test2"), "test")

  val expected =
    actual.copy(a = actual.a.copy(b = actual.a.b.copy(targetValue = 2)))

  def spec: ZSpec[Environment, Failure] = suite("example")(
    test("example") {
      assertTrue(
        actual == expected
      )
    }
  )
}

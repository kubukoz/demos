import org.scalatest.wordspec.AnyWordSpec

import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.flatspec.AsyncFlatSpec
import scala.concurrent.Future

class DemoTestScalatest extends AnyFlatSpec with Matchers {
  "demo" should "fail" in {
    throw new LinkageError("fake exception")
  }
}

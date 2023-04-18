import munit.FunSuite

class DemoTestMunit extends FunSuite {
  test("demo") {
    throw new LinkageError("fake exception")
  }
}

//> using dep "org.scalameta::munit::1.2.1"

class MacrosTest extends munit.FunSuite {

  test("single pattern variable") {
    val result = macros.transform {
      for {
        a <- macros.ctx(42)
      } yield a
    }
    assertEquals(result, Some("a"))
  }

  test("multiple pattern variables") {
    val result = macros.transform {
      for {
        a <- macros.ctx(42)
        b <- macros.ctx(100)
      } yield (a, b)
    }
    assertEquals(result, Some(("a", "b")))
  }

  test("pattern variables with underscore at end") {
    val result = macros.transform {
      for {
        a <- macros.ctx(42)
        b <- macros.ctx(100)
        _ <- Option(50)
      } yield (a, b)
    }
    assertEquals(result, Some(("a", "b")))
  }

  test("three pattern variables") {
    val result = macros.transform {
      for {
        x <- macros.ctx(1)
        y <- macros.ctx(2)
        z <- macros.ctx(3)
      } yield (x, y, z)
    }
    assertEquals(result, Some(("x", "y", "z")))
  }

  test("pattern variables with different names") {
    val result = macros.transform {
      for {
        foo <- macros.ctx("hello")
        bar <- macros.ctx("world")
      } yield s"$foo $bar"
    }
    assertEquals(result, Some("foo bar"))
  }

  test("pattern variable names are strings") {
    val result = macros.transform {
      for {
        value <- macros.ctx(42)
      } yield value
    }
    // The macro replaces the value with the string "value"
    assertEquals(result, Some("value"))
  }

  test("nested for comprehension structure") {
    val result = macros.transform {
      for {
        first <- macros.ctx(1)
        second <- macros.ctx(2)
        _ <- Option(3)
      } yield first + second
    }
    assertEquals(result, Some("firstsecond"))
  }

  test("pattern variable with longer name") {
    val result = macros.transform {
      for {
        myLongVariableName <- macros.ctx(42)
      } yield myLongVariableName
    }
    assertEquals(result, Some("myLongVariableName"))
  }

}

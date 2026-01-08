@main def demo =
  val result = macros.transform {
    for {
      a <- macros.ctx(42)
      b <- macros.ctx(100)
      _ <- Option(50)
    } yield (a, b)
  }
  println(s"Result: $result")

trait CompanionOf[T] {
  type Comp
  def instance: Comp
}

object CompanionOf {

  type Aux[T, C] = CompanionOf[T] { type Comp = C }

  transparent inline given companionOf[T]: CompanionOf[T] = ${ Macros.companionOfImpl[T] }

  def apply[T](
    using c: CompanionOf[T]
  ): c.type = c

}

enum Foo {
  case Bar
}

@main def demo = println {
  val v = CompanionOf[Foo]

  v.instance
}

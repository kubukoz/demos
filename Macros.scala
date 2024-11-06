import scala.quoted.*

object Macros {

  def companionOfImpl[T: Type](
    using Quotes
  ): Expr[CompanionOf[T]] = {
    import quotes.reflect.*
    val comp = TypeRepr.of[T].typeSymbol.companionModule
    comp.typeRef.asType match {
      case '[c] =>
        '{
          new CompanionOf[T] {
            type Comp = c
            def instance: Comp = ${ Ref(comp).asExprOf[c] }
          }
        }

    }
  }

}

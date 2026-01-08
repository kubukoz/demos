//> using option -Wunused:all
import scala.quoted.*

object macros {

  // Must be used inside macros.transform
  // The transform macro will check at compile time that all ctx calls are properly transformed
  def ctx[A](a: A): Option[String] =
    throw new AssertionError(
      "macros.ctx must be used inside macros.transform - this should have been transformed away at compile time"
    )

  // Macro that wraps the entire for-comprehension
  inline def transform[A](inline forComp: A): A = ${ transformImpl('forComp) }

  private def transformImpl[A: Type](
    forComp: Expr[A]
  )(
    using Quotes
  ): Expr[A] = {
    import quotes.reflect.*

    object CtxCall {
      def unapply(tree: Term): Option[Term] =
        tree match {
          case app @ Apply(TypeApply(Select(Ident("macros"), "ctx"), _), List(_)) => Some(app)
          case _                                                                  => None
        }
    }

    // Single pass: collect patterns and transform in one go
    val transformer =
      new TreeMap {
        val ctxToVarName = scala.collection.mutable.Map[Term, String]()

        override def transformTerm(tree: Term)(owner: Symbol): Term =
          tree match {
            // First, check if this is a flatMap/map with a ctx call - collect the pattern
            case Apply(
                  TypeApply(Select(ctxCall @ CtxCall(_), "flatMap" | "map"), _),
                  List(
                    Block(
                      List(
                        DefDef(_, List(TermParamClause(List(ValDef(varName, _, _)))), _, _)
                      ),
                      _,
                    )
                  ),
                ) =>
              ctxToVarName(ctxCall) = varName
              // Continue transforming this tree
              super.transformTerm(tree)(owner)

            // Then, transform ctx calls using collected patterns
            case ctxCall @ CtxCall(_) =>
              ctxToVarName.get(ctxCall) match {
                case Some(varName) => Expr(Some(varName)).asTerm
                case None          =>
                  report.errorAndAbort(
                    "Found macros.ctx call that wasn't transformed. Make sure it's used in a for-comprehension binding like: varName <- macros.ctx(value)",
                    ctxCall.pos,
                  )
              }

            case _ => super.transformTerm(tree)(owner)
          }
      }

    transformer.transformTerm(forComp.asTerm)(Symbol.spliceOwner).asExprOf[A]
  }

}

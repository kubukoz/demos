import scala.quoted.*

object macros {

  // Placeholder for the for-comprehension - not actually a macro
  def ctx[A](a: A): Option[String] = None

  // Macro that wraps the entire for-comprehension
  inline def transform[A](inline forComp: A): A = ${ transformImpl('forComp) }

  private def transformImpl[A: Type](
    forComp: Expr[A]
  )(
    using Quotes
  ): Expr[A] = {
    import quotes.reflect.*

    object CtxCall {
      def unapply(tree: Term): Option[Term] = tree match {
        case app @ Apply(TypeApply(Select(Ident("macros"), "ctx"), _), List(_)) => Some(app)
        case _ => None
      }
    }

    // Single pass: collect patterns and transform in one go
    val transformer = new TreeMap {
      val ctxToVarName = scala.collection.mutable.Map[Term, String]()

      override def transformTerm(tree: Term)(owner: Symbol): Term = tree match {
        // First, check if this is a flatMap/map with a ctx call - collect the pattern
        case Apply(
              TypeApply(Select(ctxCall @ CtxCall(_), "flatMap" | "map"), targs),
              List(lambda @ Block(List(DefDef(name, List(TermParamClause(List(ValDef(varName, _, _)))), tpt, rhs)), closure))
            ) =>
          ctxToVarName(ctxCall) = varName
          // Continue transforming this tree
          super.transformTerm(tree)(owner)

        // Then, transform ctx calls using collected patterns
        case ctxCall @ CtxCall(_) =>
          ctxToVarName.get(ctxCall) match {
            case Some(varName) => '{ Some(${ Expr(varName) }) }.asTerm
            case None => super.transformTerm(tree)(owner)
          }

        case _ => super.transformTerm(tree)(owner)
      }
    }

    transformer.transformTerm(forComp.asTerm)(Symbol.spliceOwner).asExprOf[A]
  }

}

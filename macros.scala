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

    val ctxToVarName = scala.collection.mutable.Map[Term, String]()

    object CtxCall {
      def unapply(tree: Term): Option[Term] = tree match {
        case app @ Apply(TypeApply(Select(Ident("macros"), "ctx"), _), List(_)) => Some(app)
        case _ => None
      }
    }

    // Use TreeTraverser to automatically handle recursion
    object PatternCollector extends TreeTraverser {
      override def traverseTree(tree: Tree)(owner: Symbol): Unit = tree match {
        case Apply(
              TypeApply(Select(ctxCall @ CtxCall(_), "flatMap" | "map"), _),
              List(Block(List(DefDef(_, List(TermParamClause(List(ValDef(varName, _, _)))), _, _)), _))
            ) =>
          ctxToVarName(ctxCall) = varName
          super.traverseTree(tree)(owner)
        case _ =>
          super.traverseTree(tree)(owner)
      }
    }

    PatternCollector.traverseTree(forComp.asTerm)(Symbol.spliceOwner)

    // Transform ctx calls to return the variable name
    val transformer = new TreeMap {
      override def transformTerm(tree: Term)(owner: Symbol): Term = tree match {
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

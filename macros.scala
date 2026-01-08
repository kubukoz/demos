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

    // Traverse to find flatMap/map with ctx calls and extract variable names
    def collectPatterns(tree: Term): Unit = tree match {
      case Apply(
            TypeApply(Select(ctxCall @ CtxCall(_), "flatMap" | "map"), _),
            List(Block(List(DefDef(_, List(TermParamClause(List(ValDef(varName, _, _)))), _, rhs)), _))
          ) =>
        ctxToVarName(ctxCall) = varName
        rhs.foreach(collectPatterns)

      case Block(stats, expr) =>
        stats.foreach {
          case term: Term => collectPatterns(term)
          case defDef: DefDef => defDef.rhs.foreach(collectPatterns)
          case _ => ()
        }
        collectPatterns(expr)
      case Apply(fun, args) =>
        collectPatterns(fun)
        args.foreach(collectPatterns)
      case TypeApply(fun, _) => collectPatterns(fun)
      case Select(qual, _) => collectPatterns(qual)
      case Inlined(_, _, expr) => collectPatterns(expr)
      case _ => ()
    }

    collectPatterns(forComp.asTerm)

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

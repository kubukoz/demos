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

    // Build a map from ctx call positions to their pattern variable names
    val ctxToVarName = scala.collection.mutable.Map[Term, String]()

    // First pass: traverse to find flatMap/map with ctx calls and extract var names
    def collectPatterns(tree: Term): Unit = tree match {
      // Match: macros.ctx(...).flatMap(a => ...) or macros.ctx(...).map(a => ...)
      case Apply(
            TypeApply(Select(ctxCall @ Apply(TypeApply(Select(Ident("macros"), "ctx"), _), _), methodName @ ("flatMap" | "map")), _),
            List(lambdaBlock @ Block(List(defDef @ DefDef(_, List(TermParamClause(List(ValDef(varName, _, _)))), _, rhs)), _))
          ) =>
        ctxToVarName(ctxCall) = varName
        // Continue traversing the lambda body to find nested patterns
        rhs.foreach(collectPatterns)

      case _ =>
        tree match {
          case Block(stats, expr) =>
            stats.foreach {
              case term: Term => collectPatterns(term)
              case defDef: DefDef =>
                defDef.rhs.foreach(collectPatterns)
              case _ => ()
            }
            collectPatterns(expr)
          case Apply(fun, args) =>
            collectPatterns(fun)
            args.foreach(collectPatterns)
          case TypeApply(fun, _) =>
            collectPatterns(fun)
          case Select(qual, _) =>
            collectPatterns(qual)
          case Inlined(_, _, expr) =>
            collectPatterns(expr)
          case _ => ()
        }
    }

    collectPatterns(forComp.asTerm)

    report.info(s"Total patterns collected: ${ctxToVarName.size}, vars: ${ctxToVarName.values.mkString(", ")}")

    // Second pass: transform ctx calls using the collected mapping
    val transformedVars = scala.collection.mutable.ListBuffer[String]()

    val transformer = new TreeMap {
      override def transformTerm(tree: Term)(owner: Symbol): Term = tree match {
        case ctxCall @ Apply(TypeApply(Select(Ident("macros"), "ctx"), _), List(arg)) =>
          ctxToVarName.get(ctxCall) match {
            case Some(varName) =>
              transformedVars += varName
              '{ Some(${ Expr(varName) }) }.asTerm
            case None =>
              super.transformTerm(tree)(owner)
          }

        case _ =>
          super.transformTerm(tree)(owner)
      }
    }

    val transformed = transformer.transformTerm(forComp.asTerm)(Symbol.spliceOwner)

    report.info(s"Collected ${ctxToVarName.size} patterns: ${ctxToVarName.values.mkString(", ")}. Transformed ${transformedVars.size}: ${transformedVars.mkString(", ")}")

    transformed.asExprOf[A]
  }

}

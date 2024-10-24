import scala.quoted.*

object FeatMacro {

  def featImpl[A: Type](
    f: Expr[A => Float]
  )(
    using Quotes
  ): Expr[Feature[A]] = {
    val valName = definingValName("Feature must be assigned directly to a val")

    '{
      new {
        def name = ${ Expr(valName.capitalize) }
        def compute(a: A) =
          ${
            Expr.betaReduce('{ ${ f }(a) })
          }
      }
    }
  }

  def discoverFeaturesImpl[A: Type](
    using Quotes
  ): Expr[FeatureSet[A]] = {
    import quotes.reflect.*
    def parentsOf(sym: Symbol): List[Symbol] =
      if (sym.maybeOwner == Symbol.noSymbol)
        sym :: Nil
      else
        sym :: parentsOf(sym.owner)

    val parents = parentsOf(Symbol.spliceOwner)

    val parent = parents
      .find(_.isClassDef)
      .getOrElse(sys.error("couldn't find class parent"))

    val featureVals: List[Tree] = parent.fieldMembers.filterNot(parents.toSet.contains).map(_.tree)

    val feats = featureVals.flatMap {
      case v @ ValDef(name, tpe, _) =>
        tpe.tpe.asType match {
          case '[Feature[t]] =>
            Some(
              Ident(parent.fieldMember(name).termRef).asExprOf[Feature[A]]
            )
          case _ => None
        }
      case v =>
        report.errorAndAbort(s"Expected val definition, got this instead: ${v.show(
            using Printer.TreeStructure
          )}")
        None
    }

    '{
      FeatureSet.of(${ Varargs(feats) }*)
    }
  }

  private def definingValName(
    errorMsg: String
  )(
    using Quotes
  ): String = {
    import quotes.reflect.*
    val term = enclosingTerm(Symbol.spliceOwner)
    if term.isValDef then term.name
    else quotes.reflect.report.errorAndAbort(errorMsg)
  }

  private def enclosingTerm(
    using Quotes
  )(
    sym: quotes.reflect.Symbol
  ): quotes.reflect.Symbol = {
    import quotes.reflect.*

    sym match {
      case sym if sym.flags.is(Flags.Macro) => enclosingTerm(sym.owner)
      case sym if !sym.isTerm               => enclosingTerm(sym.owner)
      case _                                => sym
    }
  }

}

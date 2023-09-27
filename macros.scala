import scala.quoted.*

import scala.annotation.StaticAnnotation

class kind(value: TokenKind) extends StaticAnnotation

enum TokenKind {
  case EOF()
  case Ident()
}

object MyMacro {
  inline def m[T]: TokenKind = ${ mImpl[T] }

  def mImpl[T: Type](using Quotes): Expr[TokenKind] = {
    import scala.quoted.quotes._
    import scala.quoted.quotes.reflect._
    import scala.quoted.quotes.reflect.report._

    TypeRepr
      .of[T]
      .typeSymbol
      .annotations
      .find(_.tpe <:< TypeRepr.of[kind])
      .getOrElse(report.throwError("No kind annotation found"))
      .asExpr
      .match { case '{ kind($member) } => member }
  }

}

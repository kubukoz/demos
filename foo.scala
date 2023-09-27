//> using lib "com.kubukoz::debug-utils:1.1.3"
//> using scala "3.3.0"

import scala.quoted.Expr
import scala.quoted.Quotes
import com.kubukoz.DebugUtils
import scala.annotation.StaticAnnotation
import scala.deriving.Mirror

trait KindOf[A] {
  def kind(): TokenKind
}

object KindOf {

  inline def derived[A]: KindOf[A] = () => MyMacro.m[A]

}

@kind(TokenKind.Ident())
case class Hello() derives KindOf

@main def main = println(summon[KindOf[Hello]].kind())

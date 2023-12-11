import scala.scalanative.unsigned._

import scala.scalanative.unsafe._

import cats._
import cats.implicits._
import scala.util.Random
import scalanative.libc.stdlib.malloc
import java.nio.charset.StandardCharsets
import cats.parse.Rfc5234
import cats.parse.Parser
import java.time.Instant

@extern
def consoleLog(str: CString): Unit = extern

val parser: Parser[AST] = {
  import cats.parse.{Parser => P}

  val intLit = P.charIn('0' to '9').rep.string.map(_.toInt)

  (intLit ~ (P.char('+') *> intLit).rep0).map { case (head, tail) =>
    tail.foldLeft(AST.IntLit(head)) { case (l, r) => AST.Add(l, AST.IntLit(r)) }
  }
}

enum AST {
  case IntLit(value: Int)
  case Add(left: AST, right: AST)

  def eval: Int =
    this match {
      case IntLit(value)    => value
      case Add(left, right) => left.eval + right.eval
    }

}

@exported("foo")
def scalaApi(s: CString): Unit = Zone { case given Zone =>
  consoleLog(c"Hello from Scala!")
  consoleLog(c"This is another line!")
  consoleLog(toCString("Current time is: " + Instant.now()))
  val parsed = parser
    .parseAll(fromCString(s))

  consoleLog(
    toCString(
      parsed
        .fold(e => e.toString(), _.toString())
    )
  )
  parsed.foreach { ast =>
    consoleLog(
      toCString(
        ast.eval.toString
      )
    )
  }
}

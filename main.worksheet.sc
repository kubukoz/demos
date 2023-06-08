//> using lib "org.typelevel::cats-parse:0.3.9"
//> using option "-Ykind-projector:underscores"
//> using option "-Wunused:all"

import cats.parse.Numbers
import cats.data.State
import cats.data.IndexedStateT

import cats.data.StateT
import scala.annotation.targetName
import cats.data.NonEmptyList
import cats.Defer
import cats.FlatMap
import scala.util.NotGiven
import cats.Monad
import cats.Apply
import cats.parse.Parser
import cats.parse.Rfc5234
import cats.implicits._

// { foo: "bar", baz: 1 }
enum Token {
  case BRACE_LEFT
  case BRACE_RIGHT
  case COLON
  case COMMA
  case IDENT(value: String)
  case NUMBER(value: Int)
}

extension [B](e: Either[Any, B])
  def unwrap: B = e.fold(ee => throw new Exception(ee.toString), identity)

object tokens {
  val intLiteral: Parser[Token.NUMBER] = Numbers.digits.map(_.toInt).map(Token.NUMBER(_))

  val ident: Parser[Token.IDENT] =
    (Rfc5234.alpha *> (Rfc5234.digit | Rfc5234.alpha | Parser.char('_')).rep0)
      .string
      .map(Token.IDENT(_))

  val quote = Parser.char('"')

  val colon = Parser.char(':').as(Token.COLON)
  val comma = Parser.char(',').as(Token.COMMA)
  val braceLeft = Parser.char('{').as(Token.BRACE_LEFT)
  val braceRight = Parser.char('}').as(Token.BRACE_RIGHT)

}

enum Result[A] {
  case Succ(a: A)
  case Error(a: A, tokens: List[Token])
}

case class Binding(key: String, value: AST)

enum AST {
  case IntLiteral(value: Int)
  case ObjectLiteral(value: List[Binding])

  override def toString(): String =
    this match {
      case IntLiteral(v)    => v.toString
      case ObjectLiteral(v) => v.map { case Binding(k, v) => s"$k: $v" }.mkString("{", ", ", "}")
    }

}

def parseFull(s: String): AST = {
  val node: Parser[AST] = Defer[Parser].fix { self =>
    val intLiteral = tokens.intLiteral.map(i => AST.IntLiteral(i.value))
    val ident = tokens.ident.string

    val binding = (ident <* tokens.colon, self).mapN(Binding.apply)

    val struct = binding
      .repSep0(tokens.comma)
      .with1
      .between(tokens.braceLeft, tokens.braceRight)
      .map(AST.ObjectLiteral(_))

    struct | intLiteral
  }

  node.parseAll(s).unwrap
}

def tokenize(s: String): List[Token] = {
  import tokens._
  Parser
    .oneOf(
      intLiteral
        :: ident
        :: colon
        :: comma
        :: braceLeft
        :: braceRight
        :: Nil
    )
    .rep0
    .parseAll(s)
    .unwrap
}

def inObject(tokens: List[Token]): (List[Binding], List[Token]) = {
  import Token._
  import AST._

  tokens match {
    // end of object. simple as that
    case BRACE_RIGHT :: rest => (Nil, rest)

    // got an ident. expect a colon.
    case IDENT(k) :: COLON :: rest =>
      // `rest` has to start with a proper AST node
      val (result, moreTokens) = parseAST(rest)

      inObject(moreTokens).leftMap { restBindings =>
        Binding(k, result) :: restBindings
      }

    case COMMA :: rest =>
      // unless the next token is also a comma, we're good and can continue normally after skipping this token.
      // otherwise, we're in a bad state.
      rest match {
        case COMMA :: _ => throw new Exception("double comma")
        case _          => inObject(rest)
      }
  }
}

case class Foo(s: String, y: Int)

// Foo(Parser.string("s").string, Parser.string("1").string.map(_.toInt))

def parseAST(tokens: List[Token]): (AST, List[Token]) = {
  import Token._
  import AST._

  tokens match {
    case BRACE_LEFT :: more =>
      val (obj, rest) = inObject(more)

      (AST.ObjectLiteral(obj), rest)

    case NUMBER(value) :: rest => (AST.IntLiteral(value), rest)

  }
}

import Token._

def parseFull(tokens: List[Token]): AST = {
  val (result, rest) = parseAST(tokens)
  require(rest.isEmpty, s"expected EOF, found: $rest")
  result
}

Parser.string("foo")
parseFull("""100""")
parseFull("""{foo:10,baz:1}""")
tokenize("""{foo:10,baz:1}""")
tokenize.andThen(parseFull)("""{}""")
tokenize.andThen(parseFull)("""{v:1}""")
tokenize.andThen(parseFull)("""{v:1,v2:10}""")
tokenize.andThen(parseFull)("""{v:{v2:10}}""")

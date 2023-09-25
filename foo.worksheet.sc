import java.time.Instant

import scala.util.matching.Regex

// importToken = "import"
// dotToken = "."
// hashToken = "#"
// identifier = \w+
// newline = "\n"
// namespace = ident [dotToken ident]*
// import = importToken namespace hashToken identifier
// program = import*

sealed trait Token extends Product with Serializable

object Token {
  case object Import extends Token
  case object Dot extends Token
  case object Comma extends Token
  case object Hash extends Token
  case object LB extends Token
  case object RB extends Token
  case object LBR extends Token
  case object RBR extends Token
  case object Equals extends Token
  case object Space extends Token
  case object Newline extends Token

  case class Identifier(
    value: String
  ) extends Token

  case class Error(
    value: String
  ) extends Token

}

def scan(
  s: String
) = {
  var remaining = s
  var tokens = List.empty[Token]
  def add(
    tok: Token
  ) = tokens ::= tok

  def readSimple(token: Char, tok: Token): PartialFunction[Char, Unit] = { case `token` =>
    add(tok)
    remaining = remaining.tail
  }

  def simpleTokens(
    pairings: (Char, Token)*
  ): PartialFunction[Char, Unit] = pairings
    .map(readSimple)
    .reduce(_ orElse _)

  val readOne: PartialFunction[Char, Unit] = simpleTokens(
    '.' -> Token.Dot,
    ',' -> Token.Comma,
    '#' -> Token.Hash,
    '[' -> Token.LB,
    ']' -> Token.RB,
    '{' -> Token.LBR,
    '}' -> Token.RBR,
    '=' -> Token.Equals,
    ' ' -> Token.Space,
    '\n' -> Token.Newline,
  ).orElse {
    case letter if letter.isLetter =>
      val (letters, rest) = remaining.span(_.isLetter)
      add(Token.Identifier(letters))
      remaining = rest
  }

  while (remaining.nonEmpty) // lol, clearly proper interruption handling here
    {
      if (Thread.interrupted())
        throw new InterruptedException()

      val last = remaining

      readOne.applyOrElse(
        remaining.head,
        _ => {
          val (failures, rest) = remaining.span(!readOne.isDefinedAt(_))
          add(Token.Error(failures))
          remaining = rest
        },
      )

      if (remaining == last)
        sys.error(s"no progress in the last run! remaining string: $remaining")
    }

  tokens.reverse
}

scan(".=[]{},")
scan("import com.kubukoz#identifier")
scan("import co111m.kub1ukoz#ident_ifie---,_,r\nimport a")

case class FQN(
  namespace: List[String],
  name: String,
)

def parseIdent(t: List[Token]) = {
  var tokens = t

  // find hash, which is the central piece here
  val hashIndex = tokens.indexOf(Token.Hash)

  def parseNamespace(tokens: List[Token]): List[String] =
    tokens match {
      case Token.Identifier(value) :: Token.Dot :: rest => value :: parseNamespace(rest)
      case Token.Identifier(value) :: Nil               => List(value)
      case Token.Identifier(value) :: rest =>
        value :: "ERROR(no dot, extra tokens)" :: parseNamespace(rest)
      case somethingElse :: Nil  => List(s"ERROR($somethingElse)")
      case somethingElse :: rest => s"ERROR($somethingElse)" :: parseNamespace(rest)
      case Nil                   => "ERROR(nothing left for namespace)" :: Nil
    }

  def parseName(tokens: List[Token]): String =
    tokens match {
      case Token.Identifier(value) :: Nil  => value
      case Token.Identifier(value) :: more => value + s"(more tokens afterwards: $more)"
      case others => s"ERROR(no identifier after hash, extra tokens: $others)"
    }

  hashIndex match {
    case n if n > 0 =>
      // there's defo a hash here.
      // split at it and deconstruct namespace
      val (nsTokens, nameTokens) = tokens.splitAt(n)

      FQN(parseNamespace(nsTokens), parseName(nameTokens.tail))

    case n if n < 0 =>
      // hash is missing.
      // treat everything as the namespace
      FQN(parseNamespace(tokens), "NULL")

    case n if n == 0 =>
      // hash is the first token.
      // treat everything as the name
      FQN(List.empty, parseName(tokens))
  }

}

scan("com.kubukoz#foo")
parseIdent(scan("com.kubukoz#foo"))
// so far so good
// what if no hash?
parseIdent(scan("com.kubukozfoo"))
// what if no ns?
parseIdent(scan("#foo"))

// what if... extra tokens?
parseIdent(scan("com.ku3bukoz#foo"))
parseIdent(scan("com.kubukoz#foo.bar"))
parseIdent(scan("com.kubukoz#foo#bar"))
parseIdent(scan("com.kubukoz#foo#bar.boo"))

Instant.now()

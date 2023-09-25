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

Instant.now()

package scanner

import cats.syntax.all.*

case class Token(kind: TokenKind, text: String)

enum TokenKind {
  case Import, Dot, Comma, Hash, LB, RB, LBR, RBR, Equals, Space, Newline, Identifier, Error

  def apply(text: String): Token = Token(this, text)
}

def scan(
  s: String
) = {
  var remaining = s
  var tokens = List.empty[Token]
  def add(
    tok: Token
  ) = tokens ::= tok

  def readSimple(token: Char, tok: TokenKind): PartialFunction[Char, Unit] = { case `token` =>
    add(tok(token.toString))
    remaining = remaining.tail
  }

  def simpleTokens(
    pairings: (Char, TokenKind)*
  ): PartialFunction[Char, Unit] = pairings
    .map(readSimple)
    .reduce(_ orElse _)

  val readOne: PartialFunction[Char, Unit] = simpleTokens(
    '.' -> TokenKind.Dot,
    ',' -> TokenKind.Comma,
    '#' -> TokenKind.Hash,
    '[' -> TokenKind.LB,
    ']' -> TokenKind.RB,
    '{' -> TokenKind.LBR,
    '}' -> TokenKind.RBR,
    '=' -> TokenKind.Equals,
    // todo: these should be chained at scan time
    ' ' -> TokenKind.Space,
    '\n' -> TokenKind.Newline,
  ).orElse {
    case letter if letter.isLetter =>
      val (letters, rest) = remaining.span(_.isLetter)
      add(TokenKind.Identifier(letters))
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
          add(TokenKind.Error(failures))
          remaining = rest
        },
      )

      if (remaining == last)
        sys.error(s"no progress in the last run! remaining string: $remaining")
    }

  tokens.reverse
}

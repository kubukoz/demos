package scanner

import cats.syntax.all.*

case class Token(kind: TokenKind, text: String)

enum TokenKind {
  case Import, Dot, Comma, Hash, LB, RB, LBR, RBR, Equals, Space, Newline, Identifier, Comment,
    Error

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
  ).orElse {
    case letter if letter.isLetter =>
      val (letters, rest) = remaining.span(_.isLetter)
      add(TokenKind.Identifier(letters))
      remaining = rest
  }

  // split "whitespace" string into chains of contiguous newlines OR whitespace characters.
  def whitespaceChains(whitespace: String): List[Token] = {
    val isNewline = (ch: Char) => ch == '\n'

    if (whitespace.isEmpty) Nil
    else if (isNewline(whitespace.head)) {
      val (nl, rest) = whitespace.span(isNewline)
      TokenKind.Newline(nl) :: whitespaceChains(rest)
    } else {
      val (wsp, rest) = whitespace.span(!isNewline(_))
      TokenKind.Space(wsp) :: whitespaceChains(rest)
    }
  }

  def eatWhitespace() = {
    val (wsp, rest) = remaining.span(ch => ch.isWhitespace)
    if (wsp.isEmpty()) false
    else {
      whitespaceChains(wsp).foreach(add)
      remaining = rest

      true
    }
  }

  def eatComments() =
    if (!remaining.startsWith("//")) false
    else {
      var commentFull = java.lang.StringBuilder()
      while (remaining.startsWith("//")) {
        println(s"${Console.RED}iteratin${Console.RESET}: ${remaining}")
        val (comment, rest) = remaining.span(_ != '\n')
        commentFull.append(comment)
        remaining = rest

        if (remaining.startsWith("\n")) {
          // more whitespace here. consume it
          commentFull.append("\n")
          remaining = remaining.tail
        }
      }

      add(TokenKind.Comment(commentFull.toString()))

      true
    }

  def eatErrors() = {
    // todo: bug: even if the next character starts a multi-char token, this will consider it an error.
    // instead, we should rework "readOne" to consume arbitrary constant-length tokens, and also include the possibility that `rest` has comments or whitespace.
    val (failures, rest) = remaining.span(!readOne.isDefinedAt(_))
    remaining = rest
    if (failures.nonEmpty)
      add(TokenKind.Error(failures))
      true
    else false
  }

  while (remaining.nonEmpty) // lol, clearly proper interruption handling here
    {
      if (Thread.interrupted())
        throw new InterruptedException()

      val last = remaining

      readOne.applyOrElse(
        remaining.head,
        _ =>
          // nothing matched. Eat whitespace and see if the rest is an error
          eatWhitespace() || eatComments() || eatErrors(),
      )

      if (remaining == last)
        sys.error(s"no progress in the last run! remaining string: $remaining")
    }

  tokens.reverse
}

//> using lib "org.typelevel::cats-core:2.10.0"
import cats.syntax.all.*
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

sealed trait Token extends Product with Serializable {
  // todo: might be useful
  // def kind: SyntaxKind
  def text: String
}

object Token {

  case object Import extends Token {
    def text: String = "import"
  }

  case object Dot extends Token {
    def text: String = "."
  }

  case object Comma extends Token {
    def text: String = ","
  }

  case object Hash extends Token {
    def text: String = "#"
  }

  case object LB extends Token {
    def text: String = "["
  }

  case object RB extends Token {
    def text: String = "]"
  }

  case object LBR extends Token {
    def text: String = "{"
  }

  case object RBR extends Token {
    def text: String = "}"
  }

  case object Equals extends Token {
    def text: String = "="
  }

  case object Space extends Token {
    def text: String = " "
  }

  case object Newline extends Token {
    def text: String = "\n"

  }

  case class Identifier(
    text: String
  ) extends Token

  case class Error(
    text: String
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

enum SyntaxKind {
  case File
  case FQN
  case Namespace
  case Identifier
  case ERROR
}

// library
case class GreenNode(
  kind: SyntaxKind,
  width: Int,
  children: List[Either[GreenNode, Token]],
) {
  def cast[A](using mirror: AstNodeMirror[A]): Option[A] = mirror.cast(this)
}

object GreenNode {
  def builder(kind: SyntaxKind) = new GreenNodeBuilder(kind)

  def error(
    token: Token
  ): GreenNode = builder(SyntaxKind.ERROR).addChild(token).build()

  class GreenNodeBuilder(kind: SyntaxKind) {
    private var _width: Int = _
    private var _children: Vector[Either[GreenNode, Token]] = Vector.empty

    def addChild(child: GreenNode): this.type = addChild(child.asLeft)
    def addChild(child: Token): this.type = addChild(child.asRight)

    def addChild(child: Either[GreenNode, Token]): this.type = {
      this._children :+= child
      this
    }

    def build(): GreenNode = GreenNode(
      kind = kind,
      width = _children.foldMap(_.fold(_.width, _.text.length())),
      children = _children.toList,
    )

  }

}

trait AstNode[Self] {
  def syntax: GreenNode
}

trait AstNodeMirror[Self] {
  def cast(node: GreenNode): Option[Self]
}

// concrete

case class Ident(syntax: GreenNode) extends AstNode[Ident] {

  def value: Option[String] = syntax
    .children
    .collectFirst { case Right(Token.Identifier(value)) => value }

}

given AstNodeMirror[Ident] =
  node =>
    node.kind match {
      case SyntaxKind.Identifier => Some(Ident(node))
      case _                     => None
    }

case class Namespace(syntax: GreenNode) extends AstNode[Namespace] {

  def parts: List[Ident] = syntax.children.flatMap {
    case Left(ident) => ident.cast[Ident]
    case Right(_)    => None
  }

}

given AstNodeMirror[Namespace] =
  node =>
    node.kind match {
      case SyntaxKind.Namespace => Some(Namespace(node))
      case _                    => None
    }

case class FQN(syntax: GreenNode) extends AstNode[FQN] {

  def namespace: Option[Namespace] = syntax
    .children
    .collectFirstSome(_.left.toOption.flatMap(_.cast[Namespace]))

  def name: Option[Ident] = syntax.children.collectFirstSome(_.left.toOption.flatMap(_.cast[Ident]))

}

given AstNodeMirror[FQN] =
  node =>
    node.kind match {
      case SyntaxKind.FQN => Some(FQN(node))
      case _              => None
    }

case class Tokens(private var all: List[Token], private var cursor: Int) {

  def eof: Boolean = cursor >= all.length

  def peek(): Token = all(cursor)

  def bump(): Token = {
    val result = peek()
    cursor += 1
    result
  }

}

object Tokens {
  def apply(tokens: List[Token]): Tokens = Tokens(tokens, 0)
}

def parseIdent(
  tokens: Tokens
): GreenNode = {
  val builder = GreenNode.builder(SyntaxKind.Identifier)
  val next = tokens.bump()
  next match {
    case _: Token.Identifier => builder.addChild(next)
    case other               => builder.addChild(GreenNode.error(other))
  }
  builder.build()
}

def parseNamespace(tokens: Tokens): GreenNode = {
  val builder = GreenNode.builder(SyntaxKind.Namespace)

  var done = false

  while (!tokens.eof && !done)
    tokens.peek() match {
      case t: Token.Identifier =>
        // todo: after an ident, expect dot or hash (some sort of state machine / another method in the recursive descent?)
        // if it's an ident, report an error but don't wrap in ERROR
        // otherwise, wrap in ERROR
        builder.addChild(parseIdent(tokens))

      case Token.Dot =>
        // swallow token
        builder.addChild(tokens.bump())

      case Token.Hash => done = true // end of namespace, move on

      case _ =>
        // skip extra/invalid tokens. we will report these in the future
        builder.addChild(GreenNode.error(tokens.bump()))
        tokens.bump()
    }

  builder.build()
}

def parseFQN(tokens: Tokens): GreenNode = {
  val builder = GreenNode.builder(SyntaxKind.FQN)
  builder.addChild(parseNamespace(tokens))
  if (tokens.peek() == Token.Hash) {
    builder.addChild(tokens.bump())
  }
  builder.addChild(parseIdent(tokens))
  builder.build()
}

parseIdent(Tokens(Token.Identifier("hello") :: Nil)).cast[Ident].get.value

parseIdent(Tokens(Token.Identifier("hello") :: Token.Identifier("world") :: Nil))

parseNamespace(Tokens(Nil))
parseNamespace(Tokens(Token.Identifier("hello") :: Nil))

parseNamespace(Tokens(scan("com.kubukoz.world")))
  .cast[Namespace]
  .get
  .parts
  .map(_.value)

val fqn = parseFQN(Tokens(scan("com.kubukoz#foo"))).cast[FQN]
fqn.get.namespace.get.parts.map(_.value.get)
fqn.get.name.get.value.get

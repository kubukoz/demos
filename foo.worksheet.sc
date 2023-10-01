//> using lib "org.typelevel::cats-core:2.10.0"
//> using option "-Wunused:imports"
import cats.syntax.all.*
import scala.deriving.Mirror
import scanner.*

scan(".=[]{},")
scan("import com.kubukoz#identifier")
scan("import co111m.kub1ukoz#ident_ifie---,_,r\nimport a")

case class GreenNode(
  kind: SyntaxKind,
  width: Int,
  children: List[Either[GreenNode, Token]],
) {
  def cast[A](using mirror: AstNodeMirror[A]): Option[A] = mirror.cast(this)

  def allTokens: List[Token] = children.flatMap {
    _.fold(_.allTokens, _.some)
  }

  def print: String = {
    def go(depth: Int, self: GreenNode): String =
      "  " * depth +
        s"${self.kind}:${self.width}\n" +
        self
          .children
          .map {
            case Left(node)   => go(depth + 1, node)
            case Right(token) => "  " * (depth + 1) + token.text
          }
          .mkString("\n")

    go(0, this)
  }

}

object GreenNode {
  def builder(kind: SyntaxKind) = new GreenNodeBuilder(kind)

  def error(
    token: Token
  ): GreenNode = builder(SyntaxKind.ERROR).addChild(token).build()

  class GreenNodeBuilder(kind: SyntaxKind) {
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

enum SyntaxKind {
  case File
  case FQN
  case Namespace
  case Identifier
  case ERROR
}

trait AstNode[Self] { self: Product =>
  def syntax: GreenNode

  def firstChildToken(kind: TokenKind): Option[Token] = syntax.children.collectFirst {
    case Right(tok @ Token(`kind`, text)) => tok
  }

  def allChildNodes[
    N: AstNodeMirror
  ]: List[N] = syntax.children.mapFilter(_.left.toOption.flatMap(_.cast[N]))

  def firstChildNode[
    N: AstNodeMirror
  ]: Option[N] = syntax.children.collectFirstSome(_.left.toOption.flatMap(_.cast[N]))

}

trait AstNodeMirror[Self] {
  def cast(node: GreenNode): Option[Self]
}

object AstNodeMirror {

  def derived[T <: AstNode[T]](
    using m: Mirror.ProductOf[T] { type MirroredElemTypes = Tuple1[GreenNode] },
    label: ValueOf[m.MirroredLabel],
  ): AstNodeMirror[T] = {
    val matchingSyntaxKind = SyntaxKind.valueOf(label.value)

    node =>
      node.kind match {
        case `matchingSyntaxKind` => Some(m.fromProductTyped(Tuple1(node)))
        case _                    => None
      }
  }

}

// concrete

case class Identifier(syntax: GreenNode) extends AstNode[Identifier] derives AstNodeMirror {
  def value: Option[Token] = firstChildToken(TokenKind.Identifier)
}

case class Namespace(syntax: GreenNode) extends AstNode[Namespace] derives AstNodeMirror {
  def parts: List[Identifier] = allChildNodes[Identifier]
}

case class FQN(syntax: GreenNode) extends AstNode[FQN] derives AstNodeMirror {
  def namespace: Option[Namespace] = firstChildNode[Namespace]
  def name: Option[Identifier] = firstChildNode[Identifier]
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
  next.kind match {
    case TokenKind.Identifier => builder.addChild(next)
    case _                    => builder.addChild(GreenNode.error(next))
  }
  builder.build()
}

def parseNamespace(tokens: Tokens): GreenNode = {
  val builder = GreenNode.builder(SyntaxKind.Namespace)

  var done = false

  while (!tokens.eof && !done)
    tokens.peek().kind match {
      case TokenKind.Identifier =>
        // todo: after an ident, expect dot or hash (some sort of state machine / another method in the recursive descent?)
        // if it's an ident, report an error but don't wrap in ERROR
        // otherwise, wrap in ERROR
        builder.addChild(parseIdent(tokens))

      case TokenKind.Dot =>
        // swallow token
        builder.addChild(tokens.bump())

      case TokenKind.Hash => done = true // end of namespace, move on

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
  if (tokens.peek().kind == TokenKind.Hash) {
    builder.addChild(tokens.bump())
  }
  builder.addChild(parseIdent(tokens))
  builder.build()
}

parseIdent(Tokens(TokenKind.Identifier("hello") :: Nil)).cast[Identifier].get.value

parseIdent(Tokens(TokenKind.Identifier("hello") :: TokenKind.Identifier("world") :: Nil))

parseNamespace(Tokens(Nil))
parseNamespace(Tokens(TokenKind.Identifier("hello") :: Nil))

parseNamespace(Tokens(scan("com.kubukoz.world")))
  .cast[Namespace]
  .get
  .parts
  .map(_.value)

val fqn = parseFQN(Tokens(scan("com.kubukoz#foo"))).cast[FQN]
fqn.get.namespace.get.parts.map(_.value.get)
fqn.get.name.get.value.get

println("co111m.kub1ukoz#shrek_blob---,_,r")
//todo: this should have all tokens, even extraneous ones. Should render to the string above.
println(parseFQN(Tokens(scan("co111m.kub1ukoz#shrek_blob---,_,r"))).allTokens.foldMap(_.text))

println(parseFQN(Tokens(scan("co111m.kub1ukoz#shrek_blob---,_,r"))))
println(parseFQN(Tokens(scan("co111m.kub1ukoz#shrek_blob---,_,r"))).print)

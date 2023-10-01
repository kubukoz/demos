//> using lib "org.typelevel::cats-core:2.10.0"
//> using lib "com.lihaoyi::pprint:0.8.1"
//> using option "-Wunused:imports"
import scala.annotation.tailrec
import cats.Eval
import cats.syntax.all.*
import scala.deriving.Mirror
import scanner.*
import util.chaining.*

scan(".=[]{},")
scan("import com.kubukoz#identifier")
scan("import co111m.kub1ukoz#ident_ifie---,_,r\nimport a")

case class GreenNode(
  kind: SyntaxKind,
  children: List[Either[GreenNode, Token]],
) {

  def allTokens: List[Token] = children.flatMap {
    _.fold(_.allTokens, _.some)
  }

  lazy val width: Int = children.foldMap(_.fold(_.width, _.text.length()))

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
      children = _children.toList,
    )

  }

}

case class SyntaxNode(
  offset: Int,
  parent: Eval[Option[SyntaxNode]],
  green: Either[GreenNode, Token],
) {
  def cast[A](using mirror: AstNodeMirror[A]): Option[A] = mirror.cast(this)

  def width = green.fold(_.width, _.width)

  def range: (Int, Int) = (offset, offsetUntil)

  def offsetUntil: Int = offset + width

  def includes(index: Int): Boolean = offset.until(offsetUntil).contains(index)

  def pathTo: List[SyntaxKind] = parent
    .value
    .foldMap { parent =>
      parent.pathTo ++
        parent.green.fold(_.kind.some, _ => None).toList
    }

  def findAt(index: Int): Option[SyntaxNode] =
    if (includes(index))
      children.collectFirstSome(_.findAt(index)).orElse(this.some)
    else {
      None
    }

  def children: List[SyntaxNode] = {
    val rawChildren = green.fold(_.children, tok => Nil)
    val childOffsets = rawChildren.scanLeft[Int](offset)(_ + _.fold(_.width, _.width))

    rawChildren.zip(childOffsets).map { (child, offset) =>
      SyntaxNode(offset, Eval.now(this.some), child)
    }

  }

  def print: String = {
    def go(depth: Int, self: SyntaxNode): String = {
      val content = self.green.fold(_ => "", t => s" \"${t.text}\"")
      "  " * depth +
        s"""${self.green.fold(_.kind, _.kind)}@${self.range._1}..${self.range._2}$content
           |""".stripMargin +
        self
          .children
          .map(go(depth + 1, _))
          .mkString
    }

    go(0, this)
  }

}

object SyntaxNode {

  def newRoot(
    green: GreenNode
  ): SyntaxNode = SyntaxNode(offset = 0, parent = Eval.now(None), green = green.asLeft)

}

enum SyntaxKind {
  case File
  case FQN
  case Namespace
  case Identifier
  case ERROR
}

trait AstNode[Self] { self: Product =>
  def syntax: SyntaxNode

  def firstChildToken(kind: TokenKind): Option[Token] = syntax.children.collectFirst {
    case SyntaxNode(_, _, Right(tok @ Token(`kind`, text))) => tok
  }

  def allChildNodes[N: AstNodeMirror]: List[N] = syntax.children.mapFilter(_.cast[N])
  def firstChildNode[N: AstNodeMirror]: Option[N] = syntax.children.collectFirstSome(_.cast[N])

}

trait AstNodeMirror[Self] {
  def cast(node: SyntaxNode): Option[Self]
}

object AstNodeMirror {

  def derived[T <: AstNode[T]](
    using m: Mirror.ProductOf[T] { type MirroredElemTypes = Tuple1[SyntaxNode] },
    label: ValueOf[m.MirroredLabel],
  ): AstNodeMirror[T] = {
    val matchingSyntaxKind = SyntaxKind.valueOf(label.value)

    node =>
      node.green.left.map(_.kind) match {
        case Left(`matchingSyntaxKind`) => Some(m.fromProductTyped(Tuple1(node)))
        case _                          => None
      }
  }

}

// concrete

case class Identifier(syntax: SyntaxNode) extends AstNode[Identifier] derives AstNodeMirror {
  def value: Option[Token] = firstChildToken(TokenKind.IDENT)
}

case class Namespace(syntax: SyntaxNode) extends AstNode[Namespace] derives AstNodeMirror {
  def parts: List[Identifier] = allChildNodes[Identifier]
}

case class FQN(syntax: SyntaxNode) extends AstNode[FQN] derives AstNodeMirror {
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

enum Error {
  case UnexpectedToken(token: Token)
  case MisingToken(kind: TokenKind)
  // case MissingNode(kind: SyntaxKind)
}

case class ParserState(tokens: Tokens, errors: List[Error])

object ParserState {
  def init(tokens: List[Token]): ParserState = ParserState(Tokens(tokens), errors = Nil)
  def fromString(s: String): ParserState = init(scan(s))
}

def parseIdent(
  state: ParserState
): GreenNode = {
  val builder = GreenNode.builder(SyntaxKind.Identifier)
  val next = state.tokens.bump()
  next.kind match {
    case TokenKind.IDENT => builder.addChild(next)
    case _               => builder.addChild(GreenNode.error(next))
  }
  builder.build()
}

def parseNamespace(state: ParserState): GreenNode = {
  import state.tokens
  val builder = GreenNode.builder(SyntaxKind.Namespace)

  var done = false

  while (!tokens.eof && !done)
    tokens.peek().kind match {
      case TokenKind.IDENT =>
        // todo: after an ident, expect dot or hash (some sort of state machine / another method in the recursive descent?)
        // if it's an ident, report an error but don't wrap in ERROR
        // otherwise, wrap in ERROR
        builder.addChild(parseIdent(state))

      case TokenKind.DOT =>
        // swallow token
        builder.addChild(tokens.bump())

      case TokenKind.HASH => done = true // end of namespace, move on

      case _ =>
        // skip extra/invalid tokens. we will report these in the future
        builder.addChild(GreenNode.error(tokens.bump()))
        tokens.bump()
    }

  builder.build()
}

def parseFQN(state: ParserState): GreenNode = {
  import state.tokens
  val builder = GreenNode.builder(SyntaxKind.FQN)
  builder.addChild(parseNamespace(state))
  if (tokens.peek().kind == TokenKind.HASH) {
    builder.addChild(tokens.bump())
  }
  builder.addChild(parseIdent(state))
  builder.build()
}

SyntaxNode
  .newRoot(parseIdent(ParserState.init(TokenKind.IDENT("hello") :: Nil)))
  .cast[Identifier]
  .get
  .value

parseIdent(ParserState.init(TokenKind.IDENT("hello") :: TokenKind.IDENT("world") :: Nil))

parseNamespace(ParserState.init(Nil))
parseNamespace(ParserState.init(TokenKind.IDENT("hello") :: Nil))

SyntaxNode
  .newRoot(parseNamespace(ParserState.init(scan("com.kubukoz.world"))))
  .cast[Namespace]
  .get
  .parts
  .map(_.value)

val fqn = SyntaxNode.newRoot(parseFQN(ParserState.fromString("com.kubukoz#foo"))).cast[FQN]
fqn.get.namespace.get.parts.map(_.value.get)
fqn.get.name.get.value.get

//todo: this should have all tokens, even extraneous ones. Should render to the string above.
parseFQN(ParserState.fromString("co111m.kub1ukoz#shrek_blob---,_,r")).allTokens.foldMap(_.text)

parseFQN(ParserState.fromString("co111m.kub1ukoz#shrek_blob---,_,r"))
parseFQN(ParserState.fromString("co111m.kub1ukoz#shrek_blob---,_,r")).print

val text = "com.kubukoz#helloworld"
pprint.pprintln(scan(text))
pprint.pprintln(parseFQN(ParserState.init(scan(text))))
pprint.pprintln(SyntaxNode.newRoot(parseFQN(ParserState.init(scan(text)))))
// pprint.pprintln(SyntaxNode.newRoot(parseFQN(ParserState.init(scan(text)))).children)
println(SyntaxNode.newRoot(parseFQN(ParserState.fromString(text))).print)
println(
  SyntaxNode
    .newRoot(parseFQN(ParserState.fromString(text)))
    .findAt("com.kubukoz#h".length)
    .get
    .pathTo
)

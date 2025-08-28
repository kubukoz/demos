//> using option -no-indent
//> using dep org.typelevel::cats-core:2.13.0
import cats.syntax.all.*
import cats.data.EitherNel
import scala.collection.immutable.SortedMap
import scala.collection.immutable.ListMap
import cats.Id

case class Span(start: Int, end: Int)

case class WithSymbol[A](value: A, sym: SymbolId, span: Span)

extension [A](a: WithSymbol[A]) {
  def withSym(sym: SymbolId): WithSymbol[A] = a.copy(sym = sym)
}

case class SymbolId(id: String)
object SymbolId {
  val Empty = SymbolId("<empty>")
}

enum SymbolDef {
  case Operation(service: String, operation: KnownOperation)
}

object SymbolDef {
  extension (sd: SymbolDef) {
    def definitionSource: String =
      sd match {
        case Operation(_, op) => op.definitionSource
      }
  }
}

enum Node {
  case Strink(s: String)
  case Num(n: Int)
  case Ident(name: String)

  def toType(variablesInScope: Map[String, Type]): EitherNel[String, Type] =
    this match {
      case Strink(_) => Type.Str.asRight
      case Num(_)    => Type.Int.asRight
      case Ident(k) =>
        variablesInScope.get(k).toRightNel(s"Referenced variable '$k' not found in scope.")
    }
}

case class SourceFile(
  importedServices: List[String],
  variables: ListMap[String, Node],
  rq: RunQuery,
)

case class RunQuery(
  opName: WithSymbol[String],
  input: Node,
)

enum Type {
  case NoType
  case Str
  case Int

  def matchesType(of: Type): Boolean = this == of // no subtyping for now
}
case class KnownOperation(name: String, input: Type, definitionSource: String)

case class Context(
  availableServices: Map[String, List[KnownOperation]],
  importedServices: List[String] = Nil,
  currentSchema: Option[Type] = None,
  variablesInScope: Map[String, Type] = Map.empty,
)

object Context {
  // known services go into the symbol table automatically, even if they're not imported
  def fromServiceIndex(services: Map[String, List[KnownOperation]]) = Context(services)
}

case class Compiler(
  var symbolTable: Map[SymbolId, SymbolDef] = Map.empty,
  var referenceMap: Map[SymbolId, List[Span]] = Map.empty,
) {
  def findSymbol(ref: SymbolId): Either["SymbolNotFound", SymbolDef] = symbolTable
    .get(ref)
    .toRight("SymbolNotFound")

  lazy val definitionMap: Map[Span, List[SymbolId]] = referenceMap.toList.foldMap {
    case (definitionSymbol, referenceSpans) => referenceSpans.map(_ -> List(definitionSymbol)).toMap
  }

}

object Typer {

  def typecheckFile(
    sf: SourceFile,
    ctx: Context,
    onError: String => Unit,
  )(
    using c: Compiler
  ): SourceFile = {

    ctx.availableServices.foreach { case (svcName, ops) =>
      ops.foreach { knownOp =>
        c.symbolTable +=
          (operationSymbolId(svcName, knownOp.name) ->
            SymbolDef
              .Operation(svcName, knownOp))
      }
    }

    val (existingSvcs, nonexistingSvcs) = sf
      .importedServices
      .partition(ctx.availableServices.keySet.contains_)

    nonexistingSvcs.foreach { s =>
      onError(s"Service '$s' is not available.")
    }

    val resolvedVars =
      sf.variables.foldLeft((scope = Map.empty[String, Type], issues = List.empty[String])) {
        case ((scope, issues), (name, varDef)) =>
          varDef.toType(scope) match {
            case Left(e)    => (scope + (name -> Type.NoType), issues ++ e.toList)
            case Right(tpe) => (scope + (name -> tpe), issues)
          }
      }

    resolvedVars.issues.foreach(onError)

    val newCtx = ctx.copy(
      // or pass them all, even the nonexisting ones?
      importedServices = existingSvcs,
      variablesInScope = resolvedVars.scope,
    )

    val newRQ = typecheckQuery(sf.rq, newCtx, onError)
    sf.copy(rq = newRQ)
  }

  def operationSymbolId(serviceName: String, opName: String): SymbolId = SymbolId(
    s"service:$serviceName#operation:$opName"
  )

  def typecheckQuery(
    rq: RunQuery,
    ctx: Context,
    onError: String => Unit,
  )(
    using c: Compiler
  ): RunQuery = {

    val matchingServices = ctx
      .availableServices
      .filter((k, _) => ctx.importedServices.contains(k))
      .filter(_._2.map(_.name).contains_(rq.opName.value))

    if (matchingServices.size > 1) {
      onError(
        s"Operation '${rq.opName}' is ambiguous, found in services: ${matchingServices.keys.mkString(", ")}"
      )
    } else if (matchingServices.isEmpty) {
      onError(
        s"Operation '${rq.opName}' not found in imported services: ${ctx.importedServices.mkString(", ")}"
      )
    } else {
      // all good
    }

    val (matchingServiceName, operations) = matchingServices.head
    val op = operations.find(_.name == rq.opName.value).get

    val opSymbol = operationSymbolId(matchingServiceName, op.name)

    c.referenceMap += (
      opSymbol -> (c.referenceMap.getOrElse(opSymbol, Nil) :+ rq.opName.span)
    )

    val newCtx = ctx.copy(currentSchema = op.input.some)

    val newInput = typecheckNode(rq.input, newCtx, onError)
    rq.copy(
      opName = rq.opName.withSym(opSymbol),
      input = newInput,
    )
  }

  def typecheckNode(node: Node, ctx: Context, onError: String => Unit): Node =
    ctx.currentSchema match {
      case None         => onError("No schema available for typechecking."); node
      case Some(schema) => typecheckNodeInternal(node, schema, ctx, onError)
    }

  private def typecheckNodeInternal(node: Node, schema: Type, ctx: Context, onError: String => Unit)
    : Node =
    (node, schema) match {
      case (Node.Ident(name), tpe) =>
        ctx.variablesInScope.get(name) match {
          case None => onError(s"Variable '$name' is not defined in the current scope."); node
          case Some(varType) =>
            if (!varType.matchesType(tpe)) {
              onError(s"Type mismatch for variable '$name': expected $tpe but found $varType")
            }
            node
        }

      case (Node.Strink(_), Type.Str) => node // ok
      case (Node.Num(_), Type.Int)    => node // ok
      case _ => onError(s"Type mismatch: node $node does not conform to schema $schema"); node
    }
}

val ctx = Context.fromServiceIndex(
  // service index includes just externally known symbols in global scope
  // so we have to initialize it straight up like that
  Map(
    "UserService" -> List(
      KnownOperation("GetUsers", Type.Int, "/getusers.smithy"),
      KnownOperation("CreateUser", Type.Str, "/createuser.smithy"),
    ),
    "OrderService" -> List(KnownOperation("Refresh", Type.Str, "/orderservice.smithy")),
  )
)

val sampleQuery = SourceFile(
  importedServices = List("UserService"),
  variables = ListMap(
    "userLimit" -> Node.Num(10),
    "maxUsers" -> Node.Ident("userLimit"),
  ),
  rq = RunQuery(
    opName = WithSymbol("GetUsers", SymbolId.Empty, Span(10, 18)),
    input = Node.Ident("maxUsers"),
  ),
)

sampleQuery.rq.opName

val errors = List.newBuilder[String]

given c: Compiler = Compiler(Map.empty)

val checked = Typer.typecheckFile(sampleQuery, ctx, errors.addOne)

c.symbolTable

val errorsList = errors.result()

assert(errorsList.isEmpty, errorsList.mkString("\n"))

checked.rq.opName
checked.rq.opName.sym

c.findSymbol(checked.rq.opName.sym).toOption.get.asInstanceOf[SymbolDef.Operation].operation.input

c.referenceMap(checked.rq.opName.sym)
c.definitionMap(checked.rq.opName.span)
  .map(c.findSymbol(_).toOption.get)
  .map(_.definitionSource)

// checked.rq.opName.sym.asOpRef.operation.definitionSource

// val sampleQueryElaborated = SourceFile(
//   importedServices = List(("UserService", SymbolRef("service:UserService"))),
//   variables = Map(
//     ("userLimit", SymbolDef("variable:userLimit")) -> Node.Num(10),
//     ("maxUsers", SymbolDef("variable:maxUsers")) -> Node.Ident("userLimit", SymbolRef("variable:userLimit")),
//   ),
//   rq = RunQuery(
//     opName = SymbolRef("GetUsers", "service:UserService#operation:GetUsers"),
//     input = Node.NodeMap(
//       Map(
// Q: should this also be a symbol def?
//         ("filter", SymbolRef("service:UserService#operation:GetUsers#input#key:filter")) -> Node.Strink("active"),
//         ...
//       )
//     ),
//   ),
// )

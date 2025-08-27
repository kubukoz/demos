//> using option -no-indent
//> using dep org.typelevel::cats-core:2.13.0
import cats.syntax.all.*
import cats.data.EitherNel
import scala.collection.immutable.SortedMap
import scala.collection.immutable.ListMap

enum Symbol {
  case NoSymbol
  case OperationRef(service: String, operation: KnownOperation)

  def asOpRef =
    this match {
      case orr: OperationRef => orr
      case NoSymbol          => throw new Exception("Not an operation reference")
    }
}

enum Node {
  case Strink(s: String)
  case Num(n: Int)
  case Ident(name: String, sym: Symbol = Symbol.NoSymbol)

  def toType(variablesInScope: Map[String, Type]): EitherNel[String, Type] =
    this match {
      case Strink(_) => Type.Str.asRight
      case Num(_)    => Type.Int.asRight
      case Ident(k, _) =>
        variablesInScope.get(k).toRightNel(s"Referenced variable '$k' not found in scope.")
    }
}

case class SourceFile(
  importedServices: List[String],
  variables: ListMap[String, Node],
  rq: RunQuery,
)

case class RunQuery(
  opName: Node.Ident,
  input: Node,
)

enum Type {
  case NoType
  case Str
  case Int

  def matchesType(of: Type): Boolean = this == of // no subtyping for now
}
case class KnownOperation(name: String, input: Type)

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

object Typer {

  def typecheckFile(
    sf: SourceFile,
    ctx: Context,
    onError: String => Unit,
  ): SourceFile = {

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

  def typecheckQuery(rq: RunQuery, ctx: Context, onError: String => Unit): RunQuery = {
    val matchingServices = ctx
      .availableServices
      .filter((k, _) => ctx.importedServices.contains(k))
      .filter(_._2.map(_.name).contains_(rq.opName.name))

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
    val op = operations.find(_.name == rq.opName.name).get

    val newCtx = ctx.copy(currentSchema = op.input.some)

    val newInput = typecheckNode(rq.input, newCtx, onError)
    rq.copy(
      opName = rq
        .opName
        .copy(sym = Symbol.OperationRef(matchingServiceName, op)),
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
      case (Node.Ident(name, _), tpe) =>
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
      KnownOperation("GetUsers", Type.Int),
      KnownOperation("CreateUser", Type.Str),
    ),
    "OrderService" -> List(KnownOperation("Refresh", Type.Str)),
  )
)

val sampleQuery = SourceFile(
  importedServices = List("UserService"),
  variables = ListMap(
    "userLimit" -> Node.Num(10),
    "maxUsers" -> Node.Ident("userLimit"),
  ),
  rq = RunQuery(
    opName = Node.Ident("GetUsers"),
    input = Node.Ident("maxUsers"),
  ),
)

sampleQuery.rq.opName
sampleQuery.rq.opName.sym

val errors = List.newBuilder[String]

val checked = Typer.typecheckFile(sampleQuery, ctx, errors.addOne)

val errorsList = errors.result()

assert(errorsList.isEmpty, errorsList.mkString("\n"))

checked.rq.opName
checked.rq.opName.sym
checked.rq.opName.sym.asOpRef.operation.input

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

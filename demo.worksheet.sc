//> using option -no-indent
//> using dep org.typelevel::cats-core:2.13.0
import cats.syntax.all.*
import cats.data.EitherNel
import scala.collection.immutable.SortedMap

enum Node {
  case Strink(s: String)
  case Num(n: Int)
  case NodeMap(map: Map[String, Node])
  case Ident(name: String)

  def toType(variablesInScope: Map[String, Type]): EitherNel[String, Type] =
    this match {
      case Strink(_) => Type.Str.asRight
      case Num(_)    => Type.Int.asRight
      case NodeMap(fields) =>
        fields
          .to(SortedMap)
          .traverse(_.toType(variablesInScope))
          .map(Type.Struct(_))
      case Ident(k) =>
        variablesInScope.get(k).toRightNel(s"Referenced variable '$k' not found in scope.")
    }
}

case class SourceFile(
  importedServices: List[String],
  variables: Map[String, Node],
  rq: RunQuery,
)

case class RunQuery(
  opName: String,
  input: Node,
)

enum Type {
  case NoType
  case Str
  case Int
  case Struct(fields: Map[String, Type])

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
  type Result = Unit

  def typecheckFile(sf: SourceFile, ctx: Context, onError: String => Unit): Result = {

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

    typecheckQuery(sf.rq, newCtx, onError)
  }

  def typecheckQuery(rq: RunQuery, ctx: Context, onError: String => Unit): Result = {
    val matchingServices = ctx
      .availableServices
      .filter((k, _) => ctx.importedServices.contains(k))
      .filter(_._2.map(_.name).contains_(rq.opName))

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
    val op = operations.find(_.name == rq.opName).get

    val newCtx = ctx.copy(currentSchema = op.input.some)

    typecheckNode(rq.input, newCtx, onError)
  }

  def typecheckNode(node: Node, ctx: Context, onError: String => Unit): Result =
    ctx.currentSchema match {
      case None         => onError("No schema available for typechecking.")
      case Some(schema) => typecheckNodeInternal(node, schema, ctx, onError)
    }

  private def typecheckNodeInternal(node: Node, schema: Type, ctx: Context, onError: String => Unit)
    : Result =
    (node, schema) match {
      case (Node.Ident(name), tpe) =>
        ctx.variablesInScope.get(name) match {
          case None => onError(s"Variable '$name' is not defined in the current scope.")
          case Some(varType) =>
            if (!varType.matchesType(tpe)) {
              onError(s"Type mismatch for variable '$name': expected $tpe but found $varType")
            }
        }

      case (Node.Strink(_), Type.Str) => // ok
      case (Node.Num(_), Type.Int)    => // ok
      case (Node.NodeMap(fields), Type.Struct(fieldTypes)) =>
        val missingFields = fieldTypes -- fields.keySet
        val extraneousFields = fields -- fieldTypes.keySet
        val knownPresentFields = fields.keySet.intersect(fieldTypes.keySet)

        missingFields.foreach { f =>
          onError(s"Missing required field '$f' in input object.")
        }

        extraneousFields.foreach { f =>
          onError(s"Extraneous field '$f' in input object.")
        }

        knownPresentFields.foreach { knownFieldKey =>
          val fieldNode = fields(knownFieldKey)
          val fieldType = fieldTypes(knownFieldKey)

          typecheckNodeInternal(fieldNode, fieldType, ctx, onError)
        }

      case _ => onError(s"Type mismatch: node $node does not conform to schema $schema")
    }
}

val ctx = Context.fromServiceIndex(
  // service index includes just externally known symbols in global scope
  // so we have to initialize it straight up like that
  Map(
    "UserService" -> List(
      KnownOperation("GetUsers", Type.Struct(Map("filter" -> Type.Str, "limit" -> Type.Int))),
      KnownOperation("CreateUser", Type.Struct(Map("name" -> Type.Str))),
    ),
    "OrderService" -> List(KnownOperation("Refresh", Type.Struct(Map.empty))),
  )
)

val sampleQuery = SourceFile(
  importedServices = List("UserService"),
  variables = Map(
    "userLimit" -> Node.Num(10),
    "maxUsers" -> Node.Ident("userLimit"),
  ),
  rq = RunQuery(
    opName = "GetUsers",
    input = Node.NodeMap(
      Map(
        "filter" -> Node.Strink("active"),
        "limit" -> Node.Ident("maxUsers"),
      )
    ),
  ),
)

val errors = List.newBuilder[String]

Typer.typecheckFile(sampleQuery, ctx, errors.addOne)

val errorsList = errors.result()

assert(errorsList.isEmpty, errorsList.mkString("\n"))

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

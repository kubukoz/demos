import cats.Eval

import cats.data.State
import cats.implicits._

object types {

  enum Expr {
    case Value(v: types.Value)
    case Variable(name: String)

    def tpe(st: Map[String, Type]): Type =
      this match {
        case Expr.Value(v)  => v.tpe(st)
        case Variable(name) => st(name)
      }

  }

  enum Statement {
    // case Binding(symbol: String, value: Expr)
    case Run(symbol: String, op: String, input: Expr)
  }

  enum Value {
    case Num(v: Int)
    case Str(s: String)
    case Struct(entries: Map[String, Expr])

    def tpe(st: Map[String, Type]): Type =
      this match {
        case Num(n)          => Type.SingletonNum(n)
        case Str(s)          => Type.SingletonStr(s)
        case Struct(entries) => Type.Structural(entries.map(_.fmap(_.tpe(st))))
      }

    def asInt =
      this match {
        case Num(v) => v
        case _      => sys.error("not an int: " + this)
      }

    def asStr =
      this match {
        case Str(v) => v
        case _      => sys.error("not a str: " + this)
      }

    def asStruct =
      this match {
        case Struct(e) => e
        case _         => sys.error("not a struct: " + this)
      }

  }

  enum Type {
    case SingletonNum(n: Int)
    case SingletonStr(s: String)
    case Num
    case Str
    case Structural(keys: Map[String, Type])

    def subtypes(another: Type): Boolean =
      this match {
        case SingletonNum(n) =>
          another match {
            case SingletonNum(`n`) | Num => true
            case _                       => false
          }
        case SingletonStr(n) =>
          another match {
            case SingletonStr(`n`) | Str => true
            case _                       => false
          }
        case Num => another == Num
        case Str => another == Str
        case Structural(keys) =>
          another match {
            case Structural(otherKeys) =>
              otherKeys.forall { case (k, v) => keys.get(k).exists(_.subtypes(v)) }

            case _ => false
          }
      }

  }

  case class Program(statements: Statement*)
}

import types._

type Interpreter[A] = State[Map[String, Value], A]

object Interpreter {
  def get: Interpreter[Map[String, Value]] = State.get
  def put(k: String, v: Value): Interpreter[Unit] = State.modify(_ + (k -> v))
}

def eval[A](input: Expr)(st: Map[String, A])(unwrap: Value => A): Either[String, A] =
  input match {
    case Expr.Value(v)       => unwrap(v).asRight
    case Expr.Variable(name) => st.get(name).toRight("undefined variable " + name)
  }

case class FunctionDef(name: String, inputType: Type, outputType: Type)

val functions = List(
  FunctionDef("inc", Type.Num, Type.Num),
  FunctionDef("double", Type.Num, Type.Num),
  FunctionDef("length", Type.Str, Type.Num),
  FunctionDef("reverse", Type.Str, Type.Str),
  FunctionDef("add", Type.Structural(Map("a1" -> Type.Num, "a2" -> Type.Num)), Type.Num),
  FunctionDef("lol", Type.Str, Type.SingletonStr("hello2")),
  FunctionDef("lol2", Type.SingletonStr("hello2"), Type.Num),
).groupBy(_.name).fmap(_.head)

def perform(op: String, input: Value)(st: Map[String, Value]): Value =
  op match {
    case "inc"     => Value.Num(input.asInt + 1)
    case "double"  => Value.Num(input.asInt * 2)
    case "length"  => Value.Num(input.asStr.length)
    case "reverse" => Value.Str(input.asStr.reverse)
    case "add" =>
      val in = input.asStruct
      val a1 = eval(in("a1"))(st)(identity).fold(sys.error(_), identity).asInt
      val a2 = eval(in("a2"))(st)(identity).fold(sys.error(_), identity).asInt
      Value.Num(a1 + a2)
    case "lol" =>
      input.asStr
      Value.Str("hello2")
    case "lol2" =>
      val in = input.asStr
      require(in == "hello2", "string must be hello2")
      Value.Num(2)
  }

type Typer[A] = State[Map[String, Type], A]

object Typer {
  def get: Typer[Map[String, Type]] = State.get
  def put(k: String, v: Type): Typer[Unit] = State.modify(_ + (k -> v))
}

def typecheck(statements: Program) =
  statements
    .statements
    .traverse_ {
      case Statement.Run(symbol, op, input) if functions.contains(op) =>
        Typer.get.flatMap { st =>
          val inTpe = functions(op).inputType
          val actualInputType = eval(input)(st)(_.tpe(st))
            .fold(e => sys.error("typecheck error: " + e), identity)

          if (!actualInputType.subtypes(inTpe))
            sys.error(s"input type mismatch: expected $inTpe, got $actualInputType")

          Typer.put(symbol, functions(op).outputType)
        }
      case Statement.Run(_, op, _) => sys.error(s"typecheck error: unknown operation $op")
    }
    .run(Map.empty)
    .value

def interpret(statements: Program) =
  statements
    .statements
    .traverse { case Statement.Run(symbol, op, input) =>
      Interpreter.get.flatMap { st =>
        val result =
          perform(
            op,
            eval(input)(st)(identity).fold(sys.error(_), identity),
          )(st)

        Interpreter.put(symbol, result).as(result)
      }
    }
    .map(_.last)
    .run(Map.empty)
    .value

val statements = Program(
  // Statement.Binding("a", Expr.Value(42)),
  Statement.Run("a", "reverse", Expr.Value(Value.Str("hello"))),
  Statement.Run("b", "length", Expr.Variable("a")),
  Statement.Run("x", "inc", Expr.Variable("b")),
  Statement.Run("x", "double", Expr.Variable("x")),
  Statement.Run("_", "double", Expr.Variable("x")),
  Statement.Run("_", "double", Expr.Variable("x")),
  Statement.Run(
    "_",
    "add",
    Expr.Value(Value.Struct(Map("a1" -> Expr.Variable("b"), "a2" -> Expr.Variable("x")))),
  ),
  Statement.Run("c", "lol", Expr.Value(Value.Str("aaaa"))),
  Statement.Run("_", "lol2", Expr.Variable("c")),
)

typecheck(statements)

interpret(statements)

scala.util.Random.nextInt()

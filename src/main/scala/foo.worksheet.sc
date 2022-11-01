import cats.Eval

import cats.data.State
import cats.implicits._

enum Expr {
  case Value(v: Int)
  case Variable(name: String)
}

enum Statement {
  // case Binding(symbol: String, value: Expr)
  case Run(symbol: String, op: String, input: Expr)
}

case class Program(statements: Statement*)

type Interpreter[A] = State[Map[String, Int], A]

object Interpreter {
  def get: Interpreter[Map[String, Int]] = State.get
  def put(k: String, v: Int): Interpreter[Unit] = State.modify(_ + (k -> v))
}

def eval(input: Expr)(st: Map[String, Int]): Either[String, Int] =
  input match {
    case Expr.Value(v)       => v.asRight
    case Expr.Variable(name) => st.get(name).toRight("undefined variable " + name)
  }

def perform(op: String, input: Int): Int =
  op match {
    case "inc"    => input + 1
    case "double" => input * 2
  }

def typecheck(statements: Program) =
  statements
    .statements
    .traverse_ {
      case Statement.Run(symbol, op @ ("inc" | "double"), input) =>
        Interpreter.get.flatMap { st =>
          eval(input)(st).fold(e => sys.error("typecheck error: " + e), identity)

          Interpreter.put(symbol, 0 /* todo: this should be a symbol set, not a map really */ )
        }
      case Statement.Run(_, op, _) => sys.error(s"typecheck error: unknown operation $op")
    }
    .runA(Map.empty)
    .value

def interpret(statements: Program) =
  statements
    .statements
    .traverse { case Statement.Run(symbol, op, input) =>
      Interpreter.get.flatMap { st =>
        val result = perform(op, eval(input)(st).toOption.getOrElse(sys.error("fatal error!")))
        Interpreter.put(symbol, result).as(result)
      }
    }
    .map(_.last)
    .runA(Map.empty)
    .value

val statements = Program(
  // Statement.Binding("a", Expr.Value(42)),
  Statement.Run("x", "inc", Expr.Value(10)),
  Statement.Run("x", "double", Expr.Variable("x")),
  Statement.Run("_", "double", Expr.Variable("x")),
)

typecheck(statements)
interpret(statements)

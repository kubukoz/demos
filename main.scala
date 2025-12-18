//> using dep org.typelevel::cats-core:2.13.0
//> using dep "com.kubukoz::debug-utils:1.1.3"
import cats.syntax.all.*
import cats.data.EitherNel
import com.kubukoz.DebugUtils

type Color = String

object Color {
  val GREEN = Console.GREEN_B
  val RED = Console.RED_B
  val YELLOW = Console.YELLOW_B
  val BLUE = Console.BLUE_B
  val MAGENTA = Console.MAGENTA_B
  val CYAN = Console.CYAN_B
}

enum State {
  case Available
  case Unavailable
  case Queen
}

case class Block(color: Color, state: State)

case class Board(rows: IArray[IArray[Block]]) {

  lazy val isComplete: Boolean = rows.forall(_.exists(_.state == State.Queen))

  lazy val queens: List[(color: Color, x: Int, y: Int)] =
    rows
      .zipWithIndex
      .flatMap { case (row, y) =>
        row.zipWithIndex.collect { case (Block(color, State.Queen), x) => (color, x, y) }
      }
      .toList

}

def render(b: Board): String = b
  .rows
  .map { row =>
    row.map { b =>
      val state =
        b.state match {
          case State.Available   => " "
          case State.Queen       => "Q"
          case State.Unavailable => "x"
        }

      s"${b.color}$state${Console.RESET}"
    }.mkString
  }
  .mkString("\n")

def isValid(b: Board): EitherNel[String, Unit] =
  (
    uniqueBy("color")(_.color)(b),
    uniqueBy("column")(_.x)(b),
    uniqueBy("row")(_.y)(b),
  )
    .parTupled
    .void

val rules: List[Board => Board] = Nil //todo

def step(b: Board): EitherNel[String, Option[Board]] = isValid(b).map { _ =>
  rules
    .collectFirstSome { f =>
      Some(f(b)).filterNot(_ == b)
    }
}

def solveSteps(b: Board): List[Board] =
  List.unfold(b) { board =>
    step(board) match {
      case Left(_)        => None
      case Right(None)    => None
      case Right(Some(v)) => Some((v, v))
    }
  }

private def uniqueBy[A](tag: String)(f: ((color: Color, x: Int, y: Int)) => A)(b: Board)
  : EitherNel[String, Unit] = {
  val grouped = b.queens.groupBy { case (color, x, y) => f(color, x, y) }
  val duplicates =
    grouped.collect {
      case (key, list) if list.sizeIs > 1 =>
        s"Multiple queens share the same $tag: ${list.map { case (color, x, y) => s"$color at ($x,$y)${Console.RESET}" }.mkString("\n- ", "\n- ", "\n")}"
    }.toList

  Either.cond(duplicates.isEmpty, (), duplicates).leftMap(_.toNel.get)
}

@main def demo = {
  val sample = Board(
    // 8 rows
    IArray(
      IArray(
        // 8 blocks
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Queen),
        Block(Color.BLUE, State.Unavailable),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Available),
        Block(Color.MAGENTA, State.Unavailable),
        Block(Color.RED, State.Queen),
        Block(Color.GREEN, State.Available),
      ),
      IArray(
        Block(Color.BLUE, State.Available),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Queen),
        Block(Color.MAGENTA, State.Unavailable),
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Available),
        Block(Color.BLUE, State.Unavailable),
        Block(Color.YELLOW, State.Queen),
      ),
      IArray(
        Block(Color.CYAN, State.Unavailable),
        Block(Color.MAGENTA, State.Available),
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Queen),
        Block(Color.BLUE, State.Available),
        Block(Color.YELLOW, State.Unavailable),
        Block(Color.CYAN, State.Available),
        Block(Color.MAGENTA, State.Queen),
      ),
      IArray(
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Unavailable),
        Block(Color.BLUE, State.Queen),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Available),
        Block(Color.MAGENTA, State.Queen),
        Block(Color.RED, State.Unavailable),
        Block(Color.GREEN, State.Available),
      ),
      IArray(
        Block(Color.BLUE, State.Queen),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Unavailable),
        Block(Color.MAGENTA, State.Available),
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Queen),
        Block(Color.BLUE, State.Available),
        Block(Color.YELLOW, State.Unavailable),
      ),
      IArray(
        Block(Color.CYAN, State.Available),
        Block(Color.MAGENTA, State.Queen),
        Block(Color.RED, State.Unavailable),
        Block(Color.GREEN, State.Available),
        Block(Color.BLUE, State.Queen),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Unavailable),
        Block(Color.MAGENTA, State.Available),
      ),
      IArray(
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Queen),
        Block(Color.BLUE, State.Available),
        Block(Color.YELLOW, State.Unavailable),
        Block(Color.CYAN, State.Queen),
        Block(Color.MAGENTA, State.Available),
        Block(Color.RED, State.Available),
        Block(Color.GREEN, State.Unavailable),
      ),
      IArray(
        Block(Color.BLUE, State.Unavailable),
        Block(Color.YELLOW, State.Available),
        Block(Color.CYAN, State.Queen),
        Block(Color.MAGENTA, State.Available),
        Block(Color.RED, State.Unavailable),
        Block(Color.GREEN, State.Queen),
        Block(Color.BLUE, State.Available),
        Block(Color.YELLOW, State.Available),
      ),
    )
  )

  println(render(sample))

  println(isValid(sample))

  println(solveSteps(sample))

  val n = 5.0
  DebugUtils.withDesugar(5 / n)
}

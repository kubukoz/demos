//> using dep "com.olvind.tui::tui:0.0.7"
//> using dep co.fs2::fs2-io:3.12.0
//> using scala 3.7.3-RC2
//> using options -no-indent -Wunused:all
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.kernel.Ref
import fs2.concurrent.SignallingRef
import tui.*
import tui.crossterm.CrosstermJni
import tui.crossterm.KeyModifiers
import tui.widgets.*
import cats.syntax.all.*
import fs2.io.file.Files
import fs2.io.file.Path

object ChoiceApp extends IOApp.Simple {

  def run: IO[Unit] = interop
    .terminal
    .use { case (jni, terminal) =>
      SignallingRef[IO]
        .of(
          State(
            selected = 0,
            items = List("beer", "taxes"),
            result = None,
          )
        )
        .flatMap { stateRef =>
          stateRef
            .discrete
            .foreach { s =>
              IO.blocking(render(s, terminal))
            }
            .interruptWhen(
              awaitExit(jni, stateRef).as(Right(()))
            )
            .compile
            .drain *> stateRef.get.map(_.result)
        }
    }
    .guaranteeCase(log(_))
    .flatMap(_.traverse_(choice => IO.println(s"you chose $choice!")))

  def log(msg: Any) =
    Files[IO]
      .writeUtf8(Path("debug.log"), fs2.io.file.Flags.Append)
      .apply(fs2.Stream.emit(s"$msg\n"))
      .compile
      .drain

  def awaitExit(jni: CrosstermJni, stateRef: Ref[IO, State]): IO[Unit] =
    IO
      .blocking(jni.read())
      .flatMap {
        case key: tui.crossterm.Event.Key =>
          key.keyEvent.code match {
            case char: tui.crossterm.KeyCode.Char
                if char.c() == 'q' ||
                  (char
                    .c() == 'c' && (key.keyEvent().modifiers().bits() == KeyModifiers.CONTROL)) =>
              IO.pure(true)
            case _: tui.crossterm.KeyCode.Up    => stateRef.update(_.previous).as(false)
            case _: tui.crossterm.KeyCode.Down  => stateRef.update(_.next).as(false)
            case _: tui.crossterm.KeyCode.Enter => stateRef.update(_.complete).as(true)
            case _                              => IO.pure(false)
          }
        case _ => stateRef.update(identity).as(false)
      }
      .iterateWhile(!_)
      .void

  extension (i: Int) {

    def clamped(range: Range): Int =
      if (i < range.start)
        range.end - 1
      else if (i >= range.end)
        range.start
      else
        i

  }

  case class State(
    selected: Int,
    items: List[String],
    result: Option[String],
  ) {
    def next: State = copy(selected = (selected + 1).clamped(0 until items.length))
    def previous: State = copy(selected = (selected - 1).clamped(0 until items.length))
    def complete: State = copy(result = Some(items(selected)))
  }

  def render(state: State, terminal: Terminal) = terminal.draw { f =>
    val block = BlockWidget(
      title = Some(Spans(Array(Span("select your poison", Style.DEFAULT)))),
      borders = Borders.ALL,
    )

    val list = ListWidget(
      block = Some(block),
      items =
        state
          .items
          .map { i =>
            ListWidget.Item(
              content = Text(
                Array(
                  Spans(
                    Array(Span(i, Style.DEFAULT))
                  )
                )
              )
            )
          }
          .toArray,
      style = Style.DEFAULT,
      startCorner = Corner.TopLeft,
      highlightStyle = Style.DEFAULT.fg(Color.Red),
      highlightSymbol = Some("> "),
    )

    f.renderStatefulWidget(
      list,
      terminal.viewport.area,
    )(ListWidget.State(offset = 0, selected = Some(state.selected)))
  }

}

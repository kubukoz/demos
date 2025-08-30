import cats.effect.IO
import cats.effect.kernel.Resource
import tui.*
import tui.crossterm.Command
import tui.crossterm.CrosstermJni

object interop {

  def terminal: Resource[IO, (CrosstermJni, Terminal)] =
    Resource.make {
      IO.blocking {
        val jni = new CrosstermJni
        // setup terminal
        jni.enableRawMode()
        jni.execute(new Command.EnterAlternateScreen(), new Command.EnableMouseCapture())

        val backend = new CrosstermBackend(jni)

        val terminal = Terminal.init(backend)

        (jni, terminal)
      }
    } { case (jni, terminal) =>
      IO.blocking {
        // restore terminal
        jni.disableRawMode()
        jni.execute(new Command.LeaveAlternateScreen(), new Command.DisableMouseCapture())
        terminal.backend.showCursor()
      }
    }

}

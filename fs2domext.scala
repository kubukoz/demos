package fs2.dom.ext

import cats.effect.IO
import fs2.dom.*
import org.scalajs.dom.HTMLDocument

import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import scala.scalajs.js.annotation.JSGlobal

object FS2DomExtensions {

  extension (doc: HtmlDocument[IO]) {

    def onKeyDown: fs2.Stream[IO, KeyboardEvent[IO]] = fs2
      .dom
      .events[IO, org.scalajs.dom.KeyboardEvent](
        doc.asInstanceOf[HTMLDocument],
        "keydown",
      )
      .map(KeyboardEvent[IO](_))

    def onKeyUp: fs2.Stream[IO, KeyboardEvent[IO]] = fs2
      .dom
      .events[IO, org.scalajs.dom.KeyboardEvent](
        doc.asInstanceOf[HTMLDocument],
        "keyup",
      )
      .map(KeyboardEvent[IO](_))

  }

  extension (unused: Navigator[IO]) {

    // todo: it'd be nice to access the permission as a signal
    // so gotta steal some code from todomeda
    def requestMIDIAccess: IO[MIDIAccess] = IO
      .fromPromise {
        IO(
          org
            .scalajs
            .dom
            .window
            .navigator
            .asInstanceOf[scala.scalajs.js.Dynamic]
            .requestMIDIAccess()
            .asInstanceOf[scalajs.js.Promise[natives.MIDIAccess]]
        )
      }
      .map(MIDIAccess.wrap)

  }

}

trait MIDIAccess {
  def outputs: IO[Map[String, MIDIOutput]]
}

object MIDIAccess {

  private[ext] def wrap(access: natives.MIDIAccess): MIDIAccess =
    new MIDIAccess {

      def outputs: IO[Map[String, MIDIOutput]] = IO {
        access
          .outputs
          .toMap
          .view
          .mapValues(MIDIOutput.wrap)
          .toMap
      }

    }

}

trait MIDIOutput {
  def send(data: IArray[Int]): IO[Unit]
  def clear(): IO[Unit]
}

object MIDIOutput {

  private[ext] def wrap(output: natives.MIDIOutput): MIDIOutput =
    new MIDIOutput {

      def send(data: IArray[Int]): IO[Unit] = IO {
        output.send(data.toJSArray)
      }

      def clear(): IO[Unit] = IO {
        output.clear()
      }

    }

}

object natives {

  @js.native
  @JSGlobal
  class MIDIAccess extends js.Any {
    def outputs: MIDIOutputMap = js.native
  }

  @js.native
  @JSGlobal
  class MIDIOutputMap extends js.Map[String, MIDIOutput] {}

  @js.native
  @JSGlobal
  class MIDIOutput extends js.Any {
    def send(data: scala.scalajs.js.Array[Int]): Unit = js.native
    def clear(): Unit = js.native
  }

}

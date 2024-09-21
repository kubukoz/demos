package fs2.dom.ext

import cats.effect.IO
import cats.effect.kernel.Async
import cats.effect.kernel.Resource
import cats.effect.kernel.Sync
import cats.effect.std.Dispatcher
import fs2.dom.*
import org.scalajs.dom
import org.scalajs.dom.HTMLDocument
import org.scalajs.dom.RTCIceCandidate
import org.scalajs.dom.RTCPeerConnectionIceEvent
import org.scalajs.dom.RTCSessionDescription

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

trait RTCPeerConnection[F[_]] {
  def createDataChannel(label: String, dataChannelDict: dom.RTCDataChannelInit)
    : Resource[F, RTCDataChannel[F]]

  def onIceCandidate(f: dom.RTCPeerConnectionIceEvent => F[Unit]): F[Unit]
  def onDataChannel(f: RTCDataChannel[F] => F[Unit]): F[Unit]

  def setLocalDescription(description: dom.RTCSessionDescription): F[Unit]
  def setRemoteDescription(description: dom.RTCSessionDescription): F[Unit]
  def createAnswer: F[dom.RTCSessionDescription]
  def createOffer: F[dom.RTCSessionDescription]
  def addIceCandidate(candidate: dom.RTCIceCandidate): F[Unit]

}

object RTCPeerConnection {

  def apply[F[_]: Async]: Resource[F, RTCPeerConnection[F]] = Dispatcher.parallel[F].flatMap {
    dispatcher =>
      Resource
        .make(
          Sync[F].delay(new dom.RTCPeerConnection())
        )(conn => Sync[F].delay(conn.close()))
        .map { pc =>
          new RTCPeerConnection[F] {
            def createDataChannel(label: String, dataChannelDict: dom.RTCDataChannelInit)
              : Resource[F, RTCDataChannel[F]] = Resource
              .make(Sync[F].delay(pc.createDataChannel(label, dataChannelDict)))(dc =>
                Sync[F].delay(dc.close())
              )
              .map(RTCDataChannel.lift(_, dispatcher))

            def onIceCandidate(f: RTCPeerConnectionIceEvent => F[Unit]): F[Unit] = Sync[F].delay {
              pc.onicecandidate = e => dispatcher.unsafeRunAndForget(f(e))
            }

            def onDataChannel(f: RTCDataChannel[F] => F[Unit]): F[Unit] = Sync[F].delay {
              pc.ondatachannel =
                e => dispatcher.unsafeRunAndForget(f(RTCDataChannel.lift(e.channel, dispatcher)))
            }

            def addIceCandidate(candidate: RTCIceCandidate): F[Unit] = Async[F].fromPromise(
              Sync[F].delay(pc.addIceCandidate(candidate))
            )

            def createAnswer: F[RTCSessionDescription] = Async[F].fromPromise(
              Sync[F].delay(pc.createAnswer())
            )

            def createOffer: F[RTCSessionDescription] = Async[F].fromPromise(
              Sync[F].delay(pc.createOffer())
            )

            def setLocalDescription(description: RTCSessionDescription): F[Unit] = Async[F]
              .fromPromise(
                Sync[F].delay(pc.setLocalDescription(description))
              )

            def setRemoteDescription(description: RTCSessionDescription): F[Unit] = Async[F]
              .fromPromise(
                Sync[F].delay(pc.setRemoteDescription(description))
              )

          }
        }
  }

}

trait RTCDataChannel[F[_]] {
  def send(data: String): F[Unit]
  def onOpen(f: F[Unit]): F[Unit]
  def onMessage(f: dom.MessageEvent => F[Unit]): F[Unit]
}

object RTCDataChannel {

  private[ext] def lift[F[_]: Sync](dc: dom.RTCDataChannel, dispatcher: Dispatcher[F])
    : RTCDataChannel[F] =
    new RTCDataChannel[F] {
      def send(data: String): F[Unit] = Sync[F].delay(dc.send(data))
      def onOpen(f: F[Unit]): F[Unit] = Sync[F].delay {
        dc.onopen = _ => dispatcher.unsafeRunAndForget(f)
      }
      def onMessage(f: dom.MessageEvent => F[Unit]): F[Unit] = Sync[F].delay {
        dc.onmessage = e => dispatcher.unsafeRunAndForget(f(e))
      }
    }

}

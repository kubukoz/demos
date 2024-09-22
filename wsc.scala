import calico.*
import calico.html.io.*
import calico.html.io.given
import cats.ApplicativeThrow
import cats.effect.Concurrent
import cats.effect.IO
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource
import cats.effect.std.Dispatcher
import cats.effect.std.Queue
import cats.syntax.all.*
import fs2.Chunk
import fs2.concurrent.SignallingRef
import fs2.dom.HtmlElement
import fs2.dom.ext.RTCDataChannel
import fs2.dom.ext.RTCPeerConnection
import io.circe.Codec
import io.circe.Decoder
import io.circe.Json
import io.circe.scalajs as cjs
import io.circe.syntax.*
import org.http4s.client.websocket.WSConnectionHighLevel
import org.http4s.client.websocket.WSFrame.Binary
import org.http4s.client.websocket.WSFrame.Text
import org.http4s.client.websocket.WSRequest
import org.http4s.dom.WebSocketClient
import org.http4s.implicits.*
import org.scalajs.dom

import scala.concurrent.duration.*
import cats.Functor
import scala.scalajs.js.JSON
import cats.Apply
import cats.FlatMap

def codecViaJsAny[A <: scalajs.js.Any]: Codec[A] = Codec.from(
  Decoder[Json].map(cjs.convertJsonToJs(_)).map(_.asInstanceOf[A]),
  cjs.convertJsToJson(_).toTry.get,
)

given Codec[dom.RTCSessionDescription] = codecViaJsAny

given Codec[dom.RTCIceCandidate] = codecViaJsAny

enum Message derives Codec.AsObject {
  case Offer(offer: dom.RTCSessionDescription)
  case Answer(answer: dom.RTCSessionDescription)
  case Candidate(candidate: dom.RTCIceCandidate)

  override def toString(): String =
    this match {
      case Answer(answer) => s"Answer(${JSON.stringify(answer)})"
      case Offer(offer)   => s"Offer(${JSON.stringify(offer)})"
      case Candidate(c)   => s"Candidate(${JSON.stringify(c)})"
    }

}

object wsc extends IOWebApp {

  def render: Resource[IO, HtmlElement[IO]] =
    for {
      ws <- WebSocketClient[IO].connectHighLevel(
        WSRequest(uri"ws://localhost:8080")
      )
      listenerRef <- IO.ref((s: String) => IO.println("noop: " + s)).toResource
      messages <- SignallingRef[IO].of(List.empty[String]).toResource

      localQ <- Queue.unbounded[IO, Chunk[Message]].toResource
      remoteQ <- Queue.unbounded[IO, Chunk[Message]].toResource

      sendChannel = RTCSignalling.logged("sendChannel", RTCSignalling.fromQueues(localQ, remoteQ))
      receiveChannel = RTCSignalling.logged(
        "receiveChannel",
        RTCSignalling.fromQueues(remoteQ, localQ),
      )
      _ <-
        setupConnection(
          listenerRef,
          receiveChannel,
          onReceive = msg => messages.update(_.prepended("receiveChannel: " + msg)),
          wait = 0.seconds,
        ).useForever.background
      _ <-
        setupConnection(
          listenerRef,
          sendChannel,
          onReceive = msg => messages.update(_.prepended("sendChannel: " + msg)),
          wait = 2.seconds,
        ).useForever.background
      messageRef <- SignallingRef[IO].of("").toResource
      view <- div(
        form(
          input.withSelf { self =>
            (
              placeholder := "Your message",
              value <-- messageRef,
              onChange --> (_.foreach(_ => self.value.get.flatMap(messageRef.set))),
            )
          },
          button(`type` := "submit", "send"),
          onSubmit --> (_.foreach(e =>
            e.preventDefault *> listenerRef.get.ap(messageRef.get).flatten *> messageRef.set("")
          )),
        ),
        ul(
          children <-- messages
            .map(_.toList)
            .map(_.map(li(_)))
        ),
      )

    } yield view

  def setupConnection(
    listenerRef: Ref[IO, String => IO[Unit]],
    signalling: RTCSignalling[IO, Message],
    onReceive: String => IO[Unit],
    wait: FiniteDuration,
  ) =
    for {
      peerConnection <- RTCPeerConnection[IO]
      sendChannel <- peerConnection.createDataChannel("chat", new dom.RTCDataChannelInit {})
      receiveChannel <- IO.deferred[RTCDataChannel[IO]].toResource
      _ <- peerConnection.onDataChannel(receiveChannel.complete(_).void).toResource
      _ <-
        listenerRef.update { old => msg =>
          old(msg) *>
            RTCDataChannel
              .fromDeferred(receiveChannel)
              .send(msg)
        }.toResource
      _ <-
        sendChannel.onOpen {
          IO.println("Data channel is open")
        }.toResource
      _ <-
        sendChannel.onMessage { event =>
          IO(dom.console.log("Data channel received message", event)) *>
            onReceive(event.data.toString())
        }.toResource
      _ <-
        peerConnection.onIceCandidate { event =>
          // so much for null safety in Scala
          IO.whenA(event.candidate != null) {
            signalling.send(Message.Candidate(event.candidate))
          }
        }.toResource

      dispatcher <- Dispatcher.parallel[IO]

      _ <-
        signalling
          .receiveStream
          .foreach {
            // todo: possible race condition between offer/answer (webrtc conflict)
            // should apply the perfect negotiation pattern: https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Perfect_negotiation
            case Message.Offer(offer) =>
              peerConnection.setRemoteDescription(offer) *>
                peerConnection
                  .createAnswer
                  .flatMap { answer =>
                    peerConnection.setLocalDescription(answer) *>
                      signalling.send(Message.Answer(answer))
                  }

            case Message.Answer(answer)       => peerConnection.setRemoteDescription(answer)
            case Message.Candidate(candidate) => peerConnection.addIceCandidate(candidate)
          }
          .compile
          .drain
          .both {
            IO.println("I guess we waitin now") *>
              IO.sleep(wait) *>
              IO.println("sendin'") *>
              peerConnection
                .createOffer
                .flatMap { offer =>
                  peerConnection.setLocalDescription(offer) *>
                    IO.println("yo?") *>
                    signalling.send(Message.Offer(offer))
                }
          }
          .void
          .background
    } yield ()

}

trait RTCSignalling[F[_], Msg] {
  def send(msg: Msg): F[Unit]
  def receiveStream: fs2.Stream[F, Msg]
}

object RTCSignalling {

  def fromWebSocket[F[_]: ApplicativeThrow, A: Codec](ws: WSConnectionHighLevel[F])
    : RTCSignalling[F, A] =
    new RTCSignalling[F, A] {
      def send(msg: A): F[Unit] = ws.sendText(msg.asJson.noSpaces)
      def receiveStream: fs2.Stream[F, A] = ws
        .receiveStream
        .evalMap {
          case Text(data, _) => io.circe.parser.decode[A](data).liftTo[F]
          case Binary(_, _)  => (new Throwable("Binary messages not supported")).raiseError
        }
    }

  def fromQueues[F[_]: Functor, Msg](from: Queue[F, Chunk[Msg]], to: Queue[F, Chunk[Msg]])
    : RTCSignalling[F, Msg] =
    new RTCSignalling[F, Msg] {
      def send(msg: Msg): F[Unit] = to.offer(Chunk.singleton(msg))
      def receiveStream: fs2.Stream[F, Msg] = fs2.Stream.fromQueueUnterminatedChunk(from)
    }

  def logged[F[_]: cats.effect.std.Console: FlatMap, Msg](
    label: String,
    underlying: RTCSignalling[F, Msg],
  ): RTCSignalling[F, Msg] =
    new RTCSignalling[F, Msg] {
      def send(msg: Msg): F[Unit] =
        cats.effect.std.Console[F].println(s"$label sending: $msg") *>
          underlying.send(msg)

      def receiveStream: fs2.Stream[F, Msg] = underlying
        .receiveStream
        .debug(s"$label receiving: " + _)
    }

}

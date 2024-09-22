import calico.*
import calico.html.io.*
import calico.html.io.given
import cats.ApplicativeThrow
import cats.effect.IO
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource
import cats.effect.std.Dispatcher
import cats.syntax.all.*
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
import org.scalajs.dom.RTCIceCandidate

import scala.concurrent.duration.*

def codecViaJsAny[A <: scalajs.js.Any]: Codec[A] = Codec.from(
  Decoder[Json].map(cjs.convertJsonToJs(_)).map(_.asInstanceOf[A]),
  cjs.convertJsToJson(_).toTry.get,
)

given Codec[dom.RTCSessionDescription] = codecViaJsAny

given Codec[dom.RTCIceCandidate] = codecViaJsAny

enum Message derives Codec.AsObject {
  case Offer(offer: dom.RTCSessionDescription)
  case Answer(answer: dom.RTCSessionDescription)
  case Candidate(candidate: RTCIceCandidate)
}

object wsc extends IOWebApp {

  def render: Resource[IO, HtmlElement[IO]] =
    for {
      ws <- WebSocketClient[IO].connectHighLevel(
        WSRequest(uri"ws://localhost:8080")
      )
      listenerRef <- IO.ref((s: String) => IO.println("noop: " + s)).toResource
      messages <- SignallingRef[IO].of(List.empty[String]).toResource
      _ <-
        setupConnection(
          listenerRef,
          WSChannel.fromWebSocket(ws),
          onReceive = msg => messages.update(_.prepended(msg)),
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
    // ws: WSConnectionHighLevel[IO],
    channel: WSChannel[IO, Message],
    onReceive: String => IO[Unit],
  ) =
    for {
      peerConnection <- RTCPeerConnection[IO]
      dataChannel <- peerConnection.createDataChannel("chat", new dom.RTCDataChannelInit {})
      remoteDataChannelRef <- IO.deferred[RTCDataChannel[IO]].toResource
      _ <- peerConnection.onDataChannel(remoteDataChannelRef.complete(_).void).toResource
      _ <-
        listenerRef
          .set(
            RTCDataChannel
              .suspend(
                remoteDataChannelRef
                  .tryGet
                  .flatMap(_.liftTo[IO](new Throwable("remote data channel not available")))
              )
              .send
          )
          .toResource
      _ <-
        dataChannel.onOpen {
          IO.println("Data channel is open")
        }.toResource
      _ <-
        dataChannel.onMessage { event =>
          IO(dom.console.log("Data channel received message", event)) *>
            onReceive(event.data.toString())
        }.toResource
      _ <-
        peerConnection
          .onIceCandidate(event =>
            IO.whenA(event.candidate != null) {
              channel.send(
                Message.Candidate(event.candidate)
              )
            }
          )
          .toResource
      dispatcher <- Dispatcher.parallel[IO]

      _ <- IO.println("I guess we receivin now").toResource
      _ <-
        channel
          .receiveStream
          .debug("received event " + _)
          .foreach {
            case Message.Offer(offer) =>
              peerConnection.setRemoteDescription(offer) *>
                peerConnection
                  .createAnswer
                  .flatMap { answer =>
                    peerConnection.setLocalDescription(answer) *>
                      channel.send(Message.Answer(answer))
                  }

            case Message.Answer(answer)       => peerConnection.setRemoteDescription(answer)
            case Message.Candidate(candidate) => peerConnection.addIceCandidate(candidate)
          }
          .compile
          .drain
          .both {
            IO.println("I guess we waitin now") *>
              IO.sleep(2.seconds) *>
              IO.println("sendin'") *>
              peerConnection
                .createOffer
                .flatMap { offer =>
                  peerConnection.setLocalDescription(offer) *>
                    IO.println("yo?") *>
                    channel.send(Message.Offer(offer))
                }
          }
          .void
          .background
    } yield ()

}

trait WSChannel[F[_], Msg] {
  def send(msg: Msg): F[Unit]
  def receiveStream: fs2.Stream[F, Msg]
}

object WSChannel {

  def fromWebSocket[F[_]: ApplicativeThrow, A: Codec](ws: WSConnectionHighLevel[F])
    : WSChannel[F, A] =
    new WSChannel[F, A] {
      def send(msg: A): F[Unit] = ws.sendText(msg.asJson.noSpaces)
      def receiveStream: fs2.Stream[F, A] = ws
        .receiveStream
        .evalMap {
          case Text(data, _) => io.circe.parser.decode[A](data).liftTo[F]
          case Binary(_, _)  => (new Throwable("Binary messages not supported")).raiseError
        }
    }

}

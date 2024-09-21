import calico.*
import calico.html.io.*
import calico.html.io.given
import cats.data.Chain
import cats.effect.IO
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource
import cats.effect.std.Dispatcher
import cats.syntax.all.*
import fs2.concurrent.SignallingRef
import fs2.dom.HtmlElement
import fs2.dom.ext.RTCPeerConnection
import io.circe.Codec
import io.circe.Decoder
import io.circe.Json
import io.circe.scalajs as cjs
import io.circe.syntax.*
import org.http4s.client.websocket.WSConnectionHighLevel
import org.http4s.client.websocket.WSFrame.Text
import org.http4s.client.websocket.WSRequest
import org.http4s.dom.WebSocketClient
import org.http4s.implicits.*
import org.scalajs.dom
import org.scalajs.dom.MessageEvent
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

  def asMessage: Message = this
}

object wsc extends IOWebApp {

  def render: Resource[IO, HtmlElement[IO]] =
    for {
      ws <- WebSocketClient[IO].connectHighLevel(
        WSRequest(uri"ws://localhost:8080")
      )
      listenerRef <- IO.ref((s: String) => IO.println("noop: " + s)).toResource
      messages <- SignallingRef[IO].of(Chain.empty[String]).toResource
      _ <-
        setupConnection(
          listenerRef,
          ws,
          onWebRtcMessage =
            event =>
              IO(dom.console.log("Data channel received message", event)) *>
                messages.update(_.append(event.data.toString())),
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
            e.preventDefault *> listenerRef.get.ap(messageRef.get).flatten
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
    ws: WSConnectionHighLevel[IO],
    onWebRtcMessage: MessageEvent => IO[Unit],
  ) =
    for {
      peerConnection <- RTCPeerConnection[IO]
      dataChannel <- peerConnection.createDataChannel("chat", new dom.RTCDataChannelInit {})
      dataChannelRef <- IO.ref(dataChannel).toResource
      _ <-
        dataChannel.onOpen {
          IO.println("Data channel is open") *>
            listenerRef.set(msg => dataChannelRef.get.flatMap(_.send(msg)))
        }.toResource
      _ <-
        dataChannel
          .onMessage(onWebRtcMessage)
          .toResource
      _ <-
        peerConnection
          .onIceCandidate(event =>
            ws.sendText(
              Message.Candidate(event.candidate).asMessage.asJson.noSpaces
            )
          )
          .toResource
      dispatcher <- Dispatcher.parallel[IO]
      _ <-
        peerConnection
          .onDataChannel(dataChannelRef.set)
          .toResource
      _ <- IO.println("I guess we receivin now").toResource
      _ <-
        ws
          .receiveStream
          .debug("received event " + _)
          .foreach { event =>
            event match {
              case Text(data, _) =>
                val message = io.circe.parser.decode[Message](data).toTry.get

                message match {
                  case Message.Offer(offer) =>
                    peerConnection.setRemoteDescription(offer) *>
                      peerConnection
                        .createAnswer
                        .flatMap { answer =>
                          peerConnection.setLocalDescription(answer) *>
                            ws.sendText(Message.Answer(answer).asMessage.asJson.noSpaces)
                        }

                  case Message.Answer(answer)       => peerConnection.setRemoteDescription(answer)
                  case Message.Candidate(candidate) => peerConnection.addIceCandidate(candidate)
                }
            }
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
                    ws.sendText(Message.Offer(offer).asMessage.asJson.noSpaces)
                }
          }
          .debug()
          .void
          .background
    } yield ()

}

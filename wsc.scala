import calico.*
import calico.html.io.*
import calico.html.io.given
import calico.unsafe.given
import cats.effect.IO
import cats.effect.kernel.Resource
import cats.syntax.all.*
import fs2.concurrent.SignallingRef
import fs2.dom.HtmlElement
import org.http4s.client.websocket.WSFrame.Text
import org.http4s.client.websocket.WSRequest
import org.http4s.dom.WebSocketClient
import org.http4s.implicits.*
import org.scalajs.dom
import org.scalajs.dom.RTCDataChannelInit
import org.scalajs.dom.RTCPeerConnection
import org.scalajs.dom.RTCSessionDescription

import io.circe.syntax.*
import scala.concurrent.duration.*
import cats.effect.kernel.Ref
import org.http4s.client.websocket.WSConnectionHighLevel
import io.circe.Codec
import io.circe.scalajs as cjs
import org.scalajs.dom.RTCIceCandidate
import io.circe.Decoder
import io.circe.Json

def codecViaJsAny[A <: scalajs.js.Any]: Codec[A] = Codec.from(
  Decoder[Json].map(cjs.convertJsonToJs(_)).map(_.asInstanceOf[A]),
  cjs.convertJsToJson(_).toTry.get,
)

given Codec[RTCSessionDescription] = codecViaJsAny

given Codec[RTCIceCandidate] = codecViaJsAny

enum Message derives Codec.AsObject {
  case Offer(offer: RTCSessionDescription)
  case Answer(answer: RTCSessionDescription)
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
      _ <- setupConnection(listenerRef, ws).toResource.flatMap(_.useForever.background)
      messageRef <- SignallingRef[IO].of("").toResource
      view <- form(
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
      )

    } yield view

  def setupConnection(listenerRef: Ref[IO, String => IO[Unit]], ws: WSConnectionHighLevel[IO]) =
    for {
      peerConnection <- IO(
        new RTCPeerConnection()
      )
      dataChannel <- IO(peerConnection.createDataChannel("chat", new RTCDataChannelInit {}))
      dataChannelRef <- IO.ref(dataChannel)
      _ <- IO {
        dataChannel.onopen =
          _ =>
            {
              IO.println("Data channel is open") *>
                listenerRef.set(msg => dataChannelRef.get.flatMap(dc => IO(dc.send(msg))))
            }.unsafeRunAndForget()
      }
      _ <- IO {
        dataChannel.onmessage = event => dom.console.log("Data channel received message", event)
      }
      _ <- IO {
        peerConnection.onicecandidate =
          event =>
            ws.sendText(
              Message.Candidate(event.candidate).asMessage.asJson.noSpaces
            ).unsafeRunAndForget()
      }
      _ <- IO {
        peerConnection.ondatachannel =
          event => dataChannelRef.set(event.channel).unsafeRunAndForget()
      }
      _ <- IO.println("I guess we receivin now")
    } yield ws
      .receiveStream
      .debug("received event " + _)
      .foreach { event =>
        event match {
          case Text(data, _) =>
            val message = io.circe.parser.decode[Message](data).toTry.get

            message match {
              case Message.Offer(offer) =>
                IO.fromPromise(
                  IO(
                    peerConnection.setRemoteDescription(offer)
                  )
                ) *>
                  IO.fromPromise(IO(peerConnection.createAnswer()))
                    .flatMap { answer =>
                      IO.fromPromise(IO(peerConnection.setLocalDescription(answer))) *>
                        ws.sendText(Message.Answer(answer).asMessage.asJson.noSpaces)
                    }

              case Message.Answer(answer) =>
                IO.fromPromise(
                  IO(
                    peerConnection.setRemoteDescription(answer)
                  )
                )
              case Message.Candidate(candidate) =>
                IO.fromPromise(
                  IO(peerConnection.addIceCandidate(candidate))
                )
            }
        }
      }
      .compile
      .drain
      .both {
        IO.println("I guess we waitin now") *>
          IO.sleep(1.second) *>
          IO.println("sendin'") *>
          IO
            .fromPromise(IO(peerConnection.createOffer()))
            .flatMap { offer =>
              IO.fromPromise(IO(peerConnection.setLocalDescription(offer))) *>
                IO.println("yo?") *>
                ws.sendText(Message.Offer(offer).asMessage.asJson.noSpaces)
            }
      }
      .void
      .background

}

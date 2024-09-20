//> using dep com.armanbilge::calico::0.2.2
//> using platform js
//> using jsModuleKind common
//> using option -no-indent
//> using option -Wunused:all
import calico.IOWebApp
import calico.html.io.*
import calico.html.io.given
import cats.effect.IO
import cats.effect.kernel.Ref
import cats.effect.kernel.Resource
import cats.syntax.all.*
import fs2.concurrent.SignallingRef
import fs2.dom.*
import fs2.dom.ext.FS2DomExtensions.*

import scala.concurrent.duration.*

object SeqApp extends IOWebApp {

  def render: Resource[IO, HtmlElement[IO]] = {
    for {
      currentNoteRef <- SignallingRef[IO].of(0).toResource
      holdAtRef <- SignallingRef[IO].of(none[Int]).toResource
      channelRef <- SignallingRef[IO].of(2).toResource
      _ <- Player.run(
        tracks = data.tracks,
        midiChannel = channelRef,
        holdAtRef = holdAtRef,
        currentNoteRef = currentNoteRef,
      )
      _ <-
        window
          .document
          .onKeyDown
          .filter(_.key == "h")
          .as(true)
          .merge(
            window
              .document
              .onKeyUp
              .filter(_.key == "h")
              .as(false)
          )
          .changes
          .evalMap {
            case true  => currentNoteRef.get.map(_.some)
            case false => none[Int].pure[IO]
          }
          .evalMap(holdAtRef.set)
          .compile
          .drain
          .background
    } yield div(
      ChannelSelector.show(channelRef),
      div("current note: ", currentNoteRef.map(_.toString())),
      div("hold: ", holdAtRef.map(_.toString())),
    )

  }.flatten

}

object ChannelSelector {

  def show(midiChannel: SignallingRef[IO, Int]): Resource[IO, HtmlDivElement[IO]] = div(
    "MIDI Channel:",
    select.withSelf { self =>
      (
        idAttr := "midichannel-select",
        (0 to 15).map { i =>
          option(i.toString)
        }.toList,
        value <-- midiChannel.changes.map(_.toString),
        onChange --> {
          _.evalMap(_ =>
            self
              .value
              .get
              /* wishful thinking */
              .map(_.toInt)
              .flatMap(midiChannel.set)
          ).drain
        },
      )
    },
  )

}

object Player {

  def run(
    tracks: List[List[Playable]],
    midiChannel: Ref[IO, Int],
    holdAtRef: Ref[IO, Option[Int]],
    currentNoteRef: Ref[IO, Int],
  ): Resource[IO, Unit] = Window[IO]
    .navigator
    .requestMIDIAccess
    .flatMap(_.outputs)
    .map(_.values.head)
    .toResource
    .flatMap { output =>
      val period = 1.minute / 120 / 4
      fs2
        .Stream
        .fixedRateStartImmediately[IO](period)
        .zipWithIndex
        .map(_._2)
        .map(_.%(tracks.head.size).toInt)
        .evalTap(currentNoteRef.set)
        .foreach { noteIndex =>
          (midiChannel.get, holdAtRef.get)
            .flatMapN { (channel, holdAt) =>
              // we play notes of each track in parallel
              // because their Off messages need to be sent at roughly the same time
              // and we can't wait for one note to finish before starting the next
              tracks
                .parTraverse_ { track =>
                  track(holdAt.getOrElse(noteIndex)).match {
                    case Playable.Play(noteId, velocity) =>
                      output.send(IArray(0x90 + channel, noteId, velocity)) *>
                        IO.sleep(period / 4) *>
                        output.send(IArray(0x80 + channel, noteId, 0))
                    case Playable.Rest => IO.unit
                  }
                }
            }
        }
        .compile
        .drain
        .background
        .void
    }

}

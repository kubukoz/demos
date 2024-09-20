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
      pauseRef <- SignallingRef[IO].of(false).toResource
      _ <- Player.run(
        tracks = data.tracks,
        midiChannel = channelRef,
        holdAtRef = holdAtRef,
        currentNoteRef = currentNoteRef,
        pauseRef = pauseRef,
      )
      _ <-
        KeyStatus
          .forKey("h")
          .changes
          .evalMap {
            // we inc by one because we want to hold the next note
            case true  => currentNoteRef.get.map(_.+(1).%(data.tracks.head.length)).map(_.some)
            case false => none[Int].pure[IO]
          }
          .evalMap(holdAtRef.set)
          .compile
          .drain
          .background
      _ <-
        KeyStatus
          .forKey(" ")
          .changes
          .filter(identity)
          .evalMap(_ => pauseRef.update(!_))
          .compile
          .drain
          .background
    } yield div(
      ChannelSelector.show(channelRef),
      div("current note: ", currentNoteRef.map(_.toString())),
      div("hold: ", holdAtRef.map(_.toString())),
      div(
        pauseRef.map { p =>
          if p
          then "paused"
          else
            "playing"
        }
      ),
    )

  }.flatten

}

object KeyStatus {

  def forKey(key: String) = Window[IO]
    .document
    .onKeyDown
    .filter(_.key == key)
    .as(true)
    .merge(
      Window[IO]
        .document
        .onKeyUp
        .filter(_.key == key)
        .as(false)
    )

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
    pauseRef: SignallingRef[IO, Boolean],
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
        .zipRight(fs2.Stream.emits(tracks.head.indices).repeat)
        .pauseWhen(pauseRef)
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
                      IO.uncancelable { poll =>
                        // playing is cancelable, stopping isn't.
                        // (browsers ignore this anyway though)
                        poll(output.send(MIDI.NoteOn(channel, noteId, velocity).toArray)) *>
                          IO.sleep(period / 4) *>
                          output.send(MIDI.NoteOff(channel, noteId, 0).toArray)
                      }
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

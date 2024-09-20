//> using dep com.armanbilge::calico::0.2.2
//> using platform js
//> using jsModuleKind common
import calico.IOWebApp
import fs2.dom.HtmlElement
import cats.effect.IO
import cats.effect.kernel.Resource
import calico.html.io.given
import calico.html.io.*
import calico.syntax.*
import scala.concurrent.duration.*
import fs2.dom.*
import org.scalajs.dom.EventTarget
import cats.syntax.all.*
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal
import fs2.concurrent.SignallingRef

import fs2.dom.ext.FS2DomExtensions.*

object SeqApp extends IOWebApp {

  def render: Resource[IO, HtmlElement[IO]] =
    SignallingRef[IO].of(0).toResource.flatMap { channelRef =>
      div(
        "MIDI Channel:",
        select.withSelf { self =>
          (
            idAttr := "midichannel-select",
            (0 to 15).map { i =>
              option(i.toString)
            }.toList,
            value <-- channelRef.changes.map(_.toString),
            onChange --> {
              _.evalMap(_ =>
                self
                  .value
                  .get
                  /* wishful thinking */
                  .map(_.toInt)
                  .flatMap(channelRef.set)
              ).drain
            },
          )
        },
      )
    } <&
      window
        .document
        .onKeyDown
        .map(_.key)
        .debug()
        .drain
        .compile
        .drain
        .background <&
      window
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
            .map(_.%(data.tracks.head.size).toInt)
            .foreach { noteIndex =>
              data.tracks.traverse_ { track =>
                track(noteIndex) match {
                  case Playable.Play(noteId, velocity) =>
                    output.send(IArray(0x90 + 0x02, noteId, velocity)) *>
                      IO.sleep(period / 4) *>
                      output.send(IArray(0x80 + 0x02, noteId, 0))
                  case Playable.Rest => IO.unit
                }
              }
            }
            .compile
            .drain
            .background
        }

  enum Playable {
    case Play(noteId: Int, velocity: Int)
    case Rest

    def atVelocity(velocity: Int): Playable = mapPlay(_.copy(velocity = velocity))
    def +(semitones: Int): Playable = mapPlay(p => p.copy(noteId = p.noteId + semitones))

    def mapPlay(f: Play => Play): Playable =
      this match {
        case p: Play => f(p)
        case _       => this
      }

  }

  // max velocity by default

  def play(noteId: Int): Playable.Play = Playable.Play(noteId, 0x7f)

  object data {

    val C3 = play(48)
    val C4 = play(60)
    val C5 = play(72)
    val REST = Playable.Rest

    val plucks = List(
      C4,
      REST,
      REST,
      REST,
      //
      C4 + 3,
      REST,
      REST,
      C4 + 2,
      //
      REST,
      REST,
      C4,
      REST,
      //
      C4 + 3,
      REST,
      C4 + 2,
      REST,
    ).map(_.atVelocity(0x7f * 3 / 4))

    val melody = List(
      C5 + 7,
      REST,
      REST,
      REST,
      //
      C5 + 3,
      REST,
      C5 + 5,
      REST,
      //
      C5,
      REST,
      REST,
      REST,
      //
      REST,
      REST,
      REST,
      REST,
    ).map(_.atVelocity(0x7f))

    val bass = List(
      C3,
      REST,
      REST,
      REST,
      //
      C3,
      REST,
      REST,
      REST,
      //
      C3 + 7,
      REST,
      REST,
      REST,
      //
      C3 + 7,
      REST,
      REST,
      REST,
    ).map(_.atVelocity(0x7f))

    val tracks = List(plucks, melody, bass)
  }

}

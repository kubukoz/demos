import fs2.dom.ext.FS2DomExtensions.*

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

object Playable {

  // max velocity by default

  def play(noteId: Int): Playable.Play = Playable.Play(noteId, 0x7f)
}

package demo

import pdapi.all._
import pdapi.enumerations.PDButtons.kButtonA
import pdapi.enumerations.PDSystemEvent.kEventInit
import scalanative.unsafe._
import scalanative.unsigned._

import scala.util.Random
import pdapi.enumerations.PDButtons.kButtonB
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.CharBuffer
import scala.scalanative.unsigned.UInt
import scala.util.NotGiven

object Main {

  var x = 50
  var y = 50

  val VelocityX = 5
  val VelocityY = 2

  enum DirectionX {
    case Left, Right
  }

  enum DirectionY {
    case Up, Down
  }

  var dirX = DirectionX.Left
  var dirY = DirectionY.Down

  val LCD_COLUMNS = 400
  val LCD_ROWS = 240

  var w = 10.0f
  val h = 10.0f

  var state = true

  extension [A](
    inline ptr: Ptr[A]
  ) {

    inline def !(
      using inline tag: Tag[A]
    ): A = !ptr

  }

  inline def zoned[T](f: Zone ?=> T)(using NotGiven[Zone]): T = Zone { implicit z =>
    f(
      using z
    )
  }

  @exported("foo") def foo(i: Int): Int =
    // val x = Math.pow(i, 2).toInt + 1
    // x + i * 2
    // 42
    // (1 to 100).sum
    // ("a" * i).map(_.toInt).sum
    (1 to i).sum

  class Foo(a: Int, b: Int) {
    def y = a + b
  }

  @exported("sn_event")
  def event(
    pd: Ptr[PlaydateAPI],
    event: PDSystemEvent,
    arg: UInt,
  ): Int = {
    if (event == kEventInit)
      pd.!.display.!.setRefreshRate(50.0f)
    0
  }

  @extern
  @name("pd_system_logToConsole")
  def printToConsole(msg: CString): Unit = extern

  @extern
  def pd_log_error(msg: CString): Unit = extern

  @exported("sn_update")
  def update(
    pd: Ptr[PlaydateAPI]
  ): Int = {

    val current = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val pressed = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val released = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]

    pd.!.system.!.getButtonState(current, pressed, released)

    if (pressed.!.is(kButtonA)) {
      printToConsole(c"Button A is pressed")

      if (Random.nextInt(10) > 8)
        printToConsole(c"RANDOM EVENT ON BUTTONS!")

      state = !state
    }

    // if (pressed.!.is(kButtonB)) {
    // }

    if (dirX == DirectionX.Left) {
      x -= VelocityX
      if (x < 0) {
        dirX = DirectionX.Right
      }
    } else {
      x += VelocityX
      if (x > LCD_COLUMNS - w) {
        dirX = DirectionX.Left
      }
    }
    x = 0 max ((LCD_COLUMNS - w.toInt) min x.toInt)

    if (dirY == DirectionY.Up) {
      y -= VelocityY
      if (y < 0) {
        dirY = DirectionY.Down
      }
    } else {
      y += VelocityY
      if (y > (LCD_ROWS - h)) {
        dirY = DirectionY.Up
      }
    }
    y = 0 max ((LCD_ROWS - h.toInt) min y.toInt)

    val crankDelta = pd.!.system.!.getCrankChange()

    if (crankDelta != 0) {
      w += crankDelta.toInt
    }

    // Not using colors from generated bindings because there's something wrong about their types
    val kColorWhite = 1.toUInt
    val kColorBlack = 0.toUInt

    // pd.!
    //   .graphics
    //   .!
    //   .clear(
    //     kColorWhite
    //   )

    // zoned {
    //   printToConsole(toCString(s"kColorWhite is ackshually ${LCDSolidColor.kColorWhite}"))
    // }

    pd.!.system.!.drawFPS(0, 0)

    if (state)
      pd.!
        .graphics
        .!
        .fillRect(
          x,
          y,
          w.toInt,
          h.toInt,
          kColorBlack,
        )
    else {
      // pd.!
      //   .graphics
      //   .!
      //   .drawRect(
      //     x,
      //     y,
      //     w.toInt,
      //     h.toInt,
      //     kColorBlack,
      //   )
    }

    if (!state) {
      pd.!
        .graphics
        .!
        .fillRect(
          0,
          LCD_ROWS - 50,
          50,
          50,
          kColorBlack,
        )
    }
    1
  }

}

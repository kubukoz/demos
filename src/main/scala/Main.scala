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
import pdapi.enumerations.PDSystemEvent.kEventResume

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
  ): Int = {
    if (event == kEventInit) {
      pd_log_error(c"this was an init event");
      // pd.!.display.!.setRefreshRate(50.0f)
      pd_display_setRefreshRate(50.0f)
    }

    0
  }

  @extern
  @name("pd_system_logToConsole")
  def printToConsole(msg: CString): Unit = extern

  @extern def pd_system_getButtonState(
    current: Ptr[PDButtons],
    pressed: Ptr[PDButtons],
    released: Ptr[PDButtons],
  ): Unit = extern

  @extern def pd_graphics_fillRect(
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    color: Int,
  ): Unit = extern

  @extern def pd_graphics_drawRect(
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    color: Int,
  ): Unit = extern

  @extern def pd_graphics_clear(
    color: Int
  ): Unit = extern

  @extern
  def pd_log_error(msg: CString): Unit = extern

  @extern def pd_system_getCrankChange(): Float = extern

  @extern def pd_system_drawFPS(
    x: Int,
    y: Int,
  ): Unit = extern

  @extern def pd_display_setRefreshRate(
    rate: Float
  ): Unit = extern

  @exported("sn_update")
  def update(
    pd: Ptr[PlaydateAPI]
  ): Int = {

    val current = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val pressed = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val released = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]

    // pd.!.system.!.getButtonState(current, pressed, released)
    pd_system_getButtonState(current, pressed, released)

    if (pressed.!.is(kButtonA)) {
      printToConsole(c"Button A is pressed")

      //   if (Random.nextInt(10) > 8)
      //     printToConsole(c"RANDOM EVENT ON BUTTONS!")

      state = !state
    }

    if (pressed.!.is(kButtonB)) {
      pd_log_error(c"Button B is pressed")
      // roughly 500k are enough to crash the game
      List.fill(100_000)(new Foo(1, 2))
      // val e = new Exception("aa")
      // e.printStackTrace()
    }

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

    val crankDelta = pd_system_getCrankChange()
    // val crankDelta = pd.!.system.!.getCrankChange()

    if (crankDelta != 0) {
      w += crankDelta.toInt
    }

    // Not using colors from generated bindings because there's something wrong about their types
    val kColorWhite = 1
    val kColorBlack = 0

    pd_graphics_clear(
      kColorWhite
    )

    // zoned {
    //   printToConsole(toCString(s"kColorWhite is ackshually ${LCDSolidColor.kColorWhite}"))
    // }

    pd_system_drawFPS(0, 0)

    if (state)
      pd_graphics_fillRect(
        x,
        y,
        w.toInt,
        h.toInt,
        kColorBlack,
      )
    else {
      pd_graphics_drawRect(
        x,
        y,
        w.toInt,
        h.toInt,
        kColorBlack,
      )
    }

    if (!state) {
      pd_graphics_fillRect
      /* pd.!
        .graphics
        .!
        .fillRect */ (
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

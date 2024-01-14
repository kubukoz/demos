package demo

import pdapi.all._
import pdapi.enumerations.PDButtons.kButtonA
import pdapi.enumerations.PDSystemEvent
import pdapi.enumerations.PDSystemEvent.kEventInit
import scalanative.unsafe._

import scala.scalanative.unsigned.UInt
import pdapi.aliases.signalDeallocFunc.toPtr
import scala.scalanative.unsigned.ULong
import scala.util.Random

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

  var w = 100.0f
  val h = 100.0f

  var state = false

  extension [A](
    inline ptr: Ptr[A]
  ) {

    inline def !(
      using inline tag: Tag[A]
    ): A = !ptr

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
      pd_display_setRefreshRate(50.0f)
    0
  }

  @extern
  def pd_system_logToConsole(msg: CString): Unit = extern

  @extern def pd_system_drawFPS(
    x: Int,
    y: Int,
  ): Unit = extern

  @extern def pd_system_getButtonState(
    current: Ptr[PDButtons],
    pressed: Ptr[PDButtons],
    released: Ptr[PDButtons],
  ): Unit = extern

  @extern def pd_graphics_clear(color: LCDColor): Unit = extern

  @extern def pd_graphics_drawRect(
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    // color: LCDColor,
  ): Unit = extern

  @extern def pd_graphics_fillRect(
    x: Int,
    y: Int,
    w: Int,
    h: Int,
    // color: LCDColor,
  ): Unit = extern

  @extern def pd_display_setRefreshRate(
    rate: Float
  ): Unit = extern

  @extern def pd_system_getCrankChange(): Float = extern

  @exported("sn_update")
  def update(
    pd: Ptr[PlaydateAPI]
  ): Int = {
    pd_system_drawFPS(0, 0)

    val current = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val pressed = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val released = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]

    pd_system_getButtonState(current, pressed, released)

    if (pressed.!.is(kButtonA)) {
      pd_system_logToConsole(c"Button A is pressed")

      if (Random.nextBoolean())
        pd_system_logToConsole(c"RANDOM EVENT ON BUTTONS!")

        // crashes for reasons unbeknownst to mankind
        // Zone { implicit zone =>
        //   pd_system_logToConsole(toCString("Button A is pressed"))
        // }

        state = !state
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

    if (crankDelta != 0) {
      w += crankDelta.toInt
    }

    pd_graphics_clear(
      LCDColor(uintptr_t(LCDSolidColor.kColorWhite.value))
    )

    if (state)
      pd_graphics_fillRect(
        x,
        y,
        w.toInt,
        h.toInt,
        // LCDColor(uintptr_t(LCDSolidColor.kColorBlack.value)),
      )
    else
      pd_graphics_drawRect(
        x,
        y,
        w.toInt,
        h.toInt,
        // LCDColor(uintptr_t(LCDSolidColor.kColorBlack.value)),
      )

    1
  }

}

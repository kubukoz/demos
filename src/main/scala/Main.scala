package demo

import pdapi.all._
import pdapi.enumerations.PDButtons.kButtonA
import pdapi.enumerations.PDSystemEvent
import pdapi.enumerations.PDSystemEvent.kEventInit
import scalanative.unsafe._

import scala.scalanative.unsigned.UInt
import pdapi.aliases.signalDeallocFunc.toPtr

object Main {

  // var x = 50
  // var y = 50

  // val VelocityX = 5
  // val VelocityY = 2

  // enum DirectionX {
  //   case Left, Right
  // }

  // enum DirectionY {
  //   case Up, Down
  // }

  // var dirX = DirectionX.Left
  // var dirY = DirectionY.Down

  // val LCD_COLUMNS = 400
  // val LCD_ROWS = 240

  // var w = 100.0f
  // val h = 100.0f

  // var state = false

  // extension [A](
  //   inline ptr: Ptr[A]
  // ) {

  //   inline def !(
  //     using inline tag: Tag[A]
  //   ): A = !ptr

  // }
  @exported("foo") def foo(i: Int): Int =
    // val x = Math.pow(i, 2).toInt + 1
    // x + i * 2
    // 42
    // (1 to 100).sum
    // ("a" * i).map(_.toInt).sum
    (1 to i).sum

  @exported("sn_event")
  def event(
    pd: Ptr[PlaydateAPI],
    event: PDSystemEvent,
    arg: UInt,
  ): Int = (!pd).system.toInt
  // val f: CFuncPtr1[Ptr[Byte], CInt] = update

  // val ptr: Ptr[PDCallbackFunction] = CFuncPtr.toPtr(f).asInstanceOf[Ptr[PDCallbackFunction]]

  // pd.!.system.!.logToConsole(c"Event!")
  // (!(!pd).system).logToConsole(c"Event!")
  // if (event == kEventInit)
  //   pd.!.system.!.setUpdateCallback(ptr, pd.asInstanceOf[Ptr[Byte]])
  // def update(
  //   arg: Ptr[Byte]
  // ): Int = {
  //   val pd = arg.asInstanceOf[Ptr[PlaydateAPI]]

  //   val current = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
  //   val pressed = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
  //   val released = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]

  //   pd.!.system.!.getButtonState(current, pressed, released)

  //   if (pressed.!.is(kButtonA)) {
  //     pd.!.system.!.logToConsole(c"Button A is pressed")
  //     state = !state
  //   }

  //   if (dirX == DirectionX.Left) {
  //     x -= VelocityX
  //     if (x < 0) {
  //       dirX = DirectionX.Right
  //     }
  //   } else {
  //     x += VelocityX
  //     if (x > LCD_COLUMNS - w) {
  //       dirX = DirectionX.Left
  //     }
  //   }
  //   x = 0 max ((LCD_COLUMNS - w.toInt) min x.toInt)

  //   if (dirY == DirectionY.Up) {
  //     y -= VelocityY
  //     if (y < 0) {
  //       dirY = DirectionY.Down
  //     }
  //   } else {
  //     y += VelocityY
  //     if (y > (LCD_ROWS - h)) {
  //       dirY = DirectionY.Up
  //     }
  //   }
  //   y = 0 max ((LCD_ROWS - h.toInt) min y.toInt)

  //   val crankDelta = pd.!.system.!.getCrankChange()

  //   if (crankDelta != 0) {
  //     w += crankDelta.toInt
  //   }

  //   pd.!
  //     .graphics
  //     .!
  //     .clear(
  //       LCDColor(uintptr_t(LCDSolidColor.kColorWhite.value))
  //     )

  //   if (state)
  //     pd.!
  //       .graphics
  //       .!
  //       .fillRect(
  //         x,
  //         y,
  //         w.toInt,
  //         h.toInt,
  //         LCDColor(uintptr_t(LCDSolidColor.kColorBlack.value)),
  //       )
  //   else
  //     pd.!
  //       .graphics
  //       .!
  //       .drawRect(
  //         x,
  //         y,
  //         w.toInt,
  //         h.toInt,
  //         LCDColor(uintptr_t(LCDSolidColor.kColorBlack.value)),
  //       )

  //   1
  // }

}

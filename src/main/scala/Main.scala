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
import scala.scalanative.unsafe._
import scala.scalanative.libc.string._
import scala.util.NotGiven
import pdapi.enumerations.PDSystemEvent.kEventResume
import pdapi.enumerations.PDStringEncoding.kUTF8Encoding

enum DirectionX {
  case Left, Right
}

enum DirectionY {
  case Up, Down
}

case class GameState(
  x: Float,
  y: Float,
  w: Float,
  h: Float,
  dirX: DirectionX,
  dirY: DirectionY,
  state: Boolean,
  boundsWidth: Int,
  boundsHeight: Int,
  crankDocked: Boolean,
)

object MainGame {

  def init(ctx: GameContext): GameState = GameState(
    x = 50,
    y = 50,
    w = 10,
    h = 10,
    dirX = DirectionX.Right,
    dirY = DirectionY.Down,
    state = true,
    boundsWidth = ctx.screen.width,
    boundsHeight = ctx.screen.height,
    crankDocked = ctx.crank.docked,
  )

  val VelocityX = 500
  val VelocityY = 100

  def update(ctx: GameContext): GameState => GameState = {
    val updateMode: GameState => GameState =
      state =>
        if (ctx.buttons.pressed.a)
          state.copy(state = !state.state)
        else
          state

    val updatePosition: GameState => GameState =
      state => {
        val x =
          if (state.dirX == DirectionX.Left)
            state.x - VelocityX * ctx.delta
          else
            state.x + VelocityX * ctx.delta

        val y =
          if (state.dirY == DirectionY.Up)
            state.y - VelocityY * ctx.delta
          else
            state.y + VelocityY * ctx.delta

        val dirX =
          if (x < 0)
            DirectionX.Right
          else if (x > ctx.screen.width - state.w)
            DirectionX.Left
          else
            state.dirX

        val dirY =
          if (y < 0)
            DirectionY.Down
          else if (y > ctx.screen.height - state.h)
            DirectionY.Up
          else
            state.dirY

        state.copy(
          x = 0f max ((ctx.screen.width - state.w) min x),
          y = 0f max ((ctx.screen.height - state.h) min y),
          dirX = dirX,
          dirY = dirY,
        )
      }

    val updateSize =
      (state: GameState) => {
        val crankDelta = ctx.crank.change

        state.copy(
          w = (state.w + crankDelta).toInt,
          h = (state.h + crankDelta).toInt,
        )
      }

    val updateCrank =
      (state: GameState) =>
        state.copy(
          crankDocked = ctx.crank.docked
        )

    Function
      .chain(
        Seq(
          updateMode,
          updatePosition,
          updateSize,
          updateCrank, {
            if (ctx.buttons.pressed.b)
              Zone.open().close()
            x => x
          },
        )
      )
  }

  def config: GameConfig = GameConfig(fps = 50)

  def render(state: GameState): Render = {
    import Render._

    val dot = Rect(
      x = state.x.toInt,
      y = state.y.toInt,
      w = state.w.toInt,
      h = state.h.toInt,
      color = Color.Black,
      fill =
        if (state.state)
          Fill.Fill
        else
          Fill.Draw,
    )

    val extraDot =
      Render.cond(!state.state) {
        Rect(
          x = 0,
          y = state.boundsHeight - 50,
          w = 50,
          h = 50,
          color = Color.Black,
          fill = Fill.Fill,
        )
      }

    val str =
      if (state.crankDocked)
        c"Crank docked"
      else
        c"Crank in use"

    val crankText =
      Render.withTextWidth(str) { w =>
        Text(
          state.boundsWidth - w - 20,
          0,
          str,
        )
      }

    Clear(Color.White) |+|
      FPS(0, 0) |+|
      dot |+|
      extraDot |+|
      crankText
  }

}

enum Fill {
  case Fill
  case Draw
}

enum Color {
  case Black
  case White

  def toInt: Int =
    this match {
      case Black => 0
      case White => 1
    }

}

enum Render {
  case FPS(x: Int, y: Int)
  case Combine(a: Render, b: Render)
  case Rect(x: Int, y: Int, w: Int, h: Int, color: Color, fill: Fill)
  case Text(x: Int, y: Int, text: CString)
  case Empty
  case Clear(color: Color)
  case WithTextWidth(s: CString, f: Int => Render)

  def isEmpty: Boolean = this == Empty

  def |+|(another: Render): Render =
    (this, another) match {
      case (Empty, _) => another
      case (_, Empty) => this
      case _          => Combine(this, another)
    }

}

object Render {

  def withTextWidth(s: CString)(f: Int => Render): Render = Render.WithTextWidth(s, f)

  def renderIf(
    cond: Boolean
  )(
    ifTrue: Render,
    ifFalse: Render,
  ): Render =
    if (cond)
      ifTrue
    else
      ifFalse

  def cond(condition: Boolean)(ifTrue: Render): Render = renderIf(condition)(ifTrue, Empty)
}

case class ButtonState(
  a: Boolean,
  b: Boolean,
)

case class Buttons(
  current: ButtonState,
  pressed: ButtonState,
  released: ButtonState,
)

case class Crank(
  change: Float,
  docked: Boolean,
)

case class Screen(
  width: Int,
  height: Int,
)

object Screen {

  val native: Screen = Screen(
    width = 400,
    height = 240,
  )

}

case class GameContext(
  buttons: Buttons,
  crank: Crank,
  delta: Float,
  screen: Screen,
)

case class GameConfig(fps: Int)

object Main {

  val game = MainGame

  var state: GameState = null

  var pressed: Ptr[PDButtons] = null
  var current: Ptr[PDButtons] = null
  var released: Ptr[PDButtons] = null

  extension [A](
    inline ptr: Ptr[A]
  ) {

    inline def !(
      using inline tag: Tag[A]
    ): A = !ptr

  }

  def eventNative(pd: Ptr[PlaydateAPI], event: PDSystemEvent) = {
    if (event == kEventInit) {
      state = game.init(deriveContext(pd))
      val cfg = game.config
      pd_display_setRefreshRate(cfg.fps)
    }

    0
  }

  def updateNative(
    pd: Ptr[PlaydateAPI]
  ): Int = {
    val ctx: GameContext = deriveContext(pd)
    val newState = game.update(ctx)(state)
    val actions = game.render(newState)

    state = newState
    if (actions.isEmpty) 0
    else {
      executeActions(pd, actions)

      1
    }
  }

  def executeActions(
    pd: Ptr[PlaydateAPI],
    actions: Render,
  ): Unit =
    actions match {
      case Render.Empty     => ()
      case Render.Clear(c)  => pd_graphics_clear(c.toInt)
      case Render.FPS(x, y) => pd_system_drawFPS(x, y)
      case Render.WithTextWidth(str, f) =>
        val len = strlen(str)
        val width = pd_graphics_getTextWidth(null, str, len, kUTF8Encoding)
        executeActions(pd, f(width))

      case Render.Text(x, y, str) =>
        // Zone { case given Zone =>
        //   val str = toCString(text)
        pd_graphics_drawText(
          str,
          strlen(str),
          kUTF8Encoding,
          x,
          y,
        )
      // }
      case Render.Combine(a, b) =>
        executeActions(pd, a)
        executeActions(pd, b)
      case Render.Rect(x, y, w, h, color, fill) =>
        fill match {
          case Fill.Fill =>
            pd_graphics_fillRect(
              x,
              y,
              w,
              h,
              color.toInt,
            )
          case Fill.Draw =>
            pd_graphics_drawRect(
              x,
              y,
              w,
              h,
              color.toInt,
            )
        }
    }

  def deriveButtons(buttons: Ptr[PDButtons]): ButtonState = ButtonState(
    a = buttons.!.is(kButtonA),
    b = buttons.!.is(kButtonB),
  )

  def deriveContext(pd: Ptr[PlaydateAPI]): GameContext = {
    // todo: maybe these can be optimized into fields somehow?
    val current = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val pressed = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]
    val released = stackalloc[CUnsignedInt](1).asInstanceOf[Ptr[PDButtons]]

    pd_system_getButtonState(current, pressed, released)

    val buttons = Buttons(
      current = deriveButtons(current),
      pressed = deriveButtons(pressed),
      released = deriveButtons(released),
    )

    val crank = Crank(
      change = pd_system_getCrankChange(),
      docked = pd_system_isCrankDocked(),
    )

    val delta = pd_system_getElapsedTime()
    pd_system_resetElapsedTime()

    val screen = Screen.native

    GameContext(
      buttons = buttons,
      crank = crank,
      delta = delta,
      screen = screen,
    )
  }

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

  @extern def pd_system_getCrankChange(): Float = extern

  @extern def pd_system_isCrankDocked(): Boolean = extern

  @extern def pd_system_getElapsedTime(): Float = extern

  @extern def pd_system_resetElapsedTime(): Unit = extern

  @extern def pd_system_drawFPS(
    x: Int,
    y: Int,
  ): Unit = extern

  @extern def pd_graphics_getTextWidth(
    font: Ptr[LCDFont],
    text: CString,
    len: CSize,
    encoding: PDStringEncoding,
  ): Int = extern

  @extern def pd_graphics_drawText(
    text: CString,
    len: CSize,
    encoding: PDStringEncoding,
    x: Int,
    y: Int,
  ): Unit = extern

  @extern def pd_display_setRefreshRate(
    rate: Float
  ): Unit = extern

  @extern
  @name("pd_system_logToConsole")
  def printToConsole(msg: CString): Unit = extern

  @exported("sn_event")
  def event(
    pd: Ptr[PlaydateAPI],
    event: PDSystemEvent,
  ): Int = eventNative(pd, event)

  @exported("sn_update")
  def update(
    pd: Ptr[PlaydateAPI]
  ): Int = updateNative(pd)

}

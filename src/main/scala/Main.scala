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
import scala.scalanative.posix.sys.stat
import demo.Main.pd_graphics_loadBitmap
import pdapi.enumerations.LCDLineCapStyle.kLineCapStyleButt

enum DirectionX {
  case Left, Right
}

enum DirectionY {
  case Up, Down
}

case class Assets(
  arrow: Ptr[LCDBitmap]
)

case class GameState(
  szczur: Szczur,
  assets: Assets,
)

case class Radians(value: Float) {
  def -(other: Radians): Radians = Radians(value - other.value)
  def +(other: Radians): Radians = Radians(value + other.value)
  def *(other: Float): Radians = Radians(value * other)

  def clamp(min: Radians, max: Radians): Radians = Radians(value.max(min.value).min(max.value))
  def toDegrees: Float = value * 180 / Math.PI.toFloat
}

object Radians {
  def fromDegrees(degrees: Float): Radians = Radians(degrees * Math.PI.toFloat / 180)
}

case class Szczur(
  y: Float,
  rotation: Radians,
)

extension (d: Double) def clamp(min: Double, max: Double): Double = d.max(min).min(max)

object MainGame {
  val szczurWidth = 32
  val szczurHeight = 32
  val szczurMarginX = 20
  val szczurMarginY = 20

  def init(ctx: GameContext): GameState = {
    val bitmap = Zone {
      val outErr = stackalloc[CString]()
      pd_graphics_loadBitmap(toCString("arrow.png"), outErr)
    }

    GameState(
      szczur = Szczur(
        y = ctx.screen.height / 2 - szczurHeight / 2,
        rotation = Radians(0),
      ),
      assets = Assets(
        arrow = bitmap
      ),
    )
  }

  def update(ctx: GameContext): GameState => GameState = {

    val rotateSzczur: GameState => GameState =
      state => {
        val newRotation =
          (
            state.szczur.rotation + Radians.fromDegrees(ctx.crank.change)
          )
            .clamp(
              min = Radians.fromDegrees(-60),
              max = Radians.fromDegrees(60),
            )
        state.copy(szczur = state.szczur.copy(rotation = newRotation))
      }

    val moveSzczur: GameState => GameState =
      state => {
        val newY = (state.szczur.y + Math.sin(state.szczur.rotation.value) * ctx.delta * 200)
          .clamp(20, ctx.screen.height - szczurHeight - szczurMarginY)

        state.copy(szczur = state.szczur.copy(y = newY.toFloat))
      }

    val equalizeSzczur: GameState => GameState =
      state => {
        val newRotation =
          if state.szczur.y == szczurMarginY || state
              .szczur
              .y == ctx.screen.height - szczurHeight - szczurMarginY
          then state.szczur.rotation * 0.9
          else state.szczur.rotation

        state.copy(szczur = state.szczur.copy(rotation = newRotation))
      }

    Function.chain(
      List(
        rotateSzczur,
        moveSzczur,
        equalizeSzczur,
      )
    )
  }

  def config: GameConfig = GameConfig(fps = 50)

  def render(state: GameState): Render = {
    import Render._

    val szczur = /* Render
      .Rect(
        x = 50,
        y = state.szczur.y.toInt,
        w = szczurWidth,
        h = szczurHeight,
        color = Color.Black,
        fill = Fill.Fill,
      ) */
      Render.Bitmap(
        x = szczurMarginX + szczurWidth / 2,
        y = state.szczur.y.toInt + szczurHeight / 2,
        bitmap = state.assets.arrow,
        rotation = state.szczur.rotation,
        centerX = 0.5,
        centerY = 0.5,
        xscale = 1.0,
        yscale = 1.0,
      )
    // .rotated(state.szczur.rotation)

    val debug = Render.Text(
      x = 10,
      y = 10,
      s"Rotation: ${state.szczur.rotation.value}, y: ${state.szczur.y}",
    )

    Clear(Color.White) |+|
      FPS(0, 0) |+|
      szczur |+|
      debug
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

  case Bitmap(
    x: Int,
    y: Int,
    bitmap: Ptr[LCDBitmap],
    rotation: Radians,
    centerX: Float,
    centerY: Float,
    xscale: Float,
    yscale: Float,
  )

  case Text(x: Int, y: Int, text: String)
  case Empty
  case Clear(color: Color)
  case WithTextWidth(s: String, f: Int => Render)

  def isEmpty: Boolean = this == Empty

  def |+|(another: Render): Render =
    (this, another) match {
      case (Empty, _) => another
      case (_, Empty) => this
      case _          => Combine(this, another)
    }

}

object Render {

  def withTextWidth(s: String)(f: Int => Render): Render = Render.WithTextWidth(s, f)

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
    if (actions.isEmpty)
      0
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
      case Render.Bitmap(x, y, bitmap, rotation, centerX, centerY, xscale, yscale) =>
        pd_graphics_drawRotatedBitmap(
          bitmap = bitmap,
          x = x,
          y = y,
          rotation = rotation.toDegrees,
          centerx = centerX,
          centery = centerY,
          xscale = xscale,
          yscale = yscale,
        )

      case Render.WithTextWidth(text, f) =>
        val width = Zone {
          val str = toCString(text)
          val len = strlen(str)
          pd_graphics_getTextWidth(
            font = null,
            text = str,
            len = len,
            encoding = kUTF8Encoding,
            tracking = pd_graphics_getTextTracking(),
          )
        }

        executeActions(pd, f(width))

      case Render.Text(x, y, text) =>
        Zone {
          val str = toCString(text)
          pd_graphics_drawText(
            str,
            strlen(str),
            kUTF8Encoding,
            x,
            y,
          )
        }
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

  @extern def pd_graphics_drawRotatedBitmap(
    bitmap: Ptr[LCDBitmap],
    x: Int,
    y: Int,
    rotation: Float,
    centerx: Float,
    centery: Float,
    xscale: Float,
    yscale: Float,
  ): Unit = extern

  @extern def pd_graphics_loadBitmap(
    path: CString,
    outErr: Ptr[CString],
  ): Ptr[LCDBitmap] = extern

  @extern def pd_graphics_getTextWidth(
    font: Ptr[LCDFont],
    text: CString,
    len: CSize,
    encoding: PDStringEncoding,
    tracking: Int,
  ): Int = extern

  @extern def pd_graphics_getTextTracking(): Int = extern

  @extern def pd_graphics_pushContext(
    ctx: Ptr[LCDBitmap]
  ): Unit = extern

  @extern def pd_graphics_popContext(): Unit = extern

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

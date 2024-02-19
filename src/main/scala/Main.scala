package demo

import pdapi.all._
import pdapi.enumerations.PDButtons.kButtonA
import pdapi.enumerations.PDSystemEvent.kEventInit
import scalanative.unsafe._

import pdapi.enumerations.PDButtons.kButtonB
import scala.scalanative.libc.string._
import pdapi.enumerations.PDStringEncoding.kUTF8Encoding
import pdapi.enumerations.PDSystemEvent.kEventTerminate
import scala.util.Random

enum DirectionX {
  case Left, Right
}

enum DirectionY {
  case Up, Down
}

case class GameAssets(
  arrow: Ptr[LCDBitmap]
)

enum TramDirection {
  case Horizontal
  case Vertical
}

enum Obstacle {
  case Tram(direction: TramDirection, offsetX: Float, offsetY: Float)

  def moveX(diff: Float): Obstacle =
    this match {
      case Tram(direction, offsetX, offsetY) => Tram(direction, offsetX + diff, offsetY)
    }

}

case class GameState(
  rat: Rat,
  obstacles: List[Obstacle],
  assets: GameAssets,
  offsetX: Float,
)

case class Radians private (value: Float) {
  def -(other: Radians): Radians = Radians(value - other.value)
  def +(other: Radians): Radians = Radians(value + other.value)
  def *(other: Float): Radians = Radians(value * other)

  def clamp(min: Radians, max: Radians): Radians = Radians(value.max(min.value).min(max.value))
  def toDegrees: Float = value * 180 / Math.PI.toFloat
}

object Radians {
  def fromDegrees(degrees: Float): Radians = Radians(degrees * Math.PI.toFloat / 180)
  val Zero: Radians = Radians(0)
}

case class Rat(
  y: Float,
  rotation: Radians,
)

extension (d: Double) def clamp(min: Double, max: Double): Double = d.max(min).min(max)

enum Resource[A] {
  case Pure[A](a: A) extends Resource[A]
  case Make[A](alloc: () => A, cleanup: A => Unit) extends Resource[A]
  case FlatMap[B, A](resource: Resource[B], f: B => Resource[A]) extends Resource[A]

  def map[B](f: A => B): Resource[B] = flatMap(a => Resource.pure(f(a)))

  def flatMap[B](f: A => Resource[B]): Resource[B] = FlatMap(this, f)

  def compile(): (A, () => Unit) = {
    def loop[B](resource: Resource[B]): (B, () => Unit) =
      resource match {
        case Pure(a) => (a, () => ())

        case Make(alloc, cleanup) =>
          val a = alloc()
          (a, () => cleanup(a))

        case FlatMap(resource, f) =>
          val (b, cleanup) = loop(resource)
          val (a, cleanup2) = loop(f(b))

          (
            a,
            () => {
              cleanup2()
              cleanup()
            },
          )
      }

    loop(this)
  }

}

object Resource {
  def pure[A](a: A): Resource[A] = Resource.Pure(a)
  def make[A](alloc: => A)(cleanup: A => Unit): Resource[A] = Resource.Make(() => alloc, cleanup)
}

object Assets {

  def bitmap(path: String): Resource[Ptr[LCDBitmap]] =
    Resource.make {
      Zone {
        val outErr = alloc[CString]()
        pdapiBindings.pd_graphics_loadBitmap(toCString(path), outErr)
      }
    } { ptr =>
      pdapiBindings.pd_graphics_freeBitmap(ptr)
    }

}

// todo move stuff from main
object pdapiBindings {

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

  @extern def pd_graphics_freeBitmap(
    bitmap: Ptr[LCDBitmap]
  ): Unit = extern

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

}

object MainGame {
  val ratWidth = 32
  val ratHeight = 32
  val ratMarginX = 20
  val ratMarginY = 20

  val tramWidth = 10
  val tramLength = 100

  def config: GameConfig = GameConfig(fps = 50)

  private def generateObstacles(dice: Dice): List[Obstacle] = {
    // SN issue? ranges (0 until 10) seem to crash. So does List.iterate with about 100 items.

    // generate obstacles whose distance from the previous one is at least 50 pixels, and at most 200 pixels from the previous one.

    val offsets =
      dice
        .rollN(fromInclusive = 1, untilExclusive = 4, n = 10)
        .map(_ * 50)
        .scanLeft(200)(_ + _ + tramWidth)
        .tail

    println("generated offsets: " + offsets)

    offsets
      .zipWithIndex
      .map { (offsetX, index) =>
        val offsetY = if index % 2 == 0 then 10 else 120
        Obstacle.Tram(
          direction = TramDirection.Vertical,
          offsetX = offsetX,
          offsetY = offsetY,
        )
      }
  }

  def init(ctx: GameContext): Resource[GameState] = Assets.bitmap("arrow.png").map { arrow =>
    GameState(
      rat = Rat(
        y = ctx.screen.height / 2 - ratHeight / 2,
        rotation = Radians.Zero,
      ),
      assets = GameAssets(
        arrow = arrow
      ),
      obstacles = generateObstacles(ctx.dice),
      offsetX = 0,
    )
  }

  def update(ctx: GameContext): GameState => GameState = {

    val updateOffset: GameState => GameState =
      state => {
        val pixelsPerSecond = 100

        val newOffset = state.offsetX + ctx.delta * pixelsPerSecond
        state.copy(offsetX = newOffset)
      }

    val rotateRat: GameState => GameState =
      state => {
        val newRotation =
          (
            state.rat.rotation + Radians.fromDegrees(ctx.crank.change)
          )
            .clamp(
              min = Radians.fromDegrees(-60),
              max = Radians.fromDegrees(60),
            )
        state.copy(rat = state.rat.copy(rotation = newRotation))
      }

    val moveRat: GameState => GameState =
      state => {
        val newY = (state.rat.y + Math.sin(state.rat.rotation.value) * ctx.delta * 300)
          .clamp(20, ctx.screen.height - ratHeight - ratMarginY)

        state.copy(rat = state.rat.copy(y = newY.toFloat))
      }

    // val equalizeRatRotation: GameState => GameState =
    //   state => {
    //     val newRotation =
    //       if Math.abs(state.rat.rotation.value) < 0.03
    //       then Radians.Zero
    //       else state.rat.rotation

    //     state.copy(rat = state.rat.copy(rotation = newRotation))
    //   }

    val equalizeRat: GameState => GameState =
      state => {
        val newRotation =
          if state.rat.y == ratMarginY || state
              .rat
              .y == ctx.screen.height - ratHeight - ratMarginY
          then state.rat.rotation * 0.9
          else state.rat.rotation

        state.copy(rat = state.rat.copy(rotation = newRotation))
      }

    val recycleObstacles: GameState => GameState =
      state => {
        // remove any obstacles whose right side is off the screen.
        val newObstacles = state.obstacles.filter { case Obstacle.Tram(_, offsetX, _) =>
          offsetX + tramWidth > state.offsetX
        }

        state.copy(obstacles = newObstacles)
      }

    val addObstacles: GameState => GameState =
      state => {

        // If the last obstacle is less than 50 pixels from the screen right, or if there are no obstacles at all, generate new obstacles and append to the current list.
        val newObstacles =
          state.obstacles ++ state
            .obstacles
            .lastOption
            .filter { case Obstacle.Tram(_, offsetX, _) =>
              val positionOnScreen = offsetX - state.offsetX
              positionOnScreen < ctx.screen.width + 50
            }
            .toList
            .flatMap { case Obstacle.Tram(_, offsetX, _) =>
              generateObstacles(ctx.dice).map(_.moveX(offsetX + tramWidth))
            }

        state.copy(obstacles = newObstacles)
      }

    Function.chain(
      List(
        updateOffset,
        rotateRat,
        moveRat,
        // equalizeRatRotation,
        equalizeRat,
        recycleObstacles,
        addObstacles,
      )
    )
  }

  def render(ctx: GameContext, state: GameState): Render = {
    import Render._

    val rat = Render.Bitmap(
      x = ratMarginX + ratWidth / 2,
      y = state.rat.y.toInt + ratHeight / 2,
      bitmap = state.assets.arrow,
      rotation = state.rat.rotation,
      centerX = 0.5,
      centerY = 0.5,
      xscale = 1.0,
      yscale = 1.0,
    )
    // .rotated(state.rat.rotation)

    val obstacles =
      state
        .obstacles
        .map { case Obstacle.Tram(direction, offsetX, offsetY) =>
          direction match {
            case TramDirection.Vertical =>
              Render.Rect(
                x = (offsetX - state.offsetX).toInt,
                y = offsetY.toInt,
                w = tramWidth,
                h = tramLength,
                color = Color.Black,
                fill = Fill.Fill,
              )
          }
        }
        .combineAll

    val debug = Render.Text(
      x = 10,
      y = 10,
      s"Rotation: ${state.rat.rotation.value}, y: ${state.rat.y}",
    )

    val crankIndicator =
      Render.cond(ctx.crank.docked) {
        val text = "Use the crank!"

        Render.withTextWidth(text) { w =>
          Render.Text(
            x = ctx.screen.width - w - 10,
            y = ctx.screen.height - 24 - 10,
            text = text,
          )
        }
      }

    Clear(Color.White) |+|
      FPS(0, 0) |+|
      rat |+|
      obstacles |+|
      crankIndicator |+|
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
    ifTrue: => Render,
    ifFalse: => Render,
  ): Render =
    if (cond)
      ifTrue
    else
      ifFalse

  def cond(condition: Boolean)(ifTrue: => Render): Render = renderIf(condition)(ifTrue, Empty)

  extension (l: List[Render]) def combineAll: Render = l.foldLeft(Render.Empty)(_ |+| _)
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

// Another brilliant idea from Indigo!
trait Dice {
  def rollN(fromInclusive: Int, untilExclusive: Int, n: Int): List[Int]
}

object Dice {

  case class DiceImpl(seed: Int) extends Dice {

    def rollN(fromInclusive: Int, untilExclusive: Int, n: Int): List[Int] = withRandom { rand =>
      val range = untilExclusive - fromInclusive
      List.fill(n)(rand.nextInt(range) + fromInclusive)
    }

    private def withRandom[A](f: Random => A): A = f(new Random(seed))

  }

  def fromRandom(seed: Int): Dice = new DiceImpl(seed)
}

case class GameContext(
  buttons: Buttons,
  crank: Crank,
  delta: Float,
  screen: Screen,
  dice: Dice,
)

case class GameConfig(fps: Int)

object Main {

  private val game = MainGame

  private var state: GameState = null
  private var cleanupState: () => Unit = null

  extension [A](
    inline ptr: Ptr[A]
  ) {

    inline def !(
      using inline tag: Tag[A]
    ): A = !ptr

  }

  import pdapiBindings._

  def eventNative(pd: Ptr[PlaydateAPI], event: PDSystemEvent) = {
    event.match {
      case `kEventInit` =>
        game.init(deriveContext(pd)).compile() match {
          case (state, cleanupState) =>
            this.state = state
            this.cleanupState = cleanupState
        }

        val cfg = game.config
        pd_display_setRefreshRate(cfg.fps)

      case `kEventTerminate` =>
        cleanupState()
        state = null
        cleanupState = null
      case _ => ()
    }

    0
  }

  def updateNative(
    pd: Ptr[PlaydateAPI]
  ): Int = {
    val ctx: GameContext = deriveContext(pd)
    val newState = game.update(ctx)(state)
    val actions = game.render(ctx, newState)

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
      dice = Dice.fromRandom(Random.nextInt()),
    )
  }

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

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
import util._
import pdapi.structs.LCDBitmap
import language.strictEquality

object util {

  extension [A](
    inline ptr: Ptr[A]
  ) {

    inline def !(
      using inline tag: Tag[A]
    ): A = !ptr

  }

}

enum DirectionX {
  case Left, Right
}

enum DirectionY {
  case Up, Down
}

case class GameAssets(
  arrow: Ptr[LCDBitmap],
  tram: Ptr[LCDBitmap],
  scorePlayer: Ptr[SamplePlayer],
)

enum TramDirection derives CanEqual {
  // case Horizontal
  case Vertical
}

enum Obstacle {
  case Tram(direction: TramDirection, offsetX: Float, offsetY: Float)

  def moveX(diff: Float): Obstacle =
    this match {
      case Tram(direction, offsetX, offsetY) => Tram(direction, offsetX + diff, offsetY)
    }

}

case class Score(value: Int) derives CanEqual {
  def +(other: Score): Score = Score(value + other.value)
  def *(other: Int): Score = Score(value * other)
  def >(other: Score): Boolean = value > other.value
  def max(other: Score): Score = Score(value.max(other.value))
}

object Score {
  val Init: Score = Score(0)
  val passedTram: Score = Score(10)

  extension (l: List[Score]) def combineAll: Score = l.foldLeft(Score.Init)(_ + _)
}

enum GameMode derives CanEqual {
  case Playing // (score: Score, scoring: Boolean, boost: Boolean)
  case GameOver
}

case class GameState(
  rat: Rat,
  obstacles: List[Obstacle],
  assets: GameAssets,
  offsetX: Float,
  score: Score,
  scoring: Boolean,
  highScore: Score,
  boost: Boolean,
  mode: GameMode,
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
  import pdapiBindings._

  def bitmap(path: String): Resource[Ptr[LCDBitmap]] =
    Resource.make {
      Zone {
        val outErr = alloc[CString]()
        pd_graphics_loadBitmap(toCString(path), outErr)
      }
    }(pd_graphics_freeBitmap(_))

  // note: path must contain .pda file extension.
  def sample(path: String): Resource[Ptr[AudioSample]] =
    Resource.make {
      Zone(pd_sound_sample_load(toCString(path)))
    }(pd_sound_sample_freeSample(_))

  def samplePlayer(sample: Ptr[AudioSample], volumeLeft: Float, volumeRight: Float)
    : Resource[Ptr[SamplePlayer]] = Resource
    .make {
      pd_sound_sampleplayer_newPlayer()
    }(pd_sound_sampleplayer_freePlayer(_))
    .map { player =>
      pd_sound_sampleplayer_setSample(player, sample)
      pd_sound_sampleplayer_setVolume(player, volumeLeft, volumeRight)
      player
    }

}

object pdapiBindings {

  @extern def pd_sound_sampleplayer_newPlayer(): Ptr[SamplePlayer] = extern

  @extern def pd_sound_sample_load(path: CString): Ptr[AudioSample] = extern

  @extern def pd_sound_sampleplayer_freePlayer(player: Ptr[SamplePlayer]): Unit = extern

  @extern def pd_sound_sample_freeSample(sample: Ptr[AudioSample]): Unit = extern

  @extern def pd_sound_sampleplayer_setSample(
    player: Ptr[SamplePlayer],
    sample: Ptr[AudioSample],
  ): Unit = extern

  @extern def pd_sound_sampleplayer_setVolume(
    player: Ptr[SamplePlayer],
    left: Float,
    right: Float,
  ): Unit = extern

  @extern def pd_sound_sampleplayer_play(player: Ptr[SamplePlayer], repeat: Int, rate: Float)
    : Unit = extern

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

  @extern def pd_graphics_getBitmapData(
    bitmap: Ptr[LCDBitmap],
    width: Ptr[Int],
    height: Ptr[Int],
    rowbytes: Ptr[Int],
    mask: Ptr[Ptr[CUnsignedChar]],
    data: Ptr[Ptr[CUnsignedChar]],
  ): Unit = extern

  @extern def pd_graphics_newBitmap(
    width: Int,
    height: Int,
    bgcolor: Int,
  ): Ptr[LCDBitmap] = extern

  @extern def pd_graphics_drawScaledBitmap(
    bitmap: Ptr[LCDBitmap],
    x: Int,
    y: Int,
    xscale: Float,
    yscale: Float,
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
  val ratScale = 1.0f
  val ratAssetWidth = 32
  val ratAssetHeight = 13
  val ratWidth = (ratAssetWidth * ratScale).toInt
  val ratHeight = (ratAssetHeight * ratScale).toInt
  val ratMarginX = 20
  val ratMarginY = 20

  val tramWidth = 32
  val tramLength = 128
  val tramSpacing = 64

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
      .flatMap { (offsetX, index) =>
        val offsetY = if index % 2 == 0 then 10 else -100
        val t1 = Obstacle.Tram(
          direction = TramDirection.Vertical,
          offsetX = offsetX,
          offsetY = offsetY,
        )

        val t2 = Obstacle.Tram(
          direction = TramDirection.Vertical,
          offsetX = offsetX,
          offsetY = offsetY + tramLength + tramSpacing,
        )

        t1 :: t2 :: Nil
      }
  }

  def init(ctx: GameContext): Resource[GameState] =
    for {
      arrow <- Assets.bitmap("szczur.png")
      tram <- Assets.bitmap("tram.png")
      scoreSample <- Assets.sample("score.pda")
      scorePlayer <- Assets.samplePlayer(
        sample = scoreSample,
        volumeLeft = 0.1,
        volumeRight = 0.1,
      )
      assets = GameAssets(
        arrow = arrow,
        tram = tram,
        scorePlayer = scorePlayer,
      )
      // todo: read from file
      highScore = Score(0)
    } yield initState(assets, ctx, highScore = highScore)

  def initState(assets: GameAssets, ctx: GameContext, highScore: Score): GameState = GameState(
    rat = Rat(y = ctx.screen.height / 2 - ratHeight / 2, rotation = Radians.Zero),
    assets = assets,
    obstacles = generateObstacles(ctx.dice),
    offsetX = 0,
    score = Score.Init,
    scoring = false,
    highScore = highScore,
    boost = false,
    mode = GameMode.Playing,
  )

  extension [A, B](f: A => B) def flatMap[C](g: B => (A => C)): A => C = a => g(f(a))(a)

  def update(ctx: GameContext): GameState => GameState = {

    val getMode: GameState => GameMode = _.mode
    val setBoost: GameState => GameState =
      state => {
        // edge-triggered button state. If we're holding the button, we simply don't toggle whatever was there before
        val newBoost =
          if ctx.buttons.pressed.a then true
          else if ctx.buttons.released.a then false
          else state.boost

        state.copy(boost = newBoost)
      }

    val movement: GameState => GameState =
      state => {
        val pixelsPerSecond =
          if (state.boost)
            300
          else
            100

        val newOffset = state.offsetX + ctx.delta * pixelsPerSecond

        val passThreshold = ratMarginX + ratWidth
        val scoreGains =
          state
            .obstacles
            .filter { case Obstacle.Tram(_, offsetX, _) =>
              offsetX + tramWidth - passThreshold > state.offsetX &&
              offsetX + tramWidth - passThreshold <= newOffset
            }
            .map { case t: Obstacle.Tram => Score.passedTram }
            .combineAll

        state.copy(
          offsetX = newOffset,
          score = state.score + scoreGains,
          scoring = scoreGains != Score.Init,
        )
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

    val playAgain: GameState => GameState =
      state =>
        if ctx.buttons.pressed.a then
          initState(state.assets, ctx, highScore = state.highScore max state.score)
        else state

    val gameOver: GameState => GameState =
      state =>
        if (state.score > Score.passedTram * 1)
          state.copy(mode = GameMode.GameOver, scoring = false)
        else
          state

    getMode.flatMap {
      case GameMode.GameOver =>
        Function.chain(
          List(
            playAgain
          )
        )

      case GameMode.Playing =>
        Function.chain(
          List(
            setBoost,
            movement,
            rotateRat,
            moveRat,
            equalizeRat,
            recycleObstacles,
            addObstacles,
            gameOver,
          )
        )
    }
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
      xscale = ratScale,
      yscale = ratScale,
    )
    // .rotated(state.rat.rotation)

    val obstacles =
      state
        .obstacles
        .map { case Obstacle.Tram(direction, offsetX, offsetY) =>
          direction match {
            case TramDirection.Vertical =>
              // Render.Rect(
              //   x = (offsetX - state.offsetX).toInt,
              //   y = offsetY.toInt,
              //   w = tramWidth,
              //   h = tramLength,
              //   color = Color.Black,
              //   fill = Fill.Fill,
              // )
              Render.Bitmap(
                x = (offsetX - state.offsetX).toInt,
                y = offsetY.toInt,
                bitmap = state.assets.tram,
                rotation = Radians.Zero,
                centerX = 0,
                centerY = 0,
                xscale = 1.0,
                yscale = 1.0,
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

    val score = {
      val text = "Score: " + state.score.value
      Render.withTextWidth(text) { w =>
        Render.Text(
          x = ctx.screen.width - w - 10,
          y = 10,
          text = text,
        )
      } |+|
        Render.cond(state.scoring) {
          Render.Play(state.assets.scorePlayer)
        }
    }

    val isGameOver = state.mode == GameMode.GameOver

    val gameOver =
      Render.cond(isGameOver) {

        val w = 200
        val h = 100
        val rect: Render.Rect = Render.Rect(
          x = (ctx.screen.width - w) / 2,
          y = (ctx.screen.height - h) / 2,
          w = w,
          h = h,
          color = Color.White,
          fill = Fill.Fill,
        )

        rect |+|
          rect.copy(fill = Fill.Draw, color = Color.Black) |+| {
            val text =
              s"""Game over!
                 |Final score: ${state.score.value}
                 |Last high score: ${state.highScore.value}
                 |Press A to try again!""".stripMargin

            Render.withTextWidth(text) { w =>
              Render.Text(
                x = (ctx.screen.width - w) / 2,
                y = (ctx.screen.height - 80) / 2,
                text = text,
              )
            }
          }
      }

    Clear(Color.White) |+|
      FPS(0, 0) |+|
      obstacles |+|
      rat |+|
      crankIndicator.unless(isGameOver) |+|
      debug |+|
      score.unless(isGameOver) |+|
      gameOver
  }

}

enum Fill derives CanEqual {
  case Fill
  case Draw
}

enum Color derives CanEqual {
  case Black
  case White

  def toInt: Int =
    this match {
      case Black => 0
      case White => 1
    }

}

enum Render derives CanEqual {
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
  case Play(sample: Ptr[SamplePlayer])

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
  extension (r: => Render) def unless(condition: Boolean): Render = cond(!condition)(r)
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

  import pdapiBindings._

  given CanEqual[PDSystemEvent, PDSystemEvent] = CanEqual.derived

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
      case Render.Empty        => ()
      case Render.Clear(c)     => pd_graphics_clear(c.toInt)
      case Render.FPS(x, y)    => pd_system_drawFPS(x, y)
      case Render.Play(sample) => pd_sound_sampleplayer_play(sample, 1, 1)
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

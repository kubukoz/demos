import java.nio.file.Files
import java.nio.file.Paths
import scala.scalanative.build.GC
import scala.scalanative.build.LTO
import scala.scalanative.build.BuildTarget
import scala.scalanative.build.Mode

val playdateSdk = file(
  sys.env.getOrElse(
    "PLAYDATE_SDK_PATH",
    sys.error("PLAYDATE_SDK_PATH not set! If you're on mac, consider ~/Developer/PlaydateSDK."),
  )
)

val devicePath = file(
  sys.env.getOrElse(
    "PLAYDATE_DEVICE_PATH",
    sys.error("PLAYDATE_DEVICE_PATH not set, look at flake.nix for an example"),
  )
)

val pdutilPath = playdateSdk / "bin" / "pdutil"

def pdutil(args: String*) = {
  import sys.process._
  val cmd = pdutilPath.toString :: devicePath.toString :: args.toList
  println("running " + cmd.mkString(" "))
  println(cmd.!!)
}

import scala.annotation.tailrec

@tailrec
def waitUntil(b: => Boolean): Unit =
  if (b) ()
  else {
    Thread.sleep(1000)
    waitUntil(b)
  }

def waitForVolume() = {
  import sys.process._
  println("waiting for volume...")
  waitUntil {
    "ls /Volumes/PLAYDATE/Games".! == 0
  }
}

def runOnPlaydate(buildPdxPath: File) = {
  import sys.process._

  println("booting into datadisk")
  pdutil("datadisk")
  waitForVolume()

  println("replacing game")
  val pdxFileName = buildPdxPath.name

  IO.delete(file(s"/Volumes/PLAYDATE/Games/$pdxFileName"))
  IO.copyDirectory(
    buildPdxPath,
    file(s"/Volumes/PLAYDATE/Games/$pdxFileName"),
  )

  println("ejecting...")
  "diskutil eject /Volumes/PLAYDATE".!!

  println("waiting for device...")
  waitUntil {
    s"ls $devicePath".! == 0
  }

  println("launching game")
  pdutil("run", s"/Games/$pdxFileName")
}

val playdateBuild = taskKey[File]("Build the game for Playdate")

val playdateBuildImpl =
  playdateBuild := {
    import sys.process._

    val log = streams.value.log
    val staticLib = (Compile / nativeLink).value

    val gameDir = baseDirectory.value / "game"
    val buildDir = gameDir / "build"
    val sourceDir = gameDir / "Source"
    val pdxDir = gameDir / "HelloWorld.pdx"

    IO.createDirectory(buildDir)

    val gcc = "arm-none-eabi-gcc"
    val sdk = playdateSdk

    // Link libroot.a (which now contains all C and Scala code) into pdex.elf
    val ldScript = sdk / "C_API" / "buildsupport" / "link_map.ld"
    val elf = buildDir / "pdex.elf"

    val ldFlags = Seq(
      "-nostartfiles",
      "-mthumb", "-mcpu=cortex-m7",
      "-mfloat-abi=hard", "-mfpu=fpv5-sp-d16",
      s"-T${ldScript}",
      s"-Wl,-Map=${buildDir / "game.map"},--cref,--gc-sections,--no-warn-mismatch,--emit-relocs,--allow-multiple-definition",
      "--entry", "eventHandlerShim",
      "-Wl,--defsym=_fini=0",
      "-Wl,--defsym=__exidx_start=0",
      "-Wl,--defsym=__exidx_end=0",
    )

    val linkCmd = Seq(gcc) ++ ldFlags ++ Seq(
      // --whole-archive forces all objects from libroot.a to be included,
      // not just those resolving undefined symbols. Without this, the linker
      // would discard setup.c/main.c/pdnewlib.c since nothing inside the
      // archive references them — they're referenced by the Playdate runtime.
      "-Wl,--whole-archive", staticLib.toString, "-Wl,--no-whole-archive",
      "-o", elf.toString,
    )
    log.info("Linking pdex.elf")
    val linkRc = Process(linkCmd).!
    require(linkRc == 0, "Linking failed")

    // Copy elf into Source/ for pdc
    IO.copyFile(elf, sourceDir / "pdex.elf", CopyOptions().withOverwrite(true))

    // Run pdc to produce .pdx
    val pdc = sdk / "bin" / "pdc"
    val pdcCmd = Seq(pdc.toString, sourceDir.toString, pdxDir.toString)
    log.info("Running pdc")
    val pdcRc = Process(pdcCmd).!
    require(pdcRc == 0, "pdc failed")

    pdxDir
  }

val playdateRunImpl =
  run := {
    val pdx = playdateBuild.value
    runOnPlaydate(buildPdxPath = pdx)
  }

val playdateCopyCrashLogs = taskKey[Unit]("Copy crash logs from the connected Playdate device")

val playdateCopyCrashLogsImpl =
  playdateCopyCrashLogs := {
    import sys.process._

    println("booting into datadisk")
    pdutil("datadisk")

    waitForVolume()

    IO.copyFile(
      file(s"/Volumes/PLAYDATE/crashlog.txt"),
      file("crashlog.txt"),
      CopyOptions().withOverwrite(true),
    )
  }

val pdutilDatadisk = taskKey[Unit]("Boot into datadisk mode")

val pdutilDatadiskImpl =
  pdutilDatadisk :=
    pdutil("datadisk")

val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    scalaVersion := "3.8.3",
    scalacOptions += "-Wunused:all",
    scalacOptions += "-no-indent",
    nativeConfig ~= (
      _.withBuildTarget(BuildTarget.libraryStatic)
        .withTargetTriple("arm-none-eabi")
        .withGC(GC.immix)
        .withCompileOptions(
          Seq(
            "-g3",
            "-mthumb",
            "-mcpu=cortex-m7",
            "-mfloat-abi=hard",
            "-mfpu=fpv5-sp-d16",
            "-D__FPU_USED=1",
            "-O2",
            "-falign-functions=16",
            "-fomit-frame-pointer",
            "-gdwarf-2",
            "-fverbose-asm",
            "-Wdouble-promotion",
            "-fno-common",
            "-ffunction-sections",
            "-fdata-sections",
            "-DTARGET_PLAYDATE=1",
            "-DTARGET_EXTENSION=1",
            "-DPD_DEBUG=1",
            "-D_LIBCPP_HAS_THREAD_API_PTHREAD=1",
            "-MD",
            "-MP",
            s"-I${playdateSdk / "C_API"}",
            "-march=armv7-m",
            "-m32",
            "-ferror-limit=1000",
          )
        )
        .withMultithreading(false)
    ),
    playdateBuildImpl,
    playdateRunImpl,
    pdutilDatadiskImpl,
    playdateCopyCrashLogsImpl,
  )

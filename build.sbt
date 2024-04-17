import java.nio.file.Files

import java.nio.file.Paths

import scala.scalanative.build.GC

import scala.scalanative.build.LTO

import scala.scalanative.build.BuildTarget

import scala.scalanative.build.Mode

val playdateSdk = file(
  sys
    .env
    .getOrElse(
      "PLAYDATE_SDK_PATH",
      sys.error("PLAYDATE_SDK_PATH not set! If you're on mac, consider ~/Developer/PlaydateSDK."),
    )
)

val devicePath = file(
  sys
    .env
    .getOrElse(
      "PLAYDATE_DEVICE_PATH",
      sys.error("PLAYDATE_DEVICE_PATH not set, look at flake.nix for an example"),
    )
)

val pdutilPath = playdateSdk / "bin" / "pdutil"

def pdutil(
  args: String*
) = {
  import sys.process._

  val cmd = pdutilPath.toString :: devicePath.toString :: args.toList
  println("running " + cmd.mkString(" "))
  println(cmd.!!)
}

import scala.annotation.tailrec

@tailrec
def waitUntil(b: => Boolean): Unit =
  if (b)
    ()
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

    val staticLib = (Compile / nativeLink).value

    val buildBase = baseDirectory.value / "game"

    IO.copyFile(
      staticLib,
      buildBase / staticLib.name,
      CopyOptions().withOverwrite(true),
    )
    require(
      Process("make", buildBase).! == 0,
      "make failed",
    )

    buildBase / "HelloWorld.pdx"
  }

val playdateRunImpl =
  run := {
    val pdx = playdateBuild.value

    runOnPlaydate(
      buildPdxPath = pdx
    )
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
  pdutilDatadisk := {
    pdutil("datadisk")
  }

val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    scalaVersion := "3.3.3",
    scalacOptions += "-Wunused:all",
    nativeConfig ~= (
      _.withBuildTarget(BuildTarget.libraryStatic)
        .withTargetTriple("arm-none-eabi")
        // .withGC(GC.none)
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
            // "-DPD_DEBUG=1",
            // "-DDEBUG_PRINT=1",
            "-D_LIBCPP_HAS_THREAD_API_PTHREAD=1",
            "-MD",
            "-MP",
            s"-I${playdateSdk / "C_API"}",
            "-march=armv7-m",
            "-m32",
            "-ferror-limit=1000",
            // "-v",
          )
        )
        .withMultithreading(false)
    ),
    playdateBuildImpl,
    playdateRunImpl,
    pdutilDatadiskImpl,
    playdateCopyCrashLogsImpl,
  )

// resolvers ++= Resolver.sonatypeOssRepos("snapshots")

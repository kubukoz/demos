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

def runOnPlaydate(devicePath: File, pdutilPath: File, buildPdxPath: File) = {
  import scala.annotation.tailrec
  import sys.process._

  def pdutil(args: String*) = println(
    (pdutilPath.toString :: devicePath.toString :: args.toList).!!
  )

  @tailrec
  def waitUntil(b: => Boolean): Unit =
    if (b) ()
    else {
      Thread.sleep(1000)
      waitUntil(b)
    }

  println("booting into datadisk")
  pdutil("datadisk")

  println("waiting for volume...")
  waitUntil {
    "ls /Volumes/PLAYDATE/Games".! == 0
  }

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

val playdateRun = taskKey[Unit]("Copy the game to the connected Playdate device and run it")

val playdateRunImpl =
  playdateRun := {
    import sys.process._

    val staticLib = (Compile / nativeLink).value

    val buildBase = file("/Users/kubukoz/projects/playdate-cpp/examples/hello_world")

    IO.copyFile(
      staticLib,
      buildBase / staticLib.name,
      CopyOptions().withOverwrite(true),
    )
    require(
      Process("make", buildBase).! == 0,
      "make failed",
    )

    runOnPlaydate(
      devicePath = file(
        sys
          .env
          .getOrElse(
            "PLAYDATE_DEVICE_PATH",
            sys.error("PLAYDATE_DEVICE_PATH not set, look at flake.nix for an example"),
          )
      ),
      pdutilPath = playdateSdk / "bin" / "pdutil",
      buildPdxPath = buildBase / "HelloWorld.pdx",
    )
  }

val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    scalaVersion := "3.3.1",
    nativeConfig ~= (
      _.withBuildTarget(BuildTarget.libraryStatic)
        .withTargetTriple("arm-none-eabi")
        .withGC(GC.none)
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
            "-D_LIBCPP_HAS_THREAD_API_PTHREAD=1",
            "-MD",
            "-MP",
            s"-I${playdateSdk / "C_API"}",
            "-march=armv7-m",
          )
        )
        .withMultithreadingSupport(false)
    ),
    playdateRunImpl,
  )

// resolvers ++= Resolver.sonatypeOssRepos("snapshots")

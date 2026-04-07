import PlaydateRuntime.ProjectMatrixPlaydateOps
import scala.util.control.NonFatal
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

lazy val devicePath = file(
  sys
    .env
    .getOrElse(
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
    isVolumeAvailable()
  }
}

def isVolumeAvailable() = {
  import sys.process._
  "ls /Volumes/PLAYDATE/Games".! == 0
}

def runOnPlaydate(buildPdxPath: File, launchArgs: Seq[String] = Seq.empty) = {
  import sys.process._

  datadisk()

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

  val pdxPath =
    if (launchArgs.isEmpty)
      s"/Games/$pdxFileName"
    else
      s"/Games/$pdxFileName?${launchArgs.mkString("&")}"

  println(s"launching game with path: $pdxPath")
  pdutil("run", pdxPath)
}

def deviceCFlags(sdk: File) = Seq(
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
  "-Wdouble-promotion",
  "-ffunction-sections",
  "-fdata-sections",
  "-fno-common",
  "-DTARGET_PLAYDATE=1",
  "-DTARGET_EXTENSION=1",
  "-DPD_DEBUG=1",
  s"-I${sdk / "C_API"}",
)

def simulatorCFlags(sdk: File) = Seq(
  "-DTARGET_SIMULATOR=1",
  "-DTARGET_EXTENSION=1",
  s"-I${sdk / "C_API"}",
  "-Wall",
  "-Wstrict-prototypes",
  "-Wno-unknown-pragmas",
  "-Wdouble-promotion",
  "-fPIC",
)

val playdateCompileFlags = settingKey[Seq[String]]("C compile flags for the final Playdate build")
val playdateLinkFlags = settingKey[Seq[String]]("Linker flags for the final Playdate build")
val playdateGameName = settingKey[String]("Name of the Playdate game (used for .pdx output)")

val playdateBuild = taskKey[File]("Build the game for Playdate")

val playdateDeviceBuildImpl =
  playdateBuild := {
    import sys.process._

    val log = streams.value.log
    val staticLib = (Compile / nativeLink).value

    val gameDir =
      (ThisBuild / baseDirectory).value / "modules" / "game" / "src" / "main" / "playdate"
    val outDir = target.value / "playdate"
    val buildDir = outDir / "build"
    val sourceDir = outDir / "Source"
    val pdxDir = outDir / s"${playdateGameName.value}.pdx"

    IO.createDirectory(buildDir)
    IO.copyDirectory(gameDir / "Source", sourceDir)

    val gcc = "arm-none-eabi-gcc"

    val cSources = Seq(
      gameDir / "main.c",
      playdateSdk / "C_API" / "buildsupport" / "setup.c",
    )

    val cFlags = playdateCompileFlags.value ++ Seq(s"-I${gameDir}")

    // Compile each C source to .o
    val objects = cSources.map { src =>
      val obj = buildDir / src.name.replaceAll("\\.c$", ".o")
      val cmd = Seq(gcc, "-c") ++ cFlags ++ Seq(src.toString, "-o", obj.toString)
      log.info(s"Compiling ${src.name}")
      val rc = Process(cmd).!
      require(rc == 0, s"Failed to compile ${src.name}")
      obj
    }

    // Link everything into pdex.elf
    val elf = buildDir / "pdex.elf"

    val ldFlags =
      playdateLinkFlags.value ++ Seq(
        s"-Wl,-Map=${buildDir / "game.map"},--cref,--gc-sections,--no-warn-mismatch,--emit-relocs"
      )

    val linkCmd =
      Seq(gcc) ++ ldFlags ++ objects.map(_.toString) ++ Seq(staticLib.toString, "-o", elf.toString)
    log.info("Linking pdex.elf")
    val linkRc = Process(linkCmd).!
    require(linkRc == 0, "Linking failed")

    // Copy elf into Source/ for pdc
    IO.copyFile(elf, sourceDir / "pdex.elf", CopyOptions().withOverwrite(true))

    // Run pdc to produce .pdx
    val pdc = playdateSdk / "bin" / "pdc"
    val pdcCmd = Seq(pdc.toString, sourceDir.toString, pdxDir.toString)
    log.info("Running pdc")
    val pdcRc = Process(pdcCmd).!
    require(pdcRc == 0, "pdc failed")

    pdxDir
  }

val playdateDeviceRunImpl = Seq(
  run / Keys.aggregate := false,
  run := {
    val pdx = playdateBuild.value
    val args = Def.spaceDelimited("<launch args>").parsed
    runOnPlaydate(buildPdxPath = pdx, launchArgs = args)
  },
  justRun := {
    val pdxDir = target.value / "playdate" / s"${playdateGameName.value}.pdx"
    val args = Def.spaceDelimited("<launch args>").parsed
    runOnPlaydate(buildPdxPath = pdxDir, launchArgs = args)
  },
)

val simulatorNativeSourcesImpl =
  Compile / resourceGenerators += Def.task {
    val outDir = (Compile / resourceManaged).value / "scala-native"
    IO.createDirectory(outDir)

    val gameDir =
      (ThisBuild / baseDirectory).value / "modules" / "game" / "src" / "main" / "playdate"
    val sources = Seq(
      gameDir / "main.c"
    )

    val copied = sources.map { src =>
      val dest = outDir / src.name
      IO.copyFile(src, dest, CopyOptions().withOverwrite(true))
      dest
    }

    copied
  }

val playdateSimulatorBuildImpl =
  playdateBuild := {
    import sys.process._

    val log = streams.value.log
    val dylib = (Compile / nativeLink).value

    val gameDir =
      (ThisBuild / baseDirectory).value / "modules" / "game" / "src" / "main" / "playdate"
    val outDir = target.value / "playdate"
    val sourceDir = outDir / "Source"
    val pdxDir = outDir / s"${playdateGameName.value}.pdx"

    IO.copyDirectory(gameDir / "Source", sourceDir)

    // Copy dylib into Source/ as pdex.dylib for pdc
    IO.copyFile(dylib, sourceDir / "pdex.dylib", CopyOptions().withOverwrite(true))

    // Run pdc to produce .pdx
    val pdc = playdateSdk / "bin" / "pdc"
    val pdcCmd = Seq(pdc.toString, sourceDir.toString, pdxDir.toString)
    log.info("Running pdc")
    val pdcRc = Process(pdcCmd).!
    require(pdcRc == 0, "pdc failed")

    pdxDir
  }

val playdateSimulatorRunImpl =
  run := {
    import sys.process._
    val pdx = playdateBuild.value
    val simulator =
      playdateSdk / "bin" / "Playdate Simulator.app" / "Contents" / "MacOS" / "Playdate Simulator"
    val cmd = Seq(simulator.toString, pdx.getAbsolutePath)
    streams.value.log.info(s"Launching Playdate Simulator")
    val rc = Process(cmd).!
    require(rc == 0, s"Simulator exited with code $rc")
  }

val playdateSimulatorJustRunImpl =
  justRun := {
    import sys.process._
    val pdxDir = target.value / "playdate" / s"${playdateGameName.value}.pdx"
    val args = Def.spaceDelimited("<launch args>").parsed
    val simulator =
      playdateSdk / "bin" / "Playdate Simulator.app" / "Contents" / "MacOS" / "Playdate Simulator"
    val cmd = Seq(simulator.toString, pdxDir.getAbsolutePath) ++ args
    streams.value.log.info(s"Launching Playdate Simulator")
    val rc = Process(cmd).!
    require(rc == 0, s"Simulator exited with code $rc")
  }

lazy val justRun = inputKey[Unit]("Run the game without rebuilding")

val playdateCopyCrashLogs = taskKey[Unit]("Copy crash logs from the connected Playdate device")

def datadisk() = {

  println("booting into datadisk")

  try pdutil("datadisk")
  catch {
    case NonFatal(e) =>
      println("Couldn't boot into datadisk mode. But is the device connected?")
      if (isVolumeAvailable())
        println("Volume available! Continuing...")
      else
        throw e
  }

  waitForVolume()
}

val playdateCopyCrashLogsImpl =
  playdateCopyCrashLogs := {
    datadisk()

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

val generateEnvCImpl =
  Compile / resourceGenerators += Def.task {
    val vars = (Compile / envVars).value
    val outDir = (Compile / resourceManaged).value / "scala-native"
    IO.createDirectory(outDir)
    val outFile = outDir / "pd_env.c"

    val cases = vars
      .map { case (k, v) =>
        val ek = k.replace("\\", "\\\\").replace("\"", "\\\"")
        val ev = v.replace("\\", "\\\\").replace("\"", "\\\"")
        s"""    if (strcmp(name, "$ek") == 0) return "$ev";"""
      }
      .mkString("\n")

    val content =
      s"""|#include <string.h>
          |
          |#ifdef TARGET_PLAYDATE
          |
          |char *getenv(const char *name) {
          |$cases
          |    return (char *)0;
          |}
          |
          |#endif
          |""".stripMargin

    IO.write(outFile, content)
    Seq(outFile)
  }

val shared =
  projectMatrix
    .in(file("modules") / "shared")
    .enablePlugins(Smithy4sCodegenPlugin)
    .jvmPlatform(
      scalaVersions = Seq("3.8.3"),
      settings = Seq(
        libraryDependencies ++= Seq(
          "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value
        )
      ),
    )
    .nativePlatform(
      scalaVersions = Seq("3.8.3"),
      settings = Seq(
        libraryDependencies ++= Seq(
          "com.disneystreaming.smithy4s" %%% "smithy4s-core" % smithy4sVersion.value
        )
      ),
    )
    .settings(
      scalacOptions += "-no-indent",
      libraryDependencies ++= Seq(
        "tech.neander" % "jsonrpclib-smithy" % "0.1.0+4-dc0ac59d+20260407-1932-SNAPSHOT" % Smithy4s,
        "tech.neander" %%% "jsonrpclib-smithy4s" % "0.1.0+4-dc0ac59d+20260407-1932-SNAPSHOT",
        "tech.neander" %%% "jsonrpclib-fs2" % "0.1.0+4-dc0ac59d+20260407-1932-SNAPSHOT",
        "co.fs2" %%% "fs2-io" % "3.13.0",
      ),
    )

val backend = project
  .in(file("modules") / "backend")
  .settings(
    scalaVersion := "3.8.3",
    scalacOptions += "-no-indent",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % "0.18.50",
      "org.http4s" %% "http4s-ember-server" % "0.23.30",
    ),
  )
  .dependsOn(shared.jvm("3.8.3"))

val commonGameSettings = Seq(
  moduleName := "game",
  scalacOptions += "-Wunused:all",
  scalacOptions += "-no-indent",
  playdateGameName := "RatLife",
  Compile / envVars := Map(
    "SCALANATIVE_GC_LOG_LEVEL" -> "error"
  ),
  generateEnvCImpl,
  libraryDependencies ++= Seq(
    "com.disneystreaming.smithy4s" %%% "smithy4s-json" % smithy4sVersion.value,
    "org.typelevel" %%% "cats-effect" % "3.7.0",
  ),
)

val game =
  projectMatrix
    .in(file("modules") / "game")
    .dependsOn(shared)
    .playdateRow(PlaydateRuntime.Device) {
      _.settings(commonGameSettings)
        .settings(
          nativeConfig ~= (
            _.withBuildTarget(BuildTarget.libraryStatic)
              .withTargetTriple("arm-none-eabi")
              .withGC(GC.immix)
              .withCompileOptions(
                deviceCFlags(playdateSdk) ++ Seq(
                  "-fverbose-asm",
                  "-MD",
                  "-MP",
                  "-march=armv7-m",
                  "-m32",
                  "-ferror-limit=1000",
                )
              )
              .withMultithreading(false)
          ),
          playdateCompileFlags :=
            deviceCFlags(playdateSdk) ++ Seq(
              "-Wall",
              "-Wno-unused",
              "-Wno-unknown-pragmas",
              "-D__HEAP_SIZE=8388208",
              "-D__STACK_SIZE=61800",
            ),
          playdateLinkFlags := {
            val ldScript = playdateSdk / "C_API" / "buildsupport" / "link_map.ld"
            val discardScript =
              (ThisBuild / baseDirectory).value / "modules" / "game" / "src" / "main" / "playdate" / "discard-arm-exceptions.ld"
            Seq(
              "-nostartfiles",
              "-mthumb",
              "-mcpu=cortex-m7",
              "-mfloat-abi=hard",
              "-mfpu=fpv5-sp-d16",
              s"-T${ldScript}",
              s"-T${discardScript}",
              "--entry",
              "eventHandlerShim",
              "-Wl,--defsym=_fini=0",
              "-Wl,--defsym=__exidx_start=0",
              "-Wl,--defsym=__exidx_end=0",
            )
          },
          playdateDeviceBuildImpl,
        )
        .settings(playdateDeviceRunImpl)
        .settings(
          pdutilDatadiskImpl,
          playdateCopyCrashLogsImpl,
        )
    }
    .playdateRow(PlaydateRuntime.Simulator) {
      _.settings(commonGameSettings)
        .settings(
          nativeConfig := {
            val gameDir =
              (ThisBuild / baseDirectory).value / "modules" / "game" / "src" / "main" / "playdate"
            nativeConfig
              .value
              .withBuildTarget(BuildTarget.libraryDynamic)
              .withGC(GC.immix)
              .withCompileOptions(
                simulatorCFlags(playdateSdk) ++ Seq(
                  s"-I${gameDir}",
                  // Simulator-only: disable the dylib constructor that auto-calls
                  // ScalaNativeInit(), because eventHandler already calls it manually.
                  // Without this, two MutatorThreads get registered and GC hangs.
                  "-DSCALANATIVE_NO_DYLIB_CTOR",
                )
              )
              .withMultithreading(false)
          },
          simulatorNativeSourcesImpl,
          playdateSimulatorBuildImpl,
          playdateSimulatorRunImpl,
          playdateSimulatorJustRunImpl,
        )
    }

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.8.3"
  )
  .aggregate((shared.projectRefs ++ game.projectRefs :+ (backend: ProjectReference)): _*)

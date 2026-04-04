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

def commonCFlags(sdk: File) = Seq(
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
  // "-DPD_DEBUG=1",
  s"-I${sdk / "C_API"}",
)

val playdateCompileFlags = settingKey[Seq[String]]("C compile flags for the final Playdate build")
val playdateLinkFlags = settingKey[Seq[String]]("Linker flags for the final Playdate build")

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

    // C sources to compile (not part of Scala Native)
    val cSources = Seq(
      gameDir / "main.c",
      gameDir / "pdnewlib.c",
      gameDir / "setup.c",
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
          commonCFlags(playdateSdk) ++ Seq(
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
    Compile / envVars := Map(
      "SCALANATIVE_GC_LOG_LEVEL" -> "error"
    ),
    playdateCompileFlags :=
      commonCFlags(playdateSdk) ++ Seq(
        "-Wall",
        "-Wno-unused",
        "-Wno-unknown-pragmas",
        "-D__HEAP_SIZE=8388208",
        "-D__STACK_SIZE=61800",
      ),
    playdateLinkFlags := {
      val ldScript = playdateSdk / "C_API" / "buildsupport" / "link_map.ld"
      Seq(
        "-nostartfiles",
        "-mthumb",
        "-mcpu=cortex-m7",
        "-mfloat-abi=hard",
        "-mfpu=fpv5-sp-d16",
        s"-T${ldScript}",
        "--entry",
        "eventHandlerShim",
        "-Wl,--defsym=_fini=0",
        "-Wl,--defsym=__exidx_start=0",
        "-Wl,--defsym=__exidx_end=0",
      )
    },
    generateEnvCImpl,
    playdateBuildImpl,
    playdateRunImpl,
    pdutilDatadiskImpl,
    playdateCopyCrashLogsImpl,
  )

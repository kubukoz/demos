import scala.scalanative.build.LTO

import scala.scalanative.build.BuildTarget

import scala.scalanative.build.Mode

val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    scalaVersion := "3.3.1",
    nativeConfig ~= (
      _.withBuildTarget(BuildTarget.libraryDynamic)
        .withClang(file("/Applications/LLVMEmbeddedToolchainForArm-17.0.1-Darwin/bin/clang").toPath)
        .withTargetTriple("arm-none-eabi")
        // .withTargetTriple("x86_64-unknown-linux-gnu")
        .withCompileOptions(
          Seq(
            "-nostdlib",
            "-march=armv6m",
            "-mcpu=cortex-m7",
            // "-nodefaultlibs",
            // "-v",
          )
        )
        // .withClang(file("/usr/bin/clang").toPath())
    ),
  )

// resolvers ++= Resolver.sonatypeOssRepos("snapshots")

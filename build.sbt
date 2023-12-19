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
        .withTargetTriple("arm-none-eabi")
        // .withTargetTriple("x86_64-unknown-linux-gnu")
        .withCompileOptions(Seq("-v"))
        // .withClang(file("/usr/bin/clang").toPath())
    ),
  )

// resolvers ++= Resolver.sonatypeOssRepos("snapshots")

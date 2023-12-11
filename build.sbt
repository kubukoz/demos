import scala.scalanative.build.LTO

import scala.scalanative.build.BuildTarget

import scala.scalanative.build.Mode

val root = crossProject(NativePlatform)
  .in(file("."))
  .settings(
    scalaVersion := "3.3.1",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.10.0",
      "org.typelevel" %%% "cats-parse" % "1.0.0",
      "org.ekrich" %%% "sjavatime" % "1.1.9",
    ),
  )
  .nativeSettings(
    nativeConfig ~= (
      _.withBuildTarget(BuildTarget.libraryStatic)
      // .withTargetTriple("arm-none-eabi")
      // .withCompileOptions(Seq("-v"))
    )
  )

ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")

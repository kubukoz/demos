ThisBuild / scalaVersion := "3.7.0-RC1"
ThisBuild / organization := "com.kubukoz.smithy4s-bsp"

lazy val transformation = project
  .settings(
    scalaVersion := "2.12.20",
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.56.0"
    ),
    publish / skip := true,
  )

lazy val codegen = project
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "ch.epfl.scala" % "spec" % "2.2.0-M2" % Smithy4s,
      "ch.epfl.scala" % "spec-traits" % "2.2.0-M2" % Smithy4s,
      "com.disneystreaming.smithy4s" %%% "smithy4s-core" % smithy4sVersion.value,
    ),
    Compile / smithy4sModelTransformers := List("rename-scala-namespace"),
    Compile / smithy4sAllDependenciesAsJars += (transformation / Compile / packageBin).value,
  )
  .enablePlugins(Smithy4sCodegenPlugin)

lazy val sampleServer = project
  .settings(
    libraryDependencies ++= Seq(
      "tech.neander" %%% "jsonrpclib-fs2" % "0.0.7",
      "co.fs2" %%% "fs2-io" % "3.12.0",
      "com.disneystreaming.smithy4s" %%% "smithy4s-json" % smithy4sVersion.value,
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-Wunused:all",
      "-Xkind-projector",
    ),
    name := "sample-server",
  )
  .dependsOn(codegen)
  .enablePlugins(ScalaNativePlugin)

lazy val root = project
  .in(file("."))
  .aggregate(sampleServer, codegen, transformation)

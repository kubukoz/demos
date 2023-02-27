import smithy4s.codegen.Smithy4sCodegenPlugin

ThisBuild / scalaVersion := "2.13.10"

Global / onChangedBuildSource := ReloadOnSourceChanges

// imagine this is published externally
lazy val lib = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" % "smithy4s-protocol" % "0.17.2"
    )
  )

lazy val main = project
  .enablePlugins(Smithy4sCodegenPlugin)
  .dependsOn(lib)

lazy val root = project
  .in(file("."))

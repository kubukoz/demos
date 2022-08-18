Global / onChangedBuildSource := ReloadOnSourceChanges

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.8",
    scalacOptions += "-Xsource:3.0",
    scalacOptions += "-Ymacro-annotations",
    scalacOptions -= "-Xfatal-warnings",
    fork := true,
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-aws-http4s" % smithy4sVersion.value
    ),
  )
  .enablePlugins(Smithy4sCodegenPlugin)

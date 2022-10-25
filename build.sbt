Global / onChangedBuildSource := ReloadOnSourceChanges

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.8",
    libraryDependencies += compilerPlugin(
      "org.polyvariant" % "better-tostring" % "0.3.17" cross CrossVersion.full
    ),
    scalacOptions += "-Xfatal-warnings",
  )

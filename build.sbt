val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.5.1",
    Compile / run / fork := true,
  )
  .enablePlugins(JavaAppPackaging, DockerPlugin)

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.8.3",
    scalacOptions ++= Seq("-Wunused:all"),
    semanticdbEnabled := true
  )

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.2.0",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-free" % "2.8.0"
    ),
  )

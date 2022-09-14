val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-codegen" % "0.15.3",
      "com.disneystreaming.smithy4s" %% "smithy4s-dynamic" % "0.15.3",
    ),
  )

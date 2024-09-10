
val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := "3.5.0",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-json" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-dynamic" % smithy4sVersion.value,
    ),

    fork := true
  )

val core = project
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value
    )
  )

val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .dependsOn(core)

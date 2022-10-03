val core = project

val root = project
  .in(file("."))
  .dependsOn(core)
  .enablePlugins(Smithy4sCodegenPlugin)

val kernel = project

val core = project
  .dependsOn(kernel)
  .enablePlugins(Smithy4sCodegenPlugin)

val root = project
  .in(file("."))
  .aggregate(core, kernel)
  .settings(
    addCommandAlias(
      "codegen",
      List(Compile)
        .map(_.id + "/" + managedSources.key.label)
        .mkString(";"),
    )
  )

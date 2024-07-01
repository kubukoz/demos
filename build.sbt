val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    resolvers ++= Resolver.sonatypeOssRepos("snapshots")
  )

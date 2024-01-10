lazy val pluginInterface = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
  )

lazy val scala2Impl = project.dependsOn(pluginInterface)
lazy val dottyImpl = project.dependsOn(pluginInterface)

lazy val app = project.dependsOn(pluginInterface)

lazy val root = project
  .in(file("."))
  .aggregate(scala2Impl, dottyImpl, app)

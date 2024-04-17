import scala.scalanative.build.BuildTarget

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "3.3.3",
    nativeConfig ~= {
      _.withBuildTarget(BuildTarget.libraryDynamic)
    },
  )
  .enablePlugins(ScalaNativePlugin)

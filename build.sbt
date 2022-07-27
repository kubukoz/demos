val root = project
  .in(file("."))
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaVersion := "2.13.7",
    externalNpm := {
      sys.process.Process(List("npm", "install")).!
      (ThisBuild / baseDirectory).value
    }
  )

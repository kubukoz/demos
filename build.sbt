Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.8"
  )
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .settings(
    Seq(
      externalNpm := {
        import sys.process._
        "yarn".!
        (ThisBuild / baseDirectory).value
      },
      Compile / fastOptJS / artifactPath := (ThisBuild / baseDirectory).value / "main.js",
      Compile / fullOptJS / artifactPath := (ThisBuild / baseDirectory).value / "main.js",
      test := {},
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) }
    )
  )

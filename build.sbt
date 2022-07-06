import smithy4s.codegen.Smithy4sCodegenPlugin

val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := "2.13.7",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-aws" % smithy4sVersion.value
    )
  )

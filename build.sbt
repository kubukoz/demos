Global / onChangedBuildSource := ReloadOnSourceChanges

val root = project
  .in(file("."))
  .settings(
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % "0.23.14",
      "com.disneystreaming.smithy4s" %% "smithy4s-aws-http4s" % smithy4sVersion.value,
    ),
  )
  .enablePlugins(Smithy4sCodegenPlugin)

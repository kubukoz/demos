ThisBuild / usePipelining := true

val models = project
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := "3.6.4",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value
    ),
  )

val root = project
  .in(file("."))
  .dependsOn(models)
  .settings(
    scalaVersion := "3.6.4",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-server" % "0.23.30",
      "org.http4s" %% "http4s-ember-client" % "0.23.30",
    ),
  )

ThisBuild / scalaVersion := "3.5.0"

val transformation = project
  .settings(
    scalaVersion := "2.12.20",
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.51.0"
    ),
    crossPaths := false,
  )

val codegen = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "ch.epfl.scala" % "spec" % "2.2.0-M2" % Smithy4s,
      "ch.epfl.scala" % "spec-traits" % "2.2.0-M2" % Smithy4s,
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
    ),
    Compile / smithy4sModelTransformers := List("rename-scala-namespace"),
    Compile / smithy4sAllDependenciesAsJars += (transformation / Compile / packageBin).value,
  )
  .dependsOn(transformation)
  .enablePlugins(Smithy4sCodegenPlugin)

val root = project
  .in(file("."))
  .aggregate(codegen, transformation)

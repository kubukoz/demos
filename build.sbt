ThisBuild / scalaVersion := "3.7.0-RC1"
ThisBuild / scalacOptions += "-deprecation"

lazy val transformation = project
  .settings(
    scalaVersion := "2.12.20",
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.56.0"
    ),
  )

lazy val codegen = project
  .settings(
    libraryDependencies ++= Seq(
      "ch.epfl.scala" % "spec" % "2.2.0-M2" % Smithy4s,
      "ch.epfl.scala" % "spec-traits" % "2.2.0-M2" % Smithy4s,
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
    ),
    Compile / smithy4sModelTransformers := List("rename-scala-namespace"),
    Compile / smithy4sAllDependenciesAsJars += (transformation / Compile / packageBin).value,
  )
  .enablePlugins(Smithy4sCodegenPlugin)

lazy val root = project
  .in(file("."))
  .aggregate(codegen, transformation)
  .dependsOn(codegen)

ThisBuild / scalaVersion := "2.13.11"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
    ),
    libraryDependencies ++=
      Seq(
        "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb
          .compiler
          .Version
          .scalapbVersion % "protobuf",
        "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
        "org.typelevel" %% "cats-core" % "2.9.0",
      ),
  )
  .enablePlugins(Smithy4sCodegenPlugin)

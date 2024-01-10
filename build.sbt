lazy val pluginInterface = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
  )

lazy val scala2Impl = project
  .dependsOn(pluginInterface)
  .settings(
    scalaVersion := "2.13.12",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % "0.18.5"
    ),
  )

lazy val dottyImpl = project
  .dependsOn(pluginInterface)
  .settings(
    scalaVersion := "3.3.1",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % "0.18.5"
    ),
  )

lazy val app = project
  .dependsOn(pluginInterface)
  .settings(
    scalaVersion := "2.13.12",
    buildInfoKeys ++=
      Seq[BuildInfoKey.Entry[_]](
        Def
          .task(
            List(
              (scala2Impl / Compile / fullClasspath).value.map(_.data).map(_.toString),
              (dottyImpl / Compile / fullClasspath).value.map(_.data).map(_.toString),
            )
          )
          .taskValue
          .named("classpaths")
      ),
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % "0.18.5"
    ),
  )
  .enablePlugins(BuildInfoPlugin)

lazy val root = project
  .in(file("."))
  .aggregate(scala2Impl, dottyImpl, pluginInterface, app)

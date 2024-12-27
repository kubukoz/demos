import sbt.VirtualAxis.PlatformAxis

val scalaVersions = List("3.6.2")

val leafblower4s = projectMatrix
  .jvmPlatform(
    scalaVersions,
    Seq(
      libraryDependencies ++= Seq(
        "net.java.dev.jna" % "jna" % "5.15.0"
      )
    ),
  )
  .jsPlatform(scalaVersions)
  .nativePlatform(scalaVersions)
  .settings(
    Compile / unmanagedResourceDirectories += {
      val suffix =
        virtualAxes
          .value
          .collectFirst { case p: PlatformAxis => p.directorySuffix }
          .get

      projectMatrixBaseDirectory.value.getAbsoluteFile / "src" / "main" / s"resources$suffix"
    }
  )

val root = project
  .in(file("."))
  .aggregate(leafblower4s.projectRefs: _*)
  .settings(
  )

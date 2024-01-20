def crossPlugin(
  x: sbt.librarymanagement.ModuleID
) = compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.polyvariant" % "better-tostring" % "0.3.17")
)

val commonSettings = Seq(
  scalaVersion := "3.3.1",
  scalacOptions --= Seq("-Xfatal-warnings"),
  scalacOptions ++= Seq(
    "-Wunused:all"
  ),
  name := "demo",
  Compile / doc / sources := Nil,
)

val playOutput = taskKey[Unit]("Play written output")

val app = crossProject(JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "io.chrisdavenport" %%% "crossplatformioapp" % "0.1.0"
    ) ++ compilerPlugins
  )
  .jvmConfigure(
    _.enablePlugins(JavaAppPackaging)
  )
  .nativeConfigure(
    _.settings(
      libraryDependencies ++= Seq(
        "com.armanbilge" %%% "epollcat" % "0.1.6",
        "com.lihaoyi" %%% "os-lib" % "0.9.3",
      ),
      nativeConfig ~= {

        _.withCompileOptions(
          Seq("-I/Users/kubukoz/projects/demos/lib")
        )
          .withLinkingOptions(
            Seq(
              "-L/Users/kubukoz/projects/demos/lib",
              "-rpath",
              "/Users/kubukoz/projects/demos/lib",
            )
          )
      },
      playOutput := {
        import sys.process._

        "open out.wav".!!
      },
    )
  )

val root = project
  .in(file("."))
  .aggregate(app.componentProjects.map(p => p: ProjectReference): _*)

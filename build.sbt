import scala.scalanative.build.BuildTarget

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

val app = crossProject(JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= compilerPlugins
  )
  .nativeConfigure(
    _.settings(
      nativeConfig ~= (
        _.withBuildTarget(BuildTarget.libraryStatic)
      )
    )
  )

val root = project
  .in(file("."))
  .aggregate(app.componentProjects.map(p => p: ProjectReference): _*)

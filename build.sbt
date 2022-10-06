val app = crossProject(JVMPlatform, NativePlatform, JSPlatform)
  .settings(
    scalaVersion := "3.2.0",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %%% "smithy4s-aws-kernel" % smithy4sVersion.value
    ),
  )
  .enablePlugins(Smithy4sCodegenPlugin)
  .jsSettings(
    scalaJSUseMainModuleInitializer := true
  )

val root = project
  .in(file("."))
  .aggregate(app.componentProjects.map(_.project): _*)

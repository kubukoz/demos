val demo = crossProject(JSPlatform, JVMPlatform)
  .settings(
    scalaVersion := "3.3.1",
    libraryDependencies ++= Seq(
      "com.disneystreaming" %%% "weaver-cats" % "0.8.3" % Test
    ),
  )

val root = project
  .in(file("."))
  .aggregate(demo.componentProjects.map(p => p: ProjectReference): _*)

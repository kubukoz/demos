val subproj = project

val subproj2 = project.enablePlugins(Smithy4sCodegenPlugin)

val root = project
  .in(file("."))
  .aggregate(subproj, subproj2)

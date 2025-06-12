val runtime = project

val tests = project
  .dependsOn(runtime)

val root = project.in(file(".")).aggregate(runtime, tests)

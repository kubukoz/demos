val core = project

val root = project
  .in(file("."))
  .aggregate(core)

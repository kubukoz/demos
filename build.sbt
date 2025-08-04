ThisBuild / scalaVersion := "3.7.2"
val interface = project

val implA = project.dependsOn(interface)
val implB = project.dependsOn(interface)

val root = project
  .in(file("."))
  .dependsOn(implA)
  .settings(
    envVars += (
      "B_IMPL_JAR" ->
        (implB / Compile / packageBin).value.getAbsolutePath,
    ),
    fork := true,
  )

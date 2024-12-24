ThisBuild / scalaVersion := "2.13.15"
ThisBuild / organization := "com.kubukoz.demo"

val demo = project
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect-kernel" % "3.5.7",
      "org.typelevel" %% "keypool" % "0.4.10" % Optional,
    )
  )

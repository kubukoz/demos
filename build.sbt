val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "com.disneystreaming" %% "weaver-cats" % "0.8.3" % Test,
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalameta" %% "munit" % "0.7.29" % Test,
    ),
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
  )

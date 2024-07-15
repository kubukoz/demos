import smithy4s.codegen.Smithy4sCodegenPlugin

val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := "3.4.2",
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-aws-http4s" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-client" % "0.23.27"
    ),
    fork := true,
    smithy4sAwsSpecs += AWS.sagemaker
  )

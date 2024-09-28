import smithy4s.codegen.Smithy4sCodegenPlugin

val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    scalaVersion := "2.13.15",
    libraryDependencies ++= Seq(
      // for compilation of generated only
      "com.disneystreaming.smithy4s" %% "smithy4s-aws-kernel" % smithy4sVersion.value,
      // to actually call it
      "com.disneystreaming.smithy4s" %% "smithy4s-aws-http4s" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-client" % "0.23.28"
    ),
    // some of these would be added if we were using disneystreaming/aws-smithy-sdk-specs, but that's out of date so we're not.
    // others should still be added in there.
    libraryDependencies ++= Seq(
      "smithy-aws-traits",
      "smithy-aws-cloudformation-traits",
      "smithy-aws-iam-traits",
      "smithy-waiters",
      "smithy-rules-engine",
      "smithy-aws-endpoints",
      "smithy-smoke-test-traits",
      "smithy-aws-smoke-test-model"
    ).map { art =>
      smithy4s.codegen.BuildInfo.smithyOrg % art % smithy4s.codegen.BuildInfo.smithyVersion % Smithy4s,
    },
    smithy4sAllowedNamespaces := List(
      // only needed because we're using smithy4sAllowedNamespaces at all (otherwise pretty much everything gets generated except for aws.* and smithy.*)
      "com.amazonaws.location",
      "com.amazonaws.sagemaker",
      "com.amazonaws.sns",

      // These weren't generated in smithy4s-core nor smithy4s-aws-kernel so we need to force generating them.
      "aws.cloudformation",
      "aws.test",
      "smithy.rules",
      "smithy.test"
    ),
    fork := true
  )

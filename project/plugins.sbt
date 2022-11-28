resolvers ++= Resolver.sonatypeOssRepos("releases")
addSbtPlugin(
  "com.disneystreaming.smithy4s" % "smithy4s-sbt-codegen" % "0.17.0-177-7156a5c"
)

// resolvers ++= Resolver.sonatypeOssRepos("snapshots")
// addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.4.16")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.11-SNAPSHOT")
// addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.10")

addSbtPlugin("com.eed3si9n" % "sbt-projectmatrix" % "0.11.0")

addSbtPlugin(
  "com.disneystreaming.smithy4s" % "smithy4s-sbt-codegen" % "0.18.42-13-165b35ef-20260405-1912-SNAPSHOT"
)

addDependencyTreePlugin

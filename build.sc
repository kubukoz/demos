import scalalib._

import mill.scalalib.publish._

object demo extends JavaModule with PublishModule {
  def publishVersion = "0.0.1-SNAPSHOT"
  def pomSettings = T(
    PomSettings(
      description = "",
      organization = "com.kubukoz",
      url = "https://github.com/kubukoz/demos",
      licenses = Seq(License.MIT),
      versionControl = VersionControl.github("kubukoz", "demo"),
      developers = Seq()
    )
  )

  def ivyDeps = Agg(
    ivy"com.disneystreaming.alloy:alloy-core:0.1.2"
  )

}

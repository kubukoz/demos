import mill._

import scalalib._

object millbuild extends MillBuildRootModule {

  override def ivyDeps = T {
    List(ivy"com.lihaoyi::upickle:3.1.4")
  }

}

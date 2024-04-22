import $ivy.`com.disneystreaming.smithy4s::smithy4s-mill-codegen-plugin::0.18.16`

import smithy4s.codegen.mill._
import mill._
import mill.scalalib._

object service extends Smithy4sModule {

  def millSourcePath = os.pwd

  def scalaVersion = "3.3.3"

  def foo = T {
    T.ctx().log.info(s"recomputing foo at ${java.time.Instant.now()}!")
    os.walk(smithy4sOutputDir().path).size
  }

}

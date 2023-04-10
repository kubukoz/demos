import $ivy.`com.disneystreaming.smithy4s::smithy4s-mill-codegen-plugin::0.17.5`

import smithy4s.codegen.mill._
import mill.define.Target

import mill._

import mill.scalalib._
import mill.scalalib._
import mill.scalajslib._
import mill.scalajslib.api._

object main extends ScalaJSModule with Smithy4sModule {
  def scalaVersion = "3.3.0-RC3"

  def scalaJSVersion: T[String] = "1.13.1"

  override def scalacOptions: Target[Seq[String]] = Seq("-no-indent", "-Wunused:imports")

  override def ivyDeps: T[Agg[Dep]] = Agg(
    ivy"io.indigoengine::tyrian-io::0.6.2",
    ivy"org.scala-js::scalajs-dom::2.4.0",
    ivy"com.disneystreaming.smithy4s::smithy4s-http4s::0.17.5",
    ivy"org.http4s::http4s-dom::0.2.7",
  )

  override def moduleKind: Target[ModuleKind] = T(ModuleKind.NoModule)

  /** Update the millw script.
    */
  def millw() = T.command {
    val target = mill
      .modules
      .Util
      .download("https://raw.githubusercontent.com/lefou/millw/main/millw")
    val millw = build.millSourcePath / "millw"
    os.copy.over(target.path, millw)
    os.perms.set(millw, os.perms(millw) + java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE)
    target
  }

}

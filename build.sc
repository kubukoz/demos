import mill._

import upickle.default._
import $meta._

object demo extends mill.Module {

  final case class Demo(
  )

  object Demo {
    implicit val readWriter: ReadWriter[Demo] = macroRW
  }

  def compile() = T.command {
    println(read[Demo]("""{"extra": 42}"""))

    ()
  }

}

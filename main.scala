//> using scala "3.2.2"
//> using lib "com.lihaoyi::cask:0.9.1"

import cask._

object demo extends MainRoutes {

  @get("/")
  def hello() = "Hello World!"

  initialize()
}

import $file.project.model

import model.exports._

doThing()

@
object demo extends Module {

  def thing: T[Thing] = T {
    "aa": Thing
  }

}

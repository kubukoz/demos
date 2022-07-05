import scala.scalajs.js.annotation.JSExportTopLevel
import typings.obsidian.mod.Plugin

@JSExportTopLevel("default")
class MyPlugin extends Plugin {
  override def onload(): Unit = {
    println("hello app!")
    throw new Exception("oops")
  }
}

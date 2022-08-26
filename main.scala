//> using scala "2.13.8"
//> using lib "com.kubukoz::debug-utils:1.1.3"
import util.chaining._
import com.kubukoz.DebugUtils

object main2 {

  trait Setter[S, A]

  case class user(name: String, age: Int) {
    // def name_=(v: String): User = ???
    def name_=(newName: String): user = ???
  }

  object user {
    def name_=(newName: String): user => user = ???
  }

  val u: user = ???

  DebugUtils.withDesugar(u.name = "foo")

}

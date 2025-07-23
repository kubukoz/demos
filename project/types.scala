import sjsonnew.JsonFormat

import sbt.util.CacheImplicits._
import sbt.util.Cache

case class MyCaseClass(
  name: String,
  age: Int,
)

object MyCaseClass {

  implicit val jsonFormat: JsonFormat[MyCaseClass] =
    caseClass(MyCaseClass.apply _, MyCaseClass.unapply _)("name", "age")

}

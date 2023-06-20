//> using lib "com.disneystreaming.smithy4s::smithy4s-core:0.17.8"
//> using lib "org.typelevel::cats-effect:3.5.0"
//> using scala "2.13"
package foo.bar

import demo._
import smithy4s._
import cats.effect.IO

case class AssignmentsDecoded(value: Map[String, String])

object AssignmentsDecoded {

  def from(s: String): Either[String, AssignmentsDecoded] = ??? // TODO
  def to(ad: AssignmentsDecoded): String = ??? // TODO

  implicit val refinementProvider: RefinementProvider[ProtobufEncoded, String, AssignmentsDecoded] =
    Refinement
      .drivenBy[ProtobufEncoded]
      .apply(from, to)

}

object example {

  new MyService[IO] {

    override def myOperation(
      assignments: Option[Assignments]
    ): IO[Unit] = IO {
      assignments.get.value.value.foreach(println)
    }

  }
}

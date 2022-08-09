//> using scala "2.13.8"
//> using lib "io.circe::circe-parser:0.14.2"
//> using lib "io.circe::circe-generic:0.14.2"
//> using lib "io.circe::circe-literal:0.14.2"
import cats.implicits._
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.generic.auto._
import io.circe.literal._
import io.circe.Encoder
import io.circe.syntax._

case class StringEncoded[A](value: A)
object StringEncoded {
  implicit def decoder[A: Decoder]: Decoder[StringEncoded[A]] =
    Decoder[String]
      .emapTry(io.circe.parser.decode[A](_).toTry)
      .map(StringEncoded(_))

  implicit def encoder[A: Encoder]: Encoder[StringEncoded[A]] =
    _.value.asJson.noSpaces.asJson
}

final case class Subscription(
    components: List[Component]
)

final case class Component(
    json: StringEncoded[ComponentJson]
)

final case class ComponentJson(
    policyId: Long
)

object main extends App {

  val example = json"""{
    "components": [
      {
        "json": "{\"policyId\": 1}"
      }
    ]
  }"""

  println(example.as[Subscription].toOption.get.asJson)
}

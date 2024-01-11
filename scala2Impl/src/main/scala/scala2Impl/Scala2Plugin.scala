package scala2Impl

import plugin.Plugin
import java.nio.file.Path
import java.{util => ju}
import java.nio.file.Paths
import scala.jdk.CollectionConverters._

class Scala2Plugin extends Plugin {

  def generate(data: ju.Map[String, String]): ju.List[Path] = {

    import smithy4s._

    val doc = Document.DObject(
      data.asScala.map { case (k, v) => k -> Document.fromString(v) }.toMap
    )

    println(Document.decoderFromSchema[Greeting].decode(doc))
    println(doc.getClass().getResource("/smithy4s/Document.class"))

    java
      .util
      .List
      .of(
        Paths.get("/scala-2")
      )
  }

}

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class Greeting(hello: Option[String] = None, foo: Option[String] = None)

object Greeting extends ShapeTag.Companion[Greeting] {
  val id: ShapeId = ShapeId("input", "Greeting")

  val hints: Hints = Hints.empty

  implicit val schema: Schema[Greeting] = struct(
    string.optional[Greeting]("hello", _.hello),
    string.optional[Greeting]("foo", _.foo),
  ) {
    Greeting.apply
  }.withId(id).addHints(hints)

}

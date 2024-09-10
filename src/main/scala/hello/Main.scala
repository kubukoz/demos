package hello

import smithy4s.json.Json
import smithy4s.Blob
import smithy4s.dynamic.DynamicSchemaIndex
import software.amazon.smithy.model.Model
import java.nio.file.Files
import java.nio.file.Paths
import smithy4s.ShapeId
import smithy4s.Document

object Main extends App {
  val body = Blob("""{"item": null}""")

  println("Person direct: " + Json.read[Person](body))
  println("Person2 direct: " + Json.read[Person2](body))
  println("Person3 direct: " + Json.read[Person3](body))

  val dsi = DynamicSchemaIndex.loadModel(
    Model
      .assembler()
      .addImport(Paths.get("src/main/smithy/hello.smithy"))
      .assemble()
      .unwrap()
  )

  def readDynamic(shapeId: ShapeId) = {
    val schema = dsi.getSchema(shapeId).get
    val encoder = Document.Encoder.fromSchema(schema)
    println(s"${shapeId.name} dynamic: " + Json.read(body)(using schema).map(encoder.encode(_)))
  }

  readDynamic(ShapeId.parse("hello#Person").get)
  readDynamic(ShapeId.parse("hello#Person2").get)
  readDynamic(ShapeId.parse("hello#Person3").get)

}

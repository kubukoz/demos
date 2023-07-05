//> using scala "2.13.10"
//> using lib "software.amazon.smithy:smithy-jsonschema:1.33.0"
import software.amazon.smithy.jsonschema.JsonSchemaConverter
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.node.Node
import software.amazon.smithy.model.shapes.ShapeId

val r = Model
  .assembler()
  .addUnparsedModel(
    "test.smithy",
    """namespace test
      |
      |structure Foo { @required s: String }
      |""".stripMargin,
  )
  .assemble()
  .unwrap()

println {
  Node.prettyPrintJson {
    JsonSchemaConverter
      .builder()
      .model(r)
      .build()
      .convertShape(
        r.expectShape(ShapeId.from("test#Foo"))
      )
      .toNode()
  }
}

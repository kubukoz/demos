package demo

import software.amazon.smithy.model.shapes.ModelSerializer
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.node.Node
import smithy4s.dynamic.DynamicSchemaIndex
import smithy4s.ShapeId
import smithy4s.schema.Schema

object Main extends App {
  val s = """
$version: "2"
namespace test

@mixin
structure HasData {
  @required
  data: String
}

structure Data with [HasData] {
  @required
  id: Integer
}
  """

  val model = Model.assembler().addUnparsedModel("test.smithy", s).assemble().getResult().get()

  val serialized = ModelSerializer
    .builder()
    .build()
    .serialize(model)

  println(Node.prettyPrintJson(serialized))

  println(
    DynamicSchemaIndex
      .loadModel(model)
      .toTry
      .get
      .getSchema(ShapeId("test", "Data"))
      .get
      .asInstanceOf[Schema.StructSchema[_]]
      .fields
      .map(_.label)
  )
}

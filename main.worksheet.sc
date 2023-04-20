//> using scala "2.13.10"
//> using lib "software.amazon.smithy:smithy-model:1.30.0"
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.shapes.ShapeId
import scala.jdk.CollectionConverters._
import software.amazon.smithy.model.transform.ModelTransformer
import software.amazon.smithy.model.shapes.ServiceShape
import software.amazon.smithy.model.shapes.OperationShape

val r = Model
  .assembler()
  .addUnparsedModel(
    "test.smithy",
    """namespace test
      |
      |structure Foo { @required s: String }
      |""".stripMargin,
  )
  .addUnparsedModel(
    "test2.smithy",
    """namespace test
      |
      |structure Foo { s: String }
      |""".stripMargin,
  )
  .assemble()

r.getValidationEvents().asScala.toList

val m = r.unwrap()

// ModelTransformer.create().

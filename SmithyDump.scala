//> using dep software.amazon.smithy:smithy-model:1.54.0
package com.kubukoz

import software.amazon.smithy.model.Model
import software.amazon.smithy.model.shapes.ShapeId
import java.util.stream.Collectors
import software.amazon.smithy.model.shapes.SmithyIdlModelSerializer
import software.amazon.smithy.model.transform.ModelTransformer
import software.amazon.smithy.model.shapes.ModelSerializer
import software.amazon.smithy.model.node.Node

object SmithyDump {

  def dump(input: String): String = {
    val model = Model
      .assembler()
      .addUnparsedModel("test.smithy", input)
      .assemble()
      .unwrap();

    try Node.printJson(
        ModelSerializer
          .builder()
          .build()
          .serialize(
            ModelTransformer.create().flattenAndRemoveMixins(model)
          )
      )
    catch {
      case e: Exception =>
        e.printStackTrace()
        throw e
    }
  }

}

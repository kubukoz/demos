import software.amazon.smithy.build.ProjectionTransformer

import software.amazon.smithy.build.TransformContext
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.transform.ModelTransformer
import scala.collection.JavaConverters._
import java.util.stream.Collectors
import software.amazon.smithy.model.shapes.ShapeId

class RenameScalaNamespace extends ProjectionTransformer {
  def getName(): String = "rename-scala-namespace"

  val renames = Map(
    "bsp.scala" -> "bsp.scala_",
    "bsp.java" -> "bsp.java_",
    "traits" -> "bsp.traits",
  )

  object Renamed {

    def unapply(id: ShapeId): Option[ShapeId] = renames.collectFirst {
      case (prefix, prefixRename) if id.getNamespace().startsWith(prefix) =>
        id.withNamespace(id.getNamespace().replace(prefix, prefixRename))
    }

  }

  def transform(context: TransformContext): Model = ModelTransformer
    .create()
    .renameShapes(
      context.getModel(),
      context
        .getModel()
        .getShapeIds()
        .asScala
        .collect { case id @ Renamed(renamed) => id -> renamed }
        .toMap
        .asJava,
    )

}

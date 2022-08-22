//> using scala "2.13.8"
//> using lib "com.disneystreaming.smithy4s::smithy4s-dynamic:0.15.1"
//> using lib "com.disneystreaming.smithy4s::smithy4s-codegen:0.15.1"
import smithy4s.codegen.ModelLoader
import smithy4s.dynamic.DynamicSchemaIndex
import scala.util.Try

object Main extends App {

  def buildSchemaIndex(): Try[DynamicSchemaIndex] = Try {
    ModelLoader
      .load(
        specs = Set.empty,
        dependencies = Nil,
        repositories = Nil,
        transformers = Nil,
        discoverModels = true,
        localJars = Nil,
      )
      ._2
  }
    .flatMap { model =>
      DynamicSchemaIndex.loadModel(model).toTry
    }

  println(buildSchemaIndex())
}

//> using scala 3.7.0-RC1
//> using dep software.amazon.smithy:smithy-model:1.56.0
//> using dep io.get-coursier:coursier_2.13:2.1.0
//> using dep org.typelevel::cats-core:2.13.0
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.shapes.ShapeId
import scala.jdk.CollectionConverters._
import software.amazon.smithy.model.transform.ModelTransformer
import software.amazon.smithy.model.shapes.ServiceShape
import software.amazon.smithy.model.shapes.OperationShape
import coursier.Fetch
import coursier.parse.DependencyParser
import cats.syntax.all.*
import util.chaining.*
import java.nio.file.Paths
import java.nio.file.FileSystems
import scala.util.Using
import java.nio.file.Files

val artifacts = List(
  "com.disneystreaming.smithy:aws-ec2-spec:2025.03.31-4-a9e6d2-SNAPSHOT"
)

val fetched = Fetch()
  .withDependencies(
    artifacts.map(DependencyParser.dependency(_, "3.7.0-RC1").leftMap(sys.error(_)).merge)
  )
  .run()

val r = Model
  .assembler()
  .tap { assem =>
    fetched
      .map(_.toPath())
      .filter { p =>
        Using.resource(FileSystems.newFileSystem(p)) { fs =>
          Files.exists(fs.getPath("META-INF", "smithy", "manifest"))
        }
      }
      .tapEach(assem.addImport(_))
  }
  .assemble()

r.getValidationEvents().asScala.toList

val m = r.unwrap()

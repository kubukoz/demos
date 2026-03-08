//> using scala 3.8.2
//> using dep "com.lihaoyi::upickle:4.4.3"
//> using dep "software.amazon.smithy:smithy-model:1.68.0"
//> using dep "io.github.simple-scala-tooling:lsp-smithy-definitions:0.1.0"
//> using dep "com.lihaoyi::os-lib:0.11.8"
//> using options -no-indent
import upickle.default.*
import software.amazon.smithy.model.Model
import scala.jdk.OptionConverters.*
import jsonrpclib.JsonRpcRequestTrait
import scala.jdk.CollectionConverters.*
import jsonrpclib.JsonRpcNotificationTrait
import software.amazon.smithy.model.shapes.ServiceShape
import software.amazon.smithy.model.shapes.ModelSerializer
import software.amazon.smithy.model.shapes.SmithyIdlModelSerializer

case class RequestSmall(method: String, messageDirection: String) derives ReadWriter
case class MetaModelSmall(requests: List[RequestSmall], notifications: List[RequestSmall])
  derives ReadWriter

@main def generate = {
  val metamodel = read[MetaModelSmall](
    os.read(os.pwd / os.up / "lsp-smithy" / "lspSmithy" / "resources" / "metaModel.json")
  )
  val metaOps =
    (metamodel.requests ++ metamodel.notifications).map(r => r.method -> r.messageDirection).toMap

  val model = Model.assembler().discoverModels().assemble().unwrap()
  val requests = model.getShapesWithTrait(classOf[JsonRpcRequestTrait]).asScala.toList
  val notifs = model.getShapesWithTrait(classOf[JsonRpcNotificationTrait]).asScala.toList
  val ops = requests ++ notifs

  val opsWithDir = ops
    .map { op =>
      val method = op
        .getTrait(classOf[JsonRpcRequestTrait])
        .toScala
        .map(_.getValue())
        .orElse(
          op.getTrait(classOf[JsonRpcNotificationTrait]).toScala.map(_.getValue())
        )
        .getOrElse(sys.error(s"Operation ${op.getId()} is missing a method trait"))

      val direction = metaOps(method)

      op -> direction
    }

  val serverOps = opsWithDir.filterNot(_._2 == "serverToClient").map(_._1.getId())
  val clientOps = opsWithDir.filterNot(_._2 == "clientToServer").map(_._1.getId())

  val serverService = ServiceShape
    .builder()
    .id("lsp#LSPServer")
    .operations(serverOps.asJava)
    .build()
  val clientService = ServiceShape
    .builder()
    .id("lsp#LSPClient")
    .operations(clientOps.asJava)
    .build()

  val newModel = model.toBuilder().addShape(serverService).addShape(clientService).build()

  val serialized = SmithyIdlModelSerializer
    .builder()
    .shapeFilter(shp => Set(serverService, clientService).contains(shp))
    .metadataFilter(_ => false)
    .build()
    .serialize(newModel)
    .asScala
    .toMap
    .filterNot(_._1.endsWith("metadata.smithy"))

  require(serialized.size == 1, serialized.keySet.toList)
  os.write.over(os.pwd / "services.smithy", serialized.head._2)
}

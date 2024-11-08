import java.util.ServiceLoader
import scala.jdk.CollectionConverters.*

@main def demo = println(ServiceLoader.load(classOf[com.kubukoz.API]).asScala.toList.map(_.name))

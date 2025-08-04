import java.util.ServiceLoader

import java.net.URLClassLoader
import scala.jdk.CollectionConverters.*
import java.nio.file.Paths

object Main {

  def main(args: Array[String]): Unit = {

    new URLClassLoader(
      Array(
        Paths
          .get(sys.env("B_IMPL_JAR"))
          .toUri()
          .toURL()
      ),
      this.getClass().getClassLoader(),
    ).getResources("META-INF/services/API").asScala.foreach(println)

    val services =
      ServiceLoader
        .load(
          classOf[API],
          new URLClassLoader(
            Array(
              Paths
                .get(sys.env("B_IMPL_JAR"))
                .toUri()
                .toURL()
            ),
            this.getClass().getClassLoader(),
          ),
        )
        .asScala
        .toList

    services.foreach { service =>
      println(service.name())
    }
  }

}

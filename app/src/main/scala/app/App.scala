package app

import java.nio.file.Path
import java.util.ServiceLoader
import buildinfo.BuildInfo
import java.net.URLClassLoader
import java.nio.file.Paths
import smithy4s.Document
import scala.jdk.CollectionConverters._
import java.lang

trait Plugin {
  def generate(data: Map[String, String]): List[Path]
}

object Plugin {
  def fromJava(javaPlugin: plugin.Plugin): Plugin =
    data => javaPlugin.generate(data.asJava).asScala.toList

  // this could be handwritten but it's using a Proxy for convenience.
  // We basically have to call the object's method with the same name and signature dynamically.
  def fromForeignJava[T](obj: T, clazz: Class[T]): plugin.Plugin = lang
    .reflect
    .Proxy
    .newProxyInstance(
      classOf[plugin.Plugin].getClassLoader(),
      Array(classOf[plugin.Plugin]),
      new lang.reflect.InvocationHandler {

        def invoke(proxy: Object, method: lang.reflect.Method, args: Array[Object]): Object = {
          val targetMethod = clazz.getMethod(method.getName(), method.getParameterTypes(): _*)
          targetMethod.invoke(obj, args: _*)
        }

      },
    )
    .asInstanceOf[plugin.Plugin]

}

object App {

  def main(args: Array[String]): Unit = {

    val data = Map("hello" -> "greeting", "foo" -> "42")

    locally {

      import smithy4s.Hints
      import smithy4s.Schema
      import smithy4s.ShapeId
      import smithy4s.ShapeTag
      import smithy4s.schema.Schema.int
      import smithy4s.schema.Schema.string
      import smithy4s.schema.Schema.struct

      final case class Greeting(hello: Option[String] = None, foo: Option[String] = None)

      object Greeting extends ShapeTag.Companion[Greeting] {
        val id: ShapeId = ShapeId("input", "Greeting")

        val hints: Hints = Hints.empty

        implicit val schema: Schema[Greeting] = struct(
          string.optional[Greeting]("hello", _.hello),
          string.optional[Greeting]("foo", _.foo),
        ) {
          Greeting.apply
        }.withId(id).addHints(hints)

      }

      val doc = Document.DObject(
        data.map { case (k, v) => k -> Document.fromString(v) }
      )

      println(Document.decoderFromSchema[Greeting].decode(doc))

    }

    val root =
      new URLClassLoader(
        BuildInfo
          .pluginClassPath
          .map { path =>
            Paths.get(path).toUri().toURL()
          }
          .toArray,
        null,
      )

    val foreignPluginClass = root.loadClass(classOf[plugin.Plugin].getName())

    BuildInfo.classpaths.zipWithIndex.map { case (cp, i) =>
      println(s"Classpath ${i + 1}/${BuildInfo.classpaths.size}: ${cp.head}...")

      val cl =
        new URLClassLoader(
          cp.map { path =>
            Paths.get(path).toUri().toURL()
          }.toArray,
          root,
        )

      val plugins = ServiceLoader
        .load(foreignPluginClass, cl)
        .asScala
        .toList
        .map { foreignPlugin =>
          Plugin
            .fromForeignJava(foreignPlugin, foreignPluginClass.asInstanceOf[Class[Any]])
        }
        .map(Plugin.fromJava)

      plugins.flatMap(_.generate(data)).foreach(println)

    }

  }

}

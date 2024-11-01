//> using repository sonatype-s01:snapshots
//> using dep org.polyvariant.treesitter4s::language-python::0.3-e777c6d-SNAPSHOT
import org.polyvariant.treesitter4s.TreeSitterAPI
import org.polyvariant.treesitter4s.Tree
import scala.concurrent.duration.*
import org.polyvariant.treesitter4s.Node

val start = System.nanoTime()

val input =
  """
    |def foo(x):
    |    return x + 1
    |
    |def bar(y):
    |    def baz(z):
    |        return z + 1
    |
    |    return y + 2
    |""".stripMargin

val parsed = TreeSitterAPI
  .make("python")
  .parse(input)

def allNodes(node: Node): LazyList[Node] = node.fold(_ #:: _.to(LazyList).flatten)

allNodes(parsed.rootNode.get)
  .filter(_.tpe == "function_definition")
  .map(allNodes(_).map(n => n.tpe -> n.source).mkString("\n"))
  .head

val allTypes = allNodes(parsed.rootNode.get)
  .map(_.tpe)
  .toSet
  .mkString(", ")

val allFields = allNodes(parsed.rootNode.get)
  .flatMap(_.fields.keySet)
  .toSet
  .mkString(", ")

val allIdentifiers = allNodes(parsed.rootNode.get)
  .filter(_.tpe == "identifier")
  .map(_.source)
  .toSet
  .mkString(", ")

val end = System.nanoTime()

allNodes(parsed.rootNode.get).find(_.source == "1").get.source

val nodeAtCursor: Int => Node = { i =>
  allNodes(parsed.rootNode.get)
    .filter(n => n.startByte <= i && n.endByte >= i)
    .minBy(_.source.length)
}

nodeAtCursor(69 + 4).source

((end - start) / 1e6).toString() + "ms"

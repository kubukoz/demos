//> using scala 3.7.0
//> using dep software.amazon.smithy:smithy-model:1.58.0
//> using dep org.typelevel::cats-core:2.13.0
//> using option -Wunused:imports
import cats.syntax.all.*
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.neighbor.NeighborProvider
import software.amazon.smithy.model.neighbor.RelationshipDirection
import software.amazon.smithy.model.neighbor.Walker
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.shapes.Shape

import scala.jdk.OptionConverters.*
import java.nio.file.Files
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

import util.chaining.*
import software.amazon.smithy.model.shapes.ShapeId

def closure(
  shapes: List[Shape]
)(
  using m: Model
) = shapes.flatMap(Walker(m).walkShapes(_).asScala).distinct

def startingShapes(
  using m: Model
) = closure(
  m
    .shapes()
    .toList()
    .asScala
    .filter(_.getSourceLocation().getFilename() == "test.smithy")
    .toList
)

val keyword = "#C678DD"

def renderShapeId(s: ShapeId): String = {
  val nsPart = s"${s.getNamespace()}"
  val namePart = s"${s.getName()}"
  val memberPart = s
    .getMember()
    .toScala
    .map(m => s"""$$$m""")
    .getOrElse("")

  s"<span style=\"color: #7F848E\">$nsPart#</span>$namePart$memberPart"
}

def renderHighlights(
  selectorAndStyles: (String, String)*
)(
  showVariables: Boolean = false
)(
  using m: Model
) = {

  val relationships = startingShapes
    .flatMap { shape =>
      m.pipe(NeighborProvider.of)
        // .pipe(NeighborProvider.withTraitRelationships(m, _))
        .getNeighbors(shape)
        .asScala
        .filter(_.getDirection == RelationshipDirection.DIRECTED)
    }

  val allShapesToRender =
    List.concat(startingShapes, relationships.map(_.expectNeighborShape())).toSet

  val shapeDefs = allShapesToRender
    .map { shape =>
      val shapeType = s"""<span style="color:$keyword">${shape.getType().toString()}</span>"""

      s"""  ${shape.getId()}[$shapeType&nbsp;${renderShapeId(shape.getId())}]"""
    }

  val shapeConnections = relationships
    .map { rel =>
      s"    ${rel.getShape.getId()} --> ${rel.getNeighborShapeId()}"
    }

  val (selections, highlightStyles) =
    selectorAndStyles
      .map { (selectorText, selectorStyle) =>
        Selector
          .parse(selectorText)
          .matches(m)
          .toList()
          .asScala
          .toList
          .filter(sm => allShapesToRender.contains(sm.getShape()))
          .groupBy(_.getShape()) -> selectorStyle
      }
      .toList
      .separateFoldable

  val shapeStyles = selections.zipWithIndex.map { (matches, i) =>
    s"  class ${matches.keySet.map(_.getId()).mkString(",")} highlight$i;"
  }

  val highlightDefs = highlightStyles
    .zipWithIndex
    .map((style, i) => s"  classDef highlight$i $style;")

  val variableRefs =
    if !showVariables then Nil
    else
      selections.zipWithIndex.flatMap { (matches, i) =>
        matches.flatMap { (shape, matches) =>
          matches.flatMap { shapeMatch =>
            shapeMatch.asScala.toMap.flatMap { (variableName, shapesForVariable) =>
              shapesForVariable.asScala.map { shapeForVariable =>
                s"  ${shape.getId()} --> |$i: $variableName|${shapeForVariable.getId()}"
              }
            }
          }
        }
      }

  val str = List
    .concat(
      shapeDefs,
      shapeConnections,
      variableRefs,
      highlightDefs,
      shapeStyles,
    )
    .mkString(
      s"""```mermaid
         |%%{init: {"flowchart": {"htmlLabels": false}} }%%
         |graph
         |""".stripMargin,
      "\n",
      "\n```",
    )

  Files.writeString(Paths.get("output.md"), str)

}

given Model = Model
  .assembler()
  .addUnparsedModel(
    "test.smithy",
    """$version: "2"
      |namespace test
      |
      |service Hello {
      |  operations: [GetHello, PostHello]
      |}
      |
      |
      |/// an operation
      |@http(method: "GET", uri: "/hello")
      |operation GetHello {
      |  output: Foo
      |}
      |
      |/// an operation as well
      |@http(method: "POST", uri: "/hello")
      |operation PostHello {
      |  input: Foo
      |}
      |
      |structure Foo { @required s: String }
      |""".stripMargin,
  )
  .assemble()
  .unwrap()

renderHighlights(
  "*" -> "font-family: monospace",
  "$op(operation) ${op} ~>" -> "fill:#882200,color:black",
  // "operation" -> "fill: #98C379,color:black",
  "service" -> "fill: #def,color:black",
)(
  // showVariables = true
)

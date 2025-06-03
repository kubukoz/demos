//> using scala 3.7.0
//> using dep software.amazon.smithy:smithy-model:1.58.0
//> using dep org.typelevel::cats-core:2.13.0
//> using option -Wunused:imports
import cats.syntax.all.*
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.neighbor.NeighborProvider
import software.amazon.smithy.model.neighbor.Relationship
import software.amazon.smithy.model.neighbor.RelationshipDirection
import software.amazon.smithy.model.neighbor.Walker
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.selector.Selector.ShapeMatch
import software.amazon.smithy.model.shapes.Shape
import software.amazon.smithy.model.shapes.ShapeId

import java.nio.file.Files
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import util.chaining.*

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

def shouldRender(sm: ShapeMatch, visible: Shape => Boolean) =
  visible(sm.getShape()) &&
    sm.values.asScala.flatMap(_.asScala).forall(visible)

def shouldRender(rel: Relationship, visible: Shape => Boolean) =
  visible(rel.getShape()) &&
    visible(rel.expectNeighborShape())

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
    List
      .concat(startingShapes, relationships.map(_.expectNeighborShape()))
      .toSet
  // todo: filtering here to narrow large graphs to just relevant parts.
  // e.g. compute neighbors of shapes that matched selectors up to a selected level of depth
  // .filter(_.isStructureShape())

  val shapeDefs = allShapesToRender
    .map { shape =>
      val shapeType = s"""<span style="color:$keyword">${shape.getType().toString()}</span>"""

      s"""  ${shape.getId()}[$shapeType&nbsp;${renderShapeId(shape.getId())}]"""
    }

  val shapeConnections = relationships
    .filter(shouldRender(_, allShapesToRender))
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
          .filter(shouldRender(_, allShapesToRender))
          .groupBy(_.getShape()) -> selectorStyle
      }
      .toList
      .separateFoldable

  val shapeStyles = selections
    .zipWithIndex
    // for selectors that didn't match anything
    .filterNot(_._1.isEmpty)
    .map { (matches, i) =>
      s"  class ${matches.keySet.map(_.getId()).mkString(",")} highlight$i;"
    }

  val highlightDefs = highlightStyles
    .zipWithIndex
    .map((style, i) => s"  classDef highlight$i $style;")

  val variableRefs =
    for {
      (selectorMatches, i) <- selections.zipWithIndex
      if showVariables

      (shape, matches) <- selectorMatches
      shapeMatch <- matches
      (variableName, shapesForVariable) <- shapeMatch.asScala.toMap
      shapeForVariable <- shapesForVariable.asScala
    } yield s"  ${shape.getId()} --> |$i: $variableName|${shapeForVariable.getId()}"

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
      |  output:={foo: Foo}
      |}
      |
      |/// an operation as well
      |@http(method: "POST", uri: "/hello")
      |operation PostHello {
      |  input:={foo: Foo}
      |}
      |
      |structure Foo { @required s: String }
      |""".stripMargin,
  )
  .assemble()
  .unwrap()

renderHighlights(
  "*" -> "font-family: monospace",
  "$op(operation) ${op} ~> structure" -> "fill:#882200,color:black",
  "operation" -> "fill: #98C379,color:black",
  "service" -> "fill: #def,color:black",
)(
  showVariables = true
)

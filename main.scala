//> using scala 3.7
//> using dep org.http4s::http4s-ember-server:0.23.30
//> using dep org.http4s::http4s-dsl:0.23.30
//> using dep org.http4s::http4s-scalatags::0.25.2
//> using dep software.amazon.smithy:smithy-model:1.58.0
//> using option -Wunused:imports
//> using option -Wunused:all
import cats.effect.*
import cats.syntax.all.*
import org.http4s.{h2 => _, *}
import org.http4s.dsl.io.*
import org.http4s.ember.server.*
import org.http4s.implicits.*
import org.http4s.scalatags.*
import _root_.scalatags.Text.all.*
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.neighbor.NeighborProvider
import software.amazon.smithy.model.neighbor.Relationship
import software.amazon.smithy.model.neighbor.RelationshipDirection
import software.amazon.smithy.model.neighbor.Walker
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.selector.Selector.ShapeMatch
import software.amazon.smithy.model.shapes.Shape
import software.amazon.smithy.model.shapes.ShapeId

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.util.chaining.*
import scala.util.Using

object SelectorPlayground extends IOApp.Simple {

  val initModel =
    """$version: "2"
      |namespace test
      |
      |service Hello {
      |  operations: [GetHello, PostHello]
      |}
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
      |""".stripMargin

  val initSelector = "structure"

  val indexPage = html(
    meta(charset := "utf-8"),
    head(
      script(src := "https://unpkg.com/htmx.org@2.0.4"),
      script(src := "https://cdn.jsdelivr.net/npm/mermaid@10.9.0/dist/mermaid.min.js"),
      script("mermaid.initialize({ startOnLoad: true, flowchart: { htmlLabels: true } });"),
      script(
        raw("""
          document.addEventListener('DOMContentLoaded', () => {
            document.body.addEventListener('htmx:afterSettle', () => {
              if (window.mermaid) {
                mermaid.run().then(() => {
                  document.querySelectorAll('.mermaid').forEach(el => {
                    el.style.visibility = 'visible';
                  });
                });
              }
            });
          });
        """)
      ),
      _root_
        .scalatags
        .Text
        .tags2
        .style(
          """
        body {
          font-family: sans-serif;
          margin: 0;
          padding: 1rem;
        }
        .container {
          display: flex;
        }
        .left, .right {
          flex: 1;
          padding: 1rem;
        }
        form {
          display: flex;
          flex-direction: column;
          width: 100%;
          height: 100%;
        }
        textarea.primary {
          width: 100%;
          height: 100px;
          display: block;
          margin-bottom: 1rem;
          flex-grow: 2;
        }
        .right {
          overflow: scroll;
        }
      """
        ),
    ),
    body(
      h1("Smithy Selector Playground"),
      div(
        cls := "container",
        div(
          cls := "left",
          form(
            id := "input-form",
            attr("hx-post") := "/render",
            attr("hx-target") := "#result",
            attr("hx-trigger") := "load, input from:textarea",
            p("Model:"),
            textarea(
              autocomplete := "off",
              cls := "primary",
              name := "modelText",
              placeholder := "Model text",
              initModel,
            ),
            p("Selector:"),
            textarea(
              autocomplete := "off",
              name := "selectorText",
              placeholder := "Selector text",
              initSelector,
            ),
          ),
        ),
        div(cls := "right", div(id := "result", em("Results will appear here..."))),
      ),
    ),
  )

  val routes = HttpRoutes.of[IO] {
    case GET -> Root => Ok(indexPage)

    case req @ POST -> Root / "render" =>
      req
        .as[UrlForm]
        .flatMap { form =>
          given Model = Model
            .assembler()
            .addUnparsedModel(
              "test.smithy",
              form.getFirst("modelText").getOrElse(""),
            )
            .assemble()
            .unwrap()

          val selector = form.getFirst("selectorText").getOrElse("*")

          val rendered =
            Smithy
              .renderHighlights(selector -> "fill:#882200,color:black,font-family:monospace")(
                showVariables = true
              )

          Ok(
            div(
              cls := "mermaid",
              style := "visibility: hidden;",
              raw(rendered),
            )
          )
        }
        .recoverWith { case e =>
          def slurpStackTrace(e: Throwable) =
            Using.resource(new java.io.StringWriter()) { sw =>
              e.printStackTrace(new java.io.PrintWriter(sw))
              sw.toString
            }

          Ok(
            div(
              cls := "error",
              h2("Error rendering shapes"),
              pre(code(slurpStackTrace(e))),
            )
          )

        }
  }

  override def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHttpApp(routes.orNotFound)
      .withErrorHandler { case e => IO.consoleForIO.printStackTrace(e) *> IO.raiseError(e) }
      .build
      .evalTap(server => IO.println(s"Server running at ${server.baseUri}"))
      .useForever

}

object Smithy {

  def closure(
    shapes: List[Shape]
  )(
    using m: Model
  ): List[Shape] = shapes.flatMap(Walker(m).walkShapes(_).asScala).distinct

  def startingShapes(
    using m: Model
  ): List[Shape] = closure(
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

  def shouldRender(sm: ShapeMatch, visible: Shape => Boolean): Boolean =
    visible(sm.getShape()) &&
      sm.values.asScala.flatMap(_.asScala).forall(visible)

  def shouldRender(rel: Relationship, visible: Shape => Boolean): Boolean =
    visible(rel.getShape()) &&
      visible(rel.expectNeighborShape())

  def renderHighlights(
    selectorAndStyles: (String, String)*
  )(
    showVariables: Boolean = false
  )(
    using m: Model
  ): String = {

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

    List
      .concat(
        shapeDefs,
        shapeConnections,
        variableRefs,
        highlightDefs,
        shapeStyles,
      )
      .mkString(
        s"""graph TD
           |""".stripMargin,
        "\n",
        "\n",
      )
  }

}

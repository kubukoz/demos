//> using scala 3.7.0
//> using dep software.amazon.smithy:smithy-model:1.58.0
//> using dep com.siriusxm.smithy:protocol:0.13.58-8-261c098a-SNAPSHOT
//> using dep org.typelevel::cats-core:2.13.0
//> using dep com.lihaoyi::os-lib:0.11.4
//> using option -Wunused:all
import os.Path
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.SourceLocation
import software.amazon.smithy.model.loader.ModelAssembler
import software.amazon.smithy.model.node.Node
import software.amazon.smithy.model.shapes.Shape
import software.amazon.smithy.model.shapes.ShapeId
import software.amazon.smithy.model.traits.Trait

import scala.jdk.CollectionConverters.*
import util.chaining.*
import software.amazon.smithy.model.traits.SuppressTrait
import software.amazon.smithy.model.loader.Prelude
import software.amazon.smithy.model.validation.Severity
import software.amazon.smithy.model.validation.ValidatedResult

val r = Model
  .assembler(this.getClass().getClassLoader())
  .discoverModels(this.getClass().getClassLoader())
  .addImport(
    (os.home / "dev" / "api-registry" / "smithy").toNIO
  )
  .putProperty(ModelAssembler.ALLOW_UNKNOWN_TRAITS, true)
  .putProperty(ModelAssembler.DISABLE_JAR_CACHE, true)
  .assemble()

val m = r.getResult().get()

object SmithyFix {

  def addSuppressions(r: ValidatedResult[Model], eventId: String): Unit = {
    val matching =
      r.getValidationEvents()
        .asScala
        .filter(_.getId() == eventId)
        .filterNot(_.getSeverity() == Severity.SUPPRESSED)
        .map(_.getShapeId().get())
        .map(m.expectShape)
        .toList

    println(s"Found ${matching.size} unsuppressed events for $eventId")

    applyTrait(matching, SuppressTrait.builder().addValue(eventId).build())
  }

  def applyTrait(
    shapes: Seq[Shape],
    toAdd: Trait,
  ): Unit = {
    val locs = locationsByFileLinesDescending(shapes)
    locs.foreach { case (p, linesToPrefix) =>
      val lines = os.read.lines(p).toList

      val newLines = linesToPrefix
        .foldLeft(lines) { (lines, line) =>
          val (before, after) = lines.splitAt(line - 1)

          before ++ List(
            s"""@${toAdd.toShapeId().getName()}(${Node.printJson(toAdd.toNode())})"""
          ) ++ after
        }
        .pipe(addImports(_, toAdd.toShapeId()))

      os.write.over(p, newLines.mkString("\n"))
    }

    println(s"Updated ${shapes.size} shapes in ${locs.size} files")
  }

  private def slToPath(sl: SourceLocation): os.Path = os.Path(sl.getFilename())

  private def locationsByFileLinesDescending(shapes: Seq[Shape]): Map[Path, Seq[Int]] = shapes
    .map(_.getSourceLocation())
    .distinct
    .groupBy(slToPath)
    .map { case (f, locs) => (f, locs.map(_.getLine()).sorted.reverse) }

  private def addImports(lines: List[String], shapeIds: ShapeId*): List[String] = {
    val namespaceLine = lines.indexWhere(_.startsWith("namespace "))

    val importLines = shapeIds
      .toList
      .filterNot(_.getNamespace() == Prelude.NAMESPACE)
      .map(s => s"use $s")

    val (beforeNamespace, afterNamespace) = lines.splitAt(namespaceLine + 1)

    beforeNamespace ++ importLines ++ afterNamespace
  }

}

SmithyFix.addSuppressions(r, "ClosedEnum")
SmithyFix.addSuppressions(r, "sxmRestJson.NonStructHttpPayload")
SmithyFix.addSuppressions(r, "EpochSeconds")

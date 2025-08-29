package smithy4s.codegen.internals

import cats.syntax.all.*
import software.amazon.smithy.model

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

case class Smithy4sConfig(allowedNS: Option[Set[String]], excludedNS: Option[Set[String]])

object Smithy4sConfig {

  def fromNode(node: model.node.ObjectNode): Either[Throwable, Smithy4sConfig] = Either
    .catchNonFatal {
      val allowedNS = node
        .getArrayMember("allowedNS")
        .toScala
        .map(_.getElements().asScala.map(_.expectStringNode().getValue()).toSet)

      val excludedNS = node
        .getArrayMember("excludedNS")
        .toScala
        .map(_.getElements().asScala.map(_.expectStringNode().getValue()).toSet)

      Smithy4sConfig(
        allowedNS = allowedNS,
        excludedNS = excludedNS,
      )
    }

}

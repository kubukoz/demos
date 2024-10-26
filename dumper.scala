//> using scala 2.12.20
//> using option -Xsource:3
//> using dep com.lihaoyi::os-lib::0.11.3
//> using dep io.circe::circe-core::0.14.10
//> using dep io.circe::circe-generic::0.14.10
//> using dep io.circe::circe-parser::0.14.10
//> using dep software.amazon.awssdk:cloudformation:2.29.1
//> using dep org.polyvariant::colorize:0.3.2
//> using dep org.scala-lang.modules::scala-collection-compat:2.12.0

package com.siriusxm.dumper

import io.circe.generic.auto.*
import cats.syntax.all.*
import io.circe.*
import io.circe.syntax.*
import software.amazon.awssdk.services.cloudformation.CloudFormationClient

import scala.jdk.CollectionConverters.*
import scala.util.Using
import org.polyvariant.colorize.auto.*

object Dumper extends App {
  import utils.*

  val base = os.pwd

  val env =
    sys.env("AWS_PROFILE").split("-").last match {
      case e @ ("dev" | "test" | "prod") => e
    }

  val region = sys.env("AWS_REGION")
  val stageName = "app"
  val stackName = "App"

  System.err.println(colorize"Using env: ${env.cyan}".green.render)
  System.err.println(colorize"Using region: ${region.cyan}".green.render)
  System
    .err
    .println(
      colorize"Using base directory: ${base.toString.cyan}".green.render
    )

  val templateFile = os
    .walk
    .stream(base)
    .filter(
      _.last.startsWith(s"$env${region.replace("-", "")}$stageName$stackName")
    )
    .filter(_.last.endsWith(".template.json"))
    .toSeq
    .exactlyOne("matching template file")

  System
    .err
    .println(
      colorize"Using template file: ${templateFile.toString.cyan}".green.render
    )

  val fileText = os.read(templateFile)

  val decoded: TemplateFile = io.circe.parser.decode[TemplateFile](fileText).toTry.get

  val envs =
    decoded
      .Resources
      .values
      .filter(_.Type == "AWS::ECS::TaskDefinition")
      .toSeq
      .exactlyOne("task definition")
      .Properties
      .ContainerDefinitions
      .orEmpty
      .filter(_.Essential)
      .exactlyOne("essential container")
      .Environment
      .orEmpty

  System
    .err
    .println(
      colorize"Fetching exports from CloudFormation...".green.render
    )

  val exports =
    Using
      .resource(CloudFormationClient.create()) {
        _.listExportsPaginator().exports().asScala.toList
      }
      .groupByNel(_.name())
      .map { case (k, vs) => k -> vs.head.value() }
      .toMap

  System
    .err
    .println(
      colorize"Found ${exports.size.toString.cyan} exports".green.render
    )

  val envFull = envs
    .map { envopt =>
      envopt.Value match {
        case EnvValue.Str(value)   => envopt.Name -> value
        case EnvValue.Import(name) => envopt.Name -> exports(name)
      }
    }
    .map { case (k, v) => s"""export ${k}=${escapeForBashDouble(v)}""" }

  System
    .err
    .println(
      colorize"Dumping ${envFull.size.toString.cyan} variables".green.render
    )

  println(envFull.mkString("\n"))
}

object utils {

  private[dumper] def escapeForBashDouble(str: String): String =
    "\"" + str
      .replace("\\", "\\\\")
      .replace("\"", "\\\"")
      .replace("$", "\\$") + "\""

  implicit class SeqOps[A](private val seq: Seq[A]) extends AnyVal {

    def exactlyOne(tag: String): A =
      seq match {
        case Seq(a) => a
        case _      => sys.error(s"expected exactly one $tag, got ${seq.length}")
      }

  }

}

sealed trait EnvValue

object EnvValue {
  case class Str(value: String) extends EnvValue
  case class Import(name: String) extends EnvValue

  implicit val envCodec: Codec[EnvValue] = Codec.from(
    Decoder[String]
      .map(EnvValue.Str(_))
      .widen[EnvValue]
      .or(
        Decoder[String]
          .map(EnvValue.Import(_))
          .at("Fn::ImportValue")
          .widen[EnvValue]
      ),
    Encoder.instance {
      case EnvValue.Str(value)   => value.asJson
      case EnvValue.Import(name) => Json.obj("Fn::ImportValue" -> name.asJson)
    },
  )

}

case class EnvOption(
  Name: String,
  Value: EnvValue,
)

case class ContainerDefinition(
  Name: String,
  Environment: Option[List[EnvOption]],
  Essential: Boolean,
)

case class Properties(
  ContainerDefinitions: Option[List[ContainerDefinition]]
)

case class Resource(
  Type: String,
  Properties: Properties,
)

case class TemplateFile(
  Resources: Map[String, Resource]
)

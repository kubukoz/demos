//> using scala 3.7.0
//> using dep software.amazon.smithy:smithy-model:1.57.1
//> using dep com.disneystreaming.smithy4s::smithy4s-dynamic:0.18.35
import software.amazon.smithy.model.Model
import software.amazon.smithy.model.selector.Selector
import software.amazon.smithy.model.shapes.ShapeId
import scala.jdk.CollectionConverters._
import software.amazon.smithy.model.transform.ModelTransformer
import software.amazon.smithy.model.shapes.ServiceShape
import software.amazon.smithy.model.shapes.OperationShape
import smithy4s.dynamic.DynamicSchemaIndex
import smithy4s.Document
import smithy4s.schema.Schema
import smithy4s.schema.Schema.StructSchema
import smithy.api.Length
import smithy4s.RefinementProvider
import smithy4s.ShapeTag
import smithy4s.Refinement.Aux
import smithy4s.Refinement
import cats.syntax.all.*

val r = Model
  .assembler()
  .addUnparsedModel(
    "test.smithy",
    """$version: "2"
      |namespace test
      |
      |@trait(selector: "structure:not(> member:not([trait|required]))") structure tuple {}
      |
      |
      |structure MyNested {}
      |
      |@tuple
      |structure MyTuple { @required fst: String, @required snd: MyNested }
      |
      |structure Foo { @required items: MyTuple }
      |""".stripMargin,
  )
  .assemble()

r.getValidationEvents().asScala.toList

val m = r.unwrap()

// ModelTransformer.create().

import smithy4s.~>
def lspTransform: Schema ~> Schema =
  new (Schema ~> Schema) {
    def apply[A0](schema: Schema[A0]): Schema[A0] =
      // todo: replace with .has[Tuple] when code is generated for it
      if (schema.hints.all.exists(_.keyId == smithy4s.ShapeId("test", "tuple"))) {
        schema match {
          case StructSchema(shapeId, hints, fields, make) =>
            val len = fields.size

            val decode: List[Document] => Either[String, A0] = {
              val fieldDecoders = fields.map { f =>
                Document.Decoder.fromSchema(f.schema)
              }

              items =>
                fieldDecoders
                  .zip(items.toVector)
                  .traverse { case (d, item) => d.decode(item) }
                  .map(make)
                  .leftMap(_.getMessage)
            }

            val encode: A0 => List[Document] =
              fields
                .map(f => Document.Encoder.fromSchema(f.schema).encode.compose(f.get))
                .toList
                .sequence

            Schema
              .list(Schema.document)
              .withId(shapeId)
              .validated(Length(min = Some(len), max = Some(len)))
              .refined[A0]
              .apply(
                new Refinement[List[Document], A0] {
                  type Constraint = Unit
                  val tag: ShapeTag[Unit] =
                    new ShapeTag[Unit] {
                      def id: smithy4s.ShapeId = smithy4s.ShapeId("smithy.api", "Unit")
                      def schema: Schema[Unit] = Schema.unit
                    }

                  def apply(a: List[Document]): Either[String, A0] = decode(a)
                  def from(b: A0): List[Document] = encode(b)

                  def constraint: Constraint = ()
                  def unsafe(a: List[Document]): A0 = apply(a).fold(sys.error, identity)
                }
              )
          case _ => sys.error("illegal case as per selector")
        }
      } else
        schema
  }

val fooSchema =
  DynamicSchemaIndex.loadModel(m).getSchema(smithy4s.ShapeId.parse("test#Foo").get).get

Document
  .decoderFromSchema(
    using fooSchema
  )
  .decode(
    Document.obj(
      "items" -> Document.obj(
        "fst" -> Document.fromString("hello"),
        "snd" -> Document.obj(
          "a" -> Document.fromString("world")
        ),
      )
    )
  )
  .toTry
  .get

Document
  .decoderFromSchema(
    using fooSchema.transformTransitivelyK(lspTransform)
  )
  .decode(
    Document.obj(
      "items" -> Document.array(
        Document.fromString("hello"),
        Document.obj(
          "a" -> Document.fromString("world")
        ),
      )
    )
  )
  .toTry
  .get

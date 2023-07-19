//> using scala "3.3.0"
//> using lib "com.disneystreaming.smithy4s::smithy4s-dynamic:0.18.0-698-c1f0883"
//> using lib "com.disneystreaming.smithy4s::smithy4s-http4s:0.18.0-698-c1f0883"
//> using lib "org.http4s::http4s-ember-server:0.23.22"
//> using lib "com.disneystreaming.alloy:alloy-core:0.2.3"
//> using lib "com.kubukoz::debug-utils:1.1.3"
//> using resourceDir "./resources"
//> using option "-Wunused:all"
import cats.effect.IOApp
import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import smithy4s.dynamic.DynamicSchemaIndex
import software.amazon.smithy.model.Model
import cats.implicits._
import smithy4s.Service
import org.http4s.HttpRoutes
import smithy4s.http4s.SimpleRestJsonBuilder
import smithy4s.schema.SchemaVisitor
import smithy4s.Bijection
import smithy4s.schema.Schema
import smithy4s.schema.CollectionTag
import smithy4s.Hints
import smithy4s.ShapeId
import smithy4s.schema.Primitive
import smithy4s.schema.Field
import smithy4s.schema.Alt
import smithy4s.schema.Alt.Dispatcher
import smithy4s.Refinement
import smithy4s.schema.EnumValue
import smithy4s.Lazy
import scala.util.Random
import scala.collection.immutable.ListMap
import smithy4s.schema.CollectionTag._
import smithy4s.schema.Primitive._
import smithy4s.Timestamp
import smithy4s.ByteArray
import smithy4s.Document
import org.http4s.HttpApp
import java.nio.file.Files
import java.nio.file.Paths

object Main extends IOApp.Simple {

  // explicit seed can be set
  val random = new Random( /* 1000L */ )

  trait ExampleOf[A] {
    def example(): A

    def either[B](another: ExampleOf[B]): ExampleOf[Either[A, B]] =
      () =>
        if (random.nextBoolean())
          Left(example())
        else
          Right(another.example())

    def map[B](f: A => B): ExampleOf[B] = () => f(example())
  }

  def pick[A](as: Seq[A]): A = as(random.nextInt(as.size))

  object ExampleVisitor extends SchemaVisitor[ExampleOf] {

    def enumeration[E](
      shapeId: ShapeId,
      hints: Hints,
      tag: smithy4s.schema.EnumTag,
      values: List[EnumValue[E]],
      total: E => EnumValue[E],
    ): ExampleOf[E] = () => pick(values.map(_.value))

    def option[A](underlying: Schema[A]): ExampleOf[Option[A]] =
      val base = underlying.compile(this)
      () => Option.when(random.nextBoolean())(base.example())

    def lazily[A](suspend: Lazy[Schema[A]]): ExampleOf[A] =
      val u = suspend.map(_.compile(this))
      () => u.value.example()

    def primitive[P](shapeId: ShapeId, hints: Hints, tag: Primitive[P]): ExampleOf[P] =
      tag match {
        case PInt        => () => random.nextInt()
        case PLong       => () => random.nextLong()
        case PFloat      => () => random.nextFloat()
        case PDouble     => () => random.nextDouble()
        case PShort      => () => random.nextBytes(1).head.toShort
        case PByte       => () => random.nextBytes(1).head
        case PBigInt     => () => BigInt(random.nextLong())
        case PBigDecimal => () => BigDecimal(random.nextDouble())
        case PBoolean    => () => random.nextBoolean()
        case PString =>
          () =>
            pick(
              List(
                "example",
                "some-string",
                "another-string",
                "a-string-value",
                "stringly-string",
              )
            )
        case PUUID      => () => java.util.UUID.randomUUID()
        case PTimestamp => () => Timestamp(random.nextLong(Long.MaxValue), random.nextInt(1000_000))
        case PBlob      => () => ByteArray(random.nextBytes(10))
        case PDocument =>
          () =>
            pick(
              List(
                Document.obj(
                  "foo" -> Document.fromString("bar"),
                  "baz" -> Document.fromInt(100),
                ),
                Document.array(
                  Document.fromString("foo"),
                  Document.fromString("bar"),
                  Document.fromInt(100),
                  Document.fromString("baz"),
                ),
                Document.fromString("foo"),
                Document.fromInt(100),
                Document.fromBoolean(true),
                Document.nullDoc,
              )
            )
      }

    def struct[S](
      shapeId: ShapeId,
      hints: Hints,
      fields: Vector[Field[S, ?]],
      make: IndexedSeq[Any] => S,
    ): ExampleOf[S] =
      val fieldsCompiled = fields.map(_.schema.compile(this))

      () =>
        make(
          fieldsCompiled.map(_.example())
        )

    def union[U](
      shapeId: ShapeId,
      hints: Hints,
      alternatives: Vector[Alt[U, ?]],
      dispatch: Dispatcher[U],
    ): ExampleOf[U] =
      val alts = alternatives.map { alt =>
        val i = alt.instance.compile(this)
        () => alt.inject(i.example())
      }

      () => pick(alts)()

    // this one is unsafe
    def refine[A, B](schema: Schema[A], refinement: Refinement[A, B]): ExampleOf[B] =
      val u = schema.compile(this)
      () => refinement.unsafe(u.example())

    def map[K, V](
      shapeId: ShapeId,
      hints: Hints,
      key: Schema[K],
      value: Schema[V],
    ): ExampleOf[Map[K, V]] =
      val k = key.compile(this)
      val v = value.compile(this)
      () =>
        List
          .fill(2)(
            k.example() -> v.example()
          )
          .to(ListMap)

    override def collection[C[_], A](
      shapeId: ShapeId,
      hints: Hints,
      tag: CollectionTag[C],
      member: Schema[A],
    ): ExampleOf[C[A]] =
      val m = member.compile(this)
      tag match {
        case IndexedSeqTag => () => List.fill(2)(m.example()).toVector
        case ListTag       => () => List.fill(2)(m.example())
        case VectorTag     => () => List.fill(2)(m.example()).toVector
        case SetTag        => () => List.fill(2)(m.example()).toSet
      }

    override def biject[A, B](schema: Schema[A], bijection: Bijection[A, B]): ExampleOf[B] =
      val u = schema.compile(this)
      () => bijection.to(u.example())

  }

  def interpret[Alg[_[_, _, _, _, _]]](using service: Service[Alg]): HttpRoutes[IO] =
    val pf: service.FunctorEndpointCompiler[IO] =
      new service.FunctorEndpointCompiler[IO] {
        override def apply[I, E, O, SI, SO](
          e: service.Endpoint[I, E, O, SI, SO]
        ): I => IO[O] = {
          val outExample = e.output.compile(ExampleVisitor)
          val errExample = e
            .errorable
            .map(e => e.error.compile(ExampleVisitor).map(e.unliftError(_)))

          val combinedExample = errExample.fold(outExample.map(_.asRight))(_.either(outExample))

          _ => combinedExample.example().liftTo[IO]
        }

      }

    println("Building service: " + service.id)
    val impl = service.impl(pf)
    SimpleRestJsonBuilder.routes(impl).make.toTry.get

  override def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withHttpApp {
        HttpApp { req =>
          val dsi =
            DynamicSchemaIndex
              .loadModel(
                Model
                  .assembler()
                  .discoverModels()
                  .addUnparsedModel("test.smithy", Files.readString(Paths.get("demo.smithy")))
                  .assemble()
                  .unwrap()
              )
              .toTry
              .get

          dsi
            .allServices
            .foldMapK(w =>
              interpret(
                using w.service
              )
            )
            .orNotFound
            .apply(req)
        }
      }
      .withErrorHandler { case e =>
        e.printStackTrace()
        IO.raiseError(e)
      }
      .build
      .useForever

}

//> using scala "2.13.10"
//> using lib "com.disneystreaming.smithy4s::smithy4s-core:0.17.5"
//> using lib "com.disneystreaming.smithy4s::smithy4s-json:0.17.5"
//> using lib "com.disneystreaming.smithy4s::smithy4s-http4s:0.17.5"
//> using lib "io.circe::circe-parser:0.14.5"
//> using option "-Wunused:imports"
import demo._
import smithy4s.http.json.codecs
import smithy4s.schema.Schema
import smithy4s.schema.Schema.StructSchema
import cats.effect.IO
import org.http4s.Request
import smithy4s.http4s.SimpleProtocolBuilder
import smithy4s.HintMask
import smithy4s.internals.InputOutput
import smithy4s.IntEnum
import io.circe.JsonObject
import smithy4s.http.HttpMediaType
import java.nio.ByteBuffer
import smithy4s.http.BodyPartial
import smithy4s.http.PayloadError
import io.circe.Json
import org.http4s.HttpRoutes
import smithy4s.http.CodecAPI
import smithy4s.schema.Schema.UnionSchema
import smithy4s.schema.Schema.BijectionSchema

def extendSchema[A](s: Schema[A]): Schema[A] =
  s match {
    case baseSchema: StructSchema[_] =>
      baseSchema.copy(
        // add extra fields
        fields =
          baseSchema.fields ++ Seq(
            Schema
              .string
              .required[A]("errorCategory", _ => baseSchema.shapeId.namespace)
              .addHints(smithy.api.Required()),
            Schema
              .int
              .required[A](
                "errorCode",
                _ =>
                  baseSchema
                    .hints
                    .get(smithy.api.HttpError)
                    .getOrElse(sys.error("no error code"))
                    .value,
              )
              .addHints(smithy.api.Required()),
          )
      )

    // this match is incomplete, it should probably be a SchemaVisitor instead
    case union: UnionSchema[_] =>
      union.copy(alternatives = union.alternatives.map(_.mapK(extendSchemaK)))

    case BijectionSchema(underlying, bijection) =>
      BijectionSchema(extendSchema(underlying), bijection)

    case _ => s
  }

import smithy4s.~>

val extendSchemaK =
  new (Schema ~> Schema) {
    def apply[A0](fa: Schema[A0]): Schema[A0] = extendSchema(fa)
  }

def extendCodecApi(capi: CodecAPI): CodecAPI =
  new CodecAPI {
    type Codec[A] = capi.Codec[A]
    type Cache = capi.Cache
    def createCache(): Cache = capi.createCache()

    def mediaType[A](codec: Codec[A]): HttpMediaType = capi.mediaType(codec)

    def compileCodec[A](
      schema: Schema[A],
      cache: Cache,
    ): Codec[A] = capi.compileCodec(extendSchema(schema), cache)

    def decodeFromByteArrayPartial[A](
      codec: Codec[A],
      bytes: Array[Byte],
    ): Either[PayloadError, BodyPartial[A]] = capi.decodeFromByteArrayPartial(codec, bytes)

    def decodeFromByteBufferPartial[A](
      codec: Codec[A],
      bytes: ByteBuffer,
    ): Either[PayloadError, BodyPartial[A]] = capi.decodeFromByteBufferPartial(codec, bytes)

    def writeToArray[A](codec: Codec[A], value: A): Array[Byte] = capi.writeToArray(codec, value)

  }

val impl: DemoService[IO] =
  new DemoService[IO] {

    def demoOp(
    ): IO[Unit] = IO.raiseError(DynamoError("error name", Some(42)))

  }

import org.http4s.implicits._

import cats.effect.unsafe.implicits._

import cats.implicits._
import io.circe.syntax._

def addUriMiddleware(route: HttpRoutes[IO]): HttpRoutes[IO] = HttpRoutes.apply[IO] { request =>
  route(request).semiflatMap {
    case response if !response.status.isSuccess =>
      response
        .bodyText
        .compile
        .string
        .flatMap { body =>
          io.circe.parser.decode[JsonObject](body).liftTo[IO]
        }
        .map { json =>
          response.withBodyStream(
            fs2
              .Stream
              .emits(
                json
                  .add("instance", Json.fromString(request.uri.toString()))
                  .asJson
                  .noSpaces
                  .getBytes()
              )
          )
        }
  }
}

object CustomSimpleRestJsonBuilder extends CustomSimpleRestJsonBuilder(1024)

class CustomSimpleRestJsonBuilder(maxArity: Int)
  extends SimpleProtocolBuilder[alloy.SimpleRestJson](
    extendCodecApi(
      smithy4s
        .http
        .json
        .codecs(
          alloy.SimpleRestJson.protocol.hintMask ++ HintMask(
            InputOutput,
            IntEnum,
          ),
          maxArity,
        )
    )
  ) {
  def withMaxArity(maxArity: Int): CustomSimpleRestJsonBuilder =
    new CustomSimpleRestJsonBuilder(maxArity)
}

val route =
  CustomSimpleRestJsonBuilder
    .routes(impl)
    .make
    .map(addUriMiddleware)
    .toOption
    .get

val result =
  route
    .apply(
      Request[IO](uri = uri"/demo")
    )
    .value
    .unsafeRunSync()
    .get
    .bodyText
    .compile
    .string

println(
  result
    .unsafeRunSync()
)

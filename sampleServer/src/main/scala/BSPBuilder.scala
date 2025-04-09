import bsp.traits.JsonRequest

import cats.effect.IO
import cats.syntax.all.*
import jsonrpclib.Codec
import jsonrpclib.Endpoint
import jsonrpclib.Payload
import jsonrpclib.ProtocolError
import jsonrpclib.fs2.*
import smithy4s.Blob
import smithy4s.Service
import smithy4s.json.Json
import smithy4s.schema.Schema

import util.chaining.*
import bsp.traits.JsonNotification
import bsp.WorkspaceBuildTargetsResult
import smithy4s.Document.DObject
import bsp.scala_.ScalaBuildTarget
import bsp.traits.DataKind
import smithy4s.Document
import smithy4s.Document.DArray

final case class BSPBuilder[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]] private (
  private val service: Service.Aux[Alg, Op],
  private val endpoints: Vector[BSPBuilder.BSPEndpoint],
) {
  type Op_[I, O, E, S, F] = Op[I, O, E, S, F]

  // todo: type safety for ops from multiple services / ability to combine builders
  def withHandler[Oppy[_, _, _, _, _], I, O](
    op: smithy4s.Endpoint[Oppy, I, ?, O, ?, ?]
  )(
    f: I => IO[O]
  ): BSPBuilder[Alg, Op_] = copy(
    service,
    endpoints =
      endpoints :+ new BSPBuilder.BSPEndpoint {
        type Op[I, O, E, S, F] = Oppy[I, O, E, S, F]
        type In = I
        type Out = O

        def endpoint: smithy4s.Endpoint[Oppy, I, ?, O, ?, ?] = op
        def impl(in: I): IO[Out] = f(in)
      },
  )

  def bind(chan: FS2Channel[IO]): fs2.Stream[IO, FS2Channel[IO]] = {
    def codecFor[A: Schema]: Codec[A] =
      new {
        val schema = summon[Schema[A]]
        private val decoder = Json.payloadCodecs.decoders.fromSchema[A](schema)
        private val encoder = Json
          .payloadCodecs
          .encoders
          .fromSchema[A](schema)
          .andThen(`UGLY HACK DO NOT LOOK!`(schema))

        def decode(payload: Option[Payload]): Either[ProtocolError, A] = payload
          .flatMap(_.stripNull)
          .map(_.array)
          .fold(Blob.empty)(Blob.apply(_))
          .pipe { blob =>
            decoder
              .decode(blob)
              .leftMap(pe => ProtocolError.ParseError(pe.toString()))
          }

        def encode(a: A): Payload = Payload.Data(encoder.encode(a).toArrayUnsafe)
      }

    def handle[Op[_, _, _, _, _], I, O](e: BSPBuilder.BSPEndpoint.Aux[Op, I, O]) = {
      given Codec[I] = codecFor[I](
        using e.endpoint.input
      )
      given Codec[O] = codecFor[O](
        using e.endpoint.output
      )

      Endpoint(
        e.endpoint
          .hints
          .get(JsonRequest)
          .map(_.value)
          .orElse(
            e.endpoint
              .hints
              .get(JsonNotification)
              .map(_.value)
          )
          .getOrElse(
            sys.error(s"invalid endpoint ${e.endpoint.id}: no JsonRequest/JsonNotification present")
          )
      ).simple(e.impl)
    }

    chan.withEndpointsStream(endpoints.map(handle(_)))
  }

  private def `UGLY HACK DO NOT LOOK!`(schema: Schema[?])(blob: Blob): Blob =
    blob match {
      case blob if schema.shapeId == WorkspaceBuildTargetsResult.id =>
        // we need to add a dataKind to this!

        val base: Map[String, Document] =
          Json
            .readDocument(blob)
            .toTry
            .get
            .asInstanceOf[DObject]
            .value

        val converted = base("targets").asInstanceOf[DArray].value.map { target =>
          Document.DObject {
            target.asInstanceOf[DObject].value +
              ("dataKind" -> Document.fromString(ScalaBuildTarget.hints.get[DataKind].get.kind))
          }
        }

        // fun fact, if you just write the array in DArray, you'll get a CCE in the jsoniter codec
        Json.writeDocumentAsBlob(Document.obj("targets" -> Document.array(converted*)))
      case other => other
    }

}

object BSPBuilder {

  def create[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]](
    service: Service.Aux[Alg, Op]
  ): BSPBuilder[Alg, Op] = new BSPBuilder(service, Vector.empty)

  trait BSPEndpoint {
    type Op[_, _, _, _, _]
    type In
    type Out

    def endpoint: smithy4s.Endpoint[Op, In, ?, Out, ?, ?]
    def impl(in: In): IO[Out]
  }

  object BSPEndpoint {

    type Aux[Op_[_, _, _, _, _], In_, Out_] =
      BSPEndpoint {
        type Op[I, O, E, S, F] = Op_[I, O, E, S, F]
        type In = In_
        type Out = Out_
      }

  }

}

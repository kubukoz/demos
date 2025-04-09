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

final case class BSPBuilder[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]] private (
  private val service: Service.Aux[Alg, Op],
  private val endpoints: Vector[BSPBuilder.BSPEndpoint[Op, ?, ?]],
) {

  def withHandler[I, O](
    op: service.Endpoint[I, ?, O, ?, ?]
  )(
    f: I => IO[O]
  ): BSPBuilder[Alg, Op] = copy(
    endpoints = endpoints :+ BSPBuilder.BSPEndpoint(op, f)
  )

  def bind(chan: FS2Channel[IO]): fs2.Stream[IO, FS2Channel[IO]] = {
    def codecFor[A: Schema]: Codec[A] =
      new {
        private val decoder = Json.payloadCodecs.decoders.fromSchema[A](summon[Schema[A]])
        private val encoder = Json.payloadCodecs.encoders.fromSchema[A](summon[Schema[A]])

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

    def handle[Op[_, _, _, _, _], I, O](e: BSPBuilder.BSPEndpoint[Op, I, O]) = {
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
          .getOrElse(sys.error(s"invalid endpoint ${e.endpoint.id}: no JsonRequest present"))
          .value
      ).simple(e.impl)
    }

    chan.withEndpointsStream(endpoints.map(handle(_)))
  }

}

object BSPBuilder {

  def create[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]](
    service: Service.Aux[Alg, Op]
  ): BSPBuilder[Alg, Op] = new BSPBuilder(service, Vector.empty)

  case class BSPEndpoint[Op[_, _, _, _, _], In, Out](
    endpoint: smithy4s.Endpoint[Op, In, ?, Out, ?, ?],
    impl: In => IO[Out],
  )

}

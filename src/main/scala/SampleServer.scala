import bsp.BuildClientCapabilities

import bsp.BuildServer
import bsp.BuildServerCapabilities
import bsp.BuildServerOperation
import bsp.BuildServerOperation.BuildInitialize
import bsp.BuildServerOperation.BuildShutdown
import bsp.CompileProvider
import bsp.InitializeBuildParams
import bsp.InitializeBuildParamsData
import bsp.InitializeBuildResult
import bsp.LanguageId
import bsp.URI
import bsp.traits.JsonRequest
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.kernel.Resource
import cats.syntax.all.*
import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker
import fs2.io.file.Files
import fs2.io.file.Path
import jsonrpclib.CallId
import jsonrpclib.Codec
import jsonrpclib.Endpoint
import jsonrpclib.Payload
import jsonrpclib.Payload.Data
import jsonrpclib.Payload.NullPayload
import jsonrpclib.ProtocolError
import jsonrpclib.fs2.*
import jsonrpclib.fs2.CancelTemplate
import jsonrpclib.fs2.FS2Channel
import jsonrpclib.fs2.lsp
import smithy4s.Blob
import smithy4s.Service
import smithy4s.json.Json
import smithy4s.kinds.stubs.Kind1
import smithy4s.schema.Schema

import util.chaining.*
import jsonrpclib.Channel

case class BSPEndpoint[Op[_, _, _, _, _], In, Out](
  endpoint: smithy4s.Endpoint[Op, In, ?, Out, ?, ?],
  impl: In => IO[Out],
)

final case class BSPBuilder[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]] private (
  private val service: Service.Aux[Alg, Op],
  private val endpoints: Vector[BSPEndpoint[service.Operation, ?, ?]],
) {

  def withHandler[I, O](
    op: service.Endpoint[I, ?, O, ?, ?]
  )(
    f: I => IO[O]
  ): BSPBuilder[Alg, Op] = copy(
    endpoints = endpoints :+ BSPEndpoint(op, f)
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

    def handle[Op[_, _, _, _, _], I, O](e: BSPEndpoint[Op, I, O]) = {
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

  def create[Alg[_[_, _, _, _, _]]](
    service: Service[Alg]
  ): BSPBuilder[Alg, service.Operation] = ???

}

object SampleServer extends IOApp.Simple {
  val cancelEndpoint = CancelTemplate.make[CallId]("$/cancel", identity, identity)

  def server(log: String => IO[Unit]) =
    BSPBuilder
      .create(BuildServer)
      .withHandler(BuildInitialize) { input =>
        log(s"omg! received a valid request with inputs $input") *>
          IO {
            InitializeBuildResult(
              displayName = "jk-sample-server",
              "1.0.0",
              bspVersion = "2.2.0-",
              capabilities = BuildServerCapabilities(
                compileProvider = Some(
                  CompileProvider(languageIds = List(LanguageId("hello")))
                )
              ),
            )
          }

      }
      .withHandler(BuildShutdown) { _ =>
        log("received a shutdown request") *>
          IO.unit
      }

  def run: IO[Unit] = {
    val impl = server(msg =>
      fs2.Stream(msg).through(Files[IO].writeUtf8(Path("log.txt"))).compile.drain
    )

    FS2Channel[IO](cancelTemplate = Some(cancelEndpoint))
      .flatMap(impl.bind)
      .flatMap(channel =>
        fs2
          .Stream
          .eval(IO.never) // running the server forever
          .concurrently(
            fs2
              .io
              .stdin[IO](512)
              .through(lsp.decodeMessages)
              // .broadcastThrough(_.map(_.toString).through(Files[IO].writeUtf8(Path("log.txt"))))
              .through(channel.inputOrBounce)
          )
          .concurrently(channel.output.through(lsp.encodeMessages).through(fs2.io.stdout[IO]))
      )
      .compile
      .drain
      .guarantee(IO.consoleForIO.errorln("Terminating server"))
  }

}

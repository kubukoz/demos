//> using scala "2.13.10"
//> using lib "com.disneystreaming.smithy4s::smithy4s-json:0.17.5"
//> using lib "org.typelevel::cats-effect:3.4.8"
//> using lib "org.http4s::http4s-ember-client:0.23.18"
//> using lib "io.github.etspaceman::kinesis4cats-kcl-localstack:0.0.6"
//> using lib "io.github.etspaceman::kinesis4cats-smithy4s-client-localstack:0.0.6"
//> using plugin "org.typelevel:::kind-projector:0.13.2"
//> using lib "org.tpolecat::natchez-log:0.3.1"
//> using lib "org.slf4j:slf4j-simple:2.0.7"
//> using option "-Wunused:imports"
import cats.data.NonEmptyList
import cats.effect.IO
import cats.effect.IOApp
import cats.effect.Resource
import cats.effect.implicits._
import cats.effect.kernel.Async
import cats.effect.kernel.MonadCancelThrow
import cats.effect.std
import cats.implicits._
import demo._
import kinesis4cats.kcl.CommittableRecord
import kinesis4cats.kcl.localstack.LocalstackKCLConsumer
import kinesis4cats.producer
import kinesis4cats.smithy4s.client.producer.localstack.LocalstackKinesisProducer
import natchez.Trace
import natchez.log.Log
import org.http4s.ember.client.EmberClientBuilder
import org.typelevel.log4cats.slf4j.Slf4jLogger
import smithy4s.Endpoint
import smithy4s.Service
import smithy4s.aws.kernel.AwsRegion
import smithy4s.http.json.codecs
import smithy4s.kinds.FunctorK5
import smithy4s.kinds.PolyFunction5

import java.util.UUID
import scala.util.Random

object Consume extends IOApp.Simple {

  // this needs to be added to smithy4s core
  trait Pipe[P[_[_, _, _, _, _]]] extends FunctorK5[P] {
    type Alg[_[_, _, _, _, _]]
    val service: Service[Alg]
    def endpoints: P[service.Endpoint]

    def toPolyFunction[P2[_, _, _, _, _]](algebra: P[P2]): PolyFunction5[service.Endpoint, P2]

  }

  object Pipe {

    type Aux[Alg[_[_, _, _, _, _]], P[_[_, _, _, _, _]]] =
      Pipe[Alg] {
        type Alg[Op[_, _, _, _, _]] = P[Op]
      }

  }

  // this needs to be added to smithy4s codegen
  trait MyEventServicePipe[F[_, _, _, _, _]] {
    def event1Stream: F[Event1, Nothing, Unit, Nothing, Nothing]
    def event2Stream: F[Event2, Nothing, Unit, Nothing, Nothing]
    def event3Stream: F[Event3, Nothing, Unit, Nothing, Nothing]
  }

  trait PipeProvider[Alg[_[_, _, _, _, _]]] {
    type PipeAlg[_[_, _, _, _, _]]
    val pipe: Pipe.Aux[PipeAlg, Alg]
  }

  object PipeProvider {

    type Aux[Alg[_[_, _, _, _, _]], P[_[_, _, _, _, _]]] =
      PipeProvider[Alg] {
        type PipeAlg[Op[_, _, _, _, _]] = P[Op]
      }

  }

  // something like this will end up in smithy4s
  implicit val provider: PipeProvider.Aux[MyEventServiceGen, MyEventServicePipe] =
    new PipeProvider[MyEventServiceGen] {
      type PipeAlg[Op[_, _, _, _, _]] = MyEventServicePipe[Op]
      val pipe: Pipe.Aux[PipeAlg, MyEventServiceGen] = MyEventServicePipe
    }

    // presumably generated boilerplate

  implicit object MyEventServicePipe extends Pipe[MyEventServicePipe] {
    type Alg[Op[_, _, _, _, _]] = MyEventServiceGen[Op]

    implicit val service: MyEventServiceGen.type = MyEventServiceGen

    val endpoints: MyEventServicePipe[service.Endpoint] =
      new MyEventServicePipe[service.Endpoint] {
        def event1Stream: service.Endpoint[Event1, Nothing, Unit, Nothing, Nothing] =
          MyEventServiceOperation.Event1Stream

        def event2Stream: service.Endpoint[Event2, Nothing, Unit, Nothing, Nothing] =
          MyEventServiceOperation.Event2Stream

        def event3Stream: service.Endpoint[Event3, Nothing, Unit, Nothing, Nothing] =
          MyEventServiceOperation.Event3Stream

      }

    def mapK5[F[_, _, _, _, _], G[_, _, _, _, _]](
      alg: MyEventServicePipe[F],
      function: PolyFunction5[F, G],
    ): MyEventServicePipe[G] =
      new MyEventServicePipe[G] {

        def event1Stream: G[Event1, Nothing, Unit, Nothing, Nothing] =
          function[Event1, Nothing, Unit, Nothing, Nothing](alg.event1Stream)

        def event2Stream: G[Event2, Nothing, Unit, Nothing, Nothing] =
          function[Event2, Nothing, Unit, Nothing, Nothing](alg.event2Stream)

        def event3Stream: G[Event3, Nothing, Unit, Nothing, Nothing] =
          function[Event3, Nothing, Unit, Nothing, Nothing](alg.event3Stream)

      }

    def toPolyFunction[P2[_, _, _, _, _]](
      algebra: MyEventServicePipe[P2]
    ): PolyFunction5[service.Endpoint, P2] =
      new PolyFunction5[service.Endpoint, P2] {

        def apply[A, B, C, D, E](endpoint: service.Endpoint[A, B, C, D, E]): P2[A, B, C, D, E] =
          endpoint match {
            case MyEventServiceOperation.Event1Stream => algebra.event1Stream
            case MyEventServiceOperation.Event2Stream => algebra.event2Stream
            case MyEventServiceOperation.Event3Stream => algebra.event3Stream
          }

      }

  }

  // end boilerplate

  import kinesis4cats.kcl.logging.instances.show._

  type StreamingInterpreterBuilder[Alg[_[_, _, _, _, _]], F[_]] = Alg[StreamingBuilder.Of[Alg, F]#R]

  implicit class StreamingInterpreterBuilderOps[Alg[_[_, _, _, _, _]], F[_]](
    builder: StreamingInterpreterBuilder[Alg, F]
  ) {

    def traced(
      implicit pipe: Pipe[Alg],
      trace: Trace[F],
      F: MonadCancelThrow[F],
      C: std.Console[F],
    ): StreamingInterpreterBuilder[Alg, F] =
      // this isn't a great way to do this, because any new handlers being added will not have the tracing enabled.
      // it can be fixed post-demo, though.
      modifyEndpoints {

        def mod[I](e: EndpointMapping[F]): EndpointMapping[F] =
          new EndpointMapping[F] {
            type I = e.I
            type Op[I, E, O, S, M] = e.Op[I, E, O, S, M]
            val endpoint: Endpoint[Op, I, _, _, _, _] = e.endpoint
            val handler: I => F[Unit] = i => trace.span(endpoint.name)(e.handler(i))
          }

        _.map(mod(_))
      }

    def run(
      streamName: String,
      appName: String,
    )(
      implicit pipe: Pipe[Alg],
      F: Async[F],
    ): Resource[F, Unit] = {
      val fk = pipe.toPolyFunction(builder)

      /* the choice of endpoint is arbitrary - all operations return the same value */
      val mappings = fk(pipe.service.endpoints.head).currentEndpoints

      def handle(records: List[CommittableRecord[F]]): F[Unit] = records.traverse_ { record =>
        // this would be based on some top-level value lookup to find the datatype to use
        // but why not random
        val mapping = mappings(Random.nextInt(mappings.size))
        val schema = mapping.endpoint.input
        val capi = codecs()

        // todo: caching
        val codec = capi.compileCodec(schema)

        capi
          .decodeFromByteArray(codec, record.data.array())
          .liftTo[F]
          .flatMap(mapping.handler)
      }

      LocalstackKCLConsumer.kclConsumer(streamName, appName)(handle).evalMap(_.get)
    }

    def addEndpoint[Op[_, _, _, _, _], I, E, O, SI, SO](
      endpoint: Endpoint[Op, I, E, O, SI, SO],
      consumer: I => F[Unit],
    )(
      implicit pipe: Pipe[Alg]
    ): StreamingInterpreterBuilder[Alg, F] = {

      type In = I
      type Opp[I, E, O, SI, SO] = Op[I, E, O, SI, SO]
      val e = endpoint

      modifyEndpoints {
        _.appended(new EndpointMapping[F] {
          type I = In
          type Op[I, E, O, SI, SO] = Opp[I, E, O, SI, SO]
          val endpoint: Endpoint[Op, I, _, _, _, _] = e
          val handler: I => F[Unit] = consumer
        })
      }
    }

    def modifyEndpoints(
      f: List[EndpointMapping[F]] => List[EndpointMapping[F]]
    )(
      implicit pipe: Pipe[Alg]
    ): StreamingInterpreterBuilder[Alg, F] = {

      lazy val newBuilder: StreamingInterpreterBuilder[Alg, F] = pipe.mapK5(builder, fk)
      lazy val fk =
        new PolyFunction5[StreamingBuilder.Of[Alg, F]#R, StreamingBuilder.Of[Alg, F]#R] {
          def apply[II, EE, OO, SII, SOO](
            fa: StreamingBuilder[Alg, F, II, EE, OO, SII, SOO]
          ): StreamingBuilder[Alg, F, II, EE, OO, SII, SOO] = fa.copy(
            currentEndpoints = f(fa.currentEndpoints),
            builder = newBuilder,
          )
        }

      newBuilder
    }

  }

  trait AnyOpEndpoint[I, E, O, SI, SO] {
    type Op[_, _, _, _, _]
    val endpoint: Endpoint[Op, I, E, O, SI, SO]
  }

  trait EndpointMapping[F[_]] {
    type I
    type Op[_, _, _, _, _]
    val endpoint: Endpoint[Op, I, _, _, _, _]
    val handler: I => F[Unit]
  }

  case class StreamingBuilder[Alg[_[_, _, _, _, _]], F[_], I, E, O, SI, SO](
    endpoint: AnyOpEndpoint[I, E, O, SI, SO],
    builder: StreamingInterpreterBuilder[Alg, F],
    currentEndpoints: List[EndpointMapping[F]],
  ) {
    private val e = endpoint.endpoint

    def apply(
      f: I => F[Unit]
    )(
      implicit pipe: Pipe[Alg]
    ): StreamingInterpreterBuilder[Alg, F] = builder.addEndpoint(e, f)

  }

  object StreamingBuilder {

    type Of[Alg[_[_, _, _, _, _]], F[_]] = {
      type R[I, E, O, SI, SO] = StreamingBuilder[Alg, F, I, E, O, SI, SO]
    }

  }

  def makeStreamingConsumer[Alg[_[_, _, _, _, _]], F[_]](
    // appName: String,
    // streamName: String,
  )(
    implicit service: PipeProvider[Alg]
  ): StreamingInterpreterBuilder[service.PipeAlg, F] = {

    val p = service.pipe

    type In[I, E, O, SI, SO] = p.service.Endpoint[I, E, O, SI, SO]
    type Out[I, E, O, SI, SO] = p.service.Endpoint[I, E, O, SI, SO]

    // this and the polyfunction need to be lazy vals to avoid a stack overflow.
    // the PF needs a reference to the end result of the building, which is this impl.
    lazy val impl = service
      .pipe
      .mapK5(
        p.endpoints,
        toInitialBuilder,
      )

    lazy val toInitialBuilder
      : PolyFunction5[p.service.Endpoint, StreamingBuilder.Of[service.PipeAlg, F]#R] =
      new PolyFunction5[p.service.Endpoint, StreamingBuilder.Of[service.PipeAlg, F]#R] {
        def apply[A0, A1, A2, A3, A4](
          fa: p.service.Endpoint[A0, A1, A2, A3, A4]
        ): StreamingBuilder[service.PipeAlg, F, A0, A1, A2, A3, A4] =
          new StreamingBuilder(
            new AnyOpEndpoint[A0, A1, A2, A3, A4] {
              type Op[I, E, O, SI, SO] = p.service.Operation[I, E, O, SI, SO]
              val endpoint: Endpoint[Op, A0, A1, A2, A3, A4] = fa
            },
            impl,
            currentEndpoints = Nil,
          )
      }

    impl
  }

  // makeStreamingConsumer
  //   .forUnion[MyEvent]()
  //   .types(MyEvent1, MyEvent2)
  //   .handleOne {
  //     case e: MyEvent1 => ???
  //     case e: MyEvent2 => ???
  //   }

  // lol, imagine not having a console logger in log4cats-core
  implicit val logger = Slf4jLogger.getLogger[IO]

  val consumer: Resource[IO, Unit] = Trace
    .ioTraceForEntryPoint(Log.entryPoint[IO]("my-consumer"))
    .toResource
    .flatMap { implicit trace =>
      makeStreamingConsumer[MyEventServiceGen, IO]()
        .event1Stream { event =>
          Trace[IO].span("nested") {
            IO.println("event 1: " + event)
          }
        }
        .event2Stream { event =>
          IO.println("event 2: " + event)
        }
        .traced
        // event3 is ignored
        .run(
          // todo: these should be passed earlier, probably
          streamName = "jk-test-1",
          appName = "test",
        )
    }

  def run: IO[Unit] = consumer.useForever

}

object Producer extends IOApp.Simple {

  def msg(text: String) =
    s"""{
       |   "name": "$text"
       |}""".stripMargin

  import kinesis4cats.smithy4s.client.logging.instances.show._

  import kinesis4cats.producer.logging.instances.show._
  import kinesis4cats.logging.instances.show._

  val logger = Slf4jLogger.getLogger[IO]

  def run: IO[Unit] =
    logger.info("starting") *>
      EmberClientBuilder
        .default[IO]
        .withCheckEndpointAuthentication(false)
        .build
        .use { c =>
          logger.info("starting producer") *>
            LocalstackKinesisProducer
              .resource[IO](
                client = c,
                streamName = "jk-test-1",
                region = IO(AwsRegion.US_EAST_1),
              )
              .use { kpl =>
                logger.info("producing") *>
                  kpl
                    .put(
                      NonEmptyList.fromListUnsafe(
                        List.fill(10)(
                          producer.Record(
                            data = msg(UUID.randomUUID().toString()).getBytes(),
                            partitionKey = "test",
                          )
                        )
                      )
                    )
                    .void *> logger.info("produced")
              }
        }

}

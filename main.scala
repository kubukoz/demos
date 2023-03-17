//> using scala "2.13.10"
//> using lib "com.disneystreaming.smithy4s::smithy4s-core:0.17.5"
//> using plugin "org.typelevel:::kind-projector:0.13.2"
//> using option "-Wunused:imports"
import demo._
import smithy4s.Endpoint
import smithy4s.Service
import smithy4s.kinds.FunctorK5
import smithy4s.kinds.PolyFunction5

object Consume extends App {
  //
  //
  //
  //
  //
  // boilerplate / things to be added to smithy4s start here
  //
  //
  //
  //
  //

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

  implicit val provider: PipeProvider.Aux[MyEventServiceGen, MyEventServicePipe] =
    new PipeProvider[MyEventServiceGen] {
      type PipeAlg[Op[_, _, _, _, _]] = MyEventServicePipe[Op]
      val pipe: Pipe.Aux[PipeAlg, MyEventServiceGen] = MyEventServicePipe
    }

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

  //
  //
  //
  //
  //
  // end boilerplate
  //
  //
  //
  //
  //

  type StreamingInterpreterBuilder[Alg[_[_, _, _, _, _]], F[_]] = Alg[StreamingBuilder.Of[Alg, F]#R]

  implicit class StreamingInterpreterBuilderOps[Alg[_[_, _, _, _, _]], F[_]](
    builder: StreamingInterpreterBuilder[Alg, F]
  ) {

    def showRegisteredOperations(
      implicit pipe: Pipe[Alg]
    ): List[String] =
      // the choice of endpoint is arbitrary - all operations return the same value - so I'm using .head.
      // anyway, here's why I think I need a toPolyFunction...
      pipe
        .toPolyFunction(builder)(pipe.service.endpoints.head)
        .currentEndpoints

    def addEndpoint[Op[_, _, _, _, _], I, E, O, SI, SO](
      endpoint: Endpoint[Op, I, E, O, SI, SO]
    )(
      implicit pipe: Pipe[Alg]
    ): StreamingInterpreterBuilder[Alg, F] = {

      type In = I
      type Opp[I, E, O, SI, SO] = Op[I, E, O, SI, SO]
      val e = endpoint

      modifyEndpoints {
        _.appended(e.name)
      }
    }

    def modifyEndpoints(
      f: List[String] => List[String]
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
    currentEndpoints: List[String],
  ) {

    def apply(
      consumer: Any => Unit
    )(
      implicit pipe: Pipe[Alg]
    ): StreamingInterpreterBuilder[Alg, F] = builder.addEndpoint(endpoint.endpoint)

  }

  object StreamingBuilder {

    type Of[Alg[_[_, _, _, _, _]], F[_]] = {
      type R[I, E, O, SI, SO] = StreamingBuilder[Alg, F, I, E, O, SI, SO]
    }

  }

  def buildConsumer[Alg[_[_, _, _, _, _]], F[_]](
    implicit service: PipeProvider[Alg]
  ): StreamingInterpreterBuilder[service.PipeAlg, F] = {

    val p = service.pipe

    type In[I, E, O, SI, SO] = p.service.Endpoint[I, E, O, SI, SO]
    type Out[I, E, O, SI, SO] = p.service.Endpoint[I, E, O, SI, SO]

    // start here

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

  println {
    buildConsumer[MyEventServiceGen, Any /* effect goes here */ ]
      .event1Stream { event =>
        ???
      }
      .event2Stream { event =>
        ???
      }
      .showRegisteredOperations
  }

}

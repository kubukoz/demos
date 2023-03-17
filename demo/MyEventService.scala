package demo

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.Schema.unit

trait MyEventServiceGen[F[_, _, _, _, _]] {
  self =>

  def event1Stream(name: String): F[Event1, Nothing, Unit, Nothing, Nothing]
  def event2Stream(name: String): F[Event2, Nothing, Unit, Nothing, Nothing]
  def event3Stream(name: String): F[Event3, Nothing, Unit, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[MyEventServiceGen[F]] = Transformation.of[MyEventServiceGen[F]](this)
}

object MyEventServiceGen extends Service.Mixin[MyEventServiceGen, MyEventServiceOperation] {

  val id: ShapeId = ShapeId("demo", "MyEventService")
  val version: String = ""

  val hints: Hints = Hints.empty

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[MyEventServiceOperation, _, _, _, _, _]] = List(
    MyEventServiceOperation.Event1Stream,
    MyEventServiceOperation.Event2Stream,
    MyEventServiceOperation.Event3Stream,
  )

  def endpoint[I, E, O, SI, SO](op: MyEventServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends MyEventServiceOperation.Transformed[MyEventServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: MyEventServiceGen[MyEventServiceOperation] = MyEventServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: MyEventServiceGen[P], f: PolyFunction5[P, P1]): MyEventServiceGen[P1] = new MyEventServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[MyEventServiceOperation, P]): MyEventServiceGen[P] = new MyEventServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: MyEventServiceGen[P]): PolyFunction5[MyEventServiceOperation, P] = MyEventServiceOperation.toPolyFunction(impl)

}

sealed trait MyEventServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: MyEventServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[MyEventServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object MyEventServiceOperation {

  object reified extends MyEventServiceGen[MyEventServiceOperation] {
    def event1Stream(name: String) = Event1Stream(Event1(name))
    def event2Stream(name: String) = Event2Stream(Event2(name))
    def event3Stream(name: String) = Event3Stream(Event3(name))
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: MyEventServiceGen[P], f: PolyFunction5[P, P1]) extends MyEventServiceGen[P1] {
    def event1Stream(name: String) = f[Event1, Nothing, Unit, Nothing, Nothing](alg.event1Stream(name))
    def event2Stream(name: String) = f[Event2, Nothing, Unit, Nothing, Nothing](alg.event2Stream(name))
    def event3Stream(name: String) = f[Event3, Nothing, Unit, Nothing, Nothing](alg.event3Stream(name))
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: MyEventServiceGen[P]): PolyFunction5[MyEventServiceOperation, P] = new PolyFunction5[MyEventServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: MyEventServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  case class Event1Stream(input: Event1) extends MyEventServiceOperation[Event1, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyEventServiceGen[F]): F[Event1, Nothing, Unit, Nothing, Nothing] = impl.event1Stream(input.name)
    def endpoint: (Event1, smithy4s.Endpoint[MyEventServiceOperation,Event1, Nothing, Unit, Nothing, Nothing]) = (input, Event1Stream)
  }
  object Event1Stream extends smithy4s.Endpoint[MyEventServiceOperation,Event1, Nothing, Unit, Nothing, Nothing] {
    val id: ShapeId = ShapeId("demo", "Event1Stream")
    val input: Schema[Event1] = Event1.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints.empty
    def wrap(input: Event1) = Event1Stream(input)
  }
  case class Event2Stream(input: Event2) extends MyEventServiceOperation[Event2, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyEventServiceGen[F]): F[Event2, Nothing, Unit, Nothing, Nothing] = impl.event2Stream(input.name)
    def endpoint: (Event2, smithy4s.Endpoint[MyEventServiceOperation,Event2, Nothing, Unit, Nothing, Nothing]) = (input, Event2Stream)
  }
  object Event2Stream extends smithy4s.Endpoint[MyEventServiceOperation,Event2, Nothing, Unit, Nothing, Nothing] {
    val id: ShapeId = ShapeId("demo", "Event2Stream")
    val input: Schema[Event2] = Event2.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints.empty
    def wrap(input: Event2) = Event2Stream(input)
  }
  case class Event3Stream(input: Event3) extends MyEventServiceOperation[Event3, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyEventServiceGen[F]): F[Event3, Nothing, Unit, Nothing, Nothing] = impl.event3Stream(input.name)
    def endpoint: (Event3, smithy4s.Endpoint[MyEventServiceOperation,Event3, Nothing, Unit, Nothing, Nothing]) = (input, Event3Stream)
  }
  object Event3Stream extends smithy4s.Endpoint[MyEventServiceOperation,Event3, Nothing, Unit, Nothing, Nothing] {
    val id: ShapeId = ShapeId("demo", "Event3Stream")
    val input: Schema[Event3] = Event3.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints.empty
    def wrap(input: Event3) = Event3Stream(input)
  }
}

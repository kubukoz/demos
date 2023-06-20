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

trait MyServiceGen[F[_, _, _, _, _]] {
  self =>

  def myOperation(assignments: Option[Assignments] = None): F[MyOperationInput, Nothing, Unit, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[MyServiceGen[F]] = Transformation.of[MyServiceGen[F]](this)
}

object MyServiceGen extends Service.Mixin[MyServiceGen, MyServiceOperation] {

  val id: ShapeId = ShapeId("demo", "MyService")
  val version: String = ""

  val hints: Hints = Hints.empty

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[MyServiceOperation, _, _, _, _, _]] = List(
    MyServiceOperation.MyOperation,
  )

  def endpoint[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends MyServiceOperation.Transformed[MyServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: MyServiceGen[MyServiceOperation] = MyServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: MyServiceGen[P], f: PolyFunction5[P, P1]): MyServiceGen[P1] = new MyServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[MyServiceOperation, P]): MyServiceGen[P] = new MyServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: MyServiceGen[P]): PolyFunction5[MyServiceOperation, P] = MyServiceOperation.toPolyFunction(impl)

}

sealed trait MyServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[MyServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object MyServiceOperation {

  object reified extends MyServiceGen[MyServiceOperation] {
    def myOperation(assignments: Option[Assignments] = None) = MyOperation(MyOperationInput(assignments))
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: MyServiceGen[P], f: PolyFunction5[P, P1]) extends MyServiceGen[P1] {
    def myOperation(assignments: Option[Assignments] = None) = f[MyOperationInput, Nothing, Unit, Nothing, Nothing](alg.myOperation(assignments))
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: MyServiceGen[P]): PolyFunction5[MyServiceOperation, P] = new PolyFunction5[MyServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  case class MyOperation(input: MyOperationInput) extends MyServiceOperation[MyOperationInput, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[MyOperationInput, Nothing, Unit, Nothing, Nothing] = impl.myOperation(input.assignments)
    def endpoint: (MyOperationInput, smithy4s.Endpoint[MyServiceOperation,MyOperationInput, Nothing, Unit, Nothing, Nothing]) = (input, MyOperation)
  }
  object MyOperation extends smithy4s.Endpoint[MyServiceOperation,MyOperationInput, Nothing, Unit, Nothing, Nothing] {
    val id: ShapeId = ShapeId("demo", "MyOperation")
    val input: Schema[MyOperationInput] = MyOperationInput.schema.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints.empty
    def wrap(input: MyOperationInput) = MyOperation(input)
  }
}

package demo

import smithy4s.Endpoint
import smithy4s.Errorable
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.StreamingSchema
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.Schema.UnionSchema
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union
import smithy4s.schema.Schema.unit

trait DemoServiceGen[F[_, _, _, _, _]] {
  self =>

  def demoOp(): F[Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing]

  def transform: Transformation.PartiallyApplied[DemoServiceGen[F]] = Transformation.of[DemoServiceGen[F]](this)
}

object DemoServiceGen extends Service.Mixin[DemoServiceGen, DemoServiceOperation] {

  val id: ShapeId = ShapeId("demo", "DemoService")
  val version: String = ""

  val hints: Hints = Hints(
    alloy.SimpleRestJson(),
  )

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: List[smithy4s.Endpoint[DemoServiceOperation, _, _, _, _, _]] = List(
    DemoServiceOperation.DemoOp,
  )

  def endpoint[I, E, O, SI, SO](op: DemoServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends DemoServiceOperation.Transformed[DemoServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: DemoServiceGen[DemoServiceOperation] = DemoServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: DemoServiceGen[P], f: PolyFunction5[P, P1]): DemoServiceGen[P1] = new DemoServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[DemoServiceOperation, P]): DemoServiceGen[P] = new DemoServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: DemoServiceGen[P]): PolyFunction5[DemoServiceOperation, P] = DemoServiceOperation.toPolyFunction(impl)

  type DemoOpError = DemoServiceOperation.DemoOpError
  val DemoOpError = DemoServiceOperation.DemoOpError
}

sealed trait DemoServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: DemoServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def endpoint: (Input, Endpoint[DemoServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput])
}

object DemoServiceOperation {

  object reified extends DemoServiceGen[DemoServiceOperation] {
    def demoOp() = DemoOp()
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: DemoServiceGen[P], f: PolyFunction5[P, P1]) extends DemoServiceGen[P1] {
    def demoOp() = f[Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing](alg.demoOp())
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: DemoServiceGen[P]): PolyFunction5[DemoServiceOperation, P] = new PolyFunction5[DemoServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: DemoServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  case class DemoOp() extends DemoServiceOperation[Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: DemoServiceGen[F]): F[Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing] = impl.demoOp()
    def endpoint: (Unit, smithy4s.Endpoint[DemoServiceOperation,Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing]) = ((), DemoOp)
  }
  object DemoOp extends smithy4s.Endpoint[DemoServiceOperation,Unit, DemoServiceOperation.DemoOpError, Unit, Nothing, Nothing] with Errorable[DemoOpError] {
    val id: ShapeId = ShapeId("demo", "DemoOp")
    val input: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Input.widen)
    val output: Schema[Unit] = unit.addHints(smithy4s.internals.InputOutput.Output.widen)
    val streamedInput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val streamedOutput: StreamingSchema[Nothing] = StreamingSchema.nothing
    val hints: Hints = Hints(
      smithy.api.Http(method = smithy.api.NonEmptyString("GET"), uri = smithy.api.NonEmptyString("/demo"), code = 200),
      smithy.api.Readonly(),
    )
    def wrap(input: Unit) = DemoOp()
    override val errorable: Option[Errorable[DemoOpError]] = Some(this)
    val error: UnionSchema[DemoOpError] = DemoOpError.schema
    def liftError(throwable: Throwable): Option[DemoOpError] = throwable match {
      case e: DynamoError => Some(DemoOpError.DynamoErrorCase(e))
      case _ => None
    }
    def unliftError(e: DemoOpError): Throwable = e match {
      case DemoOpError.DynamoErrorCase(e) => e
    }
  }
  sealed trait DemoOpError extends scala.Product with scala.Serializable {
    @inline final def widen: DemoOpError = this
  }
  object DemoOpError extends ShapeTag.Companion[DemoOpError] {
    val id: ShapeId = ShapeId("demo", "DemoOpError")

    val hints: Hints = Hints.empty

    case class DynamoErrorCase(dynamoError: DynamoError) extends DemoOpError

    object DynamoErrorCase {
      val hints: Hints = Hints.empty
      val schema: Schema[DynamoErrorCase] = bijection(DynamoError.schema.addHints(hints), DynamoErrorCase(_), _.dynamoError)
      val alt = schema.oneOf[DemoOpError]("DynamoError")
    }

    implicit val schema: UnionSchema[DemoOpError] = union(
      DynamoErrorCase.alt,
    ){
      case c: DynamoErrorCase => DynamoErrorCase.alt(c)
    }
  }
}

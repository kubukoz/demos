package object demo {
  type DemoService[F[_]] = smithy4s.kinds.FunctorAlgebra[DemoServiceGen, F]
  val DemoService = DemoServiceGen


}
package object demo {
  type MyService[F[_]] = smithy4s.kinds.FunctorAlgebra[MyServiceGen, F]
  val MyService = MyServiceGen

  type Assignments = demo.Assignments.Type

}
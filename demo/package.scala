package object demo {
  type MyEventService[F[_]] = smithy4s.kinds.FunctorAlgebra[MyEventServiceGen, F]
  val MyEventService = MyEventServiceGen


}
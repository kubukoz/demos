package object hello {
  type WeatherStreamService[F[_]] = smithy4s.kinds.FunctorAlgebra[WeatherStreamServiceGen, F]
  val WeatherStreamService = WeatherStreamServiceGen


}
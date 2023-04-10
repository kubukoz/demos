package app

import cats.effect.IO
import demo._
import org.http4s.dom.FetchClientBuilder
import org.http4s.implicits._
import smithy4s.http4s.SimpleRestJsonBuilder
import tyrian._

object ProductApi {

  val client: ProductService[IO] =
    SimpleRestJsonBuilder(ProductService)
      .client(FetchClientBuilder[IO].create)
      .uri(uri"https://dummyjson.com")
      .use
      .toTry
      .get

}

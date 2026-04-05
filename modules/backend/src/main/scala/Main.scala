package backend

import cats.effect.IO
import cats.effect.IOApp
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import demosmithy.*

object Main extends IOApp.Simple {

  private val greetService: GreetService[IO] = new GreetService[IO] {
    def greet(name: String): IO[GreetOutput] =
      IO.pure(GreetOutput(greeting = s"hello $name!"))
  }

  val run: IO[Unit] =
    SimpleRestJsonBuilder
      .routes(greetService)
      .resource
      .flatMap { routes =>
        EmberServerBuilder
          .default[IO]
          .withHost(host"0.0.0.0")
          .withPort(port"9000")
          .withHttpApp(routes.orNotFound)
          .build
      }
      .useForever

}

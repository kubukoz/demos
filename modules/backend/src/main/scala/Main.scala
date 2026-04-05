package backend

import cats.effect.IO
import cats.effect.IOApp
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger
import smithy4s.http4s.SimpleRestJsonBuilder
import demosmithy.*
import examples.smithy.server.ServerMain

object Main extends IOApp.Simple {

  private val greetService: GreetService[IO] =
    new GreetService[IO] {
      def greet(name: String): IO[GreetOutput] =
        IO.pure(GreetOutput(greeting = s"hello $name!"))
    }

  val run: IO[Unit] =
    SimpleRestJsonBuilder
      .routes(greetService)
      .resource
      .flatMap { routes =>
        val app =
          Logger.httpApp(logHeaders = true, logBody = true, logAction = Some(IO.println))(
            routes.orNotFound
          )
        EmberServerBuilder
          .default[IO]
          .withHost(host"0.0.0.0")
          .withPort(port"9000")
          .withHttpApp(app)
          .build
      }
      .useForever <& ServerMain.run

}

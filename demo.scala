import org.http4s.Response

import org.http4s.Uri

import org.http4s.Method
import org.http4s.implicits._
import io.circe.Codec

import org.http4s.HttpRoutes
import org.http4s.HttpApp
import org.http4s.client.Client
import cats.effect.IO
import org.http4s.circe.CirceEntityCodec._
import io.circe.syntax._
import cats.effect.IOApp
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.ember.client.EmberClientBuilder
import io.circe.Decoder
import io.circe.Encoder
import com.kubukoz.DebugUtils

@annotation.experimental
object Demo extends IOApp.Simple {

  case class User(id: Int, name: String, age: Int) derives Codec.AsObject

  trait Api {
    def getUsers(): IO[List[User]]
    def getUser(id: Int): IO[User]
    def createUser(user: User): IO[User]
    def updateUser(user: User): IO[User]
    def deleteUser(id: Int): IO[Unit]
  }

  given API[Api] = API.derived

  DebugUtils.withDesugarIgnore {
    enum ScalaVersion {
      case Scala2
      case Other(s: String)
    }
  }

  val impl: Api =
    new Api {
      def getUsers(): IO[List[User]] = IO(List(User(1, "John", 20)))
      def getUser(id: Int): IO[User] = IO(User(1, "John", 20))
      def createUser(user: User): IO[User] = IO(user)
      def updateUser(user: User): IO[User] = IO(user)
      def deleteUser(id: Int): IO[Unit] = IO.unit
    }

  def router(impl: Api): HttpApp[IO] = API[Api].toRoutes(impl)

  def client(c: Client[IO], base: Uri): Api = API[Api].toClient(c, base)

  import com.comcast.ip4s._

  def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHttpApp(router(impl))
    .withHost(host"0.0.0.0")
    .withPort(port"8080")
    .withErrorHandler { case e => IO.consoleForIO.printStackTrace(e) *> IO.raiseError(e) }
    .build
    .use { server =>
      EmberClientBuilder.default[IO].build.use { c =>
        val apiClient = client(c, server.baseUri)

        apiClient
          .getUser(42)
          .flatMap(IO.println(_)) *>
          apiClient
            .getUsers()
            .flatMap(IO.println(_))
      }
    }

}

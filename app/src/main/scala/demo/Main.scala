package demo

import cats.effect.IO
import io.chrisdavenport.crossplatformioapp.CrossPlatformIOApp

object Main extends CrossPlatformIOApp.Simple {

  override def run: IO[Unit] = IO.println("hello world!")

}

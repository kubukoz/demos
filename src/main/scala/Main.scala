import cats.effect.IOApp

import cats.effect.IO
import org.http4s.ember.client.EmberClientBuilder
import smithy4s.aws.AwsClient
import smithy4s.aws.http4s._
import org.http4s.implicits._
import com.amazonaws.kinesis._
import smithy4s.aws.AwsEnvironment
import smithy4s.aws.kernel.AwsRegion

object Main extends IOApp.Simple {

  def run: IO[Unit] = EmberClientBuilder
    .default[IO]
    .build
    .flatMap { c =>
      Kinesis.awsClient(c, AwsRegion.US_EAST_1)
    }
    .use { k =>
      k.listStreams().run
    }
    .flatMap(IO.println(_))

}

package hello

import cats.effect.IO
import cats.effect.IOApp
import smithy4s.aws.AwsClient
import smithy4s.aws.AwsEnvironment
import org.http4s.ember.client.EmberClientBuilder
import smithy4s.aws.kernel.AwsRegion
import com.amazonaws.sagemaker.SageMaker

object Main extends IOApp.Simple {
  def run = EmberClientBuilder.default[IO].build.use { c =>
    AwsEnvironment
      .default(c, AwsRegion.US_EAST_1)
      .use { env =>
        AwsClient(SageMaker, env).use { sm =>
          sm.listEndpoints().debug().void
        }
      }
  }
}

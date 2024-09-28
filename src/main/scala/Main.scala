package smithy4sdemo

import cats.effect.IO
import cats.effect.IOApp
import org.http4s.ember.client.EmberClientBuilder
import smithy4s.aws.AwsClient
import smithy4s.aws.AwsEnvironment
import smithy4s.aws.kernel.AwsRegion
import smithy4s.kinds.stubs.Kind1
import com.amazonaws.sagemaker.SageMaker
import org.http4s.client.middleware.Logger
import org.http4s.client.Client
import org.http4s.Uri
import com.comcast.ip4s._
import org.typelevel.ci.CIString

object Main extends IOApp.Simple {

  def run: IO[Unit] = EmberClientBuilder
    .default[IO]
    .build
    // .map { client =>
    //   Client[IO] { req =>
    //     if (
    //       req.uri.host.exists(
    //         _.value == "cp.tracking.ListTrackers.us-east-1.amazonaws.com"
    //       )
    //     ) {
    //       println(
    //         req.headers.headers
    //           .map(h => s"${h.name.toString}: ${h.value}")
    //           .mkString("\n")
    //       )

    //       client.run(
    //         req.withUri(
    //           req.uri.copy(authority =
    //             req.uri.authority.map(
    //               _.copy(host =
    //                 Uri.Host.fromIp4sHost(
    //                   host"cp.tracking.geo.us-east-1.amazonaws.com"
    //                 )
    //               )
    //             )
    //           )
    //         )
    //       )
    //     } else
    //       client.run(req)
    //   }
    // }
    .map(Logger.colored(true, true, logAction = Some(IO.println)))
    .use { c =>
      AwsEnvironment.default(c, AwsRegion.US_EAST_1).use { env =>
        AwsClient(com.amazonaws.location.Location, env).use { loc =>
          AwsClient(SageMaker, env).use { sm =>
            loc.listTrackers(maxResults = 1).debug() *>
              sm.listEndpoints().debug() *>
              sm.listDomains().debug() *>
              sm.listApps().debug().void
          }
        }

      }
    }

}

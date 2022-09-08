package com.kubukoz

import cats.effect.IO
import cats.effect.Resource
import cats.implicits._
import fs2.io.file.Files
import fs2.io.file.Path
import smithy4s.Service
import smithy4s.aws.AwsClient
import smithy4s.aws.kernel.AwsRegion

object Commons {

  def backend[Alg[_[_, _, _, _, _]], Op[_, _, _, _, _]](
      service: Service.Provider[Alg, Op]
  ): BackendPartiallyApplied[Alg, Op] = ???

  final class BackendPartiallyApplied[
      Alg[_[_, _, _, _, _]],
      Op[_, _, _, _, _]
  ] private[Commons] (
      private val service: Service.Provider[Alg, Op]
  ) {

    def apply[F[_]: cats.effect.Async](
        local: Boolean,
        region: AwsRegion = AwsRegion.US_EAST_1
    ): Resource[F, AwsClient[Alg, F]] = ???

  }

}

package com.comcast.lms.smithy4s

import smithy4s.RefinementProvider
import com.comcast.platform.types.Sku
import smithy4s.ShapeTag
import hello.SkuFormat
import smithy4s.Refinement

object providers {

  given RefinementProvider[SkuFormat, String, Sku] = Refinement.drivenBy[SkuFormat](
    s => Right(Sku(s)),
    (s: Sku) => s.asInstanceOf[String],
  )

}

package com.comcast.platform.types

opaque type Sku <: String = String

object Sku {
  def apply(s: String): Sku = s
}

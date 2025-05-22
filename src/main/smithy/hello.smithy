$version: "2"

namespace hello

use smithy4s.meta#refinement

@trait(selector: "string")
structure skuFormat {}

@skuFormat
string Sku

apply skuFormat @refinement(
    targetType: "com.comcast.platform.types.Sku"
    providerImport: "com.comcast.lms.smithy4s.providers.given"
)

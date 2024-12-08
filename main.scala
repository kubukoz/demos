//> using platform "native"

import libhidapi.all.*
import scala.util.Using
import scalanative.unsafe.*
import scalanative.unsigned.*
import scala.scalanative.posix.wchar
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

@main def run = {
  require(hid_init() == 0)

  try {
    val deviceInfoStart = hid_enumerate(0.toUShort, 0.toUShort)

    try List
        .unfold(deviceInfoStart) { deviceInfo =>
          Option((!deviceInfo).next).map(dev => (dev, dev))
        }
        .map { deviceInfo =>
          val vendorId = (!deviceInfo).vendor_id
          val productId = (!deviceInfo).product_id

          val productString = fromCWideString(
            (!deviceInfo).product_string.asInstanceOf[CWideString],
            StandardCharsets.UTF_8,
          )

          println(
            s"Vendor ID: $vendorId, Product ID: $productId, Product String: $productString"
          )
        }
    finally hid_free_enumeration(deviceInfoStart)
  } finally hid_exit()

}

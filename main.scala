//> using platform "native"

import libhidapi.all.*
import scala.util.Using
import scalanative.unsafe.*
import scalanative.unsigned.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import scala.scalanative.runtime.ffi
import scala.scalanative.runtime.Platform
import scala.scalanative.posix.wchar
import CExtras.wcstombs

@main def run = {
  require(hid_init() == 0)

  try {
    val deviceInfoStart = hid_enumerate(0.toUShort, 0.toUShort)

    try
      (deviceInfoStart :: List
        .unfold(deviceInfoStart) { deviceInfo =>
          Option((!deviceInfo).next).map(dev => (dev, dev))
        })
        .map { deviceInfo =>
          val vendorId = (!deviceInfo).vendor_id
          val productId = (!deviceInfo).product_id

          val productString = fromwchar_tstring((!deviceInfo).product_string)

          println(
            s"Vendor ID: $vendorId, Product ID: $productId, Product String: $productString (len ${productString.length()})"
          )
        }
    finally hid_free_enumeration(deviceInfoStart)
  } finally hid_exit()

}

def fromwchar_tstring(input: Ptr[wchar_t]): String = {

  val inputPtr = stackalloc[Ptr[wchar_t]](1)
  !inputPtr = input

  val utf8Size = wcstombs(null, input, 0.toCSize)

  if utf8Size == -1 then throw new RuntimeException("wcsrtombs failed")

  val utf8Bytes = new Array[Byte](utf8Size.toInt)

  val written = wcstombs(utf8Bytes.atUnsafe(0), input, utf8Size)

  if written == -1 then throw new RuntimeException("wcsrtombs failed")

  new String(utf8Bytes)
}

@extern
object CExtras {
  @extern
  def wcstombs(dest: Ptr[Byte], src: Ptr[wchar_t], n: CSize): CSize = extern
}

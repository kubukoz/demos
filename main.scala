//> using target.platform "native"
import libhidapi.all.*

import scalanative.unsafe.*
import scalanative.unsigned.*

@main def run = {
  require(hid_init() == 0)

  try {
    val device = hid_open(0x054c.toUShort, 0x0ce6.toUShort, null)
    require(device != null, "hid_open failed")

    val size = 64
    val data = new Array[Byte](size)

    while (true) {
      hid_read(device, data.atUnsafe(0).asInstanceOf[Ptr[CUnsignedChar]], size.toUInt)

      System.out.write(data)
      Thread.sleep(100)
    }
  } finally hid_exit()

}

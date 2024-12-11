//> using target.platform "jvm"
//> using dep org.hid4java:hid4java:0.8.0
import org.hid4java.HidManager

@main def run = {
  val hid = HidManager.getHidServices()

  try {
    val device = hid.getHidDevice(0x054c, 0x0ce6, null)

    require(device != null, "getHidDevice failed")

    val data: Array[Byte] = new Array[Byte](64)

    while (true) {
      device.read(data)
      System.out.write(data)
      Thread.sleep(100)
    }
  } finally hid.shutdown()
}

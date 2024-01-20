import ggwave.aliases.ggwave_Instance
import ggwave.enumerations.ggwave_SampleFormat
import ggwave.structs.ggwave_Parameters

import scala.scalanative.libc.string._
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._
import scala.util.Using

object Main {

  def main(
    args: Array[String]
  ): Unit = {
    val data = args.head.getBytes()
    val (waveformBuffer, waveformSize) = encodeToSamples(data.at(0), data.size.toULong)

    Zone { implicit z =>
      writeWav(
        data = waveformBuffer,
        dataBytes = waveformSize,
      )
    }
  }

  def makeGGW(
  )(
    using Zone
  ): ggwave_Instance = {
    import ggwave.all._
    import ggwave.constants._

    val params = ggwave_getDefaultParameters()
    params.operatingMode = GGWAVE_OPERATING_MODE_TX.toInt
    params.sampleFormatInp = ggwave_SampleFormat.GGWAVE_SAMPLE_FORMAT_U8
    params.sampleFormatOut = ggwave_SampleFormat.GGWAVE_SAMPLE_FORMAT_U8

    ggwave_init(params)
  }

  def encodeToSamples(
    payload: Ptr[Byte],
    payloadSize: CSize,
  ): (
    Ptr[Byte],
    CSize,
  ) = Zone { implicit z =>
    import ggwave.all._

    val ggw = makeGGW()

    try {
      def write(
        query: Int,
        ptr: Ptr[Byte],
      ) = ggwave_encode(
        ggw,
        payloadBuffer = payload,
        payloadSize = payloadSize.toInt,
        protocolId = ggwave_ProtocolId.GGWAVE_PROTOCOL_AUDIBLE_FAST,
        volume = 10,
        waveformBuffer = ptr,
        query = query,
      )
      // don't actually write, just query size of buffer
      val bufferSize: Int = write(query = 1, ptr = null)

      val waveformBuffer = alloc[Byte](bufferSize)

      val outputCode = write(query = 0, ptr = waveformBuffer)

      require(outputCode >= 0, s"output code must be >=0, was: $outputCode")

      (waveformBuffer, bufferSize.toUInt)
    } finally ggwave_free(ggw)
  }

  def writeWav(
    data: Ptr[Byte],
    dataBytes: CSize,
  )(
    using Zone
  ) = {
    import dr_wav.all._
    val wav = drwav()

    val fmtPtr = drwav_data_format()
    val fmt = !fmtPtr
    fmt.container = drwav_container.drwav_container_riff
    fmt.channels = drwav_uint32(1.toUInt)
    fmt.sampleRate = drwav_uint32(48000.toUInt)
    fmt.bitsPerSample = drwav_uint32(8.toUInt)
    // DR_WAVE_FORMAT_PCM
    fmt.format = drwav_uint32(1.toUInt)

    // typedef size_t (* drwav_write_proc)(void* pUserData, const void* pData, size_t bytesToWrite);
    // def onWrite(
    //   userData: Ptr[Byte],
    //   data: Ptr[Byte],
    //   bytesToWrite: CSize,
    // ): CSize = {
    //   println(s"onWrite: $userData, $data, $bytesToWrite")
    //   bytesToWrite
    // }

    // // opaque type drwav_seek_proc = CFuncPtr3[Ptr[Byte], CInt, drwav_seek_origin, drwav_bool32]
    // def onSeek(
    //   userData: Ptr[Byte],
    //   offset: CInt,
    //   origin: drwav_seek_origin,
    // ): drwav_bool32 = {
    //   println(s"onSeek: $userData, $offset, $origin")
    //   drwav_uint32(1.toUInt)
    // }

    dr_wav
      .functions
      .drwav_init_file_write(
        pWav = wav,
        filename = c"out.wav",
        pFormat = fmtPtr,
        null,
      )

    drwav_write_raw(wav, dataBytes, data)
    // dr_wav
    //   .functions
    //   .drwav_init_write(
    //     pWav = wav,
    //     pFormat = fmtPtr,
    //     onWrite = drwav_write_proc(onWrite),
    //     onSeek = drwav_seek_proc(onSeek),
    //     pUserData = ???,
    //     pAllocationCallbacks = ???,
    //   )

    drwav_uninit(wav)
  }

}

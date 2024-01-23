package ggwave

import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.*

object predef:
  private[ggwave] trait CEnumU[T](using eq: T =:= UInt):
    given Tag[T] = Tag.UInt.asInstanceOf[Tag[T]]
    extension (inline t: T)
     inline def int: CInt = eq.apply(t).toInt
     inline def uint: CUnsignedInt = eq.apply(t)
     inline def value: CUnsignedInt = eq.apply(t)


object enumerations:
  import predef.*
  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  opaque type ggwave_Filter = CUnsignedInt
  object ggwave_Filter extends CEnumU[ggwave_Filter]:
    given _tag: Tag[ggwave_Filter] = Tag.UInt
    inline def define(inline a: Long): ggwave_Filter = a.toUInt
    val GGWAVE_FILTER_HANN = define(0)
    val GGWAVE_FILTER_HAMMING = define(1)
    val GGWAVE_FILTER_FIRST_ORDER_HIGH_PASS = define(2)
    inline def getName(inline value: ggwave_Filter): Option[String] =
      inline value match
        case GGWAVE_FILTER_HANN => Some("GGWAVE_FILTER_HANN")
        case GGWAVE_FILTER_HAMMING => Some("GGWAVE_FILTER_HAMMING")
        case GGWAVE_FILTER_FIRST_ORDER_HIGH_PASS => Some("GGWAVE_FILTER_FIRST_ORDER_HIGH_PASS")
        case _ => None
    extension (a: ggwave_Filter)
      inline def &(b: ggwave_Filter): ggwave_Filter = a & b
      inline def |(b: ggwave_Filter): ggwave_Filter = a | b
      inline def is(b: ggwave_Filter): Boolean = (a & b) == b

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  opaque type ggwave_ProtocolId = CUnsignedInt
  object ggwave_ProtocolId extends CEnumU[ggwave_ProtocolId]:
    given _tag: Tag[ggwave_ProtocolId] = Tag.UInt
    inline def define(inline a: Long): ggwave_ProtocolId = a.toUInt
    val GGWAVE_PROTOCOL_AUDIBLE_NORMAL = define(0)
    val GGWAVE_PROTOCOL_AUDIBLE_FAST = define(1)
    val GGWAVE_PROTOCOL_AUDIBLE_FASTEST = define(2)
    val GGWAVE_PROTOCOL_ULTRASOUND_NORMAL = define(3)
    val GGWAVE_PROTOCOL_ULTRASOUND_FAST = define(4)
    val GGWAVE_PROTOCOL_ULTRASOUND_FASTEST = define(5)
    val GGWAVE_PROTOCOL_DT_NORMAL = define(6)
    val GGWAVE_PROTOCOL_DT_FAST = define(7)
    val GGWAVE_PROTOCOL_DT_FASTEST = define(8)
    val GGWAVE_PROTOCOL_MT_NORMAL = define(9)
    val GGWAVE_PROTOCOL_MT_FAST = define(10)
    val GGWAVE_PROTOCOL_MT_FASTEST = define(11)
    val GGWAVE_PROTOCOL_CUSTOM_0 = define(12)
    val GGWAVE_PROTOCOL_CUSTOM_1 = define(13)
    val GGWAVE_PROTOCOL_CUSTOM_2 = define(14)
    val GGWAVE_PROTOCOL_CUSTOM_3 = define(15)
    val GGWAVE_PROTOCOL_CUSTOM_4 = define(16)
    val GGWAVE_PROTOCOL_CUSTOM_5 = define(17)
    val GGWAVE_PROTOCOL_CUSTOM_6 = define(18)
    val GGWAVE_PROTOCOL_CUSTOM_7 = define(19)
    val GGWAVE_PROTOCOL_CUSTOM_8 = define(20)
    val GGWAVE_PROTOCOL_CUSTOM_9 = define(21)
    val GGWAVE_PROTOCOL_COUNT = define(22)
    inline def getName(inline value: ggwave_ProtocolId): Option[String] =
      inline value match
        case GGWAVE_PROTOCOL_AUDIBLE_NORMAL => Some("GGWAVE_PROTOCOL_AUDIBLE_NORMAL")
        case GGWAVE_PROTOCOL_AUDIBLE_FAST => Some("GGWAVE_PROTOCOL_AUDIBLE_FAST")
        case GGWAVE_PROTOCOL_AUDIBLE_FASTEST => Some("GGWAVE_PROTOCOL_AUDIBLE_FASTEST")
        case GGWAVE_PROTOCOL_ULTRASOUND_NORMAL => Some("GGWAVE_PROTOCOL_ULTRASOUND_NORMAL")
        case GGWAVE_PROTOCOL_ULTRASOUND_FAST => Some("GGWAVE_PROTOCOL_ULTRASOUND_FAST")
        case GGWAVE_PROTOCOL_ULTRASOUND_FASTEST => Some("GGWAVE_PROTOCOL_ULTRASOUND_FASTEST")
        case GGWAVE_PROTOCOL_DT_NORMAL => Some("GGWAVE_PROTOCOL_DT_NORMAL")
        case GGWAVE_PROTOCOL_DT_FAST => Some("GGWAVE_PROTOCOL_DT_FAST")
        case GGWAVE_PROTOCOL_DT_FASTEST => Some("GGWAVE_PROTOCOL_DT_FASTEST")
        case GGWAVE_PROTOCOL_MT_NORMAL => Some("GGWAVE_PROTOCOL_MT_NORMAL")
        case GGWAVE_PROTOCOL_MT_FAST => Some("GGWAVE_PROTOCOL_MT_FAST")
        case GGWAVE_PROTOCOL_MT_FASTEST => Some("GGWAVE_PROTOCOL_MT_FASTEST")
        case GGWAVE_PROTOCOL_CUSTOM_0 => Some("GGWAVE_PROTOCOL_CUSTOM_0")
        case GGWAVE_PROTOCOL_CUSTOM_1 => Some("GGWAVE_PROTOCOL_CUSTOM_1")
        case GGWAVE_PROTOCOL_CUSTOM_2 => Some("GGWAVE_PROTOCOL_CUSTOM_2")
        case GGWAVE_PROTOCOL_CUSTOM_3 => Some("GGWAVE_PROTOCOL_CUSTOM_3")
        case GGWAVE_PROTOCOL_CUSTOM_4 => Some("GGWAVE_PROTOCOL_CUSTOM_4")
        case GGWAVE_PROTOCOL_CUSTOM_5 => Some("GGWAVE_PROTOCOL_CUSTOM_5")
        case GGWAVE_PROTOCOL_CUSTOM_6 => Some("GGWAVE_PROTOCOL_CUSTOM_6")
        case GGWAVE_PROTOCOL_CUSTOM_7 => Some("GGWAVE_PROTOCOL_CUSTOM_7")
        case GGWAVE_PROTOCOL_CUSTOM_8 => Some("GGWAVE_PROTOCOL_CUSTOM_8")
        case GGWAVE_PROTOCOL_CUSTOM_9 => Some("GGWAVE_PROTOCOL_CUSTOM_9")
        case GGWAVE_PROTOCOL_COUNT => Some("GGWAVE_PROTOCOL_COUNT")
        case _ => None
    extension (a: ggwave_ProtocolId)
      inline def &(b: ggwave_ProtocolId): ggwave_ProtocolId = a & b
      inline def |(b: ggwave_ProtocolId): ggwave_ProtocolId = a | b
      inline def is(b: ggwave_ProtocolId): Boolean = (a & b) == b

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  opaque type ggwave_SampleFormat = CUnsignedInt
  object ggwave_SampleFormat extends CEnumU[ggwave_SampleFormat]:
    given _tag: Tag[ggwave_SampleFormat] = Tag.UInt
    inline def define(inline a: Long): ggwave_SampleFormat = a.toUInt
    val GGWAVE_SAMPLE_FORMAT_UNDEFINED = define(0)
    val GGWAVE_SAMPLE_FORMAT_U8 = define(1)
    val GGWAVE_SAMPLE_FORMAT_I8 = define(2)
    val GGWAVE_SAMPLE_FORMAT_U16 = define(3)
    val GGWAVE_SAMPLE_FORMAT_I16 = define(4)
    val GGWAVE_SAMPLE_FORMAT_F32 = define(5)
    inline def getName(inline value: ggwave_SampleFormat): Option[String] =
      inline value match
        case GGWAVE_SAMPLE_FORMAT_UNDEFINED => Some("GGWAVE_SAMPLE_FORMAT_UNDEFINED")
        case GGWAVE_SAMPLE_FORMAT_U8 => Some("GGWAVE_SAMPLE_FORMAT_U8")
        case GGWAVE_SAMPLE_FORMAT_I8 => Some("GGWAVE_SAMPLE_FORMAT_I8")
        case GGWAVE_SAMPLE_FORMAT_U16 => Some("GGWAVE_SAMPLE_FORMAT_U16")
        case GGWAVE_SAMPLE_FORMAT_I16 => Some("GGWAVE_SAMPLE_FORMAT_I16")
        case GGWAVE_SAMPLE_FORMAT_F32 => Some("GGWAVE_SAMPLE_FORMAT_F32")
        case _ => None
    extension (a: ggwave_SampleFormat)
      inline def &(b: ggwave_SampleFormat): ggwave_SampleFormat = a & b
      inline def |(b: ggwave_SampleFormat): ggwave_SampleFormat = a | b
      inline def is(b: ggwave_SampleFormat): Boolean = (a & b) == b

object aliases:
  import _root_.ggwave.enumerations.*
  import _root_.ggwave.predef.*
  import _root_.ggwave.aliases.*
  import _root_.ggwave.structs.*
  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  opaque type ggwave_Instance = CInt
  object ggwave_Instance: 
    given _tag: Tag[ggwave_Instance] = Tag.Int
    inline def apply(inline o: CInt): ggwave_Instance = o
    extension (v: ggwave_Instance)
      inline def value: CInt = v

object structs:
  import _root_.ggwave.enumerations.*
  import _root_.ggwave.predef.*
  import _root_.ggwave.aliases.*
  import _root_.ggwave.structs.*
  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  opaque type ggwave_Parameters = CStruct9[CInt, Float, Float, Float, CInt, Float, ggwave_SampleFormat, ggwave_SampleFormat, CInt]
  object ggwave_Parameters:
    given _tag: Tag[ggwave_Parameters] = Tag.materializeCStruct9Tag[CInt, Float, Float, Float, CInt, Float, ggwave_SampleFormat, ggwave_SampleFormat, CInt]
    def apply()(using Zone): Ptr[ggwave_Parameters] = scala.scalanative.unsafe.alloc[ggwave_Parameters](1)
    def apply(payloadLength : CInt, sampleRateInp : Float, sampleRateOut : Float, sampleRate : Float, samplesPerFrame : CInt, soundMarkerThreshold : Float, sampleFormatInp : ggwave_SampleFormat, sampleFormatOut : ggwave_SampleFormat, operatingMode : CInt)(using Zone): Ptr[ggwave_Parameters] = 
      val ____ptr = apply()
      (!____ptr).payloadLength = payloadLength
      (!____ptr).sampleRateInp = sampleRateInp
      (!____ptr).sampleRateOut = sampleRateOut
      (!____ptr).sampleRate = sampleRate
      (!____ptr).samplesPerFrame = samplesPerFrame
      (!____ptr).soundMarkerThreshold = soundMarkerThreshold
      (!____ptr).sampleFormatInp = sampleFormatInp
      (!____ptr).sampleFormatOut = sampleFormatOut
      (!____ptr).operatingMode = operatingMode
      ____ptr
    extension (struct: ggwave_Parameters)
      def payloadLength : CInt = struct._1
      def payloadLength_=(value: CInt): Unit = !struct.at1 = value
      def sampleRateInp : Float = struct._2
      def sampleRateInp_=(value: Float): Unit = !struct.at2 = value
      def sampleRateOut : Float = struct._3
      def sampleRateOut_=(value: Float): Unit = !struct.at3 = value
      def sampleRate : Float = struct._4
      def sampleRate_=(value: Float): Unit = !struct.at4 = value
      def samplesPerFrame : CInt = struct._5
      def samplesPerFrame_=(value: CInt): Unit = !struct.at5 = value
      def soundMarkerThreshold : Float = struct._6
      def soundMarkerThreshold_=(value: Float): Unit = !struct.at6 = value
      def sampleFormatInp : ggwave_SampleFormat = struct._7
      def sampleFormatInp_=(value: ggwave_SampleFormat): Unit = !struct.at7 = value
      def sampleFormatOut : ggwave_SampleFormat = struct._8
      def sampleFormatOut_=(value: ggwave_SampleFormat): Unit = !struct.at8 = value
      def operatingMode : CInt = struct._9
      def operatingMode_=(value: CInt): Unit = !struct.at9 = value

@link("ggwave")
@extern
private[ggwave] object extern_functions:
  import _root_.ggwave.enumerations.*
  import _root_.ggwave.predef.*
  import _root_.ggwave.aliases.*
  import _root_.ggwave.structs.*
  private[ggwave] def __sn_wrap_ggwave_ggwave_getDefaultParameters(__return : Ptr[ggwave_Parameters]): Unit = extern

  private[ggwave] def __sn_wrap_ggwave_ggwave_init(parameters : Ptr[ggwave_Parameters]): ggwave_Instance = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_decode(instance : ggwave_Instance, waveformBuffer : Ptr[Byte], waveformSize : CInt, payloadBuffer : Ptr[Byte]): CInt = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_encode(instance : ggwave_Instance, payloadBuffer : Ptr[Byte], payloadSize : CInt, protocolId : ggwave_ProtocolId, volume : CInt, waveformBuffer : Ptr[Byte], query : CInt): CInt = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_free(instance : ggwave_Instance): Unit = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_ndecode(instance : ggwave_Instance, waveformBuffer : Ptr[Byte], waveformSize : CInt, payloadBuffer : Ptr[Byte], payloadSize : CInt): CInt = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_rxDurationFrames(instance : ggwave_Instance): CInt = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_rxProtocolSetFreqStart(protocolId : ggwave_ProtocolId, freqStart : CInt): Unit = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_rxToggleProtocol(protocolId : ggwave_ProtocolId, state : CInt): Unit = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_setLogFile(fptr : Ptr[Byte]): Unit = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_txProtocolSetFreqStart(protocolId : ggwave_ProtocolId, freqStart : CInt): Unit = extern

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_txToggleProtocol(protocolId : ggwave_ProtocolId, state : CInt): Unit = extern


object functions:
  import _root_.ggwave.enumerations.*
  import _root_.ggwave.predef.*
  import _root_.ggwave.aliases.*
  import _root_.ggwave.structs.*
  import extern_functions.*
  export extern_functions.*

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_getDefaultParameters()(using Zone): ggwave_Parameters = 
    val __ptr_0: Ptr[ggwave_Parameters] = alloc[ggwave_Parameters](1)
    __sn_wrap_ggwave_ggwave_getDefaultParameters((__ptr_0 + 0))
    !(__ptr_0 + 0)

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_getDefaultParameters()(__return : Ptr[ggwave_Parameters]): Unit = 
    __sn_wrap_ggwave_ggwave_getDefaultParameters(__return)

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_init(parameters : ggwave_Parameters)(using Zone): ggwave_Instance = 
    val __ptr_0: Ptr[ggwave_Parameters] = alloc[ggwave_Parameters](1)
    !(__ptr_0 + 0) = parameters
    __sn_wrap_ggwave_ggwave_init((__ptr_0 + 0))

  /**
   * [bindgen] header: ./lib/ggwave.h
  */
  def ggwave_init(parameters : Ptr[ggwave_Parameters]): ggwave_Instance = 
    __sn_wrap_ggwave_ggwave_init(parameters)

object constants:
  val GGWAVE_OPERATING_MODE_RX: CUnsignedInt = 2.toUInt
  val GGWAVE_OPERATING_MODE_TX: CUnsignedInt = 4.toUInt
  val GGWAVE_OPERATING_MODE_RX_AND_TX: CUnsignedInt = 6.toUInt
  val GGWAVE_OPERATING_MODE_TX_ONLY_TONES: CUnsignedInt = 8.toUInt
  val GGWAVE_OPERATING_MODE_USE_DSS: CUnsignedInt = 16.toUInt
  
object types:
  export _root_.ggwave.structs.*
  export _root_.ggwave.aliases.*
  export _root_.ggwave.enumerations.*

object all:
  export _root_.ggwave.enumerations.ggwave_Filter
  export _root_.ggwave.enumerations.ggwave_ProtocolId
  export _root_.ggwave.enumerations.ggwave_SampleFormat
  export _root_.ggwave.aliases.ggwave_Instance
  export _root_.ggwave.structs.ggwave_Parameters
  export _root_.ggwave.functions.ggwave_decode
  export _root_.ggwave.functions.ggwave_encode
  export _root_.ggwave.functions.ggwave_free
  export _root_.ggwave.functions.ggwave_ndecode
  export _root_.ggwave.functions.ggwave_rxDurationFrames
  export _root_.ggwave.functions.ggwave_rxProtocolSetFreqStart
  export _root_.ggwave.functions.ggwave_rxToggleProtocol
  export _root_.ggwave.functions.ggwave_setLogFile
  export _root_.ggwave.functions.ggwave_txProtocolSetFreqStart
  export _root_.ggwave.functions.ggwave_txToggleProtocol
  export _root_.ggwave.functions.ggwave_getDefaultParameters
  export _root_.ggwave.functions.ggwave_init

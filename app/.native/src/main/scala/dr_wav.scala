package dr_wav

import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.*

object predef:
  private[dr_wav] trait CEnumU[T](using eq: T =:= UInt):
    given Tag[T] = Tag.UInt.asInstanceOf[Tag[T]]
    extension (inline t: T)
     inline def int: CInt = eq.apply(t).toInt
     inline def uint: CUnsignedInt = eq.apply(t)
     inline def value: CUnsignedInt = eq.apply(t)


object enumerations:
  import predef.*
  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_container = CUnsignedInt
  object drwav_container extends CEnumU[drwav_container]:
    given _tag: Tag[drwav_container] = Tag.UInt
    inline def define(inline a: Long): drwav_container = a.toUInt
    val drwav_container_riff = define(0)
    val drwav_container_w64 = define(1)
    val drwav_container_rf64 = define(2)
    inline def getName(inline value: drwav_container): Option[String] =
      inline value match
        case drwav_container_riff => Some("drwav_container_riff")
        case drwav_container_w64 => Some("drwav_container_w64")
        case drwav_container_rf64 => Some("drwav_container_rf64")
        case _ => None
    extension (a: drwav_container)
      inline def &(b: drwav_container): drwav_container = a & b
      inline def |(b: drwav_container): drwav_container = a | b
      inline def is(b: drwav_container): Boolean = (a & b) == b

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_seek_origin = CUnsignedInt
  object drwav_seek_origin extends CEnumU[drwav_seek_origin]:
    given _tag: Tag[drwav_seek_origin] = Tag.UInt
    inline def define(inline a: Long): drwav_seek_origin = a.toUInt
    val drwav_seek_origin_start = define(0)
    val drwav_seek_origin_current = define(1)
    inline def getName(inline value: drwav_seek_origin): Option[String] =
      inline value match
        case drwav_seek_origin_start => Some("drwav_seek_origin_start")
        case drwav_seek_origin_current => Some("drwav_seek_origin_current")
        case _ => None
    extension (a: drwav_seek_origin)
      inline def &(b: drwav_seek_origin): drwav_seek_origin = a & b
      inline def |(b: drwav_seek_origin): drwav_seek_origin = a | b
      inline def is(b: drwav_seek_origin): Boolean = (a & b) == b

object aliases:
  import _root_.dr_wav.enumerations.*
  import _root_.dr_wav.predef.*
  import _root_.dr_wav.aliases.*
  import _root_.dr_wav.structs.*
  import _root_.dr_wav.unions.*
  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  type drwav_bool32 = drwav_uint32
  object drwav_bool32: 
    given _tag: Tag[drwav_bool32] = drwav_uint32._tag
    inline def apply(inline o: drwav_uint32): drwav_bool32 = o
    extension (v: drwav_bool32)
      inline def value: drwav_uint32 = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  type drwav_bool8 = drwav_uint8
  object drwav_bool8: 
    given _tag: Tag[drwav_bool8] = drwav_uint8._tag
    inline def apply(inline o: drwav_uint8): drwav_bool8 = o
    extension (v: drwav_bool8)
      inline def value: drwav_uint8 = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_chunk_proc = CFuncPtr7[Ptr[Byte], drwav_read_proc, drwav_seek_proc, Ptr[Byte], Ptr[drwav_chunk_header], drwav_container, Ptr[drwav_fmt], drwav_uint64]
  object drwav_chunk_proc: 
    given _tag: Tag[drwav_chunk_proc] = Tag.materializeCFuncPtr7[Ptr[Byte], drwav_read_proc, drwav_seek_proc, Ptr[Byte], Ptr[drwav_chunk_header], drwav_container, Ptr[drwav_fmt], drwav_uint64]
    inline def fromPtr(ptr: Ptr[Byte]): drwav_chunk_proc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr7[Ptr[Byte], drwav_read_proc, drwav_seek_proc, Ptr[Byte], Ptr[drwav_chunk_header], drwav_container, Ptr[drwav_fmt], drwav_uint64]): drwav_chunk_proc = o
    extension (v: drwav_chunk_proc)
      inline def value: CFuncPtr7[Ptr[Byte], drwav_read_proc, drwav_seek_proc, Ptr[Byte], Ptr[drwav_chunk_header], drwav_container, Ptr[drwav_fmt], drwav_uint64] = v
      inline def toPtr: Ptr[Byte] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_int16 = CShort
  object drwav_int16: 
    given _tag: Tag[drwav_int16] = Tag.Short
    inline def apply(inline o: CShort): drwav_int16 = o
    extension (v: drwav_int16)
      inline def value: CShort = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_int32 = CInt
  object drwav_int32: 
    given _tag: Tag[drwav_int32] = Tag.Int
    inline def apply(inline o: CInt): drwav_int32 = o
    extension (v: drwav_int32)
      inline def value: CInt = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_int64 = CLongLong
  object drwav_int64: 
    given _tag: Tag[drwav_int64] = Tag.Long
    inline def apply(inline o: CLongLong): drwav_int64 = o
    extension (v: drwav_int64)
      inline def value: CLongLong = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_int8 = CChar
  object drwav_int8: 
    given _tag: Tag[drwav_int8] = Tag.Byte
    inline def apply(inline o: CChar): drwav_int8 = o
    extension (v: drwav_int8)
      inline def value: CChar = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_read_proc = CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]
  object drwav_read_proc: 
    given _tag: Tag[drwav_read_proc] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]
    inline def fromPtr(ptr: Ptr[Byte]): drwav_read_proc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]): drwav_read_proc = o
    extension (v: drwav_read_proc)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t] = v
      inline def toPtr: Ptr[Byte] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  type drwav_result = drwav_int32
  object drwav_result: 
    given _tag: Tag[drwav_result] = drwav_int32._tag
    inline def apply(inline o: drwav_int32): drwav_result = o
    extension (v: drwav_result)
      inline def value: drwav_int32 = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_seek_proc = CFuncPtr3[Ptr[Byte], CInt, drwav_seek_origin, drwav_bool32]
  object drwav_seek_proc: 
    given _tag: Tag[drwav_seek_proc] = Tag.materializeCFuncPtr3[Ptr[Byte], CInt, drwav_seek_origin, drwav_bool32]
    inline def fromPtr(ptr: Ptr[Byte]): drwav_seek_proc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], CInt, drwav_seek_origin, drwav_bool32]): drwav_seek_proc = o
    extension (v: drwav_seek_proc)
      inline def value: CFuncPtr3[Ptr[Byte], CInt, drwav_seek_origin, drwav_bool32] = v
      inline def toPtr: Ptr[Byte] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_uint16 = CUnsignedShort
  object drwav_uint16: 
    given _tag: Tag[drwav_uint16] = Tag.UShort
    inline def apply(inline o: CUnsignedShort): drwav_uint16 = o
    extension (v: drwav_uint16)
      inline def value: CUnsignedShort = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_uint32 = CUnsignedInt
  object drwav_uint32: 
    given _tag: Tag[drwav_uint32] = Tag.UInt
    inline def apply(inline o: CUnsignedInt): drwav_uint32 = o
    extension (v: drwav_uint32)
      inline def value: CUnsignedInt = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_uint64 = CUnsignedLongLong
  object drwav_uint64: 
    given _tag: Tag[drwav_uint64] = Tag.ULong
    inline def apply(inline o: CUnsignedLongLong): drwav_uint64 = o
    extension (v: drwav_uint64)
      inline def value: CUnsignedLongLong = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_uint8 = CUnsignedChar
  object drwav_uint8: 
    given _tag: Tag[drwav_uint8] = Tag.UByte
    inline def apply(inline o: CUnsignedChar): drwav_uint8 = o
    extension (v: drwav_uint8)
      inline def value: CUnsignedChar = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  type drwav_uintptr = drwav_uint64
  object drwav_uintptr: 
    given _tag: Tag[drwav_uintptr] = drwav_uint64._tag
    inline def apply(inline o: drwav_uint64): drwav_uintptr = o
    extension (v: drwav_uintptr)
      inline def value: drwav_uint64 = v

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_write_proc = CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]
  object drwav_write_proc: 
    given _tag: Tag[drwav_write_proc] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]
    inline def fromPtr(ptr: Ptr[Byte]): drwav_write_proc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t]): drwav_write_proc = o
    extension (v: drwav_write_proc)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[Byte], size_t, size_t] = v
      inline def toPtr: Ptr[Byte] = CFuncPtr.toPtr(v)

  type size_t = libc.stddef.size_t
  object size_t: 
    val _tag: Tag[size_t] = summon[Tag[libc.stddef.size_t]]
    inline def apply(inline o: libc.stddef.size_t): size_t = o
    extension (v: size_t)
      inline def value: libc.stddef.size_t = v

  type wchar_t = libc.stddef.wchar_t
  object wchar_t: 
    val _tag: Tag[wchar_t] = summon[Tag[libc.stddef.wchar_t]]
    inline def apply(inline o: libc.stddef.wchar_t): wchar_t = o
    extension (v: wchar_t)
      inline def value: libc.stddef.wchar_t = v

object structs:
  import _root_.dr_wav.enumerations.*
  import _root_.dr_wav.predef.*
  import _root_.dr_wav.aliases.*
  import _root_.dr_wav.structs.*
  import _root_.dr_wav.unions.*
  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav = CArray[CChar, Nat.Digit3[Nat._4, Nat._4, Nat._0]]
  object drwav:
    given _tag: Tag[drwav] = Tag.CArray[CChar, Nat.Digit3[Nat._4, Nat._4, Nat._0]](Tag.Byte, Tag.Digit3[Nat._4, Nat._4, Nat._0](Tag.Nat4, Tag.Nat4, Tag.Nat0))
    def apply()(using Zone): Ptr[drwav] = scala.scalanative.unsafe.alloc[drwav](1)
    def apply(onRead : drwav_read_proc, onWrite : drwav_write_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], allocationCallbacks : drwav_allocation_callbacks, container : drwav_container, fmt : drwav_fmt, sampleRate : drwav_uint32, channels : drwav_uint16, bitsPerSample : drwav_uint16, translatedFormatTag : drwav_uint16, totalPCMFrameCount : drwav_uint64, dataChunkDataSize : drwav_uint64, dataChunkDataPos : drwav_uint64, bytesRemaining : drwav_uint64, dataChunkDataSizeTargetWrite : drwav_uint64, isSequentialWrite : drwav_bool32, smpl : drwav_smpl, memoryStream : drwav__memory_stream, memoryStreamWrite : drwav__memory_stream_write, compressed : drwav_compressed, msadpcm : drwav_msadpcm, ima : drwav_ima)(using Zone): Ptr[drwav] = 
      val ____ptr = apply()
      (!____ptr).onRead = onRead
      (!____ptr).onWrite = onWrite
      (!____ptr).onSeek = onSeek
      (!____ptr).pUserData = pUserData
      (!____ptr).allocationCallbacks = allocationCallbacks
      (!____ptr).container = container
      (!____ptr).fmt = fmt
      (!____ptr).sampleRate = sampleRate
      (!____ptr).channels = channels
      (!____ptr).bitsPerSample = bitsPerSample
      (!____ptr).translatedFormatTag = translatedFormatTag
      (!____ptr).totalPCMFrameCount = totalPCMFrameCount
      (!____ptr).dataChunkDataSize = dataChunkDataSize
      (!____ptr).dataChunkDataPos = dataChunkDataPos
      (!____ptr).bytesRemaining = bytesRemaining
      (!____ptr).dataChunkDataSizeTargetWrite = dataChunkDataSizeTargetWrite
      (!____ptr).isSequentialWrite = isSequentialWrite
      (!____ptr).smpl = smpl
      (!____ptr).memoryStream = memoryStream
      (!____ptr).memoryStreamWrite = memoryStreamWrite
      (!____ptr).compressed = compressed
      (!____ptr).msadpcm = msadpcm
      (!____ptr).ima = ima
      ____ptr
    extension (struct: drwav)
      def onRead: drwav_read_proc = !struct.at(0).asInstanceOf[Ptr[drwav_read_proc]]
      def onRead_=(value: drwav_read_proc): Unit = !struct.at(0).asInstanceOf[Ptr[drwav_read_proc]] = value
      def onWrite: drwav_write_proc = !struct.at(8).asInstanceOf[Ptr[drwav_write_proc]]
      def onWrite_=(value: drwav_write_proc): Unit = !struct.at(8).asInstanceOf[Ptr[drwav_write_proc]] = value
      def onSeek: drwav_seek_proc = !struct.at(16).asInstanceOf[Ptr[drwav_seek_proc]]
      def onSeek_=(value: drwav_seek_proc): Unit = !struct.at(16).asInstanceOf[Ptr[drwav_seek_proc]] = value
      def pUserData: Ptr[Byte] = !struct.at(24).asInstanceOf[Ptr[Ptr[Byte]]]
      def pUserData_=(value: Ptr[Byte]): Unit = !struct.at(24).asInstanceOf[Ptr[Ptr[Byte]]] = value
      def allocationCallbacks: drwav_allocation_callbacks = !struct.at(32).asInstanceOf[Ptr[drwav_allocation_callbacks]]
      def allocationCallbacks_=(value: drwav_allocation_callbacks): Unit = !struct.at(32).asInstanceOf[Ptr[drwav_allocation_callbacks]] = value
      def container: drwav_container = !struct.at(64).asInstanceOf[Ptr[drwav_container]]
      def container_=(value: drwav_container): Unit = !struct.at(64).asInstanceOf[Ptr[drwav_container]] = value
      def fmt: drwav_fmt = !struct.at(68).asInstanceOf[Ptr[drwav_fmt]]
      def fmt_=(value: drwav_fmt): Unit = !struct.at(68).asInstanceOf[Ptr[drwav_fmt]] = value
      def sampleRate: drwav_uint32 = !struct.at(108).asInstanceOf[Ptr[drwav_uint32]]
      def sampleRate_=(value: drwav_uint32): Unit = !struct.at(108).asInstanceOf[Ptr[drwav_uint32]] = value
      def channels: drwav_uint16 = !struct.at(112).asInstanceOf[Ptr[drwav_uint16]]
      def channels_=(value: drwav_uint16): Unit = !struct.at(112).asInstanceOf[Ptr[drwav_uint16]] = value
      def bitsPerSample: drwav_uint16 = !struct.at(114).asInstanceOf[Ptr[drwav_uint16]]
      def bitsPerSample_=(value: drwav_uint16): Unit = !struct.at(114).asInstanceOf[Ptr[drwav_uint16]] = value
      def translatedFormatTag: drwav_uint16 = !struct.at(116).asInstanceOf[Ptr[drwav_uint16]]
      def translatedFormatTag_=(value: drwav_uint16): Unit = !struct.at(116).asInstanceOf[Ptr[drwav_uint16]] = value
      def totalPCMFrameCount: drwav_uint64 = !struct.at(120).asInstanceOf[Ptr[drwav_uint64]]
      def totalPCMFrameCount_=(value: drwav_uint64): Unit = !struct.at(120).asInstanceOf[Ptr[drwav_uint64]] = value
      def dataChunkDataSize: drwav_uint64 = !struct.at(128).asInstanceOf[Ptr[drwav_uint64]]
      def dataChunkDataSize_=(value: drwav_uint64): Unit = !struct.at(128).asInstanceOf[Ptr[drwav_uint64]] = value
      def dataChunkDataPos: drwav_uint64 = !struct.at(136).asInstanceOf[Ptr[drwav_uint64]]
      def dataChunkDataPos_=(value: drwav_uint64): Unit = !struct.at(136).asInstanceOf[Ptr[drwav_uint64]] = value
      def bytesRemaining: drwav_uint64 = !struct.at(144).asInstanceOf[Ptr[drwav_uint64]]
      def bytesRemaining_=(value: drwav_uint64): Unit = !struct.at(144).asInstanceOf[Ptr[drwav_uint64]] = value
      def dataChunkDataSizeTargetWrite: drwav_uint64 = !struct.at(152).asInstanceOf[Ptr[drwav_uint64]]
      def dataChunkDataSizeTargetWrite_=(value: drwav_uint64): Unit = !struct.at(152).asInstanceOf[Ptr[drwav_uint64]] = value
      def isSequentialWrite: drwav_bool32 = !struct.at(160).asInstanceOf[Ptr[drwav_bool32]]
      def isSequentialWrite_=(value: drwav_bool32): Unit = !struct.at(160).asInstanceOf[Ptr[drwav_bool32]] = value
      def smpl: drwav_smpl = !struct.at(164).asInstanceOf[Ptr[drwav_smpl]]
      def smpl_=(value: drwav_smpl): Unit = !struct.at(164).asInstanceOf[Ptr[drwav_smpl]] = value
      def memoryStream: drwav__memory_stream = !struct.at(224).asInstanceOf[Ptr[drwav__memory_stream]]
      def memoryStream_=(value: drwav__memory_stream): Unit = !struct.at(224).asInstanceOf[Ptr[drwav__memory_stream]] = value
      def memoryStreamWrite: drwav__memory_stream_write = !struct.at(248).asInstanceOf[Ptr[drwav__memory_stream_write]]
      def memoryStreamWrite_=(value: drwav__memory_stream_write): Unit = !struct.at(248).asInstanceOf[Ptr[drwav__memory_stream_write]] = value
      def compressed: drwav_compressed = !struct.at(288).asInstanceOf[Ptr[drwav_compressed]]
      def compressed_=(value: drwav_compressed): Unit = !struct.at(288).asInstanceOf[Ptr[drwav_compressed]] = value
      def msadpcm: drwav_msadpcm = !struct.at(296).asInstanceOf[Ptr[drwav_msadpcm]]
      def msadpcm_=(value: drwav_msadpcm): Unit = !struct.at(296).asInstanceOf[Ptr[drwav_msadpcm]] = value
      def ima: drwav_ima = !struct.at(348).asInstanceOf[Ptr[drwav_ima]]
      def ima_=(value: drwav_ima): Unit = !struct.at(348).asInstanceOf[Ptr[drwav_ima]] = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav__memory_stream = CStruct3[Ptr[drwav_uint8], size_t, size_t]
  object drwav__memory_stream:
    given _tag: Tag[drwav__memory_stream] = Tag.materializeCStruct3Tag[Ptr[drwav_uint8], size_t, size_t]
    def apply()(using Zone): Ptr[drwav__memory_stream] = scala.scalanative.unsafe.alloc[drwav__memory_stream](1)
    def apply(data : Ptr[drwav_uint8], dataSize : size_t, currentReadPos : size_t)(using Zone): Ptr[drwav__memory_stream] = 
      val ____ptr = apply()
      (!____ptr).data = data
      (!____ptr).dataSize = dataSize
      (!____ptr).currentReadPos = currentReadPos
      ____ptr
    extension (struct: drwav__memory_stream)
      def data : Ptr[drwav_uint8] = struct._1
      def data_=(value: Ptr[drwav_uint8]): Unit = !struct.at1 = value
      def dataSize : size_t = struct._2
      def dataSize_=(value: size_t): Unit = !struct.at2 = value
      def currentReadPos : size_t = struct._3
      def currentReadPos_=(value: size_t): Unit = !struct.at3 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav__memory_stream_write = CStruct5[Ptr[Ptr[Byte]], Ptr[size_t], size_t, size_t, size_t]
  object drwav__memory_stream_write:
    given _tag: Tag[drwav__memory_stream_write] = Tag.materializeCStruct5Tag[Ptr[Ptr[Byte]], Ptr[size_t], size_t, size_t, size_t]
    def apply()(using Zone): Ptr[drwav__memory_stream_write] = scala.scalanative.unsafe.alloc[drwav__memory_stream_write](1)
    def apply(ppData : Ptr[Ptr[Byte]], pDataSize : Ptr[size_t], dataSize : size_t, dataCapacity : size_t, currentWritePos : size_t)(using Zone): Ptr[drwav__memory_stream_write] = 
      val ____ptr = apply()
      (!____ptr).ppData = ppData
      (!____ptr).pDataSize = pDataSize
      (!____ptr).dataSize = dataSize
      (!____ptr).dataCapacity = dataCapacity
      (!____ptr).currentWritePos = currentWritePos
      ____ptr
    extension (struct: drwav__memory_stream_write)
      def ppData : Ptr[Ptr[Byte]] = struct._1
      def ppData_=(value: Ptr[Ptr[Byte]]): Unit = !struct.at1 = value
      def pDataSize : Ptr[size_t] = struct._2
      def pDataSize_=(value: Ptr[size_t]): Unit = !struct.at2 = value
      def dataSize : size_t = struct._3
      def dataSize_=(value: size_t): Unit = !struct.at3 = value
      def dataCapacity : size_t = struct._4
      def dataCapacity_=(value: size_t): Unit = !struct.at4 = value
      def currentWritePos : size_t = struct._5
      def currentWritePos_=(value: size_t): Unit = !struct.at5 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_allocation_callbacks = CStruct4[Ptr[Byte], CFuncPtr2[size_t, Ptr[Byte], Ptr[Byte]], CFuncPtr3[Ptr[Byte], size_t, Ptr[Byte], Ptr[Byte]], CFuncPtr2[Ptr[Byte], Ptr[Byte], Unit]]
  object drwav_allocation_callbacks:
    given _tag: Tag[drwav_allocation_callbacks] = Tag.materializeCStruct4Tag[Ptr[Byte], CFuncPtr2[size_t, Ptr[Byte], Ptr[Byte]], CFuncPtr3[Ptr[Byte], size_t, Ptr[Byte], Ptr[Byte]], CFuncPtr2[Ptr[Byte], Ptr[Byte], Unit]]
    def apply()(using Zone): Ptr[drwav_allocation_callbacks] = scala.scalanative.unsafe.alloc[drwav_allocation_callbacks](1)
    def apply(pUserData : Ptr[Byte], onMalloc : CFuncPtr2[size_t, Ptr[Byte], Ptr[Byte]], onRealloc : CFuncPtr3[Ptr[Byte], size_t, Ptr[Byte], Ptr[Byte]], onFree : CFuncPtr2[Ptr[Byte], Ptr[Byte], Unit])(using Zone): Ptr[drwav_allocation_callbacks] = 
      val ____ptr = apply()
      (!____ptr).pUserData = pUserData
      (!____ptr).onMalloc = onMalloc
      (!____ptr).onRealloc = onRealloc
      (!____ptr).onFree = onFree
      ____ptr
    extension (struct: drwav_allocation_callbacks)
      def pUserData : Ptr[Byte] = struct._1
      def pUserData_=(value: Ptr[Byte]): Unit = !struct.at1 = value
      def onMalloc : CFuncPtr2[size_t, Ptr[Byte], Ptr[Byte]] = struct._2
      def onMalloc_=(value: CFuncPtr2[size_t, Ptr[Byte], Ptr[Byte]]): Unit = !struct.at2 = value
      def onRealloc : CFuncPtr3[Ptr[Byte], size_t, Ptr[Byte], Ptr[Byte]] = struct._3
      def onRealloc_=(value: CFuncPtr3[Ptr[Byte], size_t, Ptr[Byte], Ptr[Byte]]): Unit = !struct.at3 = value
      def onFree : CFuncPtr2[Ptr[Byte], Ptr[Byte], Unit] = struct._4
      def onFree_=(value: CFuncPtr2[Ptr[Byte], Ptr[Byte], Unit]): Unit = !struct.at4 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_chunk_header = CStruct3[drwav_guid, drwav_uint64, CUnsignedInt]
  object drwav_chunk_header:
    given _tag: Tag[drwav_chunk_header] = Tag.materializeCStruct3Tag[drwav_guid, drwav_uint64, CUnsignedInt]
    def apply()(using Zone): Ptr[drwav_chunk_header] = scala.scalanative.unsafe.alloc[drwav_chunk_header](1)
    def apply(id : drwav_guid, sizeInBytes : drwav_uint64, paddingSize : CUnsignedInt)(using Zone): Ptr[drwav_chunk_header] = 
      val ____ptr = apply()
      (!____ptr).id = id
      (!____ptr).sizeInBytes = sizeInBytes
      (!____ptr).paddingSize = paddingSize
      ____ptr
    extension (struct: drwav_chunk_header)
      def id : drwav_guid = struct._1
      def id_=(value: drwav_guid): Unit = !struct.at1 = value
      def sizeInBytes : drwav_uint64 = struct._2
      def sizeInBytes_=(value: drwav_uint64): Unit = !struct.at2 = value
      def paddingSize : CUnsignedInt = struct._3
      def paddingSize_=(value: CUnsignedInt): Unit = !struct.at3 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_compressed = CStruct1[drwav_uint64]
  object drwav_compressed:
    given _tag: Tag[drwav_compressed] = Tag.materializeCStruct1Tag[drwav_uint64]
    def apply()(using Zone): Ptr[drwav_compressed] = scala.scalanative.unsafe.alloc[drwav_compressed](1)
    def apply(iCurrentPCMFrame : drwav_uint64)(using Zone): Ptr[drwav_compressed] = 
      val ____ptr = apply()
      (!____ptr).iCurrentPCMFrame = iCurrentPCMFrame
      ____ptr
    extension (struct: drwav_compressed)
      def iCurrentPCMFrame : drwav_uint64 = struct._1
      def iCurrentPCMFrame_=(value: drwav_uint64): Unit = !struct.at1 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_data_format = CStruct5[drwav_container, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32]
  object drwav_data_format:
    given _tag: Tag[drwav_data_format] = Tag.materializeCStruct5Tag[drwav_container, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32]
    def apply()(using Zone): Ptr[drwav_data_format] = scala.scalanative.unsafe.alloc[drwav_data_format](1)
    def apply(container : drwav_container, format : drwav_uint32, channels : drwav_uint32, sampleRate : drwav_uint32, bitsPerSample : drwav_uint32)(using Zone): Ptr[drwav_data_format] = 
      val ____ptr = apply()
      (!____ptr).container = container
      (!____ptr).format = format
      (!____ptr).channels = channels
      (!____ptr).sampleRate = sampleRate
      (!____ptr).bitsPerSample = bitsPerSample
      ____ptr
    extension (struct: drwav_data_format)
      def container : drwav_container = struct._1
      def container_=(value: drwav_container): Unit = !struct.at1 = value
      def format : drwav_uint32 = struct._2
      def format_=(value: drwav_uint32): Unit = !struct.at2 = value
      def channels : drwav_uint32 = struct._3
      def channels_=(value: drwav_uint32): Unit = !struct.at3 = value
      def sampleRate : drwav_uint32 = struct._4
      def sampleRate_=(value: drwav_uint32): Unit = !struct.at4 = value
      def bitsPerSample : drwav_uint32 = struct._5
      def bitsPerSample_=(value: drwav_uint32): Unit = !struct.at5 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_fmt = CStruct10[drwav_uint16, drwav_uint16, drwav_uint32, drwav_uint32, drwav_uint16, drwav_uint16, drwav_uint16, drwav_uint16, drwav_uint32, CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]]
  object drwav_fmt:
    given _tag: Tag[drwav_fmt] = Tag.materializeCStruct10Tag[drwav_uint16, drwav_uint16, drwav_uint32, drwav_uint32, drwav_uint16, drwav_uint16, drwav_uint16, drwav_uint16, drwav_uint32, CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]]
    def apply()(using Zone): Ptr[drwav_fmt] = scala.scalanative.unsafe.alloc[drwav_fmt](1)
    def apply(formatTag : drwav_uint16, channels : drwav_uint16, sampleRate : drwav_uint32, avgBytesPerSec : drwav_uint32, blockAlign : drwav_uint16, bitsPerSample : drwav_uint16, extendedSize : drwav_uint16, validBitsPerSample : drwav_uint16, channelMask : drwav_uint32, subFormat : CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]])(using Zone): Ptr[drwav_fmt] = 
      val ____ptr = apply()
      (!____ptr).formatTag = formatTag
      (!____ptr).channels = channels
      (!____ptr).sampleRate = sampleRate
      (!____ptr).avgBytesPerSec = avgBytesPerSec
      (!____ptr).blockAlign = blockAlign
      (!____ptr).bitsPerSample = bitsPerSample
      (!____ptr).extendedSize = extendedSize
      (!____ptr).validBitsPerSample = validBitsPerSample
      (!____ptr).channelMask = channelMask
      (!____ptr).subFormat = subFormat
      ____ptr
    extension (struct: drwav_fmt)
      def formatTag : drwav_uint16 = struct._1
      def formatTag_=(value: drwav_uint16): Unit = !struct.at1 = value
      def channels : drwav_uint16 = struct._2
      def channels_=(value: drwav_uint16): Unit = !struct.at2 = value
      def sampleRate : drwav_uint32 = struct._3
      def sampleRate_=(value: drwav_uint32): Unit = !struct.at3 = value
      def avgBytesPerSec : drwav_uint32 = struct._4
      def avgBytesPerSec_=(value: drwav_uint32): Unit = !struct.at4 = value
      def blockAlign : drwav_uint16 = struct._5
      def blockAlign_=(value: drwav_uint16): Unit = !struct.at5 = value
      def bitsPerSample : drwav_uint16 = struct._6
      def bitsPerSample_=(value: drwav_uint16): Unit = !struct.at6 = value
      def extendedSize : drwav_uint16 = struct._7
      def extendedSize_=(value: drwav_uint16): Unit = !struct.at7 = value
      def validBitsPerSample : drwav_uint16 = struct._8
      def validBitsPerSample_=(value: drwav_uint16): Unit = !struct.at8 = value
      def channelMask : drwav_uint32 = struct._9
      def channelMask_=(value: drwav_uint32): Unit = !struct.at9 = value
      def subFormat : CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]] = struct._10
      def subFormat_=(value: CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]): Unit = !struct.at10 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_ima = CStruct5[drwav_uint32, CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat.Digit2[Nat._1, Nat._6]], drwav_uint32]
  object drwav_ima:
    given _tag: Tag[drwav_ima] = Tag.materializeCStruct5Tag[drwav_uint32, CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat.Digit2[Nat._1, Nat._6]], drwav_uint32]
    def apply()(using Zone): Ptr[drwav_ima] = scala.scalanative.unsafe.alloc[drwav_ima](1)
    def apply(bytesRemainingInBlock : drwav_uint32, predictor : CArray[drwav_int32, Nat._2], stepIndex : CArray[drwav_int32, Nat._2], cachedFrames : CArray[drwav_int32, Nat.Digit2[Nat._1, Nat._6]], cachedFrameCount : drwav_uint32)(using Zone): Ptr[drwav_ima] = 
      val ____ptr = apply()
      (!____ptr).bytesRemainingInBlock = bytesRemainingInBlock
      (!____ptr).predictor = predictor
      (!____ptr).stepIndex = stepIndex
      (!____ptr).cachedFrames = cachedFrames
      (!____ptr).cachedFrameCount = cachedFrameCount
      ____ptr
    extension (struct: drwav_ima)
      def bytesRemainingInBlock : drwav_uint32 = struct._1
      def bytesRemainingInBlock_=(value: drwav_uint32): Unit = !struct.at1 = value
      def predictor : CArray[drwav_int32, Nat._2] = struct._2
      def predictor_=(value: CArray[drwav_int32, Nat._2]): Unit = !struct.at2 = value
      def stepIndex : CArray[drwav_int32, Nat._2] = struct._3
      def stepIndex_=(value: CArray[drwav_int32, Nat._2]): Unit = !struct.at3 = value
      def cachedFrames : CArray[drwav_int32, Nat.Digit2[Nat._1, Nat._6]] = struct._4
      def cachedFrames_=(value: CArray[drwav_int32, Nat.Digit2[Nat._1, Nat._6]]): Unit = !struct.at4 = value
      def cachedFrameCount : drwav_uint32 = struct._5
      def cachedFrameCount_=(value: drwav_uint32): Unit = !struct.at5 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_msadpcm = CStruct6[drwav_uint32, CArray[drwav_uint16, Nat._2], CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat._4], drwav_uint32, CArray[CArray[drwav_int32, Nat._2], Nat._2]]
  object drwav_msadpcm:
    given _tag: Tag[drwav_msadpcm] = Tag.materializeCStruct6Tag[drwav_uint32, CArray[drwav_uint16, Nat._2], CArray[drwav_int32, Nat._2], CArray[drwav_int32, Nat._4], drwav_uint32, CArray[CArray[drwav_int32, Nat._2], Nat._2]]
    def apply()(using Zone): Ptr[drwav_msadpcm] = scala.scalanative.unsafe.alloc[drwav_msadpcm](1)
    def apply(bytesRemainingInBlock : drwav_uint32, predictor : CArray[drwav_uint16, Nat._2], delta : CArray[drwav_int32, Nat._2], cachedFrames : CArray[drwav_int32, Nat._4], cachedFrameCount : drwav_uint32, prevFrames : CArray[CArray[drwav_int32, Nat._2], Nat._2])(using Zone): Ptr[drwav_msadpcm] = 
      val ____ptr = apply()
      (!____ptr).bytesRemainingInBlock = bytesRemainingInBlock
      (!____ptr).predictor = predictor
      (!____ptr).delta = delta
      (!____ptr).cachedFrames = cachedFrames
      (!____ptr).cachedFrameCount = cachedFrameCount
      (!____ptr).prevFrames = prevFrames
      ____ptr
    extension (struct: drwav_msadpcm)
      def bytesRemainingInBlock : drwav_uint32 = struct._1
      def bytesRemainingInBlock_=(value: drwav_uint32): Unit = !struct.at1 = value
      def predictor : CArray[drwav_uint16, Nat._2] = struct._2
      def predictor_=(value: CArray[drwav_uint16, Nat._2]): Unit = !struct.at2 = value
      def delta : CArray[drwav_int32, Nat._2] = struct._3
      def delta_=(value: CArray[drwav_int32, Nat._2]): Unit = !struct.at3 = value
      def cachedFrames : CArray[drwav_int32, Nat._4] = struct._4
      def cachedFrames_=(value: CArray[drwav_int32, Nat._4]): Unit = !struct.at4 = value
      def cachedFrameCount : drwav_uint32 = struct._5
      def cachedFrameCount_=(value: drwav_uint32): Unit = !struct.at5 = value
      def prevFrames : CArray[CArray[drwav_int32, Nat._2], Nat._2] = struct._6
      def prevFrames_=(value: CArray[CArray[drwav_int32, Nat._2], Nat._2]): Unit = !struct.at6 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_smpl = CStruct10[drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, CArray[drwav_smpl_loop, Nat._1]]
  object drwav_smpl:
    given _tag: Tag[drwav_smpl] = Tag.materializeCStruct10Tag[drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, CArray[drwav_smpl_loop, Nat._1]]
    def apply()(using Zone): Ptr[drwav_smpl] = scala.scalanative.unsafe.alloc[drwav_smpl](1)
    def apply(manufacturer : drwav_uint32, product : drwav_uint32, samplePeriod : drwav_uint32, midiUnityNotes : drwav_uint32, midiPitchFraction : drwav_uint32, smpteFormat : drwav_uint32, smpteOffset : drwav_uint32, numSampleLoops : drwav_uint32, samplerData : drwav_uint32, loops : CArray[drwav_smpl_loop, Nat._1])(using Zone): Ptr[drwav_smpl] = 
      val ____ptr = apply()
      (!____ptr).manufacturer = manufacturer
      (!____ptr).product = product
      (!____ptr).samplePeriod = samplePeriod
      (!____ptr).midiUnityNotes = midiUnityNotes
      (!____ptr).midiPitchFraction = midiPitchFraction
      (!____ptr).smpteFormat = smpteFormat
      (!____ptr).smpteOffset = smpteOffset
      (!____ptr).numSampleLoops = numSampleLoops
      (!____ptr).samplerData = samplerData
      (!____ptr).loops = loops
      ____ptr
    extension (struct: drwav_smpl)
      def manufacturer : drwav_uint32 = struct._1
      def manufacturer_=(value: drwav_uint32): Unit = !struct.at1 = value
      def product : drwav_uint32 = struct._2
      def product_=(value: drwav_uint32): Unit = !struct.at2 = value
      def samplePeriod : drwav_uint32 = struct._3
      def samplePeriod_=(value: drwav_uint32): Unit = !struct.at3 = value
      def midiUnityNotes : drwav_uint32 = struct._4
      def midiUnityNotes_=(value: drwav_uint32): Unit = !struct.at4 = value
      def midiPitchFraction : drwav_uint32 = struct._5
      def midiPitchFraction_=(value: drwav_uint32): Unit = !struct.at5 = value
      def smpteFormat : drwav_uint32 = struct._6
      def smpteFormat_=(value: drwav_uint32): Unit = !struct.at6 = value
      def smpteOffset : drwav_uint32 = struct._7
      def smpteOffset_=(value: drwav_uint32): Unit = !struct.at7 = value
      def numSampleLoops : drwav_uint32 = struct._8
      def numSampleLoops_=(value: drwav_uint32): Unit = !struct.at8 = value
      def samplerData : drwav_uint32 = struct._9
      def samplerData_=(value: drwav_uint32): Unit = !struct.at9 = value
      def loops : CArray[drwav_smpl_loop, Nat._1] = struct._10
      def loops_=(value: CArray[drwav_smpl_loop, Nat._1]): Unit = !struct.at10 = value

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_smpl_loop = CStruct6[drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32]
  object drwav_smpl_loop:
    given _tag: Tag[drwav_smpl_loop] = Tag.materializeCStruct6Tag[drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32, drwav_uint32]
    def apply()(using Zone): Ptr[drwav_smpl_loop] = scala.scalanative.unsafe.alloc[drwav_smpl_loop](1)
    def apply(cuePointId : drwav_uint32, `type` : drwav_uint32, start : drwav_uint32, end : drwav_uint32, fraction : drwav_uint32, playCount : drwav_uint32)(using Zone): Ptr[drwav_smpl_loop] = 
      val ____ptr = apply()
      (!____ptr).cuePointId = cuePointId
      (!____ptr).`type` = `type`
      (!____ptr).start = start
      (!____ptr).end = end
      (!____ptr).fraction = fraction
      (!____ptr).playCount = playCount
      ____ptr
    extension (struct: drwav_smpl_loop)
      def cuePointId : drwav_uint32 = struct._1
      def cuePointId_=(value: drwav_uint32): Unit = !struct.at1 = value
      def `type` : drwav_uint32 = struct._2
      def type_=(value: drwav_uint32): Unit = !struct.at2 = value
      def start : drwav_uint32 = struct._3
      def start_=(value: drwav_uint32): Unit = !struct.at3 = value
      def end : drwav_uint32 = struct._4
      def end_=(value: drwav_uint32): Unit = !struct.at4 = value
      def fraction : drwav_uint32 = struct._5
      def fraction_=(value: drwav_uint32): Unit = !struct.at5 = value
      def playCount : drwav_uint32 = struct._6
      def playCount_=(value: drwav_uint32): Unit = !struct.at6 = value

object unions:
  import _root_.dr_wav.enumerations.*
  import _root_.dr_wav.predef.*
  import _root_.dr_wav.aliases.*
  import _root_.dr_wav.structs.*
  import _root_.dr_wav.unions.*
  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  opaque type drwav_guid = CArray[Byte, Nat.Digit2[Nat._1, Nat._6]]
  object drwav_guid:
    given _tag: Tag[drwav_guid] = Tag.CArray[CChar, Nat.Digit2[Nat._1, Nat._6]](Tag.Byte, Tag.Digit2[Nat._1, Nat._6](Tag.Nat1, Tag.Nat6))
    def apply()(using Zone): Ptr[drwav_guid] = 
      val ___ptr = alloc[drwav_guid](1)
      ___ptr
    @scala.annotation.targetName("apply_fourcc")
    def apply(fourcc: CArray[drwav_uint8, Nat._4])(using Zone): Ptr[drwav_guid] =
      val ___ptr = alloc[drwav_guid](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat._4]]].update(0, fourcc)
      ___ptr
    @scala.annotation.targetName("apply_guid")
    def apply(guid: CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]])(using Zone): Ptr[drwav_guid] =
      val ___ptr = alloc[drwav_guid](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]]].update(0, guid)
      ___ptr
    extension (struct: drwav_guid)
      def fourcc : CArray[drwav_uint8, Nat._4] = !struct.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat._4]]]
      def fourcc_=(value: CArray[drwav_uint8, Nat._4]): Unit = !struct.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat._4]]] = value
      def guid : CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]] = !struct.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]]]
      def guid_=(value: CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]): Unit = !struct.at(0).asInstanceOf[Ptr[CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]]] = value


@extern
private[dr_wav] object extern_functions:
  import _root_.dr_wav.enumerations.*
  import _root_.dr_wav.predef.*
  import _root_.dr_wav.aliases.*
  import _root_.dr_wav.structs.*
  import _root_.dr_wav.unions.*
  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_alaw_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_alaw_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_alaw_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_s16(data : Ptr[drwav_uint8]): drwav_int16 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_s32(data : Ptr[drwav_uint8]): drwav_int32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_s64(data : Ptr[drwav_uint8]): drwav_int64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_u16(data : Ptr[drwav_uint8]): drwav_uint16 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_u32(data : Ptr[drwav_uint8]): drwav_uint32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_bytes_to_u64(data : Ptr[drwav_uint8]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_f32_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[Float], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_f32_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[Float], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_f64_to_f32(pOut : Ptr[Float], pIn : Ptr[Double], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_f64_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[Double], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_f64_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[Double], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_fmt_get_format(pFMT : Ptr[drwav_fmt]): drwav_uint16 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_fourcc_equal(a : Ptr[drwav_uint8], b : CString): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_free(p : Ptr[Byte], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_guid_equal(a : CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]], b : CArray[drwav_uint8, Nat.Digit2[Nat._1, Nat._6]]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init(pWav : Ptr[drwav], onRead : drwav_read_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_ex(pWav : Ptr[drwav], onRead : drwav_read_proc, onSeek : drwav_seek_proc, onChunk : drwav_chunk_proc, pReadSeekUserData : Ptr[Byte], pChunkUserData : Ptr[Byte], flags : drwav_uint32, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file(pWav : Ptr[drwav], filename : CString, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_ex(pWav : Ptr[drwav], filename : CString, onChunk : drwav_chunk_proc, pChunkUserData : Ptr[Byte], flags : drwav_uint32, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_ex_w(pWav : Ptr[drwav], filename : Ptr[wchar_t], onChunk : drwav_chunk_proc, pChunkUserData : Ptr[Byte], flags : drwav_uint32, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_w(pWav : Ptr[drwav], filename : Ptr[wchar_t], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write(pWav : Ptr[drwav], filename : CString, pFormat : Ptr[drwav_data_format], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write_sequential(pWav : Ptr[drwav], filename : CString, pFormat : Ptr[drwav_data_format], totalSampleCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write_sequential_pcm_frames(pWav : Ptr[drwav], filename : CString, pFormat : Ptr[drwav_data_format], totalPCMFrameCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write_sequential_pcm_frames_w(pWav : Ptr[drwav], filename : Ptr[wchar_t], pFormat : Ptr[drwav_data_format], totalPCMFrameCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write_sequential_w(pWav : Ptr[drwav], filename : Ptr[wchar_t], pFormat : Ptr[drwav_data_format], totalSampleCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_file_write_w(pWav : Ptr[drwav], filename : Ptr[wchar_t], pFormat : Ptr[drwav_data_format], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_memory(pWav : Ptr[drwav], data : Ptr[Byte], dataSize : size_t, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_memory_ex(pWav : Ptr[drwav], data : Ptr[Byte], dataSize : size_t, onChunk : drwav_chunk_proc, pChunkUserData : Ptr[Byte], flags : drwav_uint32, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_memory_write(pWav : Ptr[drwav], ppData : Ptr[Ptr[Byte]], pDataSize : Ptr[size_t], pFormat : Ptr[drwav_data_format], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_memory_write_sequential(pWav : Ptr[drwav], ppData : Ptr[Ptr[Byte]], pDataSize : Ptr[size_t], pFormat : Ptr[drwav_data_format], totalSampleCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_memory_write_sequential_pcm_frames(pWav : Ptr[drwav], ppData : Ptr[Ptr[Byte]], pDataSize : Ptr[size_t], pFormat : Ptr[drwav_data_format], totalPCMFrameCount : drwav_uint64, pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_write(pWav : Ptr[drwav], pFormat : Ptr[drwav_data_format], onWrite : drwav_write_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_write_sequential(pWav : Ptr[drwav], pFormat : Ptr[drwav_data_format], totalSampleCount : drwav_uint64, onWrite : drwav_write_proc, pUserData : Ptr[Byte], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_init_write_sequential_pcm_frames(pWav : Ptr[drwav], pFormat : Ptr[drwav_data_format], totalPCMFrameCount : drwav_uint64, onWrite : drwav_write_proc, pUserData : Ptr[Byte], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_mulaw_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_mulaw_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_mulaw_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_and_read_pcm_frames_f32(onRead : drwav_read_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[Float] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_and_read_pcm_frames_s16(onRead : drwav_read_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int16] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_and_read_pcm_frames_s32(onRead : drwav_read_proc, onSeek : drwav_seek_proc, pUserData : Ptr[Byte], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int32] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_f32(filename : CString, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[Float] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_f32_w(filename : Ptr[wchar_t], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[Float] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_s16(filename : CString, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int16] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_s16_w(filename : Ptr[wchar_t], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int16] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_s32(filename : CString, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int32] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_file_and_read_pcm_frames_s32_w(filename : Ptr[wchar_t], channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int32] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_memory_and_read_pcm_frames_f32(data : Ptr[Byte], dataSize : size_t, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[Float] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_memory_and_read_pcm_frames_s16(data : Ptr[Byte], dataSize : size_t, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int16] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_open_memory_and_read_pcm_frames_s32(data : Ptr[Byte], dataSize : size_t, channelsOut : Ptr[CUnsignedInt], sampleRateOut : Ptr[CUnsignedInt], totalFrameCountOut : Ptr[drwav_uint64], pAllocationCallbacks : Ptr[drwav_allocation_callbacks]): Ptr[drwav_int32] = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_be(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_f32(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Float]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_f32be(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Float]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_f32le(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Float]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_le(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s16(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int16]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s16be(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int16]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s16le(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int16]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s32(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int32]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s32be(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int32]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_pcm_frames_s32le(pWav : Ptr[drwav], framesToRead : drwav_uint64, pBufferOut : Ptr[drwav_int32]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_read_raw(pWav : Ptr[drwav], bytesToRead : size_t, pBufferOut : Ptr[Byte]): size_t = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s16_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_int16], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s16_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[drwav_int16], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s24_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s24_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s24_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s32_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_int32], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_s32_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[drwav_int32], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_seek_to_pcm_frame(pWav : Ptr[drwav], targetFrameIndex : drwav_uint64): drwav_bool32 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_target_write_size_bytes(pFormat : Ptr[drwav_data_format], totalSampleCount : drwav_uint64): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_u8_to_f32(pOut : Ptr[Float], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_u8_to_s16(pOut : Ptr[drwav_int16], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_u8_to_s32(pOut : Ptr[drwav_int32], pIn : Ptr[drwav_uint8], sampleCount : size_t): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_uninit(pWav : Ptr[drwav]): drwav_result = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_version(pMajor : Ptr[drwav_uint32], pMinor : Ptr[drwav_uint32], pRevision : Ptr[drwav_uint32]): Unit = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_version_string(): CString = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_write_pcm_frames(pWav : Ptr[drwav], framesToWrite : drwav_uint64, pData : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_write_pcm_frames_be(pWav : Ptr[drwav], framesToWrite : drwav_uint64, pData : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_write_pcm_frames_le(pWav : Ptr[drwav], framesToWrite : drwav_uint64, pData : Ptr[Byte]): drwav_uint64 = extern

  /**
   * [bindgen] header: ./lib/dr_wav.h
  */
  def drwav_write_raw(pWav : Ptr[drwav], bytesToWrite : size_t, pData : Ptr[Byte]): size_t = extern


object functions:
  import _root_.dr_wav.enumerations.*
  import _root_.dr_wav.predef.*
  import _root_.dr_wav.aliases.*
  import _root_.dr_wav.structs.*
  import _root_.dr_wav.unions.*
  import extern_functions.*
  export extern_functions.*

object types:
  export _root_.dr_wav.structs.*
  export _root_.dr_wav.aliases.*
  export _root_.dr_wav.unions.*
  export _root_.dr_wav.enumerations.*

object all:
  export _root_.dr_wav.enumerations.drwav_container
  export _root_.dr_wav.enumerations.drwav_seek_origin
  export _root_.dr_wav.aliases.drwav_bool32
  export _root_.dr_wav.aliases.drwav_bool8
  export _root_.dr_wav.aliases.drwav_chunk_proc
  export _root_.dr_wav.aliases.drwav_int16
  export _root_.dr_wav.aliases.drwav_int32
  export _root_.dr_wav.aliases.drwav_int64
  export _root_.dr_wav.aliases.drwav_int8
  export _root_.dr_wav.aliases.drwav_read_proc
  export _root_.dr_wav.aliases.drwav_result
  export _root_.dr_wav.aliases.drwav_seek_proc
  export _root_.dr_wav.aliases.drwav_uint16
  export _root_.dr_wav.aliases.drwav_uint32
  export _root_.dr_wav.aliases.drwav_uint64
  export _root_.dr_wav.aliases.drwav_uint8
  export _root_.dr_wav.aliases.drwav_uintptr
  export _root_.dr_wav.aliases.drwav_write_proc
  export _root_.dr_wav.aliases.size_t
  export _root_.dr_wav.aliases.wchar_t
  export _root_.dr_wav.structs.drwav
  export _root_.dr_wav.structs.drwav__memory_stream
  export _root_.dr_wav.structs.drwav__memory_stream_write
  export _root_.dr_wav.structs.drwav_allocation_callbacks
  export _root_.dr_wav.structs.drwav_chunk_header
  export _root_.dr_wav.structs.drwav_compressed
  export _root_.dr_wav.structs.drwav_data_format
  export _root_.dr_wav.structs.drwav_fmt
  export _root_.dr_wav.structs.drwav_ima
  export _root_.dr_wav.structs.drwav_msadpcm
  export _root_.dr_wav.structs.drwav_smpl
  export _root_.dr_wav.structs.drwav_smpl_loop
  export _root_.dr_wav.unions.drwav_guid
  export _root_.dr_wav.functions.drwav_alaw_to_f32
  export _root_.dr_wav.functions.drwav_alaw_to_s16
  export _root_.dr_wav.functions.drwav_alaw_to_s32
  export _root_.dr_wav.functions.drwav_bytes_to_s16
  export _root_.dr_wav.functions.drwav_bytes_to_s32
  export _root_.dr_wav.functions.drwav_bytes_to_s64
  export _root_.dr_wav.functions.drwav_bytes_to_u16
  export _root_.dr_wav.functions.drwav_bytes_to_u32
  export _root_.dr_wav.functions.drwav_bytes_to_u64
  export _root_.dr_wav.functions.drwav_f32_to_s16
  export _root_.dr_wav.functions.drwav_f32_to_s32
  export _root_.dr_wav.functions.drwav_f64_to_f32
  export _root_.dr_wav.functions.drwav_f64_to_s16
  export _root_.dr_wav.functions.drwav_f64_to_s32
  export _root_.dr_wav.functions.drwav_fmt_get_format
  export _root_.dr_wav.functions.drwav_fourcc_equal
  export _root_.dr_wav.functions.drwav_free
  export _root_.dr_wav.functions.drwav_guid_equal
  export _root_.dr_wav.functions.drwav_init
  export _root_.dr_wav.functions.drwav_init_ex
  export _root_.dr_wav.functions.drwav_init_file
  export _root_.dr_wav.functions.drwav_init_file_ex
  export _root_.dr_wav.functions.drwav_init_file_ex_w
  export _root_.dr_wav.functions.drwav_init_file_w
  export _root_.dr_wav.functions.drwav_init_file_write
  export _root_.dr_wav.functions.drwav_init_file_write_sequential
  export _root_.dr_wav.functions.drwav_init_file_write_sequential_pcm_frames
  export _root_.dr_wav.functions.drwav_init_file_write_sequential_pcm_frames_w
  export _root_.dr_wav.functions.drwav_init_file_write_sequential_w
  export _root_.dr_wav.functions.drwav_init_file_write_w
  export _root_.dr_wav.functions.drwav_init_memory
  export _root_.dr_wav.functions.drwav_init_memory_ex
  export _root_.dr_wav.functions.drwav_init_memory_write
  export _root_.dr_wav.functions.drwav_init_memory_write_sequential
  export _root_.dr_wav.functions.drwav_init_memory_write_sequential_pcm_frames
  export _root_.dr_wav.functions.drwav_init_write
  export _root_.dr_wav.functions.drwav_init_write_sequential
  export _root_.dr_wav.functions.drwav_init_write_sequential_pcm_frames
  export _root_.dr_wav.functions.drwav_mulaw_to_f32
  export _root_.dr_wav.functions.drwav_mulaw_to_s16
  export _root_.dr_wav.functions.drwav_mulaw_to_s32
  export _root_.dr_wav.functions.drwav_open_and_read_pcm_frames_f32
  export _root_.dr_wav.functions.drwav_open_and_read_pcm_frames_s16
  export _root_.dr_wav.functions.drwav_open_and_read_pcm_frames_s32
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_f32
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_f32_w
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_s16
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_s16_w
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_s32
  export _root_.dr_wav.functions.drwav_open_file_and_read_pcm_frames_s32_w
  export _root_.dr_wav.functions.drwav_open_memory_and_read_pcm_frames_f32
  export _root_.dr_wav.functions.drwav_open_memory_and_read_pcm_frames_s16
  export _root_.dr_wav.functions.drwav_open_memory_and_read_pcm_frames_s32
  export _root_.dr_wav.functions.drwav_read_pcm_frames
  export _root_.dr_wav.functions.drwav_read_pcm_frames_be
  export _root_.dr_wav.functions.drwav_read_pcm_frames_f32
  export _root_.dr_wav.functions.drwav_read_pcm_frames_f32be
  export _root_.dr_wav.functions.drwav_read_pcm_frames_f32le
  export _root_.dr_wav.functions.drwav_read_pcm_frames_le
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s16
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s16be
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s16le
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s32
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s32be
  export _root_.dr_wav.functions.drwav_read_pcm_frames_s32le
  export _root_.dr_wav.functions.drwav_read_raw
  export _root_.dr_wav.functions.drwav_s16_to_f32
  export _root_.dr_wav.functions.drwav_s16_to_s32
  export _root_.dr_wav.functions.drwav_s24_to_f32
  export _root_.dr_wav.functions.drwav_s24_to_s16
  export _root_.dr_wav.functions.drwav_s24_to_s32
  export _root_.dr_wav.functions.drwav_s32_to_f32
  export _root_.dr_wav.functions.drwav_s32_to_s16
  export _root_.dr_wav.functions.drwav_seek_to_pcm_frame
  export _root_.dr_wav.functions.drwav_target_write_size_bytes
  export _root_.dr_wav.functions.drwav_u8_to_f32
  export _root_.dr_wav.functions.drwav_u8_to_s16
  export _root_.dr_wav.functions.drwav_u8_to_s32
  export _root_.dr_wav.functions.drwav_uninit
  export _root_.dr_wav.functions.drwav_version
  export _root_.dr_wav.functions.drwav_version_string
  export _root_.dr_wav.functions.drwav_write_pcm_frames
  export _root_.dr_wav.functions.drwav_write_pcm_frames_be
  export _root_.dr_wav.functions.drwav_write_pcm_frames_le
  export _root_.dr_wav.functions.drwav_write_raw

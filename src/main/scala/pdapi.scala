// format: off
package pdapi

import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.*

object predef:
  private[pdapi] trait CEnum[T](using eq: T =:= Int):
    given Tag[T] = Tag.Int.asInstanceOf[Tag[T]]
    extension (inline t: T)
     inline def int: CInt = eq.apply(t)
     inline def uint: CUnsignedInt = eq.apply(t).toUInt
     inline def value: CInt = eq.apply(t)
  private[pdapi] trait CEnumU[T](using eq: T =:= UInt):
    given Tag[T] = Tag.UInt.asInstanceOf[Tag[T]]
    extension (inline t: T)
     inline def int: CInt = eq.apply(t).toInt
     inline def uint: CUnsignedInt = eq.apply(t)
     inline def value: CUnsignedInt = eq.apply(t)


object enumerations:
  import predef.*
  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_file.h
  */
  opaque type FileOptions = CUnsignedInt
  object FileOptions extends CEnumU[FileOptions]:
    given _tag: Tag[FileOptions] = Tag.UInt
    inline def define(inline a: Int): FileOptions = a.toUInt
    val kFileRead = define(1)
    val kFileReadData = define(2)
    val kFileWrite = define(4)
    val kFileAppend = define(8)
    inline def getName(inline value: FileOptions): Option[String] =
      inline value match
        case kFileRead => Some("kFileRead")
        case kFileReadData => Some("kFileReadData")
        case kFileWrite => Some("kFileWrite")
        case kFileAppend => Some("kFileAppend")
        case _ => None
    extension (a: FileOptions)
      inline def &(b: FileOptions): FileOptions = a & b
      inline def |(b: FileOptions): FileOptions = a | b
      inline def is(b: FileOptions): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDBitmapDrawMode = CUnsignedInt
  object LCDBitmapDrawMode extends CEnumU[LCDBitmapDrawMode]:
    given _tag: Tag[LCDBitmapDrawMode] = Tag.UInt
    inline def define(inline a: Int): LCDBitmapDrawMode = a.toUInt
    val kDrawModeCopy = define(0)
    val kDrawModeWhiteTransparent = define(1)
    val kDrawModeBlackTransparent = define(2)
    val kDrawModeFillWhite = define(3)
    val kDrawModeFillBlack = define(4)
    val kDrawModeXOR = define(5)
    val kDrawModeNXOR = define(6)
    val kDrawModeInverted = define(7)
    inline def getName(inline value: LCDBitmapDrawMode): Option[String] =
      inline value match
        case kDrawModeCopy => Some("kDrawModeCopy")
        case kDrawModeWhiteTransparent => Some("kDrawModeWhiteTransparent")
        case kDrawModeBlackTransparent => Some("kDrawModeBlackTransparent")
        case kDrawModeFillWhite => Some("kDrawModeFillWhite")
        case kDrawModeFillBlack => Some("kDrawModeFillBlack")
        case kDrawModeXOR => Some("kDrawModeXOR")
        case kDrawModeNXOR => Some("kDrawModeNXOR")
        case kDrawModeInverted => Some("kDrawModeInverted")
        case _ => None
    extension (a: LCDBitmapDrawMode)
      inline def &(b: LCDBitmapDrawMode): LCDBitmapDrawMode = a & b
      inline def |(b: LCDBitmapDrawMode): LCDBitmapDrawMode = a | b
      inline def is(b: LCDBitmapDrawMode): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDBitmapFlip = CUnsignedInt
  object LCDBitmapFlip extends CEnumU[LCDBitmapFlip]:
    given _tag: Tag[LCDBitmapFlip] = Tag.UInt
    inline def define(inline a: Int): LCDBitmapFlip = a.toUInt
    val kBitmapUnflipped = define(0)
    val kBitmapFlippedX = define(1)
    val kBitmapFlippedY = define(2)
    val kBitmapFlippedXY = define(3)
    inline def getName(inline value: LCDBitmapFlip): Option[String] =
      inline value match
        case kBitmapUnflipped => Some("kBitmapUnflipped")
        case kBitmapFlippedX => Some("kBitmapFlippedX")
        case kBitmapFlippedY => Some("kBitmapFlippedY")
        case kBitmapFlippedXY => Some("kBitmapFlippedXY")
        case _ => None
    extension (a: LCDBitmapFlip)
      inline def &(b: LCDBitmapFlip): LCDBitmapFlip = a & b
      inline def |(b: LCDBitmapFlip): LCDBitmapFlip = a | b
      inline def is(b: LCDBitmapFlip): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDFontLanguage = CUnsignedInt
  object LCDFontLanguage extends CEnumU[LCDFontLanguage]:
    given _tag: Tag[LCDFontLanguage] = Tag.UInt
    inline def define(inline a: Int): LCDFontLanguage = a.toUInt
    val kLCDFontLanguageEnglish = define(0)
    val kLCDFontLanguageJapanese = define(1)
    val kLCDFontLanguageUnknown = define(2)
    inline def getName(inline value: LCDFontLanguage): Option[String] =
      inline value match
        case kLCDFontLanguageEnglish => Some("kLCDFontLanguageEnglish")
        case kLCDFontLanguageJapanese => Some("kLCDFontLanguageJapanese")
        case kLCDFontLanguageUnknown => Some("kLCDFontLanguageUnknown")
        case _ => None
    extension (a: LCDFontLanguage)
      inline def &(b: LCDFontLanguage): LCDFontLanguage = a & b
      inline def |(b: LCDFontLanguage): LCDFontLanguage = a | b
      inline def is(b: LCDFontLanguage): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDLineCapStyle = CUnsignedInt
  object LCDLineCapStyle extends CEnumU[LCDLineCapStyle]:
    given _tag: Tag[LCDLineCapStyle] = Tag.UInt
    inline def define(inline a: Int): LCDLineCapStyle = a.toUInt
    val kLineCapStyleButt = define(0)
    val kLineCapStyleSquare = define(1)
    val kLineCapStyleRound = define(2)
    inline def getName(inline value: LCDLineCapStyle): Option[String] =
      inline value match
        case kLineCapStyleButt => Some("kLineCapStyleButt")
        case kLineCapStyleSquare => Some("kLineCapStyleSquare")
        case kLineCapStyleRound => Some("kLineCapStyleRound")
        case _ => None
    extension (a: LCDLineCapStyle)
      inline def &(b: LCDLineCapStyle): LCDLineCapStyle = a & b
      inline def |(b: LCDLineCapStyle): LCDLineCapStyle = a | b
      inline def is(b: LCDLineCapStyle): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDPolygonFillRule = CUnsignedInt
  object LCDPolygonFillRule extends CEnumU[LCDPolygonFillRule]:
    given _tag: Tag[LCDPolygonFillRule] = Tag.UInt
    inline def define(inline a: Int): LCDPolygonFillRule = a.toUInt
    val kPolygonFillNonZero = define(0)
    val kPolygonFillEvenOdd = define(1)
    inline def getName(inline value: LCDPolygonFillRule): Option[String] =
      inline value match
        case kPolygonFillNonZero => Some("kPolygonFillNonZero")
        case kPolygonFillEvenOdd => Some("kPolygonFillEvenOdd")
        case _ => None
    extension (a: LCDPolygonFillRule)
      inline def &(b: LCDPolygonFillRule): LCDPolygonFillRule = a & b
      inline def |(b: LCDPolygonFillRule): LCDPolygonFillRule = a | b
      inline def is(b: LCDPolygonFillRule): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDSolidColor = CUnsignedInt
  object LCDSolidColor extends CEnumU[LCDSolidColor]:
    given _tag: Tag[LCDSolidColor] = Tag.UInt
    inline def define(inline a: Int): LCDSolidColor = a.toUInt
    val kColorBlack = define(0)
    val kColorWhite = define(1)
    val kColorClear = define(2)
    val kColorXOR = define(3)
    inline def getName(inline value: LCDSolidColor): Option[String] =
      inline value match
        case kColorBlack => Some("kColorBlack")
        case kColorWhite => Some("kColorWhite")
        case kColorClear => Some("kColorClear")
        case kColorXOR => Some("kColorXOR")
        case _ => None
    extension (a: LCDSolidColor)
      inline def &(b: LCDSolidColor): LCDSolidColor = a & b
      inline def |(b: LCDSolidColor): LCDSolidColor = a | b
      inline def is(b: LCDSolidColor): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type LFOType = CUnsignedInt
  object LFOType extends CEnumU[LFOType]:
    given _tag: Tag[LFOType] = Tag.UInt
    inline def define(inline a: Int): LFOType = a.toUInt
    val kLFOTypeSquare = define(0)
    val kLFOTypeTriangle = define(1)
    val kLFOTypeSine = define(2)
    val kLFOTypeSampleAndHold = define(3)
    val kLFOTypeSawtoothUp = define(4)
    val kLFOTypeSawtoothDown = define(5)
    val kLFOTypeArpeggiator = define(6)
    val kLFOTypeFunction = define(7)
    inline def getName(inline value: LFOType): Option[String] =
      inline value match
        case kLFOTypeSquare => Some("kLFOTypeSquare")
        case kLFOTypeTriangle => Some("kLFOTypeTriangle")
        case kLFOTypeSine => Some("kLFOTypeSine")
        case kLFOTypeSampleAndHold => Some("kLFOTypeSampleAndHold")
        case kLFOTypeSawtoothUp => Some("kLFOTypeSawtoothUp")
        case kLFOTypeSawtoothDown => Some("kLFOTypeSawtoothDown")
        case kLFOTypeArpeggiator => Some("kLFOTypeArpeggiator")
        case kLFOTypeFunction => Some("kLFOTypeFunction")
        case _ => None
    extension (a: LFOType)
      inline def &(b: LFOType): LFOType = a & b
      inline def |(b: LFOType): LFOType = a | b
      inline def is(b: LFOType): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type LuaType = CUnsignedInt
  object LuaType extends CEnumU[LuaType]:
    given _tag: Tag[LuaType] = Tag.UInt
    inline def define(inline a: Int): LuaType = a.toUInt
    val kTypeNil = define(0)
    val kTypeBool = define(1)
    val kTypeInt = define(2)
    val kTypeFloat = define(3)
    val kTypeString = define(4)
    val kTypeTable = define(5)
    val kTypeFunction = define(6)
    val kTypeThread = define(7)
    val kTypeObject = define(8)
    inline def getName(inline value: LuaType): Option[String] =
      inline value match
        case kTypeNil => Some("kTypeNil")
        case kTypeBool => Some("kTypeBool")
        case kTypeInt => Some("kTypeInt")
        case kTypeFloat => Some("kTypeFloat")
        case kTypeString => Some("kTypeString")
        case kTypeTable => Some("kTypeTable")
        case kTypeFunction => Some("kTypeFunction")
        case kTypeThread => Some("kTypeThread")
        case kTypeObject => Some("kTypeObject")
        case _ => None
    extension (a: LuaType)
      inline def &(b: LuaType): LuaType = a & b
      inline def |(b: LuaType): LuaType = a | b
      inline def is(b: LuaType): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type PDButtons = CUnsignedInt
  object PDButtons extends CEnumU[PDButtons]:
    given _tag: Tag[PDButtons] = Tag.UInt
    inline def define(inline a: Int): PDButtons = a.toUInt
    val kButtonLeft = define(1)
    val kButtonRight = define(2)
    val kButtonUp = define(4)
    val kButtonDown = define(8)
    val kButtonB = define(16)
    val kButtonA = define(32)
    inline def getName(inline value: PDButtons): Option[String] =
      inline value match
        case kButtonLeft => Some("kButtonLeft")
        case kButtonRight => Some("kButtonRight")
        case kButtonUp => Some("kButtonUp")
        case kButtonDown => Some("kButtonDown")
        case kButtonB => Some("kButtonB")
        case kButtonA => Some("kButtonA")
        case _ => None
    extension (a: PDButtons)
      inline def &(b: PDButtons): PDButtons = a & b
      inline def |(b: PDButtons): PDButtons = a | b
      inline def is(b: PDButtons): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type PDLanguage = CUnsignedInt
  object PDLanguage extends CEnumU[PDLanguage]:
    given _tag: Tag[PDLanguage] = Tag.UInt
    inline def define(inline a: Int): PDLanguage = a.toUInt
    val kPDLanguageEnglish = define(0)
    val kPDLanguageJapanese = define(1)
    val kPDLanguageUnknown = define(2)
    inline def getName(inline value: PDLanguage): Option[String] =
      inline value match
        case kPDLanguageEnglish => Some("kPDLanguageEnglish")
        case kPDLanguageJapanese => Some("kPDLanguageJapanese")
        case kPDLanguageUnknown => Some("kPDLanguageUnknown")
        case _ => None
    extension (a: PDLanguage)
      inline def &(b: PDLanguage): PDLanguage = a & b
      inline def |(b: PDLanguage): PDLanguage = a | b
      inline def is(b: PDLanguage): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type PDPeripherals = CUnsignedInt
  object PDPeripherals extends CEnumU[PDPeripherals]:
    given _tag: Tag[PDPeripherals] = Tag.UInt
    inline def define(inline a: Int): PDPeripherals = a.toUInt
    val kNone = define(0)
    val kAccelerometer = define(1)
    val kAllPeripherals = define(65535)
    inline def getName(inline value: PDPeripherals): Option[String] =
      inline value match
        case kNone => Some("kNone")
        case kAccelerometer => Some("kAccelerometer")
        case kAllPeripherals => Some("kAllPeripherals")
        case _ => None
    extension (a: PDPeripherals)
      inline def &(b: PDPeripherals): PDPeripherals = a & b
      inline def |(b: PDPeripherals): PDPeripherals = a | b
      inline def is(b: PDPeripherals): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type PDStringEncoding = CUnsignedInt
  object PDStringEncoding extends CEnumU[PDStringEncoding]:
    given _tag: Tag[PDStringEncoding] = Tag.UInt
    inline def define(inline a: Int): PDStringEncoding = a.toUInt
    val kASCIIEncoding = define(0)
    val kUTF8Encoding = define(1)
    val k16BitLEEncoding = define(2)
    inline def getName(inline value: PDStringEncoding): Option[String] =
      inline value match
        case kASCIIEncoding => Some("kASCIIEncoding")
        case kUTF8Encoding => Some("kUTF8Encoding")
        case k16BitLEEncoding => Some("k16BitLEEncoding")
        case _ => None
    extension (a: PDStringEncoding)
      inline def &(b: PDStringEncoding): PDStringEncoding = a & b
      inline def |(b: PDStringEncoding): PDStringEncoding = a | b
      inline def is(b: PDStringEncoding): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api.h
  */
  opaque type PDSystemEvent = Int
  object PDSystemEvent extends CEnum[PDSystemEvent]:
    given _tag: Tag[PDSystemEvent] = Tag.Int
    inline def define(inline a: Int): PDSystemEvent = a.toInt
    val kEventInit = define(0)
    val kEventInitLua = define(1)
    val kEventLock = define(2)
    val kEventUnlock = define(3)
    val kEventPause = define(4)
    val kEventResume = define(5)
    val kEventTerminate = define(6)
    val kEventKeyPressed = define(7)
    val kEventKeyReleased = define(8)
    val kEventLowPower = define(9)
    inline def getName(inline value: PDSystemEvent): Option[String] =
      inline value match
        case kEventInit => Some("kEventInit")
        case kEventInitLua => Some("kEventInitLua")
        case kEventLock => Some("kEventLock")
        case kEventUnlock => Some("kEventUnlock")
        case kEventPause => Some("kEventPause")
        case kEventResume => Some("kEventResume")
        case kEventTerminate => Some("kEventTerminate")
        case kEventKeyPressed => Some("kEventKeyPressed")
        case kEventKeyReleased => Some("kEventKeyReleased")
        case kEventLowPower => Some("kEventLowPower")
        case _ => None
    extension (a: PDSystemEvent)
      inline def &(b: PDSystemEvent): PDSystemEvent = a & b
      inline def |(b: PDSystemEvent): PDSystemEvent = a | b
      inline def is(b: PDSystemEvent): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundFormat = CUnsignedInt
  object SoundFormat extends CEnumU[SoundFormat]:
    given _tag: Tag[SoundFormat] = Tag.UInt
    inline def define(inline a: Int): SoundFormat = a.toUInt
    val kSound8bitMono = define(0)
    val kSound8bitStereo = define(1)
    val kSound16bitMono = define(2)
    val kSound16bitStereo = define(3)
    val kSoundADPCMMono = define(4)
    val kSoundADPCMStereo = define(5)
    inline def getName(inline value: SoundFormat): Option[String] =
      inline value match
        case kSound8bitMono => Some("kSound8bitMono")
        case kSound8bitStereo => Some("kSound8bitStereo")
        case kSound16bitMono => Some("kSound16bitMono")
        case kSound16bitStereo => Some("kSound16bitStereo")
        case kSoundADPCMMono => Some("kSoundADPCMMono")
        case kSoundADPCMStereo => Some("kSoundADPCMStereo")
        case _ => None
    extension (a: SoundFormat)
      inline def &(b: SoundFormat): SoundFormat = a & b
      inline def |(b: SoundFormat): SoundFormat = a | b
      inline def is(b: SoundFormat): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundWaveform = CUnsignedInt
  object SoundWaveform extends CEnumU[SoundWaveform]:
    given _tag: Tag[SoundWaveform] = Tag.UInt
    inline def define(inline a: Int): SoundWaveform = a.toUInt
    val kWaveformSquare = define(0)
    val kWaveformTriangle = define(1)
    val kWaveformSine = define(2)
    val kWaveformNoise = define(3)
    val kWaveformSawtooth = define(4)
    val kWaveformPOPhase = define(5)
    val kWaveformPODigital = define(6)
    val kWaveformPOVosim = define(7)
    inline def getName(inline value: SoundWaveform): Option[String] =
      inline value match
        case kWaveformSquare => Some("kWaveformSquare")
        case kWaveformTriangle => Some("kWaveformTriangle")
        case kWaveformSine => Some("kWaveformSine")
        case kWaveformNoise => Some("kWaveformNoise")
        case kWaveformSawtooth => Some("kWaveformSawtooth")
        case kWaveformPOPhase => Some("kWaveformPOPhase")
        case kWaveformPODigital => Some("kWaveformPODigital")
        case kWaveformPOVosim => Some("kWaveformPOVosim")
        case _ => None
    extension (a: SoundWaveform)
      inline def &(b: SoundWaveform): SoundWaveform = a & b
      inline def |(b: SoundWaveform): SoundWaveform = a | b
      inline def is(b: SoundWaveform): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type SpriteCollisionResponseType = CUnsignedInt
  object SpriteCollisionResponseType extends CEnumU[SpriteCollisionResponseType]:
    given _tag: Tag[SpriteCollisionResponseType] = Tag.UInt
    inline def define(inline a: Int): SpriteCollisionResponseType = a.toUInt
    val kCollisionTypeSlide = define(0)
    val kCollisionTypeFreeze = define(1)
    val kCollisionTypeOverlap = define(2)
    val kCollisionTypeBounce = define(3)
    inline def getName(inline value: SpriteCollisionResponseType): Option[String] =
      inline value match
        case kCollisionTypeSlide => Some("kCollisionTypeSlide")
        case kCollisionTypeFreeze => Some("kCollisionTypeFreeze")
        case kCollisionTypeOverlap => Some("kCollisionTypeOverlap")
        case kCollisionTypeBounce => Some("kCollisionTypeBounce")
        case _ => None
    extension (a: SpriteCollisionResponseType)
      inline def &(b: SpriteCollisionResponseType): SpriteCollisionResponseType = a & b
      inline def |(b: SpriteCollisionResponseType): SpriteCollisionResponseType = a | b
      inline def is(b: SpriteCollisionResponseType): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type TwoPoleFilterType = CUnsignedInt
  object TwoPoleFilterType extends CEnumU[TwoPoleFilterType]:
    given _tag: Tag[TwoPoleFilterType] = Tag.UInt
    inline def define(inline a: Int): TwoPoleFilterType = a.toUInt
    val kFilterTypeLowPass = define(0)
    val kFilterTypeHighPass = define(1)
    val kFilterTypeBandPass = define(2)
    val kFilterTypeNotch = define(3)
    val kFilterTypePEQ = define(4)
    val kFilterTypeLowShelf = define(5)
    val kFilterTypeHighShelf = define(6)
    inline def getName(inline value: TwoPoleFilterType): Option[String] =
      inline value match
        case kFilterTypeLowPass => Some("kFilterTypeLowPass")
        case kFilterTypeHighPass => Some("kFilterTypeHighPass")
        case kFilterTypeBandPass => Some("kFilterTypeBandPass")
        case kFilterTypeNotch => Some("kFilterTypeNotch")
        case kFilterTypePEQ => Some("kFilterTypePEQ")
        case kFilterTypeLowShelf => Some("kFilterTypeLowShelf")
        case kFilterTypeHighShelf => Some("kFilterTypeHighShelf")
        case _ => None
    extension (a: TwoPoleFilterType)
      inline def &(b: TwoPoleFilterType): TwoPoleFilterType = a & b
      inline def |(b: TwoPoleFilterType): TwoPoleFilterType = a | b
      inline def is(b: TwoPoleFilterType): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_value_type = CUnsignedInt
  object json_value_type extends CEnumU[json_value_type]:
    given _tag: Tag[json_value_type] = Tag.UInt
    inline def define(inline a: Int): json_value_type = a.toUInt
    val kJSONNull = define(0)
    val kJSONTrue = define(1)
    val kJSONFalse = define(2)
    val kJSONInteger = define(3)
    val kJSONFloat = define(4)
    val kJSONString = define(5)
    val kJSONArray = define(6)
    val kJSONTable = define(7)
    inline def getName(inline value: json_value_type): Option[String] =
      inline value match
        case kJSONNull => Some("kJSONNull")
        case kJSONTrue => Some("kJSONTrue")
        case kJSONFalse => Some("kJSONFalse")
        case kJSONInteger => Some("kJSONInteger")
        case kJSONFloat => Some("kJSONFloat")
        case kJSONString => Some("kJSONString")
        case kJSONArray => Some("kJSONArray")
        case kJSONTable => Some("kJSONTable")
        case _ => None
    extension (a: json_value_type)
      inline def &(b: json_value_type): json_value_type = a & b
      inline def |(b: json_value_type): json_value_type = a | b
      inline def is(b: json_value_type): Boolean = (a & b) == b

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type l_valtype = CUnsignedInt
  object l_valtype extends CEnumU[l_valtype]:
    given _tag: Tag[l_valtype] = Tag.UInt
    inline def define(inline a: Int): l_valtype = a.toUInt
    val kInt = define(0)
    val kFloat = define(1)
    val kStr = define(2)
    inline def getName(inline value: l_valtype): Option[String] =
      inline value match
        case kInt => Some("kInt")
        case kFloat => Some("kFloat")
        case kStr => Some("kStr")
        case _ => None
    extension (a: l_valtype)
      inline def &(b: l_valtype): l_valtype = a & b
      inline def |(b: l_valtype): l_valtype = a | b
      inline def is(b: l_valtype): Boolean = (a & b) == b

object aliases:
  import _root_.pdapi.enumerations.*
  import _root_.pdapi.predef.*
  import _root_.pdapi.aliases.*
  import _root_.pdapi.structs.*
  import _root_.pdapi.unions.*
  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type AddScoreCallback = CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]
  object AddScoreCallback:
    given _tag: Tag[AddScoreCallback] = Tag.materializeCFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): AddScoreCallback = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]): AddScoreCallback = o
    extension (v: AddScoreCallback)
      inline def value: CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  type AudioSourceFunction = CFuncPtr4[Ptr[Byte], Ptr[int16_t], Ptr[int16_t], CInt, CInt]
  object AudioSourceFunction:
    given _tag: Tag[AudioSourceFunction] = Tag.materializeCFuncPtr4[Ptr[Byte], Ptr[int16_t], Ptr[int16_t], CInt, CInt]
    inline def apply(inline o: CFuncPtr4[Ptr[Byte], Ptr[int16_t], Ptr[int16_t], CInt, CInt]): AudioSourceFunction = o
    extension (v: AudioSourceFunction)
      inline def value: CFuncPtr4[Ptr[Byte], Ptr[int16_t], Ptr[int16_t], CInt, CInt] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type BoardsListCallback = CFuncPtr2[Ptr[PDBoardsList], Ptr[CUnsignedChar], Unit]
  object BoardsListCallback:
    given _tag: Tag[BoardsListCallback] = Tag.materializeCFuncPtr2[Ptr[PDBoardsList], Ptr[CUnsignedChar], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): BoardsListCallback = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[PDBoardsList], Ptr[CUnsignedChar], Unit]): BoardsListCallback = o
    extension (v: BoardsListCallback)
      inline def value: CFuncPtr2[Ptr[PDBoardsList], Ptr[CUnsignedChar], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  type LCDColor = Size

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDPattern = CArray[uint8_t, Nat.Digit2[Nat._1, Nat._6]]
  object LCDPattern:
    given _tag: Tag[LCDPattern] = Tag.CArray[uint8_t, Nat.Digit2[Nat._1, Nat._6]](uint8_t._tag, Tag.Digit2[Nat._1, Nat._6](Tag.Nat1, Tag.Nat6))
    inline def apply(inline o: CArray[uint8_t, Nat.Digit2[Nat._1, Nat._6]]): LCDPattern = o
    extension (v: LCDPattern)
      inline def value: CArray[uint8_t, Nat.Digit2[Nat._1, Nat._6]] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  type LCDSpriteCollisionFilterProc = CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType]
  object LCDSpriteCollisionFilterProc:
    given _tag: Tag[LCDSpriteCollisionFilterProc] = Tag.materializeCFuncPtr2[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType]
    inline def apply(inline o: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType]): LCDSpriteCollisionFilterProc = o
    extension (v: LCDSpriteCollisionFilterProc)
      inline def value: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  type LCDSpriteDrawFunction = CFuncPtr3[Ptr[LCDSprite], PDRect, PDRect, Unit]
  object LCDSpriteDrawFunction:
    given _tag: Tag[LCDSpriteDrawFunction] = Tag.materializeCFuncPtr3[Ptr[LCDSprite], PDRect, PDRect, Unit]
    inline def apply(inline o: CFuncPtr3[Ptr[LCDSprite], PDRect, PDRect, Unit]): LCDSpriteDrawFunction = o
    extension (v: LCDSpriteDrawFunction)
      inline def value: CFuncPtr3[Ptr[LCDSprite], PDRect, PDRect, Unit] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  type LCDSpriteUpdateFunction = CFuncPtr1[Ptr[LCDSprite], Unit]
  object LCDSpriteUpdateFunction:
    given _tag: Tag[LCDSpriteUpdateFunction] = Tag.materializeCFuncPtr1[Ptr[LCDSprite], Unit]
    inline def apply(inline o: CFuncPtr1[Ptr[LCDSprite], Unit]): LCDSpriteUpdateFunction = o
    extension (v: LCDSpriteUpdateFunction)
      inline def value: CFuncPtr1[Ptr[LCDSprite], Unit] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type MIDINote = Float
  object MIDINote:
    given _tag: Tag[MIDINote] = Tag.Float
    inline def apply(inline o: Float): MIDINote = o
    extension (v: MIDINote)
      inline def value: Float = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  type PDCallbackFunction = CFuncPtr1[Ptr[Byte], CInt]
  object PDCallbackFunction:
    given _tag: Tag[PDCallbackFunction] = Tag.materializeCFuncPtr1[Ptr[Byte], CInt]
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], CInt]): PDCallbackFunction = o
    extension (v: PDCallbackFunction)
      inline def value: CFuncPtr1[Ptr[Byte], CInt] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  type PDMenuItemCallbackFunction = CFuncPtr1[Ptr[Byte], Unit]
  object PDMenuItemCallbackFunction:
    given _tag: Tag[PDMenuItemCallbackFunction] = Tag.materializeCFuncPtr1[Ptr[Byte], Unit]
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], Unit]): PDMenuItemCallbackFunction = o
    extension (v: PDMenuItemCallbackFunction)
      inline def value: CFuncPtr1[Ptr[Byte], Unit] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type PersonalBestCallback = CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]
  object PersonalBestCallback:
    given _tag: Tag[PersonalBestCallback] = Tag.materializeCFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): PersonalBestCallback = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit]): PersonalBestCallback = o
    extension (v: PersonalBestCallback)
      inline def value: CFuncPtr2[Ptr[PDScore], Ptr[CUnsignedChar], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  type RecordCallback = CFuncPtr3[Ptr[Byte], Ptr[int16_t], CInt, CInt]
  object RecordCallback:
    given _tag: Tag[RecordCallback] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[int16_t], CInt, CInt]
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[int16_t], CInt, CInt]): RecordCallback = o
    extension (v: RecordCallback)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[int16_t], CInt, CInt] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_file.h
  */
  type SDFile = Unit
  object SDFile:
    given _tag: Tag[SDFile] = Tag.Unit
    inline def apply(inline o: Unit): SDFile = o
    extension (v: SDFile)
      inline def value: Unit = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type ScoresCallback = CFuncPtr2[Ptr[PDScoresList], Ptr[CUnsignedChar], Unit]
  object ScoresCallback:
    given _tag: Tag[ScoresCallback] = Tag.materializeCFuncPtr2[Ptr[PDScoresList], Ptr[CUnsignedChar], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): ScoresCallback = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[PDScoresList], Ptr[CUnsignedChar], Unit]): ScoresCallback = o
    extension (v: ScoresCallback)
      inline def value: CFuncPtr2[Ptr[PDScoresList], Ptr[CUnsignedChar], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SequenceFinishedCallback = CFuncPtr2[Ptr[SoundSequence], Ptr[Byte], Unit]
  object SequenceFinishedCallback:
    given _tag: Tag[SequenceFinishedCallback] = Tag.materializeCFuncPtr2[Ptr[SoundSequence], Ptr[Byte], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): SequenceFinishedCallback = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[SoundSequence], Ptr[Byte], Unit]): SequenceFinishedCallback = o
    extension (v: SequenceFinishedCallback)
      inline def value: CFuncPtr2[Ptr[SoundSequence], Ptr[Byte], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  type effectProc = CFuncPtr5[Ptr[SoundEffect], Ptr[int32_t], Ptr[int32_t], CInt, CInt, CInt]
  object effectProc:
    given _tag: Tag[effectProc] = Tag.materializeCFuncPtr5[Ptr[SoundEffect], Ptr[int32_t], Ptr[int32_t], CInt, CInt, CInt]
    inline def apply(inline o: CFuncPtr5[Ptr[SoundEffect], Ptr[int32_t], Ptr[int32_t], CInt, CInt, CInt]): effectProc = o
    extension (v: effectProc)
      inline def value: CFuncPtr5[Ptr[SoundEffect], Ptr[int32_t], Ptr[int32_t], CInt, CInt, CInt] = v

  type int16_t = scala.Short
  object int16_t:
    val _tag: Tag[int16_t] = summon[Tag[scala.Short]]
    inline def apply(inline o: scala.Short): int16_t = o
    extension (v: int16_t)
      inline def value: scala.Short = v

  type int32_t = scala.scalanative.unsafe.CInt
  object int32_t:
    val _tag: Tag[int32_t] = summon[Tag[scala.scalanative.unsafe.CInt]]
    inline def apply(inline o: scala.scalanative.unsafe.CInt): int32_t = o
    extension (v: int32_t)
      inline def value: scala.scalanative.unsafe.CInt = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type lua_CFunction = CFuncPtr1[Ptr[lua_State], CInt]
  object lua_CFunction:
    given _tag: Tag[lua_CFunction] = Tag.materializeCFuncPtr1[Ptr[lua_State], CInt]
    inline def fromPtr(ptr: Ptr[Byte]): lua_CFunction = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr1[Ptr[lua_State], CInt]): lua_CFunction = o
    extension (v: lua_CFunction)
      inline def value: CFuncPtr1[Ptr[lua_State], CInt] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type lua_State = Ptr[Byte]
  object lua_State:
    given _tag: Tag[lua_State] = Tag.Ptr(Tag.Byte)
    inline def apply(inline o: Ptr[Byte]): lua_State = o
    extension (v: lua_State)
      inline def value: Ptr[Byte] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type signalDeallocFunc = CFuncPtr1[Ptr[Byte], Unit]
  object signalDeallocFunc:
    given _tag: Tag[signalDeallocFunc] = Tag.materializeCFuncPtr1[Ptr[Byte], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): signalDeallocFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], Unit]): signalDeallocFunc = o
    extension (v: signalDeallocFunc)
      inline def value: CFuncPtr1[Ptr[Byte], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type signalNoteOffFunc = CFuncPtr3[Ptr[Byte], CInt, CInt, Unit]
  object signalNoteOffFunc:
    given _tag: Tag[signalNoteOffFunc] = Tag.materializeCFuncPtr3[Ptr[Byte], CInt, CInt, Unit]
    inline def fromPtr(ptr: Ptr[Byte]): signalNoteOffFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], CInt, CInt, Unit]): signalNoteOffFunc = o
    extension (v: signalNoteOffFunc)
      inline def value: CFuncPtr3[Ptr[Byte], CInt, CInt, Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type signalNoteOnFunc = CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]
  object signalNoteOnFunc:
    given _tag: Tag[signalNoteOnFunc] = Tag.materializeCFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]
    inline def fromPtr(ptr: Ptr[Byte]): signalNoteOnFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]): signalNoteOnFunc = o
    extension (v: signalNoteOnFunc)
      inline def value: CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type signalStepFunc = CFuncPtr3[Ptr[Byte], Ptr[CInt], Ptr[Float], Float]
  object signalStepFunc:
    given _tag: Tag[signalStepFunc] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[CInt], Ptr[Float], Float]
    inline def fromPtr(ptr: Ptr[Byte]): signalStepFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[CInt], Ptr[Float], Float]): signalStepFunc = o
    extension (v: signalStepFunc)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[CInt], Ptr[Float], Float] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  type size_t = libc.stddef.size_t
  object size_t:
    val _tag: Tag[size_t] = summon[Tag[libc.stddef.size_t]]
    inline def apply(inline o: libc.stddef.size_t): size_t = o
    extension (v: size_t)
      inline def value: libc.stddef.size_t = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  type sndCallbackProc = CFuncPtr1[Ptr[SoundSource], Unit]
  object sndCallbackProc:
    given _tag: Tag[sndCallbackProc] = Tag.materializeCFuncPtr1[Ptr[SoundSource], Unit]
    inline def apply(inline o: CFuncPtr1[Ptr[SoundSource], Unit]): sndCallbackProc = o
    extension (v: sndCallbackProc)
      inline def value: CFuncPtr1[Ptr[SoundSource], Unit] = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type synthDeallocFunc = CFuncPtr1[Ptr[Byte], Unit]
  object synthDeallocFunc:
    given _tag: Tag[synthDeallocFunc] = Tag.materializeCFuncPtr1[Ptr[Byte], Unit]
    inline def fromPtr(ptr: Ptr[Byte]): synthDeallocFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], Unit]): synthDeallocFunc = o
    extension (v: synthDeallocFunc)
      inline def value: CFuncPtr1[Ptr[Byte], Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type synthNoteOnFunc = CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]
  object synthNoteOnFunc:
    given _tag: Tag[synthNoteOnFunc] = Tag.materializeCFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]
    inline def fromPtr(ptr: Ptr[Byte]): synthNoteOnFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit]): synthNoteOnFunc = o
    extension (v: synthNoteOnFunc)
      inline def value: CFuncPtr4[Ptr[Byte], MIDINote, Float, Float, Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type synthReleaseFunc = CFuncPtr2[Ptr[Byte], CInt, Unit]
  object synthReleaseFunc:
    given _tag: Tag[synthReleaseFunc] = Tag.materializeCFuncPtr2[Ptr[Byte], CInt, Unit]
    inline def fromPtr(ptr: Ptr[Byte]): synthReleaseFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr2[Ptr[Byte], CInt, Unit]): synthReleaseFunc = o
    extension (v: synthReleaseFunc)
      inline def value: CFuncPtr2[Ptr[Byte], CInt, Unit] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type synthRenderFunc = CFuncPtr6[Ptr[Byte], Ptr[int32_t], Ptr[int32_t], CInt, uint32_t, int32_t, CInt]
  object synthRenderFunc:
    given _tag: Tag[synthRenderFunc] = Tag.materializeCFuncPtr6[Ptr[Byte], Ptr[int32_t], Ptr[int32_t], CInt, uint32_t, int32_t, CInt]
    inline def fromPtr(ptr: Ptr[Byte]): synthRenderFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr6[Ptr[Byte], Ptr[int32_t], Ptr[int32_t], CInt, uint32_t, int32_t, CInt]): synthRenderFunc = o
    extension (v: synthRenderFunc)
      inline def value: CFuncPtr6[Ptr[Byte], Ptr[int32_t], Ptr[int32_t], CInt, uint32_t, int32_t, CInt] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type synthSetParameterFunc = CFuncPtr3[Ptr[Byte], CInt, Float, CInt]
  object synthSetParameterFunc:
    given _tag: Tag[synthSetParameterFunc] = Tag.materializeCFuncPtr3[Ptr[Byte], CInt, Float, CInt]
    inline def fromPtr(ptr: Ptr[Byte]): synthSetParameterFunc = CFuncPtr.fromPtr(ptr)
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], CInt, Float, CInt]): synthSetParameterFunc = o
    extension (v: synthSetParameterFunc)
      inline def value: CFuncPtr3[Ptr[Byte], CInt, Float, CInt] = v
      inline def toPtr: Ptr[_] = CFuncPtr.toPtr(v)

  type uint16_t = scala.scalanative.unsigned.UShort
  object uint16_t:
    val _tag: Tag[uint16_t] = summon[Tag[scala.scalanative.unsigned.UShort]]
    inline def apply(inline o: scala.scalanative.unsigned.UShort): uint16_t = o
    extension (v: uint16_t)
      inline def value: scala.scalanative.unsigned.UShort = v

  type uint32_t = scala.scalanative.unsigned.UInt
  object uint32_t:
    val _tag: Tag[uint32_t] = summon[Tag[scala.scalanative.unsigned.UInt]]
    inline def apply(inline o: scala.scalanative.unsigned.UInt): uint32_t = o
    extension (v: uint32_t)
      inline def value: scala.scalanative.unsigned.UInt = v

  type uint8_t = scala.scalanative.unsigned.UByte
  object uint8_t:
    val _tag: Tag[uint8_t] = summon[Tag[scala.scalanative.unsigned.UByte]]
    inline def apply(inline o: scala.scalanative.unsigned.UByte): uint8_t = o
    extension (v: uint8_t)
      inline def value: scala.scalanative.unsigned.UByte = v

  /**
   * [bindgen] header: /nix/store/wkn9s7f1r35c789hqcs84hpgqg3p3q15-libSystem-11.0.0/include/sys/_types/_uintptr_t.h
  */
  // manually switched to usize
  opaque type uintptr_t = USize
  object uintptr_t:
    given _tag: Tag[uintptr_t] = Tag.USize
    inline def apply(inline o: USize): uintptr_t = o
    extension (v: uintptr_t)
      inline def value: USize = v

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  type writeFunc = CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]
  object writeFunc:
    given _tag: Tag[writeFunc] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]): writeFunc = o
    extension (v: writeFunc)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit] = v

object structs:
  import _root_.pdapi.enumerations.*
  import _root_.pdapi.predef.*
  import _root_.pdapi.aliases.*
  import _root_.pdapi.structs.*
  import _root_.pdapi.unions.*
  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type AudioSample = CStruct0
  object AudioSample:
    given _tag: Tag[AudioSample] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type BitCrusher = CStruct0
  object BitCrusher:
    given _tag: Tag[BitCrusher] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type CWCollisionInfo = CStruct0
  object CWCollisionInfo:
    given _tag: Tag[CWCollisionInfo] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type CWItemInfo = CStruct0
  object CWItemInfo:
    given _tag: Tag[CWItemInfo] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type CollisionPoint = CStruct2[Float, Float]
  object CollisionPoint:
    given _tag: Tag[CollisionPoint] = Tag.materializeCStruct2Tag[Float, Float]
    def apply()(using Zone): Ptr[CollisionPoint] = scala.scalanative.unsafe.alloc[CollisionPoint](1)
    def apply(x : Float, y : Float)(using Zone): Ptr[CollisionPoint] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: CollisionPoint)
      def x : Float = struct._1
      def x_=(value: Float): Unit = !struct.at1 = value
      def y : Float = struct._2
      def y_=(value: Float): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type CollisionVector = CStruct2[CInt, CInt]
  object CollisionVector:
    given _tag: Tag[CollisionVector] = Tag.materializeCStruct2Tag[CInt, CInt]
    def apply()(using Zone): Ptr[CollisionVector] = scala.scalanative.unsafe.alloc[CollisionVector](1)
    def apply(x : CInt, y : CInt)(using Zone): Ptr[CollisionVector] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: CollisionVector)
      def x : CInt = struct._1
      def x_=(value: CInt): Unit = !struct.at1 = value
      def y : CInt = struct._2
      def y_=(value: CInt): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type ControlSignal = CStruct0
  object ControlSignal:
    given _tag: Tag[ControlSignal] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type DelayLine = CStruct0
  object DelayLine:
    given _tag: Tag[DelayLine] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type DelayLineTap = CStruct0
  object DelayLineTap:
    given _tag: Tag[DelayLineTap] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type FilePlayer = CStruct0
  object FilePlayer:
    given _tag: Tag[FilePlayer] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_file.h
  */
  opaque type FileStat = CStruct8[CInt, CUnsignedInt, CInt, CInt, CInt, CInt, CInt, CInt]
  object FileStat:
    given _tag: Tag[FileStat] = Tag.materializeCStruct8Tag[CInt, CUnsignedInt, CInt, CInt, CInt, CInt, CInt, CInt]
    def apply()(using Zone): Ptr[FileStat] = scala.scalanative.unsafe.alloc[FileStat](1)
    def apply(isdir : CInt, size : CUnsignedInt, m_year : CInt, m_month : CInt, m_day : CInt, m_hour : CInt, m_minute : CInt, m_second : CInt)(using Zone): Ptr[FileStat] =
      val ____ptr = apply()
      (!____ptr).isdir = isdir
      (!____ptr).size = size
      (!____ptr).m_year = m_year
      (!____ptr).m_month = m_month
      (!____ptr).m_day = m_day
      (!____ptr).m_hour = m_hour
      (!____ptr).m_minute = m_minute
      (!____ptr).m_second = m_second
      ____ptr
    extension (struct: FileStat)
      def isdir : CInt = struct._1
      def isdir_=(value: CInt): Unit = !struct.at1 = value
      def size : CUnsignedInt = struct._2
      def size_=(value: CUnsignedInt): Unit = !struct.at2 = value
      def m_year : CInt = struct._3
      def m_year_=(value: CInt): Unit = !struct.at3 = value
      def m_month : CInt = struct._4
      def m_month_=(value: CInt): Unit = !struct.at4 = value
      def m_day : CInt = struct._5
      def m_day_=(value: CInt): Unit = !struct.at5 = value
      def m_hour : CInt = struct._6
      def m_hour_=(value: CInt): Unit = !struct.at6 = value
      def m_minute : CInt = struct._7
      def m_minute_=(value: CInt): Unit = !struct.at7 = value
      def m_second : CInt = struct._8
      def m_second_=(value: CInt): Unit = !struct.at8 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDBitmap = CStruct0
  object LCDBitmap:
    given _tag: Tag[LCDBitmap] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDBitmapTable = CStruct0
  object LCDBitmapTable:
    given _tag: Tag[LCDBitmapTable] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDFont = CStruct0
  object LCDFont:
    given _tag: Tag[LCDFont] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDFontData = CStruct0
  object LCDFontData:
    given _tag: Tag[LCDFontData] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDFontGlyph = CStruct0
  object LCDFontGlyph:
    given _tag: Tag[LCDFontGlyph] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDFontPage = CStruct0
  object LCDFontPage:
    given _tag: Tag[LCDFontPage] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDRect = CStruct4[CInt, CInt, CInt, CInt]
  object LCDRect:
    given _tag: Tag[LCDRect] = Tag.materializeCStruct4Tag[CInt, CInt, CInt, CInt]
    def apply()(using Zone): Ptr[LCDRect] = scala.scalanative.unsafe.alloc[LCDRect](1)
    def apply(left : CInt, right : CInt, top : CInt, bottom : CInt)(using Zone): Ptr[LCDRect] =
      val ____ptr = apply()
      (!____ptr).left = left
      (!____ptr).right = right
      (!____ptr).top = top
      (!____ptr).bottom = bottom
      ____ptr
    extension (struct: LCDRect)
      def left : CInt = struct._1
      def left_=(value: CInt): Unit = !struct.at1 = value
      def right : CInt = struct._2
      def right_=(value: CInt): Unit = !struct.at2 = value
      def top : CInt = struct._3
      def top_=(value: CInt): Unit = !struct.at3 = value
      def bottom : CInt = struct._4
      def bottom_=(value: CInt): Unit = !struct.at4 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type LCDSprite = CStruct0
  object LCDSprite:
    given _tag: Tag[LCDSprite] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type LCDVideoPlayer = CStruct0
  object LCDVideoPlayer:
    given _tag: Tag[LCDVideoPlayer] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type LuaUDObject = CStruct0
  object LuaUDObject:
    given _tag: Tag[LuaUDObject] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type OnePoleFilter = CStruct0
  object OnePoleFilter:
    given _tag: Tag[OnePoleFilter] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type Overdrive = CStruct0
  object Overdrive:
    given _tag: Tag[Overdrive] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type PDBoard = CStruct2[Ptr[CUnsignedChar], Ptr[CUnsignedChar]]
  object PDBoard:
    given _tag: Tag[PDBoard] = Tag.materializeCStruct2Tag[Ptr[CUnsignedChar], Ptr[CUnsignedChar]]
    def apply()(using Zone): Ptr[PDBoard] = scala.scalanative.unsafe.alloc[PDBoard](1)
    def apply(boardID : Ptr[CUnsignedChar], name : Ptr[CUnsignedChar])(using Zone): Ptr[PDBoard] =
      val ____ptr = apply()
      (!____ptr).boardID = boardID
      (!____ptr).name = name
      ____ptr
    extension (struct: PDBoard)
      def boardID : Ptr[CUnsignedChar] = struct._1
      def boardID_=(value: Ptr[CUnsignedChar]): Unit = !struct.at1 = value
      def name : Ptr[CUnsignedChar] = struct._2
      def name_=(value: Ptr[CUnsignedChar]): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type PDBoardsList = CStruct3[CUnsignedInt, uint32_t, Ptr[PDBoard]]
  object PDBoardsList:
    given _tag: Tag[PDBoardsList] = Tag.materializeCStruct3Tag[CUnsignedInt, uint32_t, Ptr[PDBoard]]
    def apply()(using Zone): Ptr[PDBoardsList] = scala.scalanative.unsafe.alloc[PDBoardsList](1)
    def apply(count : CUnsignedInt, lastUpdated : uint32_t, boards : Ptr[PDBoard])(using Zone): Ptr[PDBoardsList] =
      val ____ptr = apply()
      (!____ptr).count = count
      (!____ptr).lastUpdated = lastUpdated
      (!____ptr).boards = boards
      ____ptr
    extension (struct: PDBoardsList)
      def count : CUnsignedInt = struct._1
      def count_=(value: CUnsignedInt): Unit = !struct.at1 = value
      def lastUpdated : uint32_t = struct._2
      def lastUpdated_=(value: uint32_t): Unit = !struct.at2 = value
      def boards : Ptr[PDBoard] = struct._3
      def boards_=(value: Ptr[PDBoard]): Unit = !struct.at3 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type PDDateTime = CStruct7[uint16_t, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t]
  object PDDateTime:
    given _tag: Tag[PDDateTime] = Tag.materializeCStruct7Tag[uint16_t, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t, uint8_t]
    def apply()(using Zone): Ptr[PDDateTime] = scala.scalanative.unsafe.alloc[PDDateTime](1)
    def apply(year : uint16_t, month : uint8_t, day : uint8_t, weekday : uint8_t, hour : uint8_t, minute : uint8_t, second : uint8_t)(using Zone): Ptr[PDDateTime] =
      val ____ptr = apply()
      (!____ptr).year = year
      (!____ptr).month = month
      (!____ptr).day = day
      (!____ptr).weekday = weekday
      (!____ptr).hour = hour
      (!____ptr).minute = minute
      (!____ptr).second = second
      ____ptr
    extension (struct: PDDateTime)
      def year : uint16_t = struct._1
      def year_=(value: uint16_t): Unit = !struct.at1 = value
      def month : uint8_t = struct._2
      def month_=(value: uint8_t): Unit = !struct.at2 = value
      def day : uint8_t = struct._3
      def day_=(value: uint8_t): Unit = !struct.at3 = value
      def weekday : uint8_t = struct._4
      def weekday_=(value: uint8_t): Unit = !struct.at4 = value
      def hour : uint8_t = struct._5
      def hour_=(value: uint8_t): Unit = !struct.at5 = value
      def minute : uint8_t = struct._6
      def minute_=(value: uint8_t): Unit = !struct.at6 = value
      def second : uint8_t = struct._7
      def second_=(value: uint8_t): Unit = !struct.at7 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type PDMenuItem = CStruct0
  object PDMenuItem:
    given _tag: Tag[PDMenuItem] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type PDRect = CStruct4[Float, Float, Float, Float]
  object PDRect:
    given _tag: Tag[PDRect] = Tag.materializeCStruct4Tag[Float, Float, Float, Float]
    def apply()(using Zone): Ptr[PDRect] = scala.scalanative.unsafe.alloc[PDRect](1)
    def apply(x : Float, y : Float, width : Float, height : Float)(using Zone): Ptr[PDRect] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).width = width
      (!____ptr).height = height
      ____ptr
    extension (struct: PDRect)
      def x : Float = struct._1
      def x_=(value: Float): Unit = !struct.at1 = value
      def y : Float = struct._2
      def y_=(value: Float): Unit = !struct.at2 = value
      def width : Float = struct._3
      def width_=(value: Float): Unit = !struct.at3 = value
      def height : Float = struct._4
      def height_=(value: Float): Unit = !struct.at4 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type PDScore = CStruct3[uint32_t, uint32_t, Ptr[CUnsignedChar]]
  object PDScore:
    given _tag: Tag[PDScore] = Tag.materializeCStruct3Tag[uint32_t, uint32_t, Ptr[CUnsignedChar]]
    def apply()(using Zone): Ptr[PDScore] = scala.scalanative.unsafe.alloc[PDScore](1)
    def apply(rank : uint32_t, value : uint32_t, player : Ptr[CUnsignedChar])(using Zone): Ptr[PDScore] =
      val ____ptr = apply()
      (!____ptr).rank = rank
      (!____ptr).value = value
      (!____ptr).player = player
      ____ptr
    extension (struct: PDScore)
      def rank : uint32_t = struct._1
      def rank_=(value: uint32_t): Unit = !struct.at1 = value
      def value : uint32_t = struct._2
      def value_=(value: uint32_t): Unit = !struct.at2 = value
      def player : Ptr[CUnsignedChar] = struct._3
      def player_=(value: Ptr[CUnsignedChar]): Unit = !struct.at3 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type PDScoresList = CStruct6[Ptr[CUnsignedChar], CUnsignedInt, uint32_t, CInt, CUnsignedInt, Ptr[PDScore]]
  object PDScoresList:
    given _tag: Tag[PDScoresList] = Tag.materializeCStruct6Tag[Ptr[CUnsignedChar], CUnsignedInt, uint32_t, CInt, CUnsignedInt, Ptr[PDScore]]
    def apply()(using Zone): Ptr[PDScoresList] = scala.scalanative.unsafe.alloc[PDScoresList](1)
    def apply(boardID : Ptr[CUnsignedChar], count : CUnsignedInt, lastUpdated : uint32_t, playerIncluded : CInt, limit : CUnsignedInt, scores : Ptr[PDScore])(using Zone): Ptr[PDScoresList] =
      val ____ptr = apply()
      (!____ptr).boardID = boardID
      (!____ptr).count = count
      (!____ptr).lastUpdated = lastUpdated
      (!____ptr).playerIncluded = playerIncluded
      (!____ptr).limit = limit
      (!____ptr).scores = scores
      ____ptr
    extension (struct: PDScoresList)
      def boardID : Ptr[CUnsignedChar] = struct._1
      def boardID_=(value: Ptr[CUnsignedChar]): Unit = !struct.at1 = value
      def count : CUnsignedInt = struct._2
      def count_=(value: CUnsignedInt): Unit = !struct.at2 = value
      def lastUpdated : uint32_t = struct._3
      def lastUpdated_=(value: uint32_t): Unit = !struct.at3 = value
      def playerIncluded : CInt = struct._4
      def playerIncluded_=(value: CInt): Unit = !struct.at4 = value
      def limit : CUnsignedInt = struct._5
      def limit_=(value: CUnsignedInt): Unit = !struct.at5 = value
      def scores : Ptr[PDScore] = struct._6
      def scores_=(value: Ptr[PDScore]): Unit = !struct.at6 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynth = CStruct0
  object PDSynth:
    given _tag: Tag[PDSynth] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynthEnvelope = CStruct0
  object PDSynthEnvelope:
    given _tag: Tag[PDSynthEnvelope] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynthInstrument = CStruct0
  object PDSynthInstrument:
    given _tag: Tag[PDSynthInstrument] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynthLFO = CStruct0
  object PDSynthLFO:
    given _tag: Tag[PDSynthLFO] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynthSignal = CStruct0
  object PDSynthSignal:
    given _tag: Tag[PDSynthSignal] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type PDSynthSignalValue = CStruct0
  object PDSynthSignalValue:
    given _tag: Tag[PDSynthSignalValue] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api.h
  */
  opaque type PlaydateAPI = CStruct9[Ptr[playdate_sys], Ptr[playdate_file], Ptr[playdate_graphics], Ptr[playdate_sprite], Ptr[playdate_display], Ptr[playdate_sound], Ptr[playdate_lua], Ptr[Byte], Ptr[playdate_scoreboards]]
  object PlaydateAPI:
    given _tag: Tag[PlaydateAPI] = Tag.materializeCStruct9Tag[Ptr[playdate_sys], Ptr[playdate_file], Ptr[playdate_graphics], Ptr[playdate_sprite], Ptr[playdate_display], Ptr[playdate_sound], Ptr[playdate_lua], Ptr[Byte], Ptr[playdate_scoreboards]]
    def apply()(using Zone): Ptr[PlaydateAPI] = scala.scalanative.unsafe.alloc[PlaydateAPI](1)
    def apply(system : Ptr[playdate_sys], file : Ptr[playdate_file], graphics : Ptr[playdate_graphics], sprite : Ptr[playdate_sprite], display : Ptr[playdate_display], sound : Ptr[playdate_sound], lua : Ptr[playdate_lua], json : Ptr[playdate_json], scoreboards : Ptr[playdate_scoreboards])(using Zone): Ptr[PlaydateAPI] =
      val ____ptr = apply()
      (!____ptr).system = system
      (!____ptr).file = file
      (!____ptr).graphics = graphics
      (!____ptr).sprite = sprite
      (!____ptr).display = display
      (!____ptr).sound = sound
      (!____ptr).lua = lua
      (!____ptr).json = json
      (!____ptr).scoreboards = scoreboards
      ____ptr
    extension (struct: PlaydateAPI)
      def system : Ptr[playdate_sys] = struct._1
      def system_=(value: Ptr[playdate_sys]): Unit = !struct.at1 = value
      def file : Ptr[playdate_file] = struct._2
      def file_=(value: Ptr[playdate_file]): Unit = !struct.at2 = value
      def graphics : Ptr[playdate_graphics] = struct._3
      def graphics_=(value: Ptr[playdate_graphics]): Unit = !struct.at3 = value
      def sprite : Ptr[playdate_sprite] = struct._4
      def sprite_=(value: Ptr[playdate_sprite]): Unit = !struct.at4 = value
      def display : Ptr[playdate_display] = struct._5
      def display_=(value: Ptr[playdate_display]): Unit = !struct.at5 = value
      def sound : Ptr[playdate_sound] = struct._6
      def sound_=(value: Ptr[playdate_sound]): Unit = !struct.at6 = value
      def lua : Ptr[playdate_lua] = struct._7
      def lua_=(value: Ptr[playdate_lua]): Unit = !struct.at7 = value
      def json : Ptr[playdate_json] = struct._8.asInstanceOf[Ptr[playdate_json]]
      def json_=(value: Ptr[playdate_json]): Unit = !struct.at8 = value.asInstanceOf[Ptr[Byte]]
      def scoreboards : Ptr[playdate_scoreboards] = struct._9
      def scoreboards_=(value: Ptr[playdate_scoreboards]): Unit = !struct.at9 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type RingModulator = CStruct0
  object RingModulator:
    given _tag: Tag[RingModulator] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SamplePlayer = CStruct0
  object SamplePlayer:
    given _tag: Tag[SamplePlayer] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SequenceTrack = CStruct0
  object SequenceTrack:
    given _tag: Tag[SequenceTrack] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundChannel = CStruct0
  object SoundChannel:
    given _tag: Tag[SoundChannel] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundEffect = CStruct0
  object SoundEffect:
    given _tag: Tag[SoundEffect] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundSequence = CStruct0
  object SoundSequence:
    given _tag: Tag[SoundSequence] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type SoundSource = CStruct0
  object SoundSource:
    given _tag: Tag[SoundSource] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type SpriteCollisionInfo = CStruct10[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType, uint8_t, Float, CollisionPoint, CollisionVector, CollisionPoint, PDRect, PDRect]
  object SpriteCollisionInfo:
    given _tag: Tag[SpriteCollisionInfo] = Tag.materializeCStruct10Tag[Ptr[LCDSprite], Ptr[LCDSprite], SpriteCollisionResponseType, uint8_t, Float, CollisionPoint, CollisionVector, CollisionPoint, PDRect, PDRect]
    def apply()(using Zone): Ptr[SpriteCollisionInfo] = scala.scalanative.unsafe.alloc[SpriteCollisionInfo](1)
    def apply(sprite : Ptr[LCDSprite], other : Ptr[LCDSprite], responseType : SpriteCollisionResponseType, overlaps : uint8_t, ti : Float, move : CollisionPoint, normal : CollisionVector, touch : CollisionPoint, spriteRect : PDRect, otherRect : PDRect)(using Zone): Ptr[SpriteCollisionInfo] =
      val ____ptr = apply()
      (!____ptr).sprite = sprite
      (!____ptr).other = other
      (!____ptr).responseType = responseType
      (!____ptr).overlaps = overlaps
      (!____ptr).ti = ti
      (!____ptr).move = move
      (!____ptr).normal = normal
      (!____ptr).touch = touch
      (!____ptr).spriteRect = spriteRect
      (!____ptr).otherRect = otherRect
      ____ptr
    extension (struct: SpriteCollisionInfo)
      def sprite : Ptr[LCDSprite] = struct._1
      def sprite_=(value: Ptr[LCDSprite]): Unit = !struct.at1 = value
      def other : Ptr[LCDSprite] = struct._2
      def other_=(value: Ptr[LCDSprite]): Unit = !struct.at2 = value
      def responseType : SpriteCollisionResponseType = struct._3
      def responseType_=(value: SpriteCollisionResponseType): Unit = !struct.at3 = value
      def overlaps : uint8_t = struct._4
      def overlaps_=(value: uint8_t): Unit = !struct.at4 = value
      def ti : Float = struct._5
      def ti_=(value: Float): Unit = !struct.at5 = value
      def move : CollisionPoint = struct._6
      def move_=(value: CollisionPoint): Unit = !struct.at6 = value
      def normal : CollisionVector = struct._7
      def normal_=(value: CollisionVector): Unit = !struct.at7 = value
      def touch : CollisionPoint = struct._8
      def touch_=(value: CollisionPoint): Unit = !struct.at8 = value
      def spriteRect : PDRect = struct._9
      def spriteRect_=(value: PDRect): Unit = !struct.at9 = value
      def otherRect : PDRect = struct._10
      def otherRect_=(value: PDRect): Unit = !struct.at10 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type SpriteQueryInfo = CStruct5[Ptr[LCDSprite], Float, Float, CollisionPoint, CollisionPoint]
  object SpriteQueryInfo:
    given _tag: Tag[SpriteQueryInfo] = Tag.materializeCStruct5Tag[Ptr[LCDSprite], Float, Float, CollisionPoint, CollisionPoint]
    def apply()(using Zone): Ptr[SpriteQueryInfo] = scala.scalanative.unsafe.alloc[SpriteQueryInfo](1)
    def apply(sprite : Ptr[LCDSprite], ti1 : Float, ti2 : Float, entryPoint : CollisionPoint, exitPoint : CollisionPoint)(using Zone): Ptr[SpriteQueryInfo] =
      val ____ptr = apply()
      (!____ptr).sprite = sprite
      (!____ptr).ti1 = ti1
      (!____ptr).ti2 = ti2
      (!____ptr).entryPoint = entryPoint
      (!____ptr).exitPoint = exitPoint
      ____ptr
    extension (struct: SpriteQueryInfo)
      def sprite : Ptr[LCDSprite] = struct._1
      def sprite_=(value: Ptr[LCDSprite]): Unit = !struct.at1 = value
      def ti1 : Float = struct._2
      def ti1_=(value: Float): Unit = !struct.at2 = value
      def ti2 : Float = struct._3
      def ti2_=(value: Float): Unit = !struct.at3 = value
      def entryPoint : CollisionPoint = struct._4
      def entryPoint_=(value: CollisionPoint): Unit = !struct.at4 = value
      def exitPoint : CollisionPoint = struct._5
      def exitPoint_=(value: CollisionPoint): Unit = !struct.at5 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type TwoPoleFilter = CStruct0
  object TwoPoleFilter:
    given _tag: Tag[TwoPoleFilter] = Tag.materializeCStruct0Tag

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_decoder = CStruct10[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Unit], CFuncPtr2[Ptr[Byte], Ptr[CUnsignedChar], CInt], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value, Unit], CFuncPtr2[Ptr[Byte], CInt, CInt], CFuncPtr3[Ptr[Byte], CInt, json_value, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]], Ptr[Byte], CInt, Ptr[CUnsignedChar]]
  object json_decoder:
    given _tag: Tag[json_decoder] = Tag.materializeCStruct10Tag[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Unit], CFuncPtr2[Ptr[Byte], Ptr[CUnsignedChar], CInt], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value, Unit], CFuncPtr2[Ptr[Byte], CInt, CInt], CFuncPtr3[Ptr[Byte], CInt, json_value, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]], Ptr[Byte], CInt, Ptr[CUnsignedChar]]
    def apply()(using Zone): Ptr[json_decoder] = scala.scalanative.unsafe.alloc[json_decoder](1)
    def apply(decodeError : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], CInt, Unit], willDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit], shouldDecodeTableValueForKey : CFuncPtr2[Ptr[json_decoder], Ptr[CUnsignedChar], CInt], didDecodeTableValue : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value, Unit], shouldDecodeArrayValueAtIndex : CFuncPtr2[Ptr[json_decoder], CInt, CInt], didDecodeArrayValue : CFuncPtr3[Ptr[json_decoder], CInt, json_value, Unit], didDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]], userdata : Ptr[Byte], returnString : CInt, path : Ptr[CUnsignedChar])(using Zone): Ptr[json_decoder] =
      val ____ptr = apply()
      (!____ptr).decodeError = decodeError
      (!____ptr).willDecodeSublist = willDecodeSublist
      (!____ptr).shouldDecodeTableValueForKey = shouldDecodeTableValueForKey
      (!____ptr).didDecodeTableValue = didDecodeTableValue
      (!____ptr).shouldDecodeArrayValueAtIndex = shouldDecodeArrayValueAtIndex
      (!____ptr).didDecodeArrayValue = didDecodeArrayValue
      (!____ptr).didDecodeSublist = didDecodeSublist
      (!____ptr).userdata = userdata
      (!____ptr).returnString = returnString
      (!____ptr).path = path
      ____ptr
    extension (struct: json_decoder)
      def decodeError : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], CInt, Unit] = struct._1.asInstanceOf[CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], CInt, Unit]]
      def decodeError_=(value: CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], CInt, Unit]): Unit = !struct.at1 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]]
      def willDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit] = struct._2.asInstanceOf[CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit]]
      def willDecodeSublist_=(value: CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit]): Unit = !struct.at2 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Unit]]
      def shouldDecodeTableValueForKey : CFuncPtr2[Ptr[json_decoder], Ptr[CUnsignedChar], CInt] = struct._3.asInstanceOf[CFuncPtr2[Ptr[json_decoder], Ptr[CUnsignedChar], CInt]]
      def shouldDecodeTableValueForKey_=(value: CFuncPtr2[Ptr[json_decoder], Ptr[CUnsignedChar], CInt]): Unit = !struct.at3 = value.asInstanceOf[CFuncPtr2[Ptr[Byte], Ptr[CUnsignedChar], CInt]]
      def didDecodeTableValue : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value, Unit] = struct._4.asInstanceOf[CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value, Unit]]
      def didDecodeTableValue_=(value: CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value, Unit]): Unit = !struct.at4 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value, Unit]]
      def shouldDecodeArrayValueAtIndex : CFuncPtr2[Ptr[json_decoder], CInt, CInt] = struct._5.asInstanceOf[CFuncPtr2[Ptr[json_decoder], CInt, CInt]]
      def shouldDecodeArrayValueAtIndex_=(value: CFuncPtr2[Ptr[json_decoder], CInt, CInt]): Unit = !struct.at5 = value.asInstanceOf[CFuncPtr2[Ptr[Byte], CInt, CInt]]
      def didDecodeArrayValue : CFuncPtr3[Ptr[json_decoder], CInt, json_value, Unit] = struct._6.asInstanceOf[CFuncPtr3[Ptr[json_decoder], CInt, json_value, Unit]]
      def didDecodeArrayValue_=(value: CFuncPtr3[Ptr[json_decoder], CInt, json_value, Unit]): Unit = !struct.at6 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], CInt, json_value, Unit]]
      def didDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]] = struct._7.asInstanceOf[CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]]]
      def didDecodeSublist_=(value: CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]]): Unit = !struct.at7 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]]]
      def userdata : Ptr[Byte] = struct._8
      def userdata_=(value: Ptr[Byte]): Unit = !struct.at8 = value
      def returnString : CInt = struct._9
      def returnString_=(value: CInt): Unit = !struct.at9 = value
      def path : Ptr[CUnsignedChar] = struct._10
      def path_=(value: Ptr[CUnsignedChar]): Unit = !struct.at10 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_encoder = CStruct18[Ptr[writeFunc], Ptr[Byte], CInt, CInt, CInt, CInt, CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr2[Ptr[Byte], CInt, Unit], CFuncPtr2[Ptr[Byte], Double, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]]
  object json_encoder:
    given _tag: Tag[json_encoder] = Tag.materializeCStruct18Tag[Ptr[writeFunc], Ptr[Byte], CInt, CInt, CInt, CInt, CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr1[Ptr[Byte], Unit], CFuncPtr2[Ptr[Byte], CInt, Unit], CFuncPtr2[Ptr[Byte], Double, Unit], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]]
    def apply()(using Zone): Ptr[json_encoder] = scala.scalanative.unsafe.alloc[json_encoder](1)
    def apply(writeStringFunc : Ptr[writeFunc], userdata : Ptr[Byte], pretty : CInt, startedTable : CInt, startedArray : CInt, depth : CInt, startArray : CFuncPtr1[Ptr[json_encoder], Unit], addArrayMember : CFuncPtr1[Ptr[json_encoder], Unit], endArray : CFuncPtr1[Ptr[json_encoder], Unit], startTable : CFuncPtr1[Ptr[json_encoder], Unit], addTableMember : CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit], endTable : CFuncPtr1[Ptr[json_encoder], Unit], writeNull : CFuncPtr1[Ptr[json_encoder], Unit], writeFalse : CFuncPtr1[Ptr[json_encoder], Unit], writeTrue : CFuncPtr1[Ptr[json_encoder], Unit], writeInt : CFuncPtr2[Ptr[json_encoder], CInt, Unit], writeDouble : CFuncPtr2[Ptr[json_encoder], Double, Unit], writeString : CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit])(using Zone): Ptr[json_encoder] =
      val ____ptr = apply()
      (!____ptr).writeStringFunc = writeStringFunc
      (!____ptr).userdata = userdata
      (!____ptr).pretty = pretty
      (!____ptr).startedTable = startedTable
      (!____ptr).startedArray = startedArray
      (!____ptr).depth = depth
      (!____ptr).startArray = startArray
      (!____ptr).addArrayMember = addArrayMember
      (!____ptr).endArray = endArray
      (!____ptr).startTable = startTable
      (!____ptr).addTableMember = addTableMember
      (!____ptr).endTable = endTable
      (!____ptr).writeNull = writeNull
      (!____ptr).writeFalse = writeFalse
      (!____ptr).writeTrue = writeTrue
      (!____ptr).writeInt = writeInt
      (!____ptr).writeDouble = writeDouble
      (!____ptr).writeString = writeString
      ____ptr
    extension (struct: json_encoder)
      def writeStringFunc : Ptr[writeFunc] = struct._1
      def writeStringFunc_=(value: Ptr[writeFunc]): Unit = !struct.at1 = value
      def userdata : Ptr[Byte] = struct._2
      def userdata_=(value: Ptr[Byte]): Unit = !struct.at2 = value
      def pretty : CInt = struct._3
      def pretty_=(value: CInt): Unit = !struct.at3 = value
      def startedTable : CInt = struct._4
      def startedTable_=(value: CInt): Unit = !struct.at4 = value
      def startedArray : CInt = struct._5
      def startedArray_=(value: CInt): Unit = !struct.at5 = value
      def depth : CInt = struct._6
      def depth_=(value: CInt): Unit = !struct.at6 = value
      def startArray : CFuncPtr1[Ptr[json_encoder], Unit] = struct._7.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def startArray_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at7 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def addArrayMember : CFuncPtr1[Ptr[json_encoder], Unit] = struct._8.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def addArrayMember_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at8 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def endArray : CFuncPtr1[Ptr[json_encoder], Unit] = struct._9.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def endArray_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at9 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def startTable : CFuncPtr1[Ptr[json_encoder], Unit] = struct._10.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def startTable_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at10 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def addTableMember : CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit] = struct._11.asInstanceOf[CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit]]
      def addTableMember_=(value: CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit]): Unit = !struct.at11 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]]
      def endTable : CFuncPtr1[Ptr[json_encoder], Unit] = struct._12.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def endTable_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at12 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def writeNull : CFuncPtr1[Ptr[json_encoder], Unit] = struct._13.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def writeNull_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at13 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def writeFalse : CFuncPtr1[Ptr[json_encoder], Unit] = struct._14.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def writeFalse_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at14 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def writeTrue : CFuncPtr1[Ptr[json_encoder], Unit] = struct._15.asInstanceOf[CFuncPtr1[Ptr[json_encoder], Unit]]
      def writeTrue_=(value: CFuncPtr1[Ptr[json_encoder], Unit]): Unit = !struct.at15 = value.asInstanceOf[CFuncPtr1[Ptr[Byte], Unit]]
      def writeInt : CFuncPtr2[Ptr[json_encoder], CInt, Unit] = struct._16.asInstanceOf[CFuncPtr2[Ptr[json_encoder], CInt, Unit]]
      def writeInt_=(value: CFuncPtr2[Ptr[json_encoder], CInt, Unit]): Unit = !struct.at16 = value.asInstanceOf[CFuncPtr2[Ptr[Byte], CInt, Unit]]
      def writeDouble : CFuncPtr2[Ptr[json_encoder], Double, Unit] = struct._17.asInstanceOf[CFuncPtr2[Ptr[json_encoder], Double, Unit]]
      def writeDouble_=(value: CFuncPtr2[Ptr[json_encoder], Double, Unit]): Unit = !struct.at17 = value.asInstanceOf[CFuncPtr2[Ptr[Byte], Double, Unit]]
      def writeString : CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit] = struct._18.asInstanceOf[CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit]]
      def writeString_=(value: CFuncPtr3[Ptr[json_encoder], Ptr[CUnsignedChar], CInt, Unit]): Unit = !struct.at18 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Unit]]

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_reader = CStruct2[CFuncPtr3[Ptr[Byte], Ptr[uint8_t], CInt, CInt], Ptr[Byte]]
  object json_reader:
    given _tag: Tag[json_reader] = Tag.materializeCStruct2Tag[CFuncPtr3[Ptr[Byte], Ptr[uint8_t], CInt, CInt], Ptr[Byte]]
    def apply()(using Zone): Ptr[json_reader] = scala.scalanative.unsafe.alloc[json_reader](1)
    def apply(read : CFuncPtr3[Ptr[Byte], Ptr[uint8_t], CInt, CInt], userdata : Ptr[Byte])(using Zone): Ptr[json_reader] =
      val ____ptr = apply()
      (!____ptr).read = read
      (!____ptr).userdata = userdata
      ____ptr
    extension (struct: json_reader)
      def read : CFuncPtr3[Ptr[Byte], Ptr[uint8_t], CInt, CInt] = struct._1
      def read_=(value: CFuncPtr3[Ptr[Byte], Ptr[uint8_t], CInt, CInt]): Unit = !struct.at1 = value
      def userdata : Ptr[Byte] = struct._2
      def userdata_=(value: Ptr[Byte]): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_value = CStruct2[CUnsignedChar, json_value.Union0]
  object json_value:
    /**
     * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
    */
    opaque type Union0 = CArray[Byte, Nat._4]
    object Union0:
      given _tag: Tag[Union0] = Tag.CArray[CChar, Nat._4](Tag.Byte, Tag.Nat4)
      def apply()(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        ___ptr
      @scala.annotation.targetName("apply_intval")
      def apply(intval: CInt)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[CInt]].update(0, intval)
        ___ptr
      @scala.annotation.targetName("apply_floatval")
      def apply(floatval: Float)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Float]].update(0, floatval)
        ___ptr
      @scala.annotation.targetName("apply_stringval")
      def apply(stringval: Ptr[CUnsignedChar])(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]].update(0, stringval)
        ___ptr
      @scala.annotation.targetName("apply_arrayval")
      def apply(arrayval: Ptr[Byte])(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Ptr[Byte]]].update(0, arrayval)
        ___ptr
      @scala.annotation.targetName("apply_tableval")
      def apply(tableval: Ptr[Byte])(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Ptr[Byte]]].update(0, tableval)
        ___ptr
      extension (struct: Union0)
        def intval : CInt = !struct.at(0).asInstanceOf[Ptr[CInt]]
        def intval_=(value: CInt): Unit = !struct.at(0).asInstanceOf[Ptr[CInt]] = value
        def floatval : Float = !struct.at(0).asInstanceOf[Ptr[Float]]
        def floatval_=(value: Float): Unit = !struct.at(0).asInstanceOf[Ptr[Float]] = value
        def stringval : Ptr[CUnsignedChar] = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]]
        def stringval_=(value: Ptr[CUnsignedChar]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]] = value
        def arrayval : Ptr[Byte] = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]]
        def arrayval_=(value: Ptr[Byte]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]] = value
        def tableval : Ptr[Byte] = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]]
        def tableval_=(value: Ptr[Byte]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]] = value
    given _tag: Tag[json_value] = Tag.materializeCStruct2Tag[CUnsignedChar, json_value.Union0]
    def apply()(using Zone): Ptr[json_value] = scala.scalanative.unsafe.alloc[json_value](1)
    def apply(`type` : CUnsignedChar, data : json_value.Union0)(using Zone): Ptr[json_value] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).data = data
      ____ptr
    extension (struct: json_value)
      def `type` : CUnsignedChar = struct._1
      def type_=(value: CUnsignedChar): Unit = !struct.at1 = value
      def data : json_value.Union0 = struct._2
      def data_=(value: json_value.Union0): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type lua_reg = CStruct2[Ptr[CUnsignedChar], lua_CFunction]
  object lua_reg:
    given _tag: Tag[lua_reg] = Tag.materializeCStruct2Tag[Ptr[CUnsignedChar], lua_CFunction]
    def apply()(using Zone): Ptr[lua_reg] = scala.scalanative.unsafe.alloc[lua_reg](1)
    def apply(name : Ptr[CUnsignedChar], func : lua_CFunction)(using Zone): Ptr[lua_reg] =
      val ____ptr = apply()
      (!____ptr).name = name
      (!____ptr).func = func
      ____ptr
    extension (struct: lua_reg)
      def name : Ptr[CUnsignedChar] = struct._1
      def name_=(value: Ptr[CUnsignedChar]): Unit = !struct.at1 = value
      def func : lua_CFunction = struct._2
      def func_=(value: lua_CFunction): Unit = !struct.at2 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type lua_val = CStruct3[Ptr[CUnsignedChar], l_valtype, lua_val.Union0]
  object lua_val:
    /**
     * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
    */
    opaque type Union0 = CArray[Byte, Nat._4]
    object Union0:
      given _tag: Tag[Union0] = Tag.CArray[CChar, Nat._4](Tag.Byte, Tag.Nat4)
      def apply()(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        ___ptr
      @scala.annotation.targetName("apply_intval")
      def apply(intval: CUnsignedInt)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[CUnsignedInt]].update(0, intval)
        ___ptr
      @scala.annotation.targetName("apply_floatval")
      def apply(floatval: Float)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Float]].update(0, floatval)
        ___ptr
      @scala.annotation.targetName("apply_strval")
      def apply(strval: Ptr[CUnsignedChar])(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un = !___ptr
        un.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]].update(0, strval)
        ___ptr
      extension (struct: Union0)
        def intval : CUnsignedInt = !struct.at(0).asInstanceOf[Ptr[CUnsignedInt]]
        def intval_=(value: CUnsignedInt): Unit = !struct.at(0).asInstanceOf[Ptr[CUnsignedInt]] = value
        def floatval : Float = !struct.at(0).asInstanceOf[Ptr[Float]]
        def floatval_=(value: Float): Unit = !struct.at(0).asInstanceOf[Ptr[Float]] = value
        def strval : Ptr[CUnsignedChar] = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]]
        def strval_=(value: Ptr[CUnsignedChar]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]] = value
    given _tag: Tag[lua_val] = Tag.materializeCStruct3Tag[Ptr[CUnsignedChar], l_valtype, lua_val.Union0]
    def apply()(using Zone): Ptr[lua_val] = scala.scalanative.unsafe.alloc[lua_val](1)
    def apply(name : Ptr[CUnsignedChar], `type` : l_valtype, v : lua_val.Union0)(using Zone): Ptr[lua_val] =
      val ____ptr = apply()
      (!____ptr).name = name
      (!____ptr).`type` = `type`
      (!____ptr).v = v
      ____ptr
    extension (struct: lua_val)
      def name : Ptr[CUnsignedChar] = struct._1
      def name_=(value: Ptr[CUnsignedChar]): Unit = !struct.at1 = value
      def `type` : l_valtype = struct._2
      def type_=(value: l_valtype): Unit = !struct.at2 = value
      def v : lua_val.Union0 = struct._3
      def v_=(value: lua_val.Union0): Unit = !struct.at3 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_control_signal = CStruct6[CFuncPtr0[Ptr[ControlSignal]], CFuncPtr1[Ptr[ControlSignal], Unit], CFuncPtr1[Ptr[ControlSignal], Unit], CFuncPtr4[Ptr[ControlSignal], CInt, Float, CInt, Unit], CFuncPtr2[Ptr[ControlSignal], CInt, Unit], CFuncPtr1[Ptr[ControlSignal], CInt]]
  object playdate_control_signal:
    given _tag: Tag[playdate_control_signal] = Tag.materializeCStruct6Tag[CFuncPtr0[Ptr[ControlSignal]], CFuncPtr1[Ptr[ControlSignal], Unit], CFuncPtr1[Ptr[ControlSignal], Unit], CFuncPtr4[Ptr[ControlSignal], CInt, Float, CInt, Unit], CFuncPtr2[Ptr[ControlSignal], CInt, Unit], CFuncPtr1[Ptr[ControlSignal], CInt]]
    def apply()(using Zone): Ptr[playdate_control_signal] = scala.scalanative.unsafe.alloc[playdate_control_signal](1)
    def apply(newSignal : CFuncPtr0[Ptr[ControlSignal]], freeSignal : CFuncPtr1[Ptr[ControlSignal], Unit], clearEvents : CFuncPtr1[Ptr[ControlSignal], Unit], addEvent : CFuncPtr4[Ptr[ControlSignal], CInt, Float, CInt, Unit], removeEvent : CFuncPtr2[Ptr[ControlSignal], CInt, Unit], getMIDIControllerNumber : CFuncPtr1[Ptr[ControlSignal], CInt])(using Zone): Ptr[playdate_control_signal] =
      val ____ptr = apply()
      (!____ptr).newSignal = newSignal
      (!____ptr).freeSignal = freeSignal
      (!____ptr).clearEvents = clearEvents
      (!____ptr).addEvent = addEvent
      (!____ptr).removeEvent = removeEvent
      (!____ptr).getMIDIControllerNumber = getMIDIControllerNumber
      ____ptr
    extension (struct: playdate_control_signal)
      def newSignal : CFuncPtr0[Ptr[ControlSignal]] = struct._1
      def newSignal_=(value: CFuncPtr0[Ptr[ControlSignal]]): Unit = !struct.at1 = value
      def freeSignal : CFuncPtr1[Ptr[ControlSignal], Unit] = struct._2
      def freeSignal_=(value: CFuncPtr1[Ptr[ControlSignal], Unit]): Unit = !struct.at2 = value
      def clearEvents : CFuncPtr1[Ptr[ControlSignal], Unit] = struct._3
      def clearEvents_=(value: CFuncPtr1[Ptr[ControlSignal], Unit]): Unit = !struct.at3 = value
      def addEvent : CFuncPtr4[Ptr[ControlSignal], CInt, Float, CInt, Unit] = struct._4
      def addEvent_=(value: CFuncPtr4[Ptr[ControlSignal], CInt, Float, CInt, Unit]): Unit = !struct.at4 = value
      def removeEvent : CFuncPtr2[Ptr[ControlSignal], CInt, Unit] = struct._5
      def removeEvent_=(value: CFuncPtr2[Ptr[ControlSignal], CInt, Unit]): Unit = !struct.at5 = value
      def getMIDIControllerNumber : CFuncPtr1[Ptr[ControlSignal], CInt] = struct._6
      def getMIDIControllerNumber_=(value: CFuncPtr1[Ptr[ControlSignal], CInt]): Unit = !struct.at6 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_display.h
  */
  opaque type playdate_display = CStruct8[CFuncPtr0[CInt], CFuncPtr0[CInt], CFuncPtr1[Float, Unit], CFuncPtr1[CInt, Unit], CFuncPtr1[CUnsignedInt, Unit], CFuncPtr2[CUnsignedInt, CUnsignedInt, Unit], CFuncPtr2[CInt, CInt, Unit], CFuncPtr2[CInt, CInt, Unit]]
  object playdate_display:
    given _tag: Tag[playdate_display] = Tag.materializeCStruct8Tag[CFuncPtr0[CInt], CFuncPtr0[CInt], CFuncPtr1[Float, Unit], CFuncPtr1[CInt, Unit], CFuncPtr1[CUnsignedInt, Unit], CFuncPtr2[CUnsignedInt, CUnsignedInt, Unit], CFuncPtr2[CInt, CInt, Unit], CFuncPtr2[CInt, CInt, Unit]]
    def apply()(using Zone): Ptr[playdate_display] = scala.scalanative.unsafe.alloc[playdate_display](1)
    def apply(getWidth : CFuncPtr0[CInt], getHeight : CFuncPtr0[CInt], setRefreshRate : CFuncPtr1[Float, Unit], setInverted : CFuncPtr1[CInt, Unit], setScale : CFuncPtr1[CUnsignedInt, Unit], setMosaic : CFuncPtr2[CUnsignedInt, CUnsignedInt, Unit], setFlipped : CFuncPtr2[CInt, CInt, Unit], setOffset : CFuncPtr2[CInt, CInt, Unit])(using Zone): Ptr[playdate_display] =
      val ____ptr = apply()
      (!____ptr).getWidth = getWidth
      (!____ptr).getHeight = getHeight
      (!____ptr).setRefreshRate = setRefreshRate
      (!____ptr).setInverted = setInverted
      (!____ptr).setScale = setScale
      (!____ptr).setMosaic = setMosaic
      (!____ptr).setFlipped = setFlipped
      (!____ptr).setOffset = setOffset
      ____ptr
    extension (struct: playdate_display)
      def getWidth : CFuncPtr0[CInt] = struct._1
      def getWidth_=(value: CFuncPtr0[CInt]): Unit = !struct.at1 = value
      def getHeight : CFuncPtr0[CInt] = struct._2
      def getHeight_=(value: CFuncPtr0[CInt]): Unit = !struct.at2 = value
      def setRefreshRate : CFuncPtr1[Float, Unit] = struct._3
      def setRefreshRate_=(value: CFuncPtr1[Float, Unit]): Unit = !struct.at3 = value
      def setInverted : CFuncPtr1[CInt, Unit] = struct._4
      def setInverted_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at4 = value
      def setScale : CFuncPtr1[CUnsignedInt, Unit] = struct._5
      def setScale_=(value: CFuncPtr1[CUnsignedInt, Unit]): Unit = !struct.at5 = value
      def setMosaic : CFuncPtr2[CUnsignedInt, CUnsignedInt, Unit] = struct._6
      def setMosaic_=(value: CFuncPtr2[CUnsignedInt, CUnsignedInt, Unit]): Unit = !struct.at6 = value
      def setFlipped : CFuncPtr2[CInt, CInt, Unit] = struct._7
      def setFlipped_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at7 = value
      def setOffset : CFuncPtr2[CInt, CInt, Unit] = struct._8
      def setOffset_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at8 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_file.h
  */
  opaque type playdate_file = CStruct13[CFuncPtr0[Ptr[CUnsignedChar]], CFuncPtr4[Ptr[CUnsignedChar], CFuncPtr2[Ptr[CUnsignedChar], Ptr[Byte], Unit], Ptr[Byte], CInt, CInt], CFuncPtr2[Ptr[CUnsignedChar], Ptr[FileStat], CInt], CFuncPtr1[Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[CUnsignedChar], CInt, CInt], CFuncPtr2[Ptr[CUnsignedChar], Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[CUnsignedChar], FileOptions, Ptr[SDFile]], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr3[Ptr[SDFile], CInt, CInt, CInt]]
  object playdate_file:
    given _tag: Tag[playdate_file] = Tag.materializeCStruct13Tag[CFuncPtr0[Ptr[CUnsignedChar]], CFuncPtr4[Ptr[CUnsignedChar], CFuncPtr2[Ptr[CUnsignedChar], Ptr[Byte], Unit], Ptr[Byte], CInt, CInt], CFuncPtr2[Ptr[CUnsignedChar], Ptr[FileStat], CInt], CFuncPtr1[Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[CUnsignedChar], CInt, CInt], CFuncPtr2[Ptr[CUnsignedChar], Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[CUnsignedChar], FileOptions, Ptr[SDFile]], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr1[Ptr[SDFile], CInt], CFuncPtr3[Ptr[SDFile], CInt, CInt, CInt]]
    def apply()(using Zone): Ptr[playdate_file] = scala.scalanative.unsafe.alloc[playdate_file](1)
    def apply(geterr : CFuncPtr0[Ptr[CUnsignedChar]], listfiles : CFuncPtr4[Ptr[CUnsignedChar], CFuncPtr2[Ptr[CUnsignedChar], Ptr[Byte], Unit], Ptr[Byte], CInt, CInt], stat : CFuncPtr2[Ptr[CUnsignedChar], Ptr[FileStat], CInt], mkdir : CFuncPtr1[Ptr[CUnsignedChar], CInt], unlink : CFuncPtr2[Ptr[CUnsignedChar], CInt, CInt], rename : CFuncPtr2[Ptr[CUnsignedChar], Ptr[CUnsignedChar], CInt], open : CFuncPtr2[Ptr[CUnsignedChar], FileOptions, Ptr[SDFile]], close : CFuncPtr1[Ptr[SDFile], CInt], read : CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], write : CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt], flush : CFuncPtr1[Ptr[SDFile], CInt], tell : CFuncPtr1[Ptr[SDFile], CInt], seek : CFuncPtr3[Ptr[SDFile], CInt, CInt, CInt])(using Zone): Ptr[playdate_file] =
      val ____ptr = apply()
      (!____ptr).geterr = geterr
      (!____ptr).listfiles = listfiles
      (!____ptr).stat = stat
      (!____ptr).mkdir = mkdir
      (!____ptr).unlink = unlink
      (!____ptr).rename = rename
      (!____ptr).open = open
      (!____ptr).close = close
      (!____ptr).read = read
      (!____ptr).write = write
      (!____ptr).flush = flush
      (!____ptr).tell = tell
      (!____ptr).seek = seek
      ____ptr
    extension (struct: playdate_file)
      def geterr : CFuncPtr0[Ptr[CUnsignedChar]] = struct._1
      def geterr_=(value: CFuncPtr0[Ptr[CUnsignedChar]]): Unit = !struct.at1 = value
      def listfiles : CFuncPtr4[Ptr[CUnsignedChar], CFuncPtr2[Ptr[CUnsignedChar], Ptr[Byte], Unit], Ptr[Byte], CInt, CInt] = struct._2
      def listfiles_=(value: CFuncPtr4[Ptr[CUnsignedChar], CFuncPtr2[Ptr[CUnsignedChar], Ptr[Byte], Unit], Ptr[Byte], CInt, CInt]): Unit = !struct.at2 = value
      def stat : CFuncPtr2[Ptr[CUnsignedChar], Ptr[FileStat], CInt] = struct._3
      def stat_=(value: CFuncPtr2[Ptr[CUnsignedChar], Ptr[FileStat], CInt]): Unit = !struct.at3 = value
      def mkdir : CFuncPtr1[Ptr[CUnsignedChar], CInt] = struct._4
      def mkdir_=(value: CFuncPtr1[Ptr[CUnsignedChar], CInt]): Unit = !struct.at4 = value
      def unlink : CFuncPtr2[Ptr[CUnsignedChar], CInt, CInt] = struct._5
      def unlink_=(value: CFuncPtr2[Ptr[CUnsignedChar], CInt, CInt]): Unit = !struct.at5 = value
      def rename : CFuncPtr2[Ptr[CUnsignedChar], Ptr[CUnsignedChar], CInt] = struct._6
      def rename_=(value: CFuncPtr2[Ptr[CUnsignedChar], Ptr[CUnsignedChar], CInt]): Unit = !struct.at6 = value
      def open : CFuncPtr2[Ptr[CUnsignedChar], FileOptions, Ptr[SDFile]] = struct._7
      def open_=(value: CFuncPtr2[Ptr[CUnsignedChar], FileOptions, Ptr[SDFile]]): Unit = !struct.at7 = value
      def close : CFuncPtr1[Ptr[SDFile], CInt] = struct._8
      def close_=(value: CFuncPtr1[Ptr[SDFile], CInt]): Unit = !struct.at8 = value
      def read : CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt] = struct._9
      def read_=(value: CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt]): Unit = !struct.at9 = value
      def write : CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt] = struct._10
      def write_=(value: CFuncPtr3[Ptr[SDFile], Ptr[Byte], CUnsignedInt, CInt]): Unit = !struct.at10 = value
      def flush : CFuncPtr1[Ptr[SDFile], CInt] = struct._11
      def flush_=(value: CFuncPtr1[Ptr[SDFile], CInt]): Unit = !struct.at11 = value
      def tell : CFuncPtr1[Ptr[SDFile], CInt] = struct._12
      def tell_=(value: CFuncPtr1[Ptr[SDFile], CInt]): Unit = !struct.at12 = value
      def seek : CFuncPtr3[Ptr[SDFile], CInt, CInt, CInt] = struct._13
      def seek_=(value: CFuncPtr3[Ptr[SDFile], CInt, CInt, CInt]): Unit = !struct.at13 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type playdate_graphics = CArray[CChar, Nat.Digit3[Nat._2, Nat._4, Nat._0]]
  object playdate_graphics:
    given _tag: Tag[playdate_graphics] = Tag.CArray[CChar, Nat.Digit3[Nat._2, Nat._4, Nat._0]](Tag.Byte, Tag.Digit3[Nat._2, Nat._4, Nat._0](Tag.Nat2, Tag.Nat4, Tag.Nat0))
    def apply()(using Zone): Ptr[playdate_graphics] = scala.scalanative.unsafe.alloc[playdate_graphics](1)
    def apply(video : Ptr[playdate_video], clear : CFuncPtr1[LCDColor, Unit], setBackgroundColor : CFuncPtr1[LCDSolidColor, Unit], setStencil : CFuncPtr1[Ptr[LCDBitmap], Unit], setDrawMode : CFuncPtr1[LCDBitmapDrawMode, Unit], setDrawOffset : CFuncPtr2[CInt, CInt, Unit], setClipRect : CFuncPtr4[CInt, CInt, CInt, CInt, Unit], clearClipRect : CFuncPtr0[Unit], setLineCapStyle : CFuncPtr1[LCDLineCapStyle, Unit], setFont : CFuncPtr1[Ptr[LCDFont], Unit], setTextTracking : CFuncPtr1[CInt, Unit], pushContext : CFuncPtr1[Ptr[LCDBitmap], Unit], popContext : CFuncPtr0[Unit], drawBitmap : CFuncPtr4[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Unit], tileBitmap : CFuncPtr6[Ptr[LCDBitmap], CInt, CInt, CInt, CInt, LCDBitmapFlip, Unit], drawLine : CFuncPtr6[CInt, CInt, CInt, CInt, CInt, LCDColor, Unit], fillTriangle : CFuncPtr7[CInt, CInt, CInt, CInt, CInt, CInt, LCDColor, Unit], drawRect : CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit], fillRect : CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit], drawEllipse : CFuncPtr8[CInt, CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit], fillEllipse : CFuncPtr7[CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit], drawScaledBitmap : CFuncPtr5[Ptr[LCDBitmap], CInt, CInt, Float, Float, Unit], drawText : CFuncPtr5[Ptr[Byte], size_t, PDStringEncoding, CInt, CInt, CInt], newBitmap : CFuncPtr3[CInt, CInt, LCDColor, Ptr[LCDBitmap]], freeBitmap : CFuncPtr1[Ptr[LCDBitmap], Unit], loadBitmap : CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmap]], copyBitmap : CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]], loadIntoBitmap : CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmap], Ptr[Ptr[CUnsignedChar]], Unit], getBitmapData : CFuncPtr6[Ptr[LCDBitmap], Ptr[CInt], Ptr[CInt], Ptr[CInt], Ptr[Ptr[uint8_t]], Ptr[Ptr[uint8_t]], Unit], clearBitmap : CFuncPtr2[Ptr[LCDBitmap], LCDColor, Unit], rotatedBitmap : CFuncPtr5[Ptr[LCDBitmap], Float, Float, Float, Ptr[CInt], Ptr[LCDBitmap]], newBitmapTable : CFuncPtr3[CInt, CInt, CInt, Ptr[LCDBitmapTable]], freeBitmapTable : CFuncPtr1[Ptr[LCDBitmapTable], Unit], loadBitmapTable : CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmapTable]], loadIntoBitmapTable : CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmapTable], Ptr[Ptr[CUnsignedChar]], Unit], getTableBitmap : CFuncPtr2[Ptr[LCDBitmapTable], CInt, Ptr[LCDBitmap]], loadFont : CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDFont]], getFontPage : CFuncPtr2[Ptr[LCDFont], uint32_t, Ptr[LCDFontPage]], getPageGlyph : CFuncPtr4[Ptr[LCDFontPage], uint32_t, Ptr[Ptr[LCDBitmap]], Ptr[CInt], Ptr[LCDFontGlyph]], getGlyphKerning : CFuncPtr3[Ptr[LCDFontGlyph], uint32_t, uint32_t, CInt], getTextWidth : CFuncPtr5[Ptr[LCDFont], Ptr[Byte], size_t, PDStringEncoding, CInt, CInt], getFrame : CFuncPtr0[Ptr[uint8_t]], getDisplayFrame : CFuncPtr0[Ptr[uint8_t]], getDebugBitmap : CFuncPtr0[Ptr[LCDBitmap]], copyFrameBufferBitmap : CFuncPtr0[Ptr[LCDBitmap]], markUpdatedRows : CFuncPtr2[CInt, CInt, Unit], display : CFuncPtr0[Unit], setColorToPattern : CFuncPtr4[Ptr[LCDColor], Ptr[LCDBitmap], CInt, CInt, Unit], checkMaskCollision : CFuncPtr9[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, LCDRect, CInt], setScreenClipRect : CFuncPtr4[CInt, CInt, CInt, CInt, Unit], fillPolygon : CFuncPtr4[CInt, Ptr[CInt], LCDColor, LCDPolygonFillRule, Unit], getFontHeight : CFuncPtr1[Ptr[LCDFont], uint8_t], getDisplayBufferBitmap : CFuncPtr0[Ptr[LCDBitmap]], drawRotatedBitmap : CFuncPtr8[Ptr[LCDBitmap], CInt, CInt, Float, Float, Float, Float, Float, Unit], setTextLeading : CFuncPtr1[CInt, Unit], setBitmapMask : CFuncPtr2[Ptr[LCDBitmap], Ptr[LCDBitmap], CInt], getBitmapMask : CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]], setStencilImage : CFuncPtr2[Ptr[LCDBitmap], CInt, Unit], makeFontFromData : CFuncPtr2[Ptr[LCDFontData], CInt, Ptr[LCDFont]], getTextTracking : CFuncPtr0[CInt])(using Zone): Ptr[playdate_graphics] =
      val ____ptr = apply()
      (!____ptr).video = video
      (!____ptr).clear = clear
      (!____ptr).setBackgroundColor = setBackgroundColor
      (!____ptr).setStencil = setStencil
      (!____ptr).setDrawMode = setDrawMode
      (!____ptr).setDrawOffset = setDrawOffset
      (!____ptr).setClipRect = setClipRect
      (!____ptr).clearClipRect = clearClipRect
      (!____ptr).setLineCapStyle = setLineCapStyle
      (!____ptr).setFont = setFont
      (!____ptr).setTextTracking = setTextTracking
      (!____ptr).pushContext = pushContext
      (!____ptr).popContext = popContext
      (!____ptr).drawBitmap = drawBitmap
      (!____ptr).tileBitmap = tileBitmap
      (!____ptr).drawLine = drawLine
      (!____ptr).fillTriangle = fillTriangle
      (!____ptr).drawRect = drawRect
      (!____ptr).fillRect = fillRect
      (!____ptr).drawEllipse = drawEllipse
      (!____ptr).fillEllipse = fillEllipse
      (!____ptr).drawScaledBitmap = drawScaledBitmap
      (!____ptr).drawText = drawText
      (!____ptr).newBitmap = newBitmap
      (!____ptr).freeBitmap = freeBitmap
      (!____ptr).loadBitmap = loadBitmap
      (!____ptr).copyBitmap = copyBitmap
      (!____ptr).loadIntoBitmap = loadIntoBitmap
      (!____ptr).getBitmapData = getBitmapData
      (!____ptr).clearBitmap = clearBitmap
      (!____ptr).rotatedBitmap = rotatedBitmap
      (!____ptr).newBitmapTable = newBitmapTable
      (!____ptr).freeBitmapTable = freeBitmapTable
      (!____ptr).loadBitmapTable = loadBitmapTable
      (!____ptr).loadIntoBitmapTable = loadIntoBitmapTable
      (!____ptr).getTableBitmap = getTableBitmap
      (!____ptr).loadFont = loadFont
      (!____ptr).getFontPage = getFontPage
      (!____ptr).getPageGlyph = getPageGlyph
      (!____ptr).getGlyphKerning = getGlyphKerning
      (!____ptr).getTextWidth = getTextWidth
      (!____ptr).getFrame = getFrame
      (!____ptr).getDisplayFrame = getDisplayFrame
      (!____ptr).getDebugBitmap = getDebugBitmap
      (!____ptr).copyFrameBufferBitmap = copyFrameBufferBitmap
      (!____ptr).markUpdatedRows = markUpdatedRows
      (!____ptr).display = display
      (!____ptr).setColorToPattern = setColorToPattern
      (!____ptr).checkMaskCollision = checkMaskCollision
      (!____ptr).setScreenClipRect = setScreenClipRect
      (!____ptr).fillPolygon = fillPolygon
      (!____ptr).getFontHeight = getFontHeight
      (!____ptr).getDisplayBufferBitmap = getDisplayBufferBitmap
      (!____ptr).drawRotatedBitmap = drawRotatedBitmap
      (!____ptr).setTextLeading = setTextLeading
      (!____ptr).setBitmapMask = setBitmapMask
      (!____ptr).getBitmapMask = getBitmapMask
      (!____ptr).setStencilImage = setStencilImage
      (!____ptr).makeFontFromData = makeFontFromData
      (!____ptr).getTextTracking = getTextTracking
      ____ptr
    extension (struct: playdate_graphics)
      def video: Ptr[playdate_video] = !struct.at(0).asInstanceOf[Ptr[Ptr[playdate_video]]]
      def video_=(value: Ptr[playdate_video]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[playdate_video]]] = value
      def clear: CFuncPtr1[LCDColor, Unit] = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[LCDColor, Unit]]]
      def clear_=(value: CFuncPtr1[LCDColor, Unit]): Unit = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[LCDColor, Unit]]] = value
      def setBackgroundColor: CFuncPtr1[LCDSolidColor, Unit] = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[LCDSolidColor, Unit]]]
      def setBackgroundColor_=(value: CFuncPtr1[LCDSolidColor, Unit]): Unit = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[LCDSolidColor, Unit]]] = value
      def setStencil: CFuncPtr1[Ptr[LCDBitmap], Unit] = !struct.at(12).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]]
      def setStencil_=(value: CFuncPtr1[Ptr[LCDBitmap], Unit]): Unit = !struct.at(12).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]] = value
      def setDrawMode: CFuncPtr1[LCDBitmapDrawMode, Unit] = !struct.at(16).asInstanceOf[Ptr[CFuncPtr1[LCDBitmapDrawMode, Unit]]]
      def setDrawMode_=(value: CFuncPtr1[LCDBitmapDrawMode, Unit]): Unit = !struct.at(16).asInstanceOf[Ptr[CFuncPtr1[LCDBitmapDrawMode, Unit]]] = value
      def setDrawOffset: CFuncPtr2[CInt, CInt, Unit] = !struct.at(20).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]]
      def setDrawOffset_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at(20).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]] = value
      def setClipRect: CFuncPtr4[CInt, CInt, CInt, CInt, Unit] = !struct.at(24).asInstanceOf[Ptr[CFuncPtr4[CInt, CInt, CInt, CInt, Unit]]]
      def setClipRect_=(value: CFuncPtr4[CInt, CInt, CInt, CInt, Unit]): Unit = !struct.at(24).asInstanceOf[Ptr[CFuncPtr4[CInt, CInt, CInt, CInt, Unit]]] = value
      def clearClipRect: CFuncPtr0[Unit] = !struct.at(28).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def clearClipRect_=(value: CFuncPtr0[Unit]): Unit = !struct.at(28).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def setLineCapStyle: CFuncPtr1[LCDLineCapStyle, Unit] = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[LCDLineCapStyle, Unit]]]
      def setLineCapStyle_=(value: CFuncPtr1[LCDLineCapStyle, Unit]): Unit = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[LCDLineCapStyle, Unit]]] = value
      def setFont: CFuncPtr1[Ptr[LCDFont], Unit] = !struct.at(36).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDFont], Unit]]]
      def setFont_=(value: CFuncPtr1[Ptr[LCDFont], Unit]): Unit = !struct.at(36).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDFont], Unit]]] = value
      def setTextTracking: CFuncPtr1[CInt, Unit] = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def setTextTracking_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def pushContext: CFuncPtr1[Ptr[LCDBitmap], Unit] = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]]
      def pushContext_=(value: CFuncPtr1[Ptr[LCDBitmap], Unit]): Unit = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]] = value
      def popContext: CFuncPtr0[Unit] = !struct.at(48).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def popContext_=(value: CFuncPtr0[Unit]): Unit = !struct.at(48).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def drawBitmap: CFuncPtr4[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Unit] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Unit]]]
      def drawBitmap_=(value: CFuncPtr4[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Unit]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Unit]]] = value
      def tileBitmap: CFuncPtr6[Ptr[LCDBitmap], CInt, CInt, CInt, CInt, LCDBitmapFlip, Unit] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDBitmap], CInt, CInt, CInt, CInt, LCDBitmapFlip, Unit]]]
      def tileBitmap_=(value: CFuncPtr6[Ptr[LCDBitmap], CInt, CInt, CInt, CInt, LCDBitmapFlip, Unit]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDBitmap], CInt, CInt, CInt, CInt, LCDBitmapFlip, Unit]]] = value
      def drawLine: CFuncPtr6[CInt, CInt, CInt, CInt, CInt, LCDColor, Unit] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr6[CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]]]
      def drawLine_=(value: CFuncPtr6[CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr6[CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]]] = value
      def fillTriangle: CFuncPtr7[CInt, CInt, CInt, CInt, CInt, CInt, LCDColor, Unit] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr7[CInt, CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]]]
      def fillTriangle_=(value: CFuncPtr7[CInt, CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr7[CInt, CInt, CInt, CInt, CInt, CInt, LCDColor, Unit]]] = value
      def drawRect: CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]]]
      def drawRect_=(value: CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]]] = value
      def fillRect: CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]]]
      def fillRect_=(value: CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr5[CInt, CInt, CInt, CInt, LCDColor, Unit]]] = value
      def drawEllipse: CFuncPtr8[CInt, CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr8[CInt, CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]]]
      def drawEllipse_=(value: CFuncPtr8[CInt, CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr8[CInt, CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]]] = value
      def fillEllipse: CFuncPtr7[CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr7[CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]]]
      def fillEllipse_=(value: CFuncPtr7[CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr7[CInt, CInt, CInt, CInt, Float, Float, LCDColor, Unit]]] = value
      def drawScaledBitmap: CFuncPtr5[Ptr[LCDBitmap], CInt, CInt, Float, Float, Unit] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDBitmap], CInt, CInt, Float, Float, Unit]]]
      def drawScaledBitmap_=(value: CFuncPtr5[Ptr[LCDBitmap], CInt, CInt, Float, Float, Unit]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDBitmap], CInt, CInt, Float, Float, Unit]]] = value
      def drawText: CFuncPtr5[Ptr[Byte], size_t, PDStringEncoding, CInt, CInt, CInt] = !struct.at(88).asInstanceOf[Ptr[CFuncPtr5[Ptr[Byte], size_t, PDStringEncoding, CInt, CInt, CInt]]]
      def drawText_=(value: CFuncPtr5[Ptr[Byte], size_t, PDStringEncoding, CInt, CInt, CInt]): Unit = !struct.at(88).asInstanceOf[Ptr[CFuncPtr5[Ptr[Byte], size_t, PDStringEncoding, CInt, CInt, CInt]]] = value
      def newBitmap: CFuncPtr3[CInt, CInt, LCDColor, Ptr[LCDBitmap]] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr3[CInt, CInt, LCDColor, Ptr[LCDBitmap]]]]
      def newBitmap_=(value: CFuncPtr3[CInt, CInt, LCDColor, Ptr[LCDBitmap]]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr3[CInt, CInt, LCDColor, Ptr[LCDBitmap]]]] = value
      def freeBitmap: CFuncPtr1[Ptr[LCDBitmap], Unit] = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]]
      def freeBitmap_=(value: CFuncPtr1[Ptr[LCDBitmap], Unit]): Unit = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]] = value
      def loadBitmap: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmap]] = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmap]]]]
      def loadBitmap_=(value: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmap]]): Unit = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmap]]]] = value
      def copyBitmap: CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]] = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]]]
      def copyBitmap_=(value: CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]): Unit = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]]] = value
      def loadIntoBitmap: CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmap], Ptr[Ptr[CUnsignedChar]], Unit] = !struct.at(108).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmap], Ptr[Ptr[CUnsignedChar]], Unit]]]
      def loadIntoBitmap_=(value: CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmap], Ptr[Ptr[CUnsignedChar]], Unit]): Unit = !struct.at(108).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmap], Ptr[Ptr[CUnsignedChar]], Unit]]] = value
      def getBitmapData: CFuncPtr6[Ptr[LCDBitmap], Ptr[CInt], Ptr[CInt], Ptr[CInt], Ptr[Ptr[uint8_t]], Ptr[Ptr[uint8_t]], Unit] = !struct.at(112).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDBitmap], Ptr[CInt], Ptr[CInt], Ptr[CInt], Ptr[Ptr[uint8_t]], Ptr[Ptr[uint8_t]], Unit]]]
      def getBitmapData_=(value: CFuncPtr6[Ptr[LCDBitmap], Ptr[CInt], Ptr[CInt], Ptr[CInt], Ptr[Ptr[uint8_t]], Ptr[Ptr[uint8_t]], Unit]): Unit = !struct.at(112).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDBitmap], Ptr[CInt], Ptr[CInt], Ptr[CInt], Ptr[Ptr[uint8_t]], Ptr[Ptr[uint8_t]], Unit]]] = value
      def clearBitmap: CFuncPtr2[Ptr[LCDBitmap], LCDColor, Unit] = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], LCDColor, Unit]]]
      def clearBitmap_=(value: CFuncPtr2[Ptr[LCDBitmap], LCDColor, Unit]): Unit = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], LCDColor, Unit]]] = value
      def rotatedBitmap: CFuncPtr5[Ptr[LCDBitmap], Float, Float, Float, Ptr[CInt], Ptr[LCDBitmap]] = !struct.at(120).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDBitmap], Float, Float, Float, Ptr[CInt], Ptr[LCDBitmap]]]]
      def rotatedBitmap_=(value: CFuncPtr5[Ptr[LCDBitmap], Float, Float, Float, Ptr[CInt], Ptr[LCDBitmap]]): Unit = !struct.at(120).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDBitmap], Float, Float, Float, Ptr[CInt], Ptr[LCDBitmap]]]] = value
      def newBitmapTable: CFuncPtr3[CInt, CInt, CInt, Ptr[LCDBitmapTable]] = !struct.at(124).asInstanceOf[Ptr[CFuncPtr3[CInt, CInt, CInt, Ptr[LCDBitmapTable]]]]
      def newBitmapTable_=(value: CFuncPtr3[CInt, CInt, CInt, Ptr[LCDBitmapTable]]): Unit = !struct.at(124).asInstanceOf[Ptr[CFuncPtr3[CInt, CInt, CInt, Ptr[LCDBitmapTable]]]] = value
      def freeBitmapTable: CFuncPtr1[Ptr[LCDBitmapTable], Unit] = !struct.at(128).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmapTable], Unit]]]
      def freeBitmapTable_=(value: CFuncPtr1[Ptr[LCDBitmapTable], Unit]): Unit = !struct.at(128).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmapTable], Unit]]] = value
      def loadBitmapTable: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmapTable]] = !struct.at(132).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmapTable]]]]
      def loadBitmapTable_=(value: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmapTable]]): Unit = !struct.at(132).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDBitmapTable]]]] = value
      def loadIntoBitmapTable: CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmapTable], Ptr[Ptr[CUnsignedChar]], Unit] = !struct.at(136).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmapTable], Ptr[Ptr[CUnsignedChar]], Unit]]]
      def loadIntoBitmapTable_=(value: CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmapTable], Ptr[Ptr[CUnsignedChar]], Unit]): Unit = !struct.at(136).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[LCDBitmapTable], Ptr[Ptr[CUnsignedChar]], Unit]]] = value
      def getTableBitmap: CFuncPtr2[Ptr[LCDBitmapTable], CInt, Ptr[LCDBitmap]] = !struct.at(140).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmapTable], CInt, Ptr[LCDBitmap]]]]
      def getTableBitmap_=(value: CFuncPtr2[Ptr[LCDBitmapTable], CInt, Ptr[LCDBitmap]]): Unit = !struct.at(140).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmapTable], CInt, Ptr[LCDBitmap]]]] = value
      def loadFont: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDFont]] = !struct.at(144).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDFont]]]]
      def loadFont_=(value: CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDFont]]): Unit = !struct.at(144).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], Ptr[LCDFont]]]] = value
      def getFontPage: CFuncPtr2[Ptr[LCDFont], uint32_t, Ptr[LCDFontPage]] = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDFont], uint32_t, Ptr[LCDFontPage]]]]
      def getFontPage_=(value: CFuncPtr2[Ptr[LCDFont], uint32_t, Ptr[LCDFontPage]]): Unit = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDFont], uint32_t, Ptr[LCDFontPage]]]] = value
      def getPageGlyph: CFuncPtr4[Ptr[LCDFontPage], uint32_t, Ptr[Ptr[LCDBitmap]], Ptr[CInt], Ptr[LCDFontGlyph]] = !struct.at(152).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDFontPage], uint32_t, Ptr[Ptr[LCDBitmap]], Ptr[CInt], Ptr[LCDFontGlyph]]]]
      def getPageGlyph_=(value: CFuncPtr4[Ptr[LCDFontPage], uint32_t, Ptr[Ptr[LCDBitmap]], Ptr[CInt], Ptr[LCDFontGlyph]]): Unit = !struct.at(152).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDFontPage], uint32_t, Ptr[Ptr[LCDBitmap]], Ptr[CInt], Ptr[LCDFontGlyph]]]] = value
      def getGlyphKerning: CFuncPtr3[Ptr[LCDFontGlyph], uint32_t, uint32_t, CInt] = !struct.at(156).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDFontGlyph], uint32_t, uint32_t, CInt]]]
      def getGlyphKerning_=(value: CFuncPtr3[Ptr[LCDFontGlyph], uint32_t, uint32_t, CInt]): Unit = !struct.at(156).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDFontGlyph], uint32_t, uint32_t, CInt]]] = value
      def getTextWidth: CFuncPtr5[Ptr[LCDFont], Ptr[Byte], size_t, PDStringEncoding, CInt, CInt] = !struct.at(160).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDFont], Ptr[Byte], size_t, PDStringEncoding, CInt, CInt]]]
      def getTextWidth_=(value: CFuncPtr5[Ptr[LCDFont], Ptr[Byte], size_t, PDStringEncoding, CInt, CInt]): Unit = !struct.at(160).asInstanceOf[Ptr[CFuncPtr5[Ptr[LCDFont], Ptr[Byte], size_t, PDStringEncoding, CInt, CInt]]] = value
      def getFrame: CFuncPtr0[Ptr[uint8_t]] = !struct.at(164).asInstanceOf[Ptr[CFuncPtr0[Ptr[uint8_t]]]]
      def getFrame_=(value: CFuncPtr0[Ptr[uint8_t]]): Unit = !struct.at(164).asInstanceOf[Ptr[CFuncPtr0[Ptr[uint8_t]]]] = value
      def getDisplayFrame: CFuncPtr0[Ptr[uint8_t]] = !struct.at(168).asInstanceOf[Ptr[CFuncPtr0[Ptr[uint8_t]]]]
      def getDisplayFrame_=(value: CFuncPtr0[Ptr[uint8_t]]): Unit = !struct.at(168).asInstanceOf[Ptr[CFuncPtr0[Ptr[uint8_t]]]] = value
      def getDebugBitmap: CFuncPtr0[Ptr[LCDBitmap]] = !struct.at(172).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]]
      def getDebugBitmap_=(value: CFuncPtr0[Ptr[LCDBitmap]]): Unit = !struct.at(172).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]] = value
      def copyFrameBufferBitmap: CFuncPtr0[Ptr[LCDBitmap]] = !struct.at(176).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]]
      def copyFrameBufferBitmap_=(value: CFuncPtr0[Ptr[LCDBitmap]]): Unit = !struct.at(176).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]] = value
      def markUpdatedRows: CFuncPtr2[CInt, CInt, Unit] = !struct.at(180).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]]
      def markUpdatedRows_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at(180).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]] = value
      def display: CFuncPtr0[Unit] = !struct.at(184).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def display_=(value: CFuncPtr0[Unit]): Unit = !struct.at(184).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def setColorToPattern: CFuncPtr4[Ptr[LCDColor], Ptr[LCDBitmap], CInt, CInt, Unit] = !struct.at(188).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDColor], Ptr[LCDBitmap], CInt, CInt, Unit]]]
      def setColorToPattern_=(value: CFuncPtr4[Ptr[LCDColor], Ptr[LCDBitmap], CInt, CInt, Unit]): Unit = !struct.at(188).asInstanceOf[Ptr[CFuncPtr4[Ptr[LCDColor], Ptr[LCDBitmap], CInt, CInt, Unit]]] = value
      def checkMaskCollision: CFuncPtr9[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, LCDRect, CInt] = !struct.at(192).asInstanceOf[Ptr[CFuncPtr9[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, LCDRect, CInt]]]
      def checkMaskCollision_=(value: CFuncPtr9[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, LCDRect, CInt]): Unit = !struct.at(192).asInstanceOf[Ptr[CFuncPtr9[Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, Ptr[LCDBitmap], CInt, CInt, LCDBitmapFlip, LCDRect, CInt]]] = value
      def setScreenClipRect: CFuncPtr4[CInt, CInt, CInt, CInt, Unit] = !struct.at(196).asInstanceOf[Ptr[CFuncPtr4[CInt, CInt, CInt, CInt, Unit]]]
      def setScreenClipRect_=(value: CFuncPtr4[CInt, CInt, CInt, CInt, Unit]): Unit = !struct.at(196).asInstanceOf[Ptr[CFuncPtr4[CInt, CInt, CInt, CInt, Unit]]] = value
      def fillPolygon: CFuncPtr4[CInt, Ptr[CInt], LCDColor, LCDPolygonFillRule, Unit] = !struct.at(200).asInstanceOf[Ptr[CFuncPtr4[CInt, Ptr[CInt], LCDColor, LCDPolygonFillRule, Unit]]]
      def fillPolygon_=(value: CFuncPtr4[CInt, Ptr[CInt], LCDColor, LCDPolygonFillRule, Unit]): Unit = !struct.at(200).asInstanceOf[Ptr[CFuncPtr4[CInt, Ptr[CInt], LCDColor, LCDPolygonFillRule, Unit]]] = value
      def getFontHeight: CFuncPtr1[Ptr[LCDFont], uint8_t] = !struct.at(204).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDFont], uint8_t]]]
      def getFontHeight_=(value: CFuncPtr1[Ptr[LCDFont], uint8_t]): Unit = !struct.at(204).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDFont], uint8_t]]] = value
      def getDisplayBufferBitmap: CFuncPtr0[Ptr[LCDBitmap]] = !struct.at(208).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]]
      def getDisplayBufferBitmap_=(value: CFuncPtr0[Ptr[LCDBitmap]]): Unit = !struct.at(208).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDBitmap]]]] = value
      def drawRotatedBitmap: CFuncPtr8[Ptr[LCDBitmap], CInt, CInt, Float, Float, Float, Float, Float, Unit] = !struct.at(212).asInstanceOf[Ptr[CFuncPtr8[Ptr[LCDBitmap], CInt, CInt, Float, Float, Float, Float, Float, Unit]]]
      def drawRotatedBitmap_=(value: CFuncPtr8[Ptr[LCDBitmap], CInt, CInt, Float, Float, Float, Float, Float, Unit]): Unit = !struct.at(212).asInstanceOf[Ptr[CFuncPtr8[Ptr[LCDBitmap], CInt, CInt, Float, Float, Float, Float, Float, Unit]]] = value
      def setTextLeading: CFuncPtr1[CInt, Unit] = !struct.at(216).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def setTextLeading_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(216).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def setBitmapMask: CFuncPtr2[Ptr[LCDBitmap], Ptr[LCDBitmap], CInt] = !struct.at(220).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], Ptr[LCDBitmap], CInt]]]
      def setBitmapMask_=(value: CFuncPtr2[Ptr[LCDBitmap], Ptr[LCDBitmap], CInt]): Unit = !struct.at(220).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], Ptr[LCDBitmap], CInt]]] = value
      def getBitmapMask: CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]] = !struct.at(224).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]]]
      def getBitmapMask_=(value: CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]): Unit = !struct.at(224).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Ptr[LCDBitmap]]]] = value
      def setStencilImage: CFuncPtr2[Ptr[LCDBitmap], CInt, Unit] = !struct.at(228).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]]]
      def setStencilImage_=(value: CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]): Unit = !struct.at(228).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]]] = value
      def makeFontFromData: CFuncPtr2[Ptr[LCDFontData], CInt, Ptr[LCDFont]] = !struct.at(232).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDFontData], CInt, Ptr[LCDFont]]]]
      def makeFontFromData_=(value: CFuncPtr2[Ptr[LCDFontData], CInt, Ptr[LCDFont]]): Unit = !struct.at(232).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDFontData], CInt, Ptr[LCDFont]]]] = value
      def getTextTracking: CFuncPtr0[CInt] = !struct.at(236).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def getTextTracking_=(value: CFuncPtr0[CInt]): Unit = !struct.at(236).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type playdate_json = CStruct3[CFuncPtr4[Ptr[Byte], Ptr[writeFunc], Ptr[Byte], CInt, Unit], CFuncPtr3[Ptr[Byte], json_reader, Ptr[json_value], CInt], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], Ptr[json_value], CInt]]
  object playdate_json:
    given _tag: Tag[playdate_json] = Tag.materializeCStruct3Tag[CFuncPtr4[Ptr[Byte], Ptr[writeFunc], Ptr[Byte], CInt, Unit], CFuncPtr3[Ptr[Byte], json_reader, Ptr[json_value], CInt], CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], Ptr[json_value], CInt]]
    def apply()(using Zone): Ptr[playdate_json] = scala.scalanative.unsafe.alloc[playdate_json](1)
    def apply(initEncoder : CFuncPtr4[Ptr[json_encoder], Ptr[writeFunc], Ptr[Byte], CInt, Unit], decode : CFuncPtr3[Ptr[json_decoder], json_reader, Ptr[json_value], CInt], decodeString : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], Ptr[json_value], CInt])(using Zone): Ptr[playdate_json] =
      val ____ptr = apply()
      (!____ptr).initEncoder = initEncoder
      (!____ptr).decode = decode
      (!____ptr).decodeString = decodeString
      ____ptr
    extension (struct: playdate_json)
      def initEncoder : CFuncPtr4[Ptr[json_encoder], Ptr[writeFunc], Ptr[Byte], CInt, Unit] = struct._1.asInstanceOf[CFuncPtr4[Ptr[json_encoder], Ptr[writeFunc], Ptr[Byte], CInt, Unit]]
      def initEncoder_=(value: CFuncPtr4[Ptr[json_encoder], Ptr[writeFunc], Ptr[Byte], CInt, Unit]): Unit = !struct.at1 = value.asInstanceOf[CFuncPtr4[Ptr[Byte], Ptr[writeFunc], Ptr[Byte], CInt, Unit]]
      def decode : CFuncPtr3[Ptr[json_decoder], json_reader, Ptr[json_value], CInt] = struct._2.asInstanceOf[CFuncPtr3[Ptr[json_decoder], json_reader, Ptr[json_value], CInt]]
      def decode_=(value: CFuncPtr3[Ptr[json_decoder], json_reader, Ptr[json_value], CInt]): Unit = !struct.at2 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], json_reader, Ptr[json_value], CInt]]
      def decodeString : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], Ptr[json_value], CInt] = struct._3.asInstanceOf[CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], Ptr[json_value], CInt]]
      def decodeString_=(value: CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], Ptr[json_value], CInt]): Unit = !struct.at3 = value.asInstanceOf[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], Ptr[json_value], CInt]]

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type playdate_lua = CArray[CChar, Nat.Digit3[Nat._1, Nat._2, Nat._8]]
  object playdate_lua:
    given _tag: Tag[playdate_lua] = Tag.CArray[CChar, Nat.Digit3[Nat._1, Nat._2, Nat._8]](Tag.Byte, Tag.Digit3[Nat._1, Nat._2, Nat._8](Tag.Nat1, Tag.Nat2, Tag.Nat8))
    def apply()(using Zone): Ptr[playdate_lua] = scala.scalanative.unsafe.alloc[playdate_lua](1)
    def apply(addFunction : CFuncPtr3[lua_CFunction, Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt], registerClass : CFuncPtr5[Ptr[CUnsignedChar], Ptr[lua_reg], Ptr[lua_val], CInt, Ptr[Ptr[CUnsignedChar]], CInt], pushFunction : CFuncPtr1[lua_CFunction, Unit], indexMetatable : CFuncPtr0[CInt], stop : CFuncPtr0[Unit], start : CFuncPtr0[Unit], getArgCount : CFuncPtr0[CInt], getArgType : CFuncPtr2[CInt, Ptr[Ptr[CUnsignedChar]], LuaType], argIsNil : CFuncPtr1[CInt, CInt], getArgBool : CFuncPtr1[CInt, CInt], getArgInt : CFuncPtr1[CInt, CInt], getArgFloat : CFuncPtr1[CInt, Float], getArgString : CFuncPtr1[CInt, Ptr[CUnsignedChar]], getArgBytes : CFuncPtr2[CInt, Ptr[size_t], Ptr[CUnsignedChar]], getArgObject : CFuncPtr3[CInt, Ptr[CUnsignedChar], Ptr[Ptr[LuaUDObject]], Ptr[Byte]], getBitmap : CFuncPtr1[CInt, Ptr[LCDBitmap]], getSprite : CFuncPtr1[CInt, Ptr[LCDSprite]], pushNil : CFuncPtr0[Unit], pushBool : CFuncPtr1[CInt, Unit], pushInt : CFuncPtr1[CInt, Unit], pushFloat : CFuncPtr1[Float, Unit], pushString : CFuncPtr1[Ptr[CUnsignedChar], Unit], pushBytes : CFuncPtr2[Ptr[CUnsignedChar], size_t, Unit], pushBitmap : CFuncPtr1[Ptr[LCDBitmap], Unit], pushSprite : CFuncPtr1[Ptr[LCDSprite], Unit], pushObject : CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Ptr[LuaUDObject]], retainObject : CFuncPtr1[Ptr[LuaUDObject], Ptr[LuaUDObject]], releaseObject : CFuncPtr1[Ptr[LuaUDObject], Unit], setUserValue : CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, Unit], getUserValue : CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, CInt], callFunction_deprecated : CFuncPtr2[Ptr[CUnsignedChar], CInt, Unit], callFunction : CFuncPtr3[Ptr[CUnsignedChar], CInt, Ptr[Ptr[CUnsignedChar]], CInt])(using Zone): Ptr[playdate_lua] =
      val ____ptr = apply()
      (!____ptr).addFunction = addFunction
      (!____ptr).registerClass = registerClass
      (!____ptr).pushFunction = pushFunction
      (!____ptr).indexMetatable = indexMetatable
      (!____ptr).stop = stop
      (!____ptr).start = start
      (!____ptr).getArgCount = getArgCount
      (!____ptr).getArgType = getArgType
      (!____ptr).argIsNil = argIsNil
      (!____ptr).getArgBool = getArgBool
      (!____ptr).getArgInt = getArgInt
      (!____ptr).getArgFloat = getArgFloat
      (!____ptr).getArgString = getArgString
      (!____ptr).getArgBytes = getArgBytes
      (!____ptr).getArgObject = getArgObject
      (!____ptr).getBitmap = getBitmap
      (!____ptr).getSprite = getSprite
      (!____ptr).pushNil = pushNil
      (!____ptr).pushBool = pushBool
      (!____ptr).pushInt = pushInt
      (!____ptr).pushFloat = pushFloat
      (!____ptr).pushString = pushString
      (!____ptr).pushBytes = pushBytes
      (!____ptr).pushBitmap = pushBitmap
      (!____ptr).pushSprite = pushSprite
      (!____ptr).pushObject = pushObject
      (!____ptr).retainObject = retainObject
      (!____ptr).releaseObject = releaseObject
      (!____ptr).setUserValue = setUserValue
      (!____ptr).getUserValue = getUserValue
      (!____ptr).callFunction_deprecated = callFunction_deprecated
      (!____ptr).callFunction = callFunction
      ____ptr
    extension (struct: playdate_lua)
      def addFunction: CFuncPtr3[lua_CFunction, Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt] = !struct.at(0).asInstanceOf[Ptr[CFuncPtr3[lua_CFunction, Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt]]]
      def addFunction_=(value: CFuncPtr3[lua_CFunction, Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt]): Unit = !struct.at(0).asInstanceOf[Ptr[CFuncPtr3[lua_CFunction, Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt]]] = value
      def registerClass: CFuncPtr5[Ptr[CUnsignedChar], Ptr[lua_reg], Ptr[lua_val], CInt, Ptr[Ptr[CUnsignedChar]], CInt] = !struct.at(4).asInstanceOf[Ptr[CFuncPtr5[Ptr[CUnsignedChar], Ptr[lua_reg], Ptr[lua_val], CInt, Ptr[Ptr[CUnsignedChar]], CInt]]]
      def registerClass_=(value: CFuncPtr5[Ptr[CUnsignedChar], Ptr[lua_reg], Ptr[lua_val], CInt, Ptr[Ptr[CUnsignedChar]], CInt]): Unit = !struct.at(4).asInstanceOf[Ptr[CFuncPtr5[Ptr[CUnsignedChar], Ptr[lua_reg], Ptr[lua_val], CInt, Ptr[Ptr[CUnsignedChar]], CInt]]] = value
      def pushFunction: CFuncPtr1[lua_CFunction, Unit] = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[lua_CFunction, Unit]]]
      def pushFunction_=(value: CFuncPtr1[lua_CFunction, Unit]): Unit = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[lua_CFunction, Unit]]] = value
      def indexMetatable: CFuncPtr0[CInt] = !struct.at(12).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def indexMetatable_=(value: CFuncPtr0[CInt]): Unit = !struct.at(12).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def stop: CFuncPtr0[Unit] = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def stop_=(value: CFuncPtr0[Unit]): Unit = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def start: CFuncPtr0[Unit] = !struct.at(20).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def start_=(value: CFuncPtr0[Unit]): Unit = !struct.at(20).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def getArgCount: CFuncPtr0[CInt] = !struct.at(24).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def getArgCount_=(value: CFuncPtr0[CInt]): Unit = !struct.at(24).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def getArgType: CFuncPtr2[CInt, Ptr[Ptr[CUnsignedChar]], LuaType] = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[CInt, Ptr[Ptr[CUnsignedChar]], LuaType]]]
      def getArgType_=(value: CFuncPtr2[CInt, Ptr[Ptr[CUnsignedChar]], LuaType]): Unit = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[CInt, Ptr[Ptr[CUnsignedChar]], LuaType]]] = value
      def argIsNil: CFuncPtr1[CInt, CInt] = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]]
      def argIsNil_=(value: CFuncPtr1[CInt, CInt]): Unit = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]] = value
      def getArgBool: CFuncPtr1[CInt, CInt] = !struct.at(36).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]]
      def getArgBool_=(value: CFuncPtr1[CInt, CInt]): Unit = !struct.at(36).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]] = value
      def getArgInt: CFuncPtr1[CInt, CInt] = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]]
      def getArgInt_=(value: CFuncPtr1[CInt, CInt]): Unit = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]] = value
      def getArgFloat: CFuncPtr1[CInt, Float] = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[CInt, Float]]]
      def getArgFloat_=(value: CFuncPtr1[CInt, Float]): Unit = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[CInt, Float]]] = value
      def getArgString: CFuncPtr1[CInt, Ptr[CUnsignedChar]] = !struct.at(48).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[CUnsignedChar]]]]
      def getArgString_=(value: CFuncPtr1[CInt, Ptr[CUnsignedChar]]): Unit = !struct.at(48).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[CUnsignedChar]]]] = value
      def getArgBytes: CFuncPtr2[CInt, Ptr[size_t], Ptr[CUnsignedChar]] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr2[CInt, Ptr[size_t], Ptr[CUnsignedChar]]]]
      def getArgBytes_=(value: CFuncPtr2[CInt, Ptr[size_t], Ptr[CUnsignedChar]]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr2[CInt, Ptr[size_t], Ptr[CUnsignedChar]]]] = value
      def getArgObject: CFuncPtr3[CInt, Ptr[CUnsignedChar], Ptr[Ptr[LuaUDObject]], Ptr[Byte]] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[CInt, Ptr[CUnsignedChar], Ptr[Ptr[LuaUDObject]], Ptr[Byte]]]]
      def getArgObject_=(value: CFuncPtr3[CInt, Ptr[CUnsignedChar], Ptr[Ptr[LuaUDObject]], Ptr[Byte]]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[CInt, Ptr[CUnsignedChar], Ptr[Ptr[LuaUDObject]], Ptr[Byte]]]] = value
      def getBitmap: CFuncPtr1[CInt, Ptr[LCDBitmap]] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[LCDBitmap]]]]
      def getBitmap_=(value: CFuncPtr1[CInt, Ptr[LCDBitmap]]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[LCDBitmap]]]] = value
      def getSprite: CFuncPtr1[CInt, Ptr[LCDSprite]] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[LCDSprite]]]]
      def getSprite_=(value: CFuncPtr1[CInt, Ptr[LCDSprite]]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr1[CInt, Ptr[LCDSprite]]]] = value
      def pushNil: CFuncPtr0[Unit] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def pushNil_=(value: CFuncPtr0[Unit]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def pushBool: CFuncPtr1[CInt, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def pushBool_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def pushInt: CFuncPtr1[CInt, Unit] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def pushInt_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def pushFloat: CFuncPtr1[Float, Unit] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr1[Float, Unit]]]
      def pushFloat_=(value: CFuncPtr1[Float, Unit]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr1[Float, Unit]]] = value
      def pushString: CFuncPtr1[Ptr[CUnsignedChar], Unit] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]]
      def pushString_=(value: CFuncPtr1[Ptr[CUnsignedChar], Unit]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]] = value
      def pushBytes: CFuncPtr2[Ptr[CUnsignedChar], size_t, Unit] = !struct.at(88).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], size_t, Unit]]]
      def pushBytes_=(value: CFuncPtr2[Ptr[CUnsignedChar], size_t, Unit]): Unit = !struct.at(88).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], size_t, Unit]]] = value
      def pushBitmap: CFuncPtr1[Ptr[LCDBitmap], Unit] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]]
      def pushBitmap_=(value: CFuncPtr1[Ptr[LCDBitmap], Unit]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDBitmap], Unit]]] = value
      def pushSprite: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def pushSprite_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def pushObject: CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Ptr[LuaUDObject]] = !struct.at(100).asInstanceOf[Ptr[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Ptr[LuaUDObject]]]]
      def pushObject_=(value: CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Ptr[LuaUDObject]]): Unit = !struct.at(100).asInstanceOf[Ptr[CFuncPtr3[Ptr[Byte], Ptr[CUnsignedChar], CInt, Ptr[LuaUDObject]]]] = value
      def retainObject: CFuncPtr1[Ptr[LuaUDObject], Ptr[LuaUDObject]] = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LuaUDObject], Ptr[LuaUDObject]]]]
      def retainObject_=(value: CFuncPtr1[Ptr[LuaUDObject], Ptr[LuaUDObject]]): Unit = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LuaUDObject], Ptr[LuaUDObject]]]] = value
      def releaseObject: CFuncPtr1[Ptr[LuaUDObject], Unit] = !struct.at(108).asInstanceOf[Ptr[CFuncPtr1[Ptr[LuaUDObject], Unit]]]
      def releaseObject_=(value: CFuncPtr1[Ptr[LuaUDObject], Unit]): Unit = !struct.at(108).asInstanceOf[Ptr[CFuncPtr1[Ptr[LuaUDObject], Unit]]] = value
      def setUserValue: CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, Unit] = !struct.at(112).asInstanceOf[Ptr[CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, Unit]]]
      def setUserValue_=(value: CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, Unit]): Unit = !struct.at(112).asInstanceOf[Ptr[CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, Unit]]] = value
      def getUserValue: CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, CInt] = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, CInt]]]
      def getUserValue_=(value: CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, CInt]): Unit = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LuaUDObject], CUnsignedInt, CInt]]] = value
      def callFunction_deprecated: CFuncPtr2[Ptr[CUnsignedChar], CInt, Unit] = !struct.at(120).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], CInt, Unit]]]
      def callFunction_deprecated_=(value: CFuncPtr2[Ptr[CUnsignedChar], CInt, Unit]): Unit = !struct.at(120).asInstanceOf[Ptr[CFuncPtr2[Ptr[CUnsignedChar], CInt, Unit]]] = value
      def callFunction: CFuncPtr3[Ptr[CUnsignedChar], CInt, Ptr[Ptr[CUnsignedChar]], CInt] = !struct.at(124).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], CInt, Ptr[Ptr[CUnsignedChar]], CInt]]]
      def callFunction_=(value: CFuncPtr3[Ptr[CUnsignedChar], CInt, Ptr[Ptr[CUnsignedChar]], CInt]): Unit = !struct.at(124).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], CInt, Ptr[Ptr[CUnsignedChar]], CInt]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_scoreboards.h
  */
  opaque type playdate_scoreboards = CStruct7[CFuncPtr3[Ptr[CUnsignedChar], uint32_t, AddScoreCallback, CInt], CFuncPtr2[Ptr[CUnsignedChar], PersonalBestCallback, CInt], CFuncPtr1[Ptr[PDScore], Unit], CFuncPtr1[BoardsListCallback, CInt], CFuncPtr1[Ptr[PDBoardsList], Unit], CFuncPtr2[Ptr[CUnsignedChar], ScoresCallback, CInt], CFuncPtr1[Ptr[PDScoresList], Unit]]
  object playdate_scoreboards:
    given _tag: Tag[playdate_scoreboards] = Tag.materializeCStruct7Tag[CFuncPtr3[Ptr[CUnsignedChar], uint32_t, AddScoreCallback, CInt], CFuncPtr2[Ptr[CUnsignedChar], PersonalBestCallback, CInt], CFuncPtr1[Ptr[PDScore], Unit], CFuncPtr1[BoardsListCallback, CInt], CFuncPtr1[Ptr[PDBoardsList], Unit], CFuncPtr2[Ptr[CUnsignedChar], ScoresCallback, CInt], CFuncPtr1[Ptr[PDScoresList], Unit]]
    def apply()(using Zone): Ptr[playdate_scoreboards] = scala.scalanative.unsafe.alloc[playdate_scoreboards](1)
    def apply(addScore : CFuncPtr3[Ptr[CUnsignedChar], uint32_t, AddScoreCallback, CInt], getPersonalBest : CFuncPtr2[Ptr[CUnsignedChar], PersonalBestCallback, CInt], freeScore : CFuncPtr1[Ptr[PDScore], Unit], getScoreboards : CFuncPtr1[BoardsListCallback, CInt], freeBoardsList : CFuncPtr1[Ptr[PDBoardsList], Unit], getScores : CFuncPtr2[Ptr[CUnsignedChar], ScoresCallback, CInt], freeScoresList : CFuncPtr1[Ptr[PDScoresList], Unit])(using Zone): Ptr[playdate_scoreboards] =
      val ____ptr = apply()
      (!____ptr).addScore = addScore
      (!____ptr).getPersonalBest = getPersonalBest
      (!____ptr).freeScore = freeScore
      (!____ptr).getScoreboards = getScoreboards
      (!____ptr).freeBoardsList = freeBoardsList
      (!____ptr).getScores = getScores
      (!____ptr).freeScoresList = freeScoresList
      ____ptr
    extension (struct: playdate_scoreboards)
      def addScore : CFuncPtr3[Ptr[CUnsignedChar], uint32_t, AddScoreCallback, CInt] = struct._1
      def addScore_=(value: CFuncPtr3[Ptr[CUnsignedChar], uint32_t, AddScoreCallback, CInt]): Unit = !struct.at1 = value
      def getPersonalBest : CFuncPtr2[Ptr[CUnsignedChar], PersonalBestCallback, CInt] = struct._2
      def getPersonalBest_=(value: CFuncPtr2[Ptr[CUnsignedChar], PersonalBestCallback, CInt]): Unit = !struct.at2 = value
      def freeScore : CFuncPtr1[Ptr[PDScore], Unit] = struct._3
      def freeScore_=(value: CFuncPtr1[Ptr[PDScore], Unit]): Unit = !struct.at3 = value
      def getScoreboards : CFuncPtr1[BoardsListCallback, CInt] = struct._4
      def getScoreboards_=(value: CFuncPtr1[BoardsListCallback, CInt]): Unit = !struct.at4 = value
      def freeBoardsList : CFuncPtr1[Ptr[PDBoardsList], Unit] = struct._5
      def freeBoardsList_=(value: CFuncPtr1[Ptr[PDBoardsList], Unit]): Unit = !struct.at5 = value
      def getScores : CFuncPtr2[Ptr[CUnsignedChar], ScoresCallback, CInt] = struct._6
      def getScores_=(value: CFuncPtr2[Ptr[CUnsignedChar], ScoresCallback, CInt]): Unit = !struct.at6 = value
      def freeScoresList : CFuncPtr1[Ptr[PDScoresList], Unit] = struct._7
      def freeScoresList_=(value: CFuncPtr1[Ptr[PDScoresList], Unit]): Unit = !struct.at7 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound = CArray[CChar, Nat.Digit2[Nat._9, Nat._6]]
  object playdate_sound:
    given _tag: Tag[playdate_sound] = Tag.CArray[CChar, Nat.Digit2[Nat._9, Nat._6]](Tag.Byte, Tag.Digit2[Nat._9, Nat._6](Tag.Nat9, Tag.Nat6))
    def apply()(using Zone): Ptr[playdate_sound] = scala.scalanative.unsafe.alloc[playdate_sound](1)
    def apply(channel : Ptr[playdate_sound_channel], fileplayer : Ptr[playdate_sound_fileplayer], sample : Ptr[playdate_sound_sample], sampleplayer : Ptr[playdate_sound_sampleplayer], synth : Ptr[playdate_sound_synth], sequence : Ptr[playdate_sound_sequence], effect : Ptr[playdate_sound_effect], lfo : Ptr[playdate_sound_lfo], envelope : Ptr[playdate_sound_envelope], source : Ptr[playdate_sound_source], controlsignal : Ptr[playdate_control_signal], track : Ptr[playdate_sound_track], instrument : Ptr[playdate_sound_instrument], getCurrentTime : CFuncPtr0[uint32_t], addSource : CFuncPtr3[Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]], getDefaultChannel : CFuncPtr0[Ptr[SoundChannel]], addChannel : CFuncPtr1[Ptr[SoundChannel], CInt], removeChannel : CFuncPtr1[Ptr[SoundChannel], CInt], setMicCallback : CFuncPtr3[Ptr[RecordCallback], Ptr[Byte], CInt, Unit], getHeadphoneState : CFuncPtr3[Ptr[CInt], Ptr[CInt], CFuncPtr2[CInt, CInt, Unit], Unit], setOutputsActive : CFuncPtr2[CInt, CInt, Unit], removeSource : CFuncPtr1[Ptr[SoundSource], CInt], signal : Ptr[playdate_sound_signal], getError : CFuncPtr0[Ptr[CUnsignedChar]])(using Zone): Ptr[playdate_sound] =
      val ____ptr = apply()
      (!____ptr).channel = channel
      (!____ptr).fileplayer = fileplayer
      (!____ptr).sample = sample
      (!____ptr).sampleplayer = sampleplayer
      (!____ptr).synth = synth
      (!____ptr).sequence = sequence
      (!____ptr).effect = effect
      (!____ptr).lfo = lfo
      (!____ptr).envelope = envelope
      (!____ptr).source = source
      (!____ptr).controlsignal = controlsignal
      (!____ptr).track = track
      (!____ptr).instrument = instrument
      (!____ptr).getCurrentTime = getCurrentTime
      (!____ptr).addSource = addSource
      (!____ptr).getDefaultChannel = getDefaultChannel
      (!____ptr).addChannel = addChannel
      (!____ptr).removeChannel = removeChannel
      (!____ptr).setMicCallback = setMicCallback
      (!____ptr).getHeadphoneState = getHeadphoneState
      (!____ptr).setOutputsActive = setOutputsActive
      (!____ptr).removeSource = removeSource
      (!____ptr).signal = signal
      (!____ptr).getError = getError
      ____ptr
    extension (struct: playdate_sound)
      def channel: Ptr[playdate_sound_channel] = !struct.at(0).asInstanceOf[Ptr[Ptr[playdate_sound_channel]]]
      def channel_=(value: Ptr[playdate_sound_channel]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[playdate_sound_channel]]] = value
      def fileplayer: Ptr[playdate_sound_fileplayer] = !struct.at(4).asInstanceOf[Ptr[Ptr[playdate_sound_fileplayer]]]
      def fileplayer_=(value: Ptr[playdate_sound_fileplayer]): Unit = !struct.at(4).asInstanceOf[Ptr[Ptr[playdate_sound_fileplayer]]] = value
      def sample: Ptr[playdate_sound_sample] = !struct.at(8).asInstanceOf[Ptr[Ptr[playdate_sound_sample]]]
      def sample_=(value: Ptr[playdate_sound_sample]): Unit = !struct.at(8).asInstanceOf[Ptr[Ptr[playdate_sound_sample]]] = value
      def sampleplayer: Ptr[playdate_sound_sampleplayer] = !struct.at(12).asInstanceOf[Ptr[Ptr[playdate_sound_sampleplayer]]]
      def sampleplayer_=(value: Ptr[playdate_sound_sampleplayer]): Unit = !struct.at(12).asInstanceOf[Ptr[Ptr[playdate_sound_sampleplayer]]] = value
      def synth: Ptr[playdate_sound_synth] = !struct.at(16).asInstanceOf[Ptr[Ptr[playdate_sound_synth]]]
      def synth_=(value: Ptr[playdate_sound_synth]): Unit = !struct.at(16).asInstanceOf[Ptr[Ptr[playdate_sound_synth]]] = value
      def sequence: Ptr[playdate_sound_sequence] = !struct.at(20).asInstanceOf[Ptr[Ptr[playdate_sound_sequence]]]
      def sequence_=(value: Ptr[playdate_sound_sequence]): Unit = !struct.at(20).asInstanceOf[Ptr[Ptr[playdate_sound_sequence]]] = value
      def effect: Ptr[playdate_sound_effect] = !struct.at(24).asInstanceOf[Ptr[Ptr[playdate_sound_effect]]]
      def effect_=(value: Ptr[playdate_sound_effect]): Unit = !struct.at(24).asInstanceOf[Ptr[Ptr[playdate_sound_effect]]] = value
      def lfo: Ptr[playdate_sound_lfo] = !struct.at(28).asInstanceOf[Ptr[Ptr[playdate_sound_lfo]]]
      def lfo_=(value: Ptr[playdate_sound_lfo]): Unit = !struct.at(28).asInstanceOf[Ptr[Ptr[playdate_sound_lfo]]] = value
      def envelope: Ptr[playdate_sound_envelope] = !struct.at(32).asInstanceOf[Ptr[Ptr[playdate_sound_envelope]]]
      def envelope_=(value: Ptr[playdate_sound_envelope]): Unit = !struct.at(32).asInstanceOf[Ptr[Ptr[playdate_sound_envelope]]] = value
      def source: Ptr[playdate_sound_source] = !struct.at(36).asInstanceOf[Ptr[Ptr[playdate_sound_source]]]
      def source_=(value: Ptr[playdate_sound_source]): Unit = !struct.at(36).asInstanceOf[Ptr[Ptr[playdate_sound_source]]] = value
      def controlsignal: Ptr[playdate_control_signal] = !struct.at(40).asInstanceOf[Ptr[Ptr[playdate_control_signal]]]
      def controlsignal_=(value: Ptr[playdate_control_signal]): Unit = !struct.at(40).asInstanceOf[Ptr[Ptr[playdate_control_signal]]] = value
      def track: Ptr[playdate_sound_track] = !struct.at(44).asInstanceOf[Ptr[Ptr[playdate_sound_track]]]
      def track_=(value: Ptr[playdate_sound_track]): Unit = !struct.at(44).asInstanceOf[Ptr[Ptr[playdate_sound_track]]] = value
      def instrument: Ptr[playdate_sound_instrument] = !struct.at(48).asInstanceOf[Ptr[Ptr[playdate_sound_instrument]]]
      def instrument_=(value: Ptr[playdate_sound_instrument]): Unit = !struct.at(48).asInstanceOf[Ptr[Ptr[playdate_sound_instrument]]] = value
      def getCurrentTime: CFuncPtr0[uint32_t] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr0[uint32_t]]]
      def getCurrentTime_=(value: CFuncPtr0[uint32_t]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr0[uint32_t]]] = value
      def addSource: CFuncPtr3[Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]]]]
      def addSource_=(value: CFuncPtr3[Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]]]] = value
      def getDefaultChannel: CFuncPtr0[Ptr[SoundChannel]] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr0[Ptr[SoundChannel]]]]
      def getDefaultChannel_=(value: CFuncPtr0[Ptr[SoundChannel]]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr0[Ptr[SoundChannel]]]] = value
      def addChannel: CFuncPtr1[Ptr[SoundChannel], CInt] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundChannel], CInt]]]
      def addChannel_=(value: CFuncPtr1[Ptr[SoundChannel], CInt]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundChannel], CInt]]] = value
      def removeChannel: CFuncPtr1[Ptr[SoundChannel], CInt] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundChannel], CInt]]]
      def removeChannel_=(value: CFuncPtr1[Ptr[SoundChannel], CInt]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundChannel], CInt]]] = value
      def setMicCallback: CFuncPtr3[Ptr[RecordCallback], Ptr[Byte], CInt, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr3[Ptr[RecordCallback], Ptr[Byte], CInt, Unit]]]
      def setMicCallback_=(value: CFuncPtr3[Ptr[RecordCallback], Ptr[Byte], CInt, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr3[Ptr[RecordCallback], Ptr[Byte], CInt, Unit]]] = value
      def getHeadphoneState: CFuncPtr3[Ptr[CInt], Ptr[CInt], CFuncPtr2[CInt, CInt, Unit], Unit] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr3[Ptr[CInt], Ptr[CInt], CFuncPtr2[CInt, CInt, Unit], Unit]]]
      def getHeadphoneState_=(value: CFuncPtr3[Ptr[CInt], Ptr[CInt], CFuncPtr2[CInt, CInt, Unit], Unit]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr3[Ptr[CInt], Ptr[CInt], CFuncPtr2[CInt, CInt, Unit], Unit]]] = value
      def setOutputsActive: CFuncPtr2[CInt, CInt, Unit] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]]
      def setOutputsActive_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]] = value
      def removeSource: CFuncPtr1[Ptr[SoundSource], CInt] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundSource], CInt]]]
      def removeSource_=(value: CFuncPtr1[Ptr[SoundSource], CInt]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[SoundSource], CInt]]] = value
      def signal: Ptr[playdate_sound_signal] = !struct.at(88).asInstanceOf[Ptr[Ptr[playdate_sound_signal]]]
      def signal_=(value: Ptr[playdate_sound_signal]): Unit = !struct.at(88).asInstanceOf[Ptr[Ptr[playdate_sound_signal]]] = value
      def getError: CFuncPtr0[Ptr[CUnsignedChar]] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr0[Ptr[CUnsignedChar]]]]
      def getError_=(value: CFuncPtr0[Ptr[CUnsignedChar]]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr0[Ptr[CUnsignedChar]]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_channel = CStruct16[CFuncPtr0[Ptr[SoundChannel]], CFuncPtr1[Ptr[SoundChannel], Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], CFuncPtr4[Ptr[SoundChannel], Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundChannel], Float, Unit], CFuncPtr1[Ptr[SoundChannel], Float], CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[SoundChannel], Float, Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]]
  object playdate_sound_channel:
    given _tag: Tag[playdate_sound_channel] = Tag.materializeCStruct16Tag[CFuncPtr0[Ptr[SoundChannel]], CFuncPtr1[Ptr[SoundChannel], Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], CFuncPtr4[Ptr[SoundChannel], Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundChannel], Float, Unit], CFuncPtr1[Ptr[SoundChannel], Float], CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[SoundChannel], Float, Unit], CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_channel] = scala.scalanative.unsafe.alloc[playdate_sound_channel](1)
    def apply(newChannel : CFuncPtr0[Ptr[SoundChannel]], freeChannel : CFuncPtr1[Ptr[SoundChannel], Unit], addSource : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], removeSource : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt], addCallbackSource : CFuncPtr4[Ptr[SoundChannel], Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]], addEffect : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], removeEffect : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit], setVolume : CFuncPtr2[Ptr[SoundChannel], Float, Unit], getVolume : CFuncPtr1[Ptr[SoundChannel], Float], setVolumeModulator : CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], getVolumeModulator : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], setPan : CFuncPtr2[Ptr[SoundChannel], Float, Unit], setPanModulator : CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit], getPanModulator : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], getDryLevelSignal : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]], getWetLevelSignal : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_channel] =
      val ____ptr = apply()
      (!____ptr).newChannel = newChannel
      (!____ptr).freeChannel = freeChannel
      (!____ptr).addSource = addSource
      (!____ptr).removeSource = removeSource
      (!____ptr).addCallbackSource = addCallbackSource
      (!____ptr).addEffect = addEffect
      (!____ptr).removeEffect = removeEffect
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).setVolumeModulator = setVolumeModulator
      (!____ptr).getVolumeModulator = getVolumeModulator
      (!____ptr).setPan = setPan
      (!____ptr).setPanModulator = setPanModulator
      (!____ptr).getPanModulator = getPanModulator
      (!____ptr).getDryLevelSignal = getDryLevelSignal
      (!____ptr).getWetLevelSignal = getWetLevelSignal
      ____ptr
    extension (struct: playdate_sound_channel)
      def newChannel : CFuncPtr0[Ptr[SoundChannel]] = struct._1
      def newChannel_=(value: CFuncPtr0[Ptr[SoundChannel]]): Unit = !struct.at1 = value
      def freeChannel : CFuncPtr1[Ptr[SoundChannel], Unit] = struct._2
      def freeChannel_=(value: CFuncPtr1[Ptr[SoundChannel], Unit]): Unit = !struct.at2 = value
      def addSource : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt] = struct._3
      def addSource_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt]): Unit = !struct.at3 = value
      def removeSource : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt] = struct._4
      def removeSource_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[SoundSource], CInt]): Unit = !struct.at4 = value
      def addCallbackSource : CFuncPtr4[Ptr[SoundChannel], Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]] = struct._5
      def addCallbackSource_=(value: CFuncPtr4[Ptr[SoundChannel], Ptr[AudioSourceFunction], Ptr[Byte], CInt, Ptr[SoundSource]]): Unit = !struct.at5 = value
      def addEffect : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit] = struct._6
      def addEffect_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit]): Unit = !struct.at6 = value
      def removeEffect : CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit] = struct._7
      def removeEffect_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[SoundEffect], Unit]): Unit = !struct.at7 = value
      def setVolume : CFuncPtr2[Ptr[SoundChannel], Float, Unit] = struct._8
      def setVolume_=(value: CFuncPtr2[Ptr[SoundChannel], Float, Unit]): Unit = !struct.at8 = value
      def getVolume : CFuncPtr1[Ptr[SoundChannel], Float] = struct._9
      def getVolume_=(value: CFuncPtr1[Ptr[SoundChannel], Float]): Unit = !struct.at9 = value
      def setVolumeModulator : CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit] = struct._10
      def setVolumeModulator_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at10 = value
      def getVolumeModulator : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]] = struct._11
      def getVolumeModulator_=(value: CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]): Unit = !struct.at11 = value
      def setPan : CFuncPtr2[Ptr[SoundChannel], Float, Unit] = struct._12
      def setPan_=(value: CFuncPtr2[Ptr[SoundChannel], Float, Unit]): Unit = !struct.at12 = value
      def setPanModulator : CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit] = struct._13
      def setPanModulator_=(value: CFuncPtr2[Ptr[SoundChannel], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at13 = value
      def getPanModulator : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]] = struct._14
      def getPanModulator_=(value: CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]): Unit = !struct.at14 = value
      def getDryLevelSignal : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]] = struct._15
      def getDryLevelSignal_=(value: CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]): Unit = !struct.at15 = value
      def getWetLevelSignal : CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]] = struct._16
      def getWetLevelSignal_=(value: CFuncPtr1[Ptr[SoundChannel], Ptr[PDSynthSignalValue]]): Unit = !struct.at16 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect = CStruct13[CFuncPtr2[Ptr[effectProc], Ptr[Byte], Ptr[SoundEffect]], CFuncPtr1[Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundEffect], Float, Unit], CFuncPtr2[Ptr[SoundEffect], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundEffect], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[SoundEffect], Ptr[Byte], Unit], CFuncPtr1[Ptr[SoundEffect], Ptr[Byte]], Ptr[playdate_sound_effect_twopolefilter], Ptr[playdate_sound_effect_onepolefilter], Ptr[playdate_sound_effect_bitcrusher], Ptr[playdate_sound_effect_ringmodulator], Ptr[playdate_sound_effect_delayline], Ptr[playdate_sound_effect_overdrive]]
  object playdate_sound_effect:
    given _tag: Tag[playdate_sound_effect] = Tag.materializeCStruct13Tag[CFuncPtr2[Ptr[effectProc], Ptr[Byte], Ptr[SoundEffect]], CFuncPtr1[Ptr[SoundEffect], Unit], CFuncPtr2[Ptr[SoundEffect], Float, Unit], CFuncPtr2[Ptr[SoundEffect], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[SoundEffect], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[SoundEffect], Ptr[Byte], Unit], CFuncPtr1[Ptr[SoundEffect], Ptr[Byte]], Ptr[playdate_sound_effect_twopolefilter], Ptr[playdate_sound_effect_onepolefilter], Ptr[playdate_sound_effect_bitcrusher], Ptr[playdate_sound_effect_ringmodulator], Ptr[playdate_sound_effect_delayline], Ptr[playdate_sound_effect_overdrive]]
    def apply()(using Zone): Ptr[playdate_sound_effect] = scala.scalanative.unsafe.alloc[playdate_sound_effect](1)
    def apply(newEffect : CFuncPtr2[Ptr[effectProc], Ptr[Byte], Ptr[SoundEffect]], freeEffect : CFuncPtr1[Ptr[SoundEffect], Unit], setMix : CFuncPtr2[Ptr[SoundEffect], Float, Unit], setMixModulator : CFuncPtr2[Ptr[SoundEffect], Ptr[PDSynthSignalValue], Unit], getMixModulator : CFuncPtr1[Ptr[SoundEffect], Ptr[PDSynthSignalValue]], setUserdata : CFuncPtr2[Ptr[SoundEffect], Ptr[Byte], Unit], getUserdata : CFuncPtr1[Ptr[SoundEffect], Ptr[Byte]], twopolefilter : Ptr[playdate_sound_effect_twopolefilter], onepolefilter : Ptr[playdate_sound_effect_onepolefilter], bitcrusher : Ptr[playdate_sound_effect_bitcrusher], ringmodulator : Ptr[playdate_sound_effect_ringmodulator], delayline : Ptr[playdate_sound_effect_delayline], overdrive : Ptr[playdate_sound_effect_overdrive])(using Zone): Ptr[playdate_sound_effect] =
      val ____ptr = apply()
      (!____ptr).newEffect = newEffect
      (!____ptr).freeEffect = freeEffect
      (!____ptr).setMix = setMix
      (!____ptr).setMixModulator = setMixModulator
      (!____ptr).getMixModulator = getMixModulator
      (!____ptr).setUserdata = setUserdata
      (!____ptr).getUserdata = getUserdata
      (!____ptr).twopolefilter = twopolefilter
      (!____ptr).onepolefilter = onepolefilter
      (!____ptr).bitcrusher = bitcrusher
      (!____ptr).ringmodulator = ringmodulator
      (!____ptr).delayline = delayline
      (!____ptr).overdrive = overdrive
      ____ptr
    extension (struct: playdate_sound_effect)
      def newEffect : CFuncPtr2[Ptr[effectProc], Ptr[Byte], Ptr[SoundEffect]] = struct._1
      def newEffect_=(value: CFuncPtr2[Ptr[effectProc], Ptr[Byte], Ptr[SoundEffect]]): Unit = !struct.at1 = value
      def freeEffect : CFuncPtr1[Ptr[SoundEffect], Unit] = struct._2
      def freeEffect_=(value: CFuncPtr1[Ptr[SoundEffect], Unit]): Unit = !struct.at2 = value
      def setMix : CFuncPtr2[Ptr[SoundEffect], Float, Unit] = struct._3
      def setMix_=(value: CFuncPtr2[Ptr[SoundEffect], Float, Unit]): Unit = !struct.at3 = value
      def setMixModulator : CFuncPtr2[Ptr[SoundEffect], Ptr[PDSynthSignalValue], Unit] = struct._4
      def setMixModulator_=(value: CFuncPtr2[Ptr[SoundEffect], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at4 = value
      def getMixModulator : CFuncPtr1[Ptr[SoundEffect], Ptr[PDSynthSignalValue]] = struct._5
      def getMixModulator_=(value: CFuncPtr1[Ptr[SoundEffect], Ptr[PDSynthSignalValue]]): Unit = !struct.at5 = value
      def setUserdata : CFuncPtr2[Ptr[SoundEffect], Ptr[Byte], Unit] = struct._6
      def setUserdata_=(value: CFuncPtr2[Ptr[SoundEffect], Ptr[Byte], Unit]): Unit = !struct.at6 = value
      def getUserdata : CFuncPtr1[Ptr[SoundEffect], Ptr[Byte]] = struct._7
      def getUserdata_=(value: CFuncPtr1[Ptr[SoundEffect], Ptr[Byte]]): Unit = !struct.at7 = value
      def twopolefilter : Ptr[playdate_sound_effect_twopolefilter] = struct._8
      def twopolefilter_=(value: Ptr[playdate_sound_effect_twopolefilter]): Unit = !struct.at8 = value
      def onepolefilter : Ptr[playdate_sound_effect_onepolefilter] = struct._9
      def onepolefilter_=(value: Ptr[playdate_sound_effect_onepolefilter]): Unit = !struct.at9 = value
      def bitcrusher : Ptr[playdate_sound_effect_bitcrusher] = struct._10
      def bitcrusher_=(value: Ptr[playdate_sound_effect_bitcrusher]): Unit = !struct.at10 = value
      def ringmodulator : Ptr[playdate_sound_effect_ringmodulator] = struct._11
      def ringmodulator_=(value: Ptr[playdate_sound_effect_ringmodulator]): Unit = !struct.at11 = value
      def delayline : Ptr[playdate_sound_effect_delayline] = struct._12
      def delayline_=(value: Ptr[playdate_sound_effect_delayline]): Unit = !struct.at12 = value
      def overdrive : Ptr[playdate_sound_effect_overdrive] = struct._13
      def overdrive_=(value: Ptr[playdate_sound_effect_overdrive]): Unit = !struct.at13 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_bitcrusher = CStruct8[CFuncPtr0[Ptr[BitCrusher]], CFuncPtr1[Ptr[BitCrusher], Unit], CFuncPtr2[Ptr[BitCrusher], Float, Unit], CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[BitCrusher], Float, Unit], CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]]]
  object playdate_sound_effect_bitcrusher:
    given _tag: Tag[playdate_sound_effect_bitcrusher] = Tag.materializeCStruct8Tag[CFuncPtr0[Ptr[BitCrusher]], CFuncPtr1[Ptr[BitCrusher], Unit], CFuncPtr2[Ptr[BitCrusher], Float, Unit], CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[BitCrusher], Float, Unit], CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_effect_bitcrusher] = scala.scalanative.unsafe.alloc[playdate_sound_effect_bitcrusher](1)
    def apply(newBitCrusher : CFuncPtr0[Ptr[BitCrusher]], freeBitCrusher : CFuncPtr1[Ptr[BitCrusher], Unit], setAmount : CFuncPtr2[Ptr[BitCrusher], Float, Unit], setAmountModulator : CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], getAmountModulator : CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]], setUndersampling : CFuncPtr2[Ptr[BitCrusher], Float, Unit], setUndersampleModulator : CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit], getUndersampleModulator : CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_effect_bitcrusher] =
      val ____ptr = apply()
      (!____ptr).newBitCrusher = newBitCrusher
      (!____ptr).freeBitCrusher = freeBitCrusher
      (!____ptr).setAmount = setAmount
      (!____ptr).setAmountModulator = setAmountModulator
      (!____ptr).getAmountModulator = getAmountModulator
      (!____ptr).setUndersampling = setUndersampling
      (!____ptr).setUndersampleModulator = setUndersampleModulator
      (!____ptr).getUndersampleModulator = getUndersampleModulator
      ____ptr
    extension (struct: playdate_sound_effect_bitcrusher)
      def newBitCrusher : CFuncPtr0[Ptr[BitCrusher]] = struct._1
      def newBitCrusher_=(value: CFuncPtr0[Ptr[BitCrusher]]): Unit = !struct.at1 = value
      def freeBitCrusher : CFuncPtr1[Ptr[BitCrusher], Unit] = struct._2
      def freeBitCrusher_=(value: CFuncPtr1[Ptr[BitCrusher], Unit]): Unit = !struct.at2 = value
      def setAmount : CFuncPtr2[Ptr[BitCrusher], Float, Unit] = struct._3
      def setAmount_=(value: CFuncPtr2[Ptr[BitCrusher], Float, Unit]): Unit = !struct.at3 = value
      def setAmountModulator : CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit] = struct._4
      def setAmountModulator_=(value: CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at4 = value
      def getAmountModulator : CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]] = struct._5
      def getAmountModulator_=(value: CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]]): Unit = !struct.at5 = value
      def setUndersampling : CFuncPtr2[Ptr[BitCrusher], Float, Unit] = struct._6
      def setUndersampling_=(value: CFuncPtr2[Ptr[BitCrusher], Float, Unit]): Unit = !struct.at6 = value
      def setUndersampleModulator : CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit] = struct._7
      def setUndersampleModulator_=(value: CFuncPtr2[Ptr[BitCrusher], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at7 = value
      def getUndersampleModulator : CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]] = struct._8
      def getUndersampleModulator_=(value: CFuncPtr1[Ptr[BitCrusher], Ptr[PDSynthSignalValue]]): Unit = !struct.at8 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_delayline = CStruct10[CFuncPtr2[CInt, CInt, Ptr[DelayLine]], CFuncPtr1[Ptr[DelayLine], Unit], CFuncPtr2[Ptr[DelayLine], CInt, Unit], CFuncPtr2[Ptr[DelayLine], Float, Unit], CFuncPtr2[Ptr[DelayLine], CInt, Ptr[DelayLineTap]], CFuncPtr1[Ptr[DelayLineTap], Unit], CFuncPtr2[Ptr[DelayLineTap], CInt, Unit], CFuncPtr2[Ptr[DelayLineTap], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[DelayLineTap], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[DelayLineTap], CInt, Unit]]
  object playdate_sound_effect_delayline:
    given _tag: Tag[playdate_sound_effect_delayline] = Tag.materializeCStruct10Tag[CFuncPtr2[CInt, CInt, Ptr[DelayLine]], CFuncPtr1[Ptr[DelayLine], Unit], CFuncPtr2[Ptr[DelayLine], CInt, Unit], CFuncPtr2[Ptr[DelayLine], Float, Unit], CFuncPtr2[Ptr[DelayLine], CInt, Ptr[DelayLineTap]], CFuncPtr1[Ptr[DelayLineTap], Unit], CFuncPtr2[Ptr[DelayLineTap], CInt, Unit], CFuncPtr2[Ptr[DelayLineTap], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[DelayLineTap], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[DelayLineTap], CInt, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_effect_delayline] = scala.scalanative.unsafe.alloc[playdate_sound_effect_delayline](1)
    def apply(newDelayLine : CFuncPtr2[CInt, CInt, Ptr[DelayLine]], freeDelayLine : CFuncPtr1[Ptr[DelayLine], Unit], setLength : CFuncPtr2[Ptr[DelayLine], CInt, Unit], setFeedback : CFuncPtr2[Ptr[DelayLine], Float, Unit], addTap : CFuncPtr2[Ptr[DelayLine], CInt, Ptr[DelayLineTap]], freeTap : CFuncPtr1[Ptr[DelayLineTap], Unit], setTapDelay : CFuncPtr2[Ptr[DelayLineTap], CInt, Unit], setTapDelayModulator : CFuncPtr2[Ptr[DelayLineTap], Ptr[PDSynthSignalValue], Unit], getTapDelayModulator : CFuncPtr1[Ptr[DelayLineTap], Ptr[PDSynthSignalValue]], setTapChannelsFlipped : CFuncPtr2[Ptr[DelayLineTap], CInt, Unit])(using Zone): Ptr[playdate_sound_effect_delayline] =
      val ____ptr = apply()
      (!____ptr).newDelayLine = newDelayLine
      (!____ptr).freeDelayLine = freeDelayLine
      (!____ptr).setLength = setLength
      (!____ptr).setFeedback = setFeedback
      (!____ptr).addTap = addTap
      (!____ptr).freeTap = freeTap
      (!____ptr).setTapDelay = setTapDelay
      (!____ptr).setTapDelayModulator = setTapDelayModulator
      (!____ptr).getTapDelayModulator = getTapDelayModulator
      (!____ptr).setTapChannelsFlipped = setTapChannelsFlipped
      ____ptr
    extension (struct: playdate_sound_effect_delayline)
      def newDelayLine : CFuncPtr2[CInt, CInt, Ptr[DelayLine]] = struct._1
      def newDelayLine_=(value: CFuncPtr2[CInt, CInt, Ptr[DelayLine]]): Unit = !struct.at1 = value
      def freeDelayLine : CFuncPtr1[Ptr[DelayLine], Unit] = struct._2
      def freeDelayLine_=(value: CFuncPtr1[Ptr[DelayLine], Unit]): Unit = !struct.at2 = value
      def setLength : CFuncPtr2[Ptr[DelayLine], CInt, Unit] = struct._3
      def setLength_=(value: CFuncPtr2[Ptr[DelayLine], CInt, Unit]): Unit = !struct.at3 = value
      def setFeedback : CFuncPtr2[Ptr[DelayLine], Float, Unit] = struct._4
      def setFeedback_=(value: CFuncPtr2[Ptr[DelayLine], Float, Unit]): Unit = !struct.at4 = value
      def addTap : CFuncPtr2[Ptr[DelayLine], CInt, Ptr[DelayLineTap]] = struct._5
      def addTap_=(value: CFuncPtr2[Ptr[DelayLine], CInt, Ptr[DelayLineTap]]): Unit = !struct.at5 = value
      def freeTap : CFuncPtr1[Ptr[DelayLineTap], Unit] = struct._6
      def freeTap_=(value: CFuncPtr1[Ptr[DelayLineTap], Unit]): Unit = !struct.at6 = value
      def setTapDelay : CFuncPtr2[Ptr[DelayLineTap], CInt, Unit] = struct._7
      def setTapDelay_=(value: CFuncPtr2[Ptr[DelayLineTap], CInt, Unit]): Unit = !struct.at7 = value
      def setTapDelayModulator : CFuncPtr2[Ptr[DelayLineTap], Ptr[PDSynthSignalValue], Unit] = struct._8
      def setTapDelayModulator_=(value: CFuncPtr2[Ptr[DelayLineTap], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at8 = value
      def getTapDelayModulator : CFuncPtr1[Ptr[DelayLineTap], Ptr[PDSynthSignalValue]] = struct._9
      def getTapDelayModulator_=(value: CFuncPtr1[Ptr[DelayLineTap], Ptr[PDSynthSignalValue]]): Unit = !struct.at9 = value
      def setTapChannelsFlipped : CFuncPtr2[Ptr[DelayLineTap], CInt, Unit] = struct._10
      def setTapChannelsFlipped_=(value: CFuncPtr2[Ptr[DelayLineTap], CInt, Unit]): Unit = !struct.at10 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_onepolefilter = CStruct5[CFuncPtr0[Ptr[OnePoleFilter]], CFuncPtr1[Ptr[OnePoleFilter], Unit], CFuncPtr2[Ptr[OnePoleFilter], Float, Unit], CFuncPtr2[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue]]]
  object playdate_sound_effect_onepolefilter:
    given _tag: Tag[playdate_sound_effect_onepolefilter] = Tag.materializeCStruct5Tag[CFuncPtr0[Ptr[OnePoleFilter]], CFuncPtr1[Ptr[OnePoleFilter], Unit], CFuncPtr2[Ptr[OnePoleFilter], Float, Unit], CFuncPtr2[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_effect_onepolefilter] = scala.scalanative.unsafe.alloc[playdate_sound_effect_onepolefilter](1)
    def apply(newFilter : CFuncPtr0[Ptr[OnePoleFilter]], freeFilter : CFuncPtr1[Ptr[OnePoleFilter], Unit], setParameter : CFuncPtr2[Ptr[OnePoleFilter], Float, Unit], setParameterModulator : CFuncPtr2[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue], Unit], getParameterModulator : CFuncPtr1[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_effect_onepolefilter] =
      val ____ptr = apply()
      (!____ptr).newFilter = newFilter
      (!____ptr).freeFilter = freeFilter
      (!____ptr).setParameter = setParameter
      (!____ptr).setParameterModulator = setParameterModulator
      (!____ptr).getParameterModulator = getParameterModulator
      ____ptr
    extension (struct: playdate_sound_effect_onepolefilter)
      def newFilter : CFuncPtr0[Ptr[OnePoleFilter]] = struct._1
      def newFilter_=(value: CFuncPtr0[Ptr[OnePoleFilter]]): Unit = !struct.at1 = value
      def freeFilter : CFuncPtr1[Ptr[OnePoleFilter], Unit] = struct._2
      def freeFilter_=(value: CFuncPtr1[Ptr[OnePoleFilter], Unit]): Unit = !struct.at2 = value
      def setParameter : CFuncPtr2[Ptr[OnePoleFilter], Float, Unit] = struct._3
      def setParameter_=(value: CFuncPtr2[Ptr[OnePoleFilter], Float, Unit]): Unit = !struct.at3 = value
      def setParameterModulator : CFuncPtr2[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue], Unit] = struct._4
      def setParameterModulator_=(value: CFuncPtr2[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at4 = value
      def getParameterModulator : CFuncPtr1[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue]] = struct._5
      def getParameterModulator_=(value: CFuncPtr1[Ptr[OnePoleFilter], Ptr[PDSynthSignalValue]]): Unit = !struct.at5 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_overdrive = CStruct9[CFuncPtr0[Ptr[Overdrive]], CFuncPtr1[Ptr[Overdrive], Unit], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]]]
  object playdate_sound_effect_overdrive:
    given _tag: Tag[playdate_sound_effect_overdrive] = Tag.materializeCStruct9Tag[CFuncPtr0[Ptr[Overdrive]], CFuncPtr1[Ptr[Overdrive], Unit], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[Overdrive], Float, Unit], CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_effect_overdrive] = scala.scalanative.unsafe.alloc[playdate_sound_effect_overdrive](1)
    def apply(newOverdrive : CFuncPtr0[Ptr[Overdrive]], freeOverdrive : CFuncPtr1[Ptr[Overdrive], Unit], setGain : CFuncPtr2[Ptr[Overdrive], Float, Unit], setLimit : CFuncPtr2[Ptr[Overdrive], Float, Unit], setLimitModulator : CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], getLimitModulator : CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]], setOffset : CFuncPtr2[Ptr[Overdrive], Float, Unit], setOffsetModulator : CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit], getOffsetModulator : CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_effect_overdrive] =
      val ____ptr = apply()
      (!____ptr).newOverdrive = newOverdrive
      (!____ptr).freeOverdrive = freeOverdrive
      (!____ptr).setGain = setGain
      (!____ptr).setLimit = setLimit
      (!____ptr).setLimitModulator = setLimitModulator
      (!____ptr).getLimitModulator = getLimitModulator
      (!____ptr).setOffset = setOffset
      (!____ptr).setOffsetModulator = setOffsetModulator
      (!____ptr).getOffsetModulator = getOffsetModulator
      ____ptr
    extension (struct: playdate_sound_effect_overdrive)
      def newOverdrive : CFuncPtr0[Ptr[Overdrive]] = struct._1
      def newOverdrive_=(value: CFuncPtr0[Ptr[Overdrive]]): Unit = !struct.at1 = value
      def freeOverdrive : CFuncPtr1[Ptr[Overdrive], Unit] = struct._2
      def freeOverdrive_=(value: CFuncPtr1[Ptr[Overdrive], Unit]): Unit = !struct.at2 = value
      def setGain : CFuncPtr2[Ptr[Overdrive], Float, Unit] = struct._3
      def setGain_=(value: CFuncPtr2[Ptr[Overdrive], Float, Unit]): Unit = !struct.at3 = value
      def setLimit : CFuncPtr2[Ptr[Overdrive], Float, Unit] = struct._4
      def setLimit_=(value: CFuncPtr2[Ptr[Overdrive], Float, Unit]): Unit = !struct.at4 = value
      def setLimitModulator : CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit] = struct._5
      def setLimitModulator_=(value: CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at5 = value
      def getLimitModulator : CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]] = struct._6
      def getLimitModulator_=(value: CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]]): Unit = !struct.at6 = value
      def setOffset : CFuncPtr2[Ptr[Overdrive], Float, Unit] = struct._7
      def setOffset_=(value: CFuncPtr2[Ptr[Overdrive], Float, Unit]): Unit = !struct.at7 = value
      def setOffsetModulator : CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit] = struct._8
      def setOffsetModulator_=(value: CFuncPtr2[Ptr[Overdrive], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at8 = value
      def getOffsetModulator : CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]] = struct._9
      def getOffsetModulator_=(value: CFuncPtr1[Ptr[Overdrive], Ptr[PDSynthSignalValue]]): Unit = !struct.at9 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_ringmodulator = CStruct5[CFuncPtr0[Ptr[RingModulator]], CFuncPtr1[Ptr[RingModulator], Unit], CFuncPtr2[Ptr[RingModulator], Float, Unit], CFuncPtr2[Ptr[RingModulator], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[RingModulator], Ptr[PDSynthSignalValue]]]
  object playdate_sound_effect_ringmodulator:
    given _tag: Tag[playdate_sound_effect_ringmodulator] = Tag.materializeCStruct5Tag[CFuncPtr0[Ptr[RingModulator]], CFuncPtr1[Ptr[RingModulator], Unit], CFuncPtr2[Ptr[RingModulator], Float, Unit], CFuncPtr2[Ptr[RingModulator], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[RingModulator], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_effect_ringmodulator] = scala.scalanative.unsafe.alloc[playdate_sound_effect_ringmodulator](1)
    def apply(newRingmod : CFuncPtr0[Ptr[RingModulator]], freeRingmod : CFuncPtr1[Ptr[RingModulator], Unit], setFrequency : CFuncPtr2[Ptr[RingModulator], Float, Unit], setFrequencyModulator : CFuncPtr2[Ptr[RingModulator], Ptr[PDSynthSignalValue], Unit], getFrequencyModulator : CFuncPtr1[Ptr[RingModulator], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_effect_ringmodulator] =
      val ____ptr = apply()
      (!____ptr).newRingmod = newRingmod
      (!____ptr).freeRingmod = freeRingmod
      (!____ptr).setFrequency = setFrequency
      (!____ptr).setFrequencyModulator = setFrequencyModulator
      (!____ptr).getFrequencyModulator = getFrequencyModulator
      ____ptr
    extension (struct: playdate_sound_effect_ringmodulator)
      def newRingmod : CFuncPtr0[Ptr[RingModulator]] = struct._1
      def newRingmod_=(value: CFuncPtr0[Ptr[RingModulator]]): Unit = !struct.at1 = value
      def freeRingmod : CFuncPtr1[Ptr[RingModulator], Unit] = struct._2
      def freeRingmod_=(value: CFuncPtr1[Ptr[RingModulator], Unit]): Unit = !struct.at2 = value
      def setFrequency : CFuncPtr2[Ptr[RingModulator], Float, Unit] = struct._3
      def setFrequency_=(value: CFuncPtr2[Ptr[RingModulator], Float, Unit]): Unit = !struct.at3 = value
      def setFrequencyModulator : CFuncPtr2[Ptr[RingModulator], Ptr[PDSynthSignalValue], Unit] = struct._4
      def setFrequencyModulator_=(value: CFuncPtr2[Ptr[RingModulator], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at4 = value
      def getFrequencyModulator : CFuncPtr1[Ptr[RingModulator], Ptr[PDSynthSignalValue]] = struct._5
      def getFrequencyModulator_=(value: CFuncPtr1[Ptr[RingModulator], Ptr[PDSynthSignalValue]]): Unit = !struct.at5 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_effect_twopolefilter = CStruct10[CFuncPtr0[Ptr[TwoPoleFilter]], CFuncPtr1[Ptr[TwoPoleFilter], Unit], CFuncPtr2[Ptr[TwoPoleFilter], TwoPoleFilterType, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]]]
  object playdate_sound_effect_twopolefilter:
    given _tag: Tag[playdate_sound_effect_twopolefilter] = Tag.materializeCStruct10Tag[CFuncPtr0[Ptr[TwoPoleFilter]], CFuncPtr1[Ptr[TwoPoleFilter], Unit], CFuncPtr2[Ptr[TwoPoleFilter], TwoPoleFilterType, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]]]
    def apply()(using Zone): Ptr[playdate_sound_effect_twopolefilter] = scala.scalanative.unsafe.alloc[playdate_sound_effect_twopolefilter](1)
    def apply(newFilter : CFuncPtr0[Ptr[TwoPoleFilter]], freeFilter : CFuncPtr1[Ptr[TwoPoleFilter], Unit], setType : CFuncPtr2[Ptr[TwoPoleFilter], TwoPoleFilterType, Unit], setFrequency : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], setFrequencyModulator : CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], getFrequencyModulator : CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]], setGain : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], setResonance : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit], setResonanceModulator : CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit], getResonanceModulator : CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]])(using Zone): Ptr[playdate_sound_effect_twopolefilter] =
      val ____ptr = apply()
      (!____ptr).newFilter = newFilter
      (!____ptr).freeFilter = freeFilter
      (!____ptr).setType = setType
      (!____ptr).setFrequency = setFrequency
      (!____ptr).setFrequencyModulator = setFrequencyModulator
      (!____ptr).getFrequencyModulator = getFrequencyModulator
      (!____ptr).setGain = setGain
      (!____ptr).setResonance = setResonance
      (!____ptr).setResonanceModulator = setResonanceModulator
      (!____ptr).getResonanceModulator = getResonanceModulator
      ____ptr
    extension (struct: playdate_sound_effect_twopolefilter)
      def newFilter : CFuncPtr0[Ptr[TwoPoleFilter]] = struct._1
      def newFilter_=(value: CFuncPtr0[Ptr[TwoPoleFilter]]): Unit = !struct.at1 = value
      def freeFilter : CFuncPtr1[Ptr[TwoPoleFilter], Unit] = struct._2
      def freeFilter_=(value: CFuncPtr1[Ptr[TwoPoleFilter], Unit]): Unit = !struct.at2 = value
      def setType : CFuncPtr2[Ptr[TwoPoleFilter], TwoPoleFilterType, Unit] = struct._3
      def setType_=(value: CFuncPtr2[Ptr[TwoPoleFilter], TwoPoleFilterType, Unit]): Unit = !struct.at3 = value
      def setFrequency : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit] = struct._4
      def setFrequency_=(value: CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit]): Unit = !struct.at4 = value
      def setFrequencyModulator : CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit] = struct._5
      def setFrequencyModulator_=(value: CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at5 = value
      def getFrequencyModulator : CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]] = struct._6
      def getFrequencyModulator_=(value: CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]]): Unit = !struct.at6 = value
      def setGain : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit] = struct._7
      def setGain_=(value: CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit]): Unit = !struct.at7 = value
      def setResonance : CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit] = struct._8
      def setResonance_=(value: CFuncPtr2[Ptr[TwoPoleFilter], Float, Unit]): Unit = !struct.at8 = value
      def setResonanceModulator : CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit] = struct._9
      def setResonanceModulator_=(value: CFuncPtr2[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at9 = value
      def getResonanceModulator : CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]] = struct._10
      def getResonanceModulator_=(value: CFuncPtr1[Ptr[TwoPoleFilter], Ptr[PDSynthSignalValue]]): Unit = !struct.at10 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_envelope = CStruct12[CFuncPtr4[Float, Float, Float, Float, Ptr[PDSynthEnvelope]], CFuncPtr1[Ptr[PDSynthEnvelope], Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], CFuncPtr1[Ptr[PDSynthEnvelope], Float], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr4[Ptr[PDSynthEnvelope], Float, MIDINote, MIDINote, Unit]]
  object playdate_sound_envelope:
    given _tag: Tag[playdate_sound_envelope] = Tag.materializeCStruct12Tag[CFuncPtr4[Float, Float, Float, Float, Ptr[PDSynthEnvelope]], CFuncPtr1[Ptr[PDSynthEnvelope], Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], CFuncPtr1[Ptr[PDSynthEnvelope], Float], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], CFuncPtr4[Ptr[PDSynthEnvelope], Float, MIDINote, MIDINote, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_envelope] = scala.scalanative.unsafe.alloc[playdate_sound_envelope](1)
    def apply(newEnvelope : CFuncPtr4[Float, Float, Float, Float, Ptr[PDSynthEnvelope]], freeEnvelope : CFuncPtr1[Ptr[PDSynthEnvelope], Unit], setAttack : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setDecay : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setSustain : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setRelease : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setLegato : CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], setRetrigger : CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit], getValue : CFuncPtr1[Ptr[PDSynthEnvelope], Float], setCurvature : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setVelocitySensitivity : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit], setRateScaling : CFuncPtr4[Ptr[PDSynthEnvelope], Float, MIDINote, MIDINote, Unit])(using Zone): Ptr[playdate_sound_envelope] =
      val ____ptr = apply()
      (!____ptr).newEnvelope = newEnvelope
      (!____ptr).freeEnvelope = freeEnvelope
      (!____ptr).setAttack = setAttack
      (!____ptr).setDecay = setDecay
      (!____ptr).setSustain = setSustain
      (!____ptr).setRelease = setRelease
      (!____ptr).setLegato = setLegato
      (!____ptr).setRetrigger = setRetrigger
      (!____ptr).getValue = getValue
      (!____ptr).setCurvature = setCurvature
      (!____ptr).setVelocitySensitivity = setVelocitySensitivity
      (!____ptr).setRateScaling = setRateScaling
      ____ptr
    extension (struct: playdate_sound_envelope)
      def newEnvelope : CFuncPtr4[Float, Float, Float, Float, Ptr[PDSynthEnvelope]] = struct._1
      def newEnvelope_=(value: CFuncPtr4[Float, Float, Float, Float, Ptr[PDSynthEnvelope]]): Unit = !struct.at1 = value
      def freeEnvelope : CFuncPtr1[Ptr[PDSynthEnvelope], Unit] = struct._2
      def freeEnvelope_=(value: CFuncPtr1[Ptr[PDSynthEnvelope], Unit]): Unit = !struct.at2 = value
      def setAttack : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._3
      def setAttack_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at3 = value
      def setDecay : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._4
      def setDecay_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at4 = value
      def setSustain : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._5
      def setSustain_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at5 = value
      def setRelease : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._6
      def setRelease_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at6 = value
      def setLegato : CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit] = struct._7
      def setLegato_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit]): Unit = !struct.at7 = value
      def setRetrigger : CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit] = struct._8
      def setRetrigger_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], CInt, Unit]): Unit = !struct.at8 = value
      def getValue : CFuncPtr1[Ptr[PDSynthEnvelope], Float] = struct._9
      def getValue_=(value: CFuncPtr1[Ptr[PDSynthEnvelope], Float]): Unit = !struct.at9 = value
      def setCurvature : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._10
      def setCurvature_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at10 = value
      def setVelocitySensitivity : CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit] = struct._11
      def setVelocitySensitivity_=(value: CFuncPtr2[Ptr[PDSynthEnvelope], Float, Unit]): Unit = !struct.at11 = value
      def setRateScaling : CFuncPtr4[Ptr[PDSynthEnvelope], Float, MIDINote, MIDINote, Unit] = struct._12
      def setRateScaling_=(value: CFuncPtr4[Ptr[PDSynthEnvelope], Float, MIDINote, MIDINote, Unit]): Unit = !struct.at12 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_fileplayer = CStruct22[CFuncPtr0[Ptr[FilePlayer]], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr2[Ptr[FilePlayer], Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr2[Ptr[FilePlayer], CInt, CInt], CFuncPtr1[Ptr[FilePlayer], CInt], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], CFuncPtr3[Ptr[FilePlayer], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], CFuncPtr1[Ptr[FilePlayer], CInt], CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr2[Ptr[FilePlayer], CInt, Unit], CFuncPtr5[Ptr[FilePlayer], Float, Float, int32_t, sndCallbackProc, Unit], CFuncPtr4[Ptr[FilePlayer], CFuncPtr3[Ptr[uint8_t], CInt, Ptr[Byte], CInt], Ptr[Byte], Float, Unit]]
  object playdate_sound_fileplayer:
    given _tag: Tag[playdate_sound_fileplayer] = Tag.materializeCStruct22Tag[CFuncPtr0[Ptr[FilePlayer]], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr2[Ptr[FilePlayer], Ptr[CUnsignedChar], CInt], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr2[Ptr[FilePlayer], CInt, CInt], CFuncPtr1[Ptr[FilePlayer], CInt], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr1[Ptr[FilePlayer], Unit], CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], CFuncPtr3[Ptr[FilePlayer], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr2[Ptr[FilePlayer], Float, Unit], CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], CFuncPtr1[Ptr[FilePlayer], CInt], CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr1[Ptr[FilePlayer], Float], CFuncPtr2[Ptr[FilePlayer], CInt, Unit], CFuncPtr5[Ptr[FilePlayer], Float, Float, int32_t, sndCallbackProc, Unit], CFuncPtr4[Ptr[FilePlayer], CFuncPtr3[Ptr[uint8_t], CInt, Ptr[Byte], CInt], Ptr[Byte], Float, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_fileplayer] = scala.scalanative.unsafe.alloc[playdate_sound_fileplayer](1)
    def apply(newPlayer : CFuncPtr0[Ptr[FilePlayer]], freePlayer : CFuncPtr1[Ptr[FilePlayer], Unit], loadIntoPlayer : CFuncPtr2[Ptr[FilePlayer], Ptr[CUnsignedChar], CInt], setBufferLength : CFuncPtr2[Ptr[FilePlayer], Float, Unit], play : CFuncPtr2[Ptr[FilePlayer], CInt, CInt], isPlaying : CFuncPtr1[Ptr[FilePlayer], CInt], pause : CFuncPtr1[Ptr[FilePlayer], Unit], stop : CFuncPtr1[Ptr[FilePlayer], Unit], setVolume : CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], getVolume : CFuncPtr3[Ptr[FilePlayer], Ptr[Float], Ptr[Float], Unit], getLength : CFuncPtr1[Ptr[FilePlayer], Float], setOffset : CFuncPtr2[Ptr[FilePlayer], Float, Unit], setRate : CFuncPtr2[Ptr[FilePlayer], Float, Unit], setLoopRange : CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit], didUnderrun : CFuncPtr1[Ptr[FilePlayer], CInt], setFinishCallback : CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], setLoopCallback : CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit], getOffset : CFuncPtr1[Ptr[FilePlayer], Float], getRate : CFuncPtr1[Ptr[FilePlayer], Float], setStopOnUnderrun : CFuncPtr2[Ptr[FilePlayer], CInt, Unit], fadeVolume : CFuncPtr5[Ptr[FilePlayer], Float, Float, int32_t, sndCallbackProc, Unit], setMP3StreamSource : CFuncPtr4[Ptr[FilePlayer], CFuncPtr3[Ptr[uint8_t], CInt, Ptr[Byte], CInt], Ptr[Byte], Float, Unit])(using Zone): Ptr[playdate_sound_fileplayer] =
      val ____ptr = apply()
      (!____ptr).newPlayer = newPlayer
      (!____ptr).freePlayer = freePlayer
      (!____ptr).loadIntoPlayer = loadIntoPlayer
      (!____ptr).setBufferLength = setBufferLength
      (!____ptr).play = play
      (!____ptr).isPlaying = isPlaying
      (!____ptr).pause = pause
      (!____ptr).stop = stop
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).getLength = getLength
      (!____ptr).setOffset = setOffset
      (!____ptr).setRate = setRate
      (!____ptr).setLoopRange = setLoopRange
      (!____ptr).didUnderrun = didUnderrun
      (!____ptr).setFinishCallback = setFinishCallback
      (!____ptr).setLoopCallback = setLoopCallback
      (!____ptr).getOffset = getOffset
      (!____ptr).getRate = getRate
      (!____ptr).setStopOnUnderrun = setStopOnUnderrun
      (!____ptr).fadeVolume = fadeVolume
      (!____ptr).setMP3StreamSource = setMP3StreamSource
      ____ptr
    extension (struct: playdate_sound_fileplayer)
      def newPlayer : CFuncPtr0[Ptr[FilePlayer]] = struct._1
      def newPlayer_=(value: CFuncPtr0[Ptr[FilePlayer]]): Unit = !struct.at1 = value
      def freePlayer : CFuncPtr1[Ptr[FilePlayer], Unit] = struct._2
      def freePlayer_=(value: CFuncPtr1[Ptr[FilePlayer], Unit]): Unit = !struct.at2 = value
      def loadIntoPlayer : CFuncPtr2[Ptr[FilePlayer], Ptr[CUnsignedChar], CInt] = struct._3
      def loadIntoPlayer_=(value: CFuncPtr2[Ptr[FilePlayer], Ptr[CUnsignedChar], CInt]): Unit = !struct.at3 = value
      def setBufferLength : CFuncPtr2[Ptr[FilePlayer], Float, Unit] = struct._4
      def setBufferLength_=(value: CFuncPtr2[Ptr[FilePlayer], Float, Unit]): Unit = !struct.at4 = value
      def play : CFuncPtr2[Ptr[FilePlayer], CInt, CInt] = struct._5
      def play_=(value: CFuncPtr2[Ptr[FilePlayer], CInt, CInt]): Unit = !struct.at5 = value
      def isPlaying : CFuncPtr1[Ptr[FilePlayer], CInt] = struct._6
      def isPlaying_=(value: CFuncPtr1[Ptr[FilePlayer], CInt]): Unit = !struct.at6 = value
      def pause : CFuncPtr1[Ptr[FilePlayer], Unit] = struct._7
      def pause_=(value: CFuncPtr1[Ptr[FilePlayer], Unit]): Unit = !struct.at7 = value
      def stop : CFuncPtr1[Ptr[FilePlayer], Unit] = struct._8
      def stop_=(value: CFuncPtr1[Ptr[FilePlayer], Unit]): Unit = !struct.at8 = value
      def setVolume : CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit] = struct._9
      def setVolume_=(value: CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit]): Unit = !struct.at9 = value
      def getVolume : CFuncPtr3[Ptr[FilePlayer], Ptr[Float], Ptr[Float], Unit] = struct._10
      def getVolume_=(value: CFuncPtr3[Ptr[FilePlayer], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at10 = value
      def getLength : CFuncPtr1[Ptr[FilePlayer], Float] = struct._11
      def getLength_=(value: CFuncPtr1[Ptr[FilePlayer], Float]): Unit = !struct.at11 = value
      def setOffset : CFuncPtr2[Ptr[FilePlayer], Float, Unit] = struct._12
      def setOffset_=(value: CFuncPtr2[Ptr[FilePlayer], Float, Unit]): Unit = !struct.at12 = value
      def setRate : CFuncPtr2[Ptr[FilePlayer], Float, Unit] = struct._13
      def setRate_=(value: CFuncPtr2[Ptr[FilePlayer], Float, Unit]): Unit = !struct.at13 = value
      def setLoopRange : CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit] = struct._14
      def setLoopRange_=(value: CFuncPtr3[Ptr[FilePlayer], Float, Float, Unit]): Unit = !struct.at14 = value
      def didUnderrun : CFuncPtr1[Ptr[FilePlayer], CInt] = struct._15
      def didUnderrun_=(value: CFuncPtr1[Ptr[FilePlayer], CInt]): Unit = !struct.at15 = value
      def setFinishCallback : CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit] = struct._16
      def setFinishCallback_=(value: CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit]): Unit = !struct.at16 = value
      def setLoopCallback : CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit] = struct._17
      def setLoopCallback_=(value: CFuncPtr2[Ptr[FilePlayer], sndCallbackProc, Unit]): Unit = !struct.at17 = value
      def getOffset : CFuncPtr1[Ptr[FilePlayer], Float] = struct._18
      def getOffset_=(value: CFuncPtr1[Ptr[FilePlayer], Float]): Unit = !struct.at18 = value
      def getRate : CFuncPtr1[Ptr[FilePlayer], Float] = struct._19
      def getRate_=(value: CFuncPtr1[Ptr[FilePlayer], Float]): Unit = !struct.at19 = value
      def setStopOnUnderrun : CFuncPtr2[Ptr[FilePlayer], CInt, Unit] = struct._20
      def setStopOnUnderrun_=(value: CFuncPtr2[Ptr[FilePlayer], CInt, Unit]): Unit = !struct.at20 = value
      def fadeVolume : CFuncPtr5[Ptr[FilePlayer], Float, Float, int32_t, sndCallbackProc, Unit] = struct._21
      def fadeVolume_=(value: CFuncPtr5[Ptr[FilePlayer], Float, Float, int32_t, sndCallbackProc, Unit]): Unit = !struct.at21 = value
      def setMP3StreamSource : CFuncPtr4[Ptr[FilePlayer], CFuncPtr3[Ptr[uint8_t], CInt, Ptr[Byte], CInt], Ptr[Byte], Float, Unit] = struct._22
      def setMP3StreamSource_=(value: CFuncPtr4[Ptr[FilePlayer], CFuncPtr3[Ptr[uint8_t], CInt, Ptr[Byte], CInt], Ptr[Byte], Float, Unit]): Unit = !struct.at22 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_instrument = CStruct13[CFuncPtr0[Ptr[PDSynthInstrument]], CFuncPtr1[Ptr[PDSynthInstrument], Unit], CFuncPtr5[Ptr[PDSynthInstrument], Ptr[PDSynth], MIDINote, MIDINote, Float, CInt], CFuncPtr5[Ptr[PDSynthInstrument], Float, Float, Float, uint32_t, Ptr[PDSynth]], CFuncPtr5[Ptr[PDSynthInstrument], MIDINote, Float, Float, uint32_t, Ptr[PDSynth]], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr3[Ptr[PDSynthInstrument], MIDINote, uint32_t, Unit], CFuncPtr2[Ptr[PDSynthInstrument], uint32_t, Unit], CFuncPtr3[Ptr[PDSynthInstrument], Float, Float, Unit], CFuncPtr3[Ptr[PDSynthInstrument], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[PDSynthInstrument], CInt]]
  object playdate_sound_instrument:
    given _tag: Tag[playdate_sound_instrument] = Tag.materializeCStruct13Tag[CFuncPtr0[Ptr[PDSynthInstrument]], CFuncPtr1[Ptr[PDSynthInstrument], Unit], CFuncPtr5[Ptr[PDSynthInstrument], Ptr[PDSynth], MIDINote, MIDINote, Float, CInt], CFuncPtr5[Ptr[PDSynthInstrument], Float, Float, Float, uint32_t, Ptr[PDSynth]], CFuncPtr5[Ptr[PDSynthInstrument], MIDINote, Float, Float, uint32_t, Ptr[PDSynth]], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], CFuncPtr3[Ptr[PDSynthInstrument], MIDINote, uint32_t, Unit], CFuncPtr2[Ptr[PDSynthInstrument], uint32_t, Unit], CFuncPtr3[Ptr[PDSynthInstrument], Float, Float, Unit], CFuncPtr3[Ptr[PDSynthInstrument], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[PDSynthInstrument], CInt]]
    def apply()(using Zone): Ptr[playdate_sound_instrument] = scala.scalanative.unsafe.alloc[playdate_sound_instrument](1)
    def apply(newInstrument : CFuncPtr0[Ptr[PDSynthInstrument]], freeInstrument : CFuncPtr1[Ptr[PDSynthInstrument], Unit], addVoice : CFuncPtr5[Ptr[PDSynthInstrument], Ptr[PDSynth], MIDINote, MIDINote, Float, CInt], playNote : CFuncPtr5[Ptr[PDSynthInstrument], Float, Float, Float, uint32_t, Ptr[PDSynth]], playMIDINote : CFuncPtr5[Ptr[PDSynthInstrument], MIDINote, Float, Float, uint32_t, Ptr[PDSynth]], setPitchBend : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], setPitchBendRange : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], setTranspose : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit], noteOff : CFuncPtr3[Ptr[PDSynthInstrument], MIDINote, uint32_t, Unit], allNotesOff : CFuncPtr2[Ptr[PDSynthInstrument], uint32_t, Unit], setVolume : CFuncPtr3[Ptr[PDSynthInstrument], Float, Float, Unit], getVolume : CFuncPtr3[Ptr[PDSynthInstrument], Ptr[Float], Ptr[Float], Unit], activeVoiceCount : CFuncPtr1[Ptr[PDSynthInstrument], CInt])(using Zone): Ptr[playdate_sound_instrument] =
      val ____ptr = apply()
      (!____ptr).newInstrument = newInstrument
      (!____ptr).freeInstrument = freeInstrument
      (!____ptr).addVoice = addVoice
      (!____ptr).playNote = playNote
      (!____ptr).playMIDINote = playMIDINote
      (!____ptr).setPitchBend = setPitchBend
      (!____ptr).setPitchBendRange = setPitchBendRange
      (!____ptr).setTranspose = setTranspose
      (!____ptr).noteOff = noteOff
      (!____ptr).allNotesOff = allNotesOff
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).activeVoiceCount = activeVoiceCount
      ____ptr
    extension (struct: playdate_sound_instrument)
      def newInstrument : CFuncPtr0[Ptr[PDSynthInstrument]] = struct._1
      def newInstrument_=(value: CFuncPtr0[Ptr[PDSynthInstrument]]): Unit = !struct.at1 = value
      def freeInstrument : CFuncPtr1[Ptr[PDSynthInstrument], Unit] = struct._2
      def freeInstrument_=(value: CFuncPtr1[Ptr[PDSynthInstrument], Unit]): Unit = !struct.at2 = value
      def addVoice : CFuncPtr5[Ptr[PDSynthInstrument], Ptr[PDSynth], MIDINote, MIDINote, Float, CInt] = struct._3
      def addVoice_=(value: CFuncPtr5[Ptr[PDSynthInstrument], Ptr[PDSynth], MIDINote, MIDINote, Float, CInt]): Unit = !struct.at3 = value
      def playNote : CFuncPtr5[Ptr[PDSynthInstrument], Float, Float, Float, uint32_t, Ptr[PDSynth]] = struct._4
      def playNote_=(value: CFuncPtr5[Ptr[PDSynthInstrument], Float, Float, Float, uint32_t, Ptr[PDSynth]]): Unit = !struct.at4 = value
      def playMIDINote : CFuncPtr5[Ptr[PDSynthInstrument], MIDINote, Float, Float, uint32_t, Ptr[PDSynth]] = struct._5
      def playMIDINote_=(value: CFuncPtr5[Ptr[PDSynthInstrument], MIDINote, Float, Float, uint32_t, Ptr[PDSynth]]): Unit = !struct.at5 = value
      def setPitchBend : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit] = struct._6
      def setPitchBend_=(value: CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit]): Unit = !struct.at6 = value
      def setPitchBendRange : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit] = struct._7
      def setPitchBendRange_=(value: CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit]): Unit = !struct.at7 = value
      def setTranspose : CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit] = struct._8
      def setTranspose_=(value: CFuncPtr2[Ptr[PDSynthInstrument], Float, Unit]): Unit = !struct.at8 = value
      def noteOff : CFuncPtr3[Ptr[PDSynthInstrument], MIDINote, uint32_t, Unit] = struct._9
      def noteOff_=(value: CFuncPtr3[Ptr[PDSynthInstrument], MIDINote, uint32_t, Unit]): Unit = !struct.at9 = value
      def allNotesOff : CFuncPtr2[Ptr[PDSynthInstrument], uint32_t, Unit] = struct._10
      def allNotesOff_=(value: CFuncPtr2[Ptr[PDSynthInstrument], uint32_t, Unit]): Unit = !struct.at10 = value
      def setVolume : CFuncPtr3[Ptr[PDSynthInstrument], Float, Float, Unit] = struct._11
      def setVolume_=(value: CFuncPtr3[Ptr[PDSynthInstrument], Float, Float, Unit]): Unit = !struct.at11 = value
      def getVolume : CFuncPtr3[Ptr[PDSynthInstrument], Ptr[Float], Ptr[Float], Unit] = struct._12
      def getVolume_=(value: CFuncPtr3[Ptr[PDSynthInstrument], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at12 = value
      def activeVoiceCount : CFuncPtr1[Ptr[PDSynthInstrument], CInt] = struct._13
      def activeVoiceCount_=(value: CFuncPtr1[Ptr[PDSynthInstrument], CInt]): Unit = !struct.at13 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_lfo = CStruct14[CFuncPtr1[LFOType, Ptr[PDSynthLFO]], CFuncPtr1[Ptr[PDSynthLFO], Unit], CFuncPtr2[Ptr[PDSynthLFO], LFOType, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr3[Ptr[PDSynthLFO], CInt, Ptr[Float], Unit], CFuncPtr4[Ptr[PDSynthLFO], CFuncPtr2[Ptr[PDSynthLFO], Ptr[Byte], Float], Ptr[Byte], CInt, Unit], CFuncPtr3[Ptr[PDSynthLFO], Float, Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], CFuncPtr1[Ptr[PDSynthLFO], Float], CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]]
  object playdate_sound_lfo:
    given _tag: Tag[playdate_sound_lfo] = Tag.materializeCStruct14Tag[CFuncPtr1[LFOType, Ptr[PDSynthLFO]], CFuncPtr1[Ptr[PDSynthLFO], Unit], CFuncPtr2[Ptr[PDSynthLFO], LFOType, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], CFuncPtr3[Ptr[PDSynthLFO], CInt, Ptr[Float], Unit], CFuncPtr4[Ptr[PDSynthLFO], CFuncPtr2[Ptr[PDSynthLFO], Ptr[Byte], Float], Ptr[Byte], CInt, Unit], CFuncPtr3[Ptr[PDSynthLFO], Float, Float, Unit], CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], CFuncPtr1[Ptr[PDSynthLFO], Float], CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_lfo] = scala.scalanative.unsafe.alloc[playdate_sound_lfo](1)
    def apply(newLFO : CFuncPtr1[LFOType, Ptr[PDSynthLFO]], freeLFO : CFuncPtr1[Ptr[PDSynthLFO], Unit], setType : CFuncPtr2[Ptr[PDSynthLFO], LFOType, Unit], setRate : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], setPhase : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], setCenter : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], setDepth : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit], setArpeggiation : CFuncPtr3[Ptr[PDSynthLFO], CInt, Ptr[Float], Unit], setFunction : CFuncPtr4[Ptr[PDSynthLFO], CFuncPtr2[Ptr[PDSynthLFO], Ptr[Byte], Float], Ptr[Byte], CInt, Unit], setDelay : CFuncPtr3[Ptr[PDSynthLFO], Float, Float, Unit], setRetrigger : CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], getValue : CFuncPtr1[Ptr[PDSynthLFO], Float], setGlobal : CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit], setStartPhase : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit])(using Zone): Ptr[playdate_sound_lfo] =
      val ____ptr = apply()
      (!____ptr).newLFO = newLFO
      (!____ptr).freeLFO = freeLFO
      (!____ptr).setType = setType
      (!____ptr).setRate = setRate
      (!____ptr).setPhase = setPhase
      (!____ptr).setCenter = setCenter
      (!____ptr).setDepth = setDepth
      (!____ptr).setArpeggiation = setArpeggiation
      (!____ptr).setFunction = setFunction
      (!____ptr).setDelay = setDelay
      (!____ptr).setRetrigger = setRetrigger
      (!____ptr).getValue = getValue
      (!____ptr).setGlobal = setGlobal
      (!____ptr).setStartPhase = setStartPhase
      ____ptr
    extension (struct: playdate_sound_lfo)
      def newLFO : CFuncPtr1[LFOType, Ptr[PDSynthLFO]] = struct._1
      def newLFO_=(value: CFuncPtr1[LFOType, Ptr[PDSynthLFO]]): Unit = !struct.at1 = value
      def freeLFO : CFuncPtr1[Ptr[PDSynthLFO], Unit] = struct._2
      def freeLFO_=(value: CFuncPtr1[Ptr[PDSynthLFO], Unit]): Unit = !struct.at2 = value
      def setType : CFuncPtr2[Ptr[PDSynthLFO], LFOType, Unit] = struct._3
      def setType_=(value: CFuncPtr2[Ptr[PDSynthLFO], LFOType, Unit]): Unit = !struct.at3 = value
      def setRate : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit] = struct._4
      def setRate_=(value: CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]): Unit = !struct.at4 = value
      def setPhase : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit] = struct._5
      def setPhase_=(value: CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]): Unit = !struct.at5 = value
      def setCenter : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit] = struct._6
      def setCenter_=(value: CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]): Unit = !struct.at6 = value
      def setDepth : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit] = struct._7
      def setDepth_=(value: CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]): Unit = !struct.at7 = value
      def setArpeggiation : CFuncPtr3[Ptr[PDSynthLFO], CInt, Ptr[Float], Unit] = struct._8
      def setArpeggiation_=(value: CFuncPtr3[Ptr[PDSynthLFO], CInt, Ptr[Float], Unit]): Unit = !struct.at8 = value
      def setFunction : CFuncPtr4[Ptr[PDSynthLFO], CFuncPtr2[Ptr[PDSynthLFO], Ptr[Byte], Float], Ptr[Byte], CInt, Unit] = struct._9
      def setFunction_=(value: CFuncPtr4[Ptr[PDSynthLFO], CFuncPtr2[Ptr[PDSynthLFO], Ptr[Byte], Float], Ptr[Byte], CInt, Unit]): Unit = !struct.at9 = value
      def setDelay : CFuncPtr3[Ptr[PDSynthLFO], Float, Float, Unit] = struct._10
      def setDelay_=(value: CFuncPtr3[Ptr[PDSynthLFO], Float, Float, Unit]): Unit = !struct.at10 = value
      def setRetrigger : CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit] = struct._11
      def setRetrigger_=(value: CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit]): Unit = !struct.at11 = value
      def getValue : CFuncPtr1[Ptr[PDSynthLFO], Float] = struct._12
      def getValue_=(value: CFuncPtr1[Ptr[PDSynthLFO], Float]): Unit = !struct.at12 = value
      def setGlobal : CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit] = struct._13
      def setGlobal_=(value: CFuncPtr2[Ptr[PDSynthLFO], CInt, Unit]): Unit = !struct.at13 = value
      def setStartPhase : CFuncPtr2[Ptr[PDSynthLFO], Float, Unit] = struct._14
      def setStartPhase_=(value: CFuncPtr2[Ptr[PDSynthLFO], Float, Unit]): Unit = !struct.at14 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_sample = CStruct7[CFuncPtr1[CInt, Ptr[AudioSample]], CFuncPtr2[Ptr[AudioSample], Ptr[CUnsignedChar], CInt], CFuncPtr1[Ptr[CUnsignedChar], Ptr[AudioSample]], CFuncPtr4[Ptr[uint8_t], SoundFormat, uint32_t, CInt, Ptr[AudioSample]], CFuncPtr5[Ptr[AudioSample], Ptr[Ptr[uint8_t]], Ptr[SoundFormat], Ptr[uint32_t], Ptr[uint32_t], Unit], CFuncPtr1[Ptr[AudioSample], Unit], CFuncPtr1[Ptr[AudioSample], Float]]
  object playdate_sound_sample:
    given _tag: Tag[playdate_sound_sample] = Tag.materializeCStruct7Tag[CFuncPtr1[CInt, Ptr[AudioSample]], CFuncPtr2[Ptr[AudioSample], Ptr[CUnsignedChar], CInt], CFuncPtr1[Ptr[CUnsignedChar], Ptr[AudioSample]], CFuncPtr4[Ptr[uint8_t], SoundFormat, uint32_t, CInt, Ptr[AudioSample]], CFuncPtr5[Ptr[AudioSample], Ptr[Ptr[uint8_t]], Ptr[SoundFormat], Ptr[uint32_t], Ptr[uint32_t], Unit], CFuncPtr1[Ptr[AudioSample], Unit], CFuncPtr1[Ptr[AudioSample], Float]]
    def apply()(using Zone): Ptr[playdate_sound_sample] = scala.scalanative.unsafe.alloc[playdate_sound_sample](1)
    def apply(newSampleBuffer : CFuncPtr1[CInt, Ptr[AudioSample]], loadIntoSample : CFuncPtr2[Ptr[AudioSample], Ptr[CUnsignedChar], CInt], load : CFuncPtr1[Ptr[CUnsignedChar], Ptr[AudioSample]], newSampleFromData : CFuncPtr4[Ptr[uint8_t], SoundFormat, uint32_t, CInt, Ptr[AudioSample]], getData : CFuncPtr5[Ptr[AudioSample], Ptr[Ptr[uint8_t]], Ptr[SoundFormat], Ptr[uint32_t], Ptr[uint32_t], Unit], freeSample : CFuncPtr1[Ptr[AudioSample], Unit], getLength : CFuncPtr1[Ptr[AudioSample], Float])(using Zone): Ptr[playdate_sound_sample] =
      val ____ptr = apply()
      (!____ptr).newSampleBuffer = newSampleBuffer
      (!____ptr).loadIntoSample = loadIntoSample
      (!____ptr).load = load
      (!____ptr).newSampleFromData = newSampleFromData
      (!____ptr).getData = getData
      (!____ptr).freeSample = freeSample
      (!____ptr).getLength = getLength
      ____ptr
    extension (struct: playdate_sound_sample)
      def newSampleBuffer : CFuncPtr1[CInt, Ptr[AudioSample]] = struct._1
      def newSampleBuffer_=(value: CFuncPtr1[CInt, Ptr[AudioSample]]): Unit = !struct.at1 = value
      def loadIntoSample : CFuncPtr2[Ptr[AudioSample], Ptr[CUnsignedChar], CInt] = struct._2
      def loadIntoSample_=(value: CFuncPtr2[Ptr[AudioSample], Ptr[CUnsignedChar], CInt]): Unit = !struct.at2 = value
      def load : CFuncPtr1[Ptr[CUnsignedChar], Ptr[AudioSample]] = struct._3
      def load_=(value: CFuncPtr1[Ptr[CUnsignedChar], Ptr[AudioSample]]): Unit = !struct.at3 = value
      def newSampleFromData : CFuncPtr4[Ptr[uint8_t], SoundFormat, uint32_t, CInt, Ptr[AudioSample]] = struct._4
      def newSampleFromData_=(value: CFuncPtr4[Ptr[uint8_t], SoundFormat, uint32_t, CInt, Ptr[AudioSample]]): Unit = !struct.at4 = value
      def getData : CFuncPtr5[Ptr[AudioSample], Ptr[Ptr[uint8_t]], Ptr[SoundFormat], Ptr[uint32_t], Ptr[uint32_t], Unit] = struct._5
      def getData_=(value: CFuncPtr5[Ptr[AudioSample], Ptr[Ptr[uint8_t]], Ptr[SoundFormat], Ptr[uint32_t], Ptr[uint32_t], Unit]): Unit = !struct.at5 = value
      def freeSample : CFuncPtr1[Ptr[AudioSample], Unit] = struct._6
      def freeSample_=(value: CFuncPtr1[Ptr[AudioSample], Unit]): Unit = !struct.at6 = value
      def getLength : CFuncPtr1[Ptr[AudioSample], Float] = struct._7
      def getLength_=(value: CFuncPtr1[Ptr[AudioSample], Float]): Unit = !struct.at7 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_sampleplayer = CStruct17[CFuncPtr0[Ptr[SamplePlayer]], CFuncPtr1[Ptr[SamplePlayer], Unit], CFuncPtr2[Ptr[SamplePlayer], Ptr[AudioSample], Unit], CFuncPtr3[Ptr[SamplePlayer], CInt, Float, CInt], CFuncPtr1[Ptr[SamplePlayer], CInt], CFuncPtr1[Ptr[SamplePlayer], Unit], CFuncPtr3[Ptr[SamplePlayer], Float, Float, Unit], CFuncPtr3[Ptr[SamplePlayer], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr2[Ptr[SamplePlayer], Float, Unit], CFuncPtr2[Ptr[SamplePlayer], Float, Unit], CFuncPtr3[Ptr[SamplePlayer], CInt, CInt, Unit], CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr2[Ptr[SamplePlayer], CInt, Unit]]
  object playdate_sound_sampleplayer:
    given _tag: Tag[playdate_sound_sampleplayer] = Tag.materializeCStruct17Tag[CFuncPtr0[Ptr[SamplePlayer]], CFuncPtr1[Ptr[SamplePlayer], Unit], CFuncPtr2[Ptr[SamplePlayer], Ptr[AudioSample], Unit], CFuncPtr3[Ptr[SamplePlayer], CInt, Float, CInt], CFuncPtr1[Ptr[SamplePlayer], CInt], CFuncPtr1[Ptr[SamplePlayer], Unit], CFuncPtr3[Ptr[SamplePlayer], Float, Float, Unit], CFuncPtr3[Ptr[SamplePlayer], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr2[Ptr[SamplePlayer], Float, Unit], CFuncPtr2[Ptr[SamplePlayer], Float, Unit], CFuncPtr3[Ptr[SamplePlayer], CInt, CInt, Unit], CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr1[Ptr[SamplePlayer], Float], CFuncPtr2[Ptr[SamplePlayer], CInt, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_sampleplayer] = scala.scalanative.unsafe.alloc[playdate_sound_sampleplayer](1)
    def apply(newPlayer : CFuncPtr0[Ptr[SamplePlayer]], freePlayer : CFuncPtr1[Ptr[SamplePlayer], Unit], setSample : CFuncPtr2[Ptr[SamplePlayer], Ptr[AudioSample], Unit], play : CFuncPtr3[Ptr[SamplePlayer], CInt, Float, CInt], isPlaying : CFuncPtr1[Ptr[SamplePlayer], CInt], stop : CFuncPtr1[Ptr[SamplePlayer], Unit], setVolume : CFuncPtr3[Ptr[SamplePlayer], Float, Float, Unit], getVolume : CFuncPtr3[Ptr[SamplePlayer], Ptr[Float], Ptr[Float], Unit], getLength : CFuncPtr1[Ptr[SamplePlayer], Float], setOffset : CFuncPtr2[Ptr[SamplePlayer], Float, Unit], setRate : CFuncPtr2[Ptr[SamplePlayer], Float, Unit], setPlayRange : CFuncPtr3[Ptr[SamplePlayer], CInt, CInt, Unit], setFinishCallback : CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], setLoopCallback : CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit], getOffset : CFuncPtr1[Ptr[SamplePlayer], Float], getRate : CFuncPtr1[Ptr[SamplePlayer], Float], setPaused : CFuncPtr2[Ptr[SamplePlayer], CInt, Unit])(using Zone): Ptr[playdate_sound_sampleplayer] =
      val ____ptr = apply()
      (!____ptr).newPlayer = newPlayer
      (!____ptr).freePlayer = freePlayer
      (!____ptr).setSample = setSample
      (!____ptr).play = play
      (!____ptr).isPlaying = isPlaying
      (!____ptr).stop = stop
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).getLength = getLength
      (!____ptr).setOffset = setOffset
      (!____ptr).setRate = setRate
      (!____ptr).setPlayRange = setPlayRange
      (!____ptr).setFinishCallback = setFinishCallback
      (!____ptr).setLoopCallback = setLoopCallback
      (!____ptr).getOffset = getOffset
      (!____ptr).getRate = getRate
      (!____ptr).setPaused = setPaused
      ____ptr
    extension (struct: playdate_sound_sampleplayer)
      def newPlayer : CFuncPtr0[Ptr[SamplePlayer]] = struct._1
      def newPlayer_=(value: CFuncPtr0[Ptr[SamplePlayer]]): Unit = !struct.at1 = value
      def freePlayer : CFuncPtr1[Ptr[SamplePlayer], Unit] = struct._2
      def freePlayer_=(value: CFuncPtr1[Ptr[SamplePlayer], Unit]): Unit = !struct.at2 = value
      def setSample : CFuncPtr2[Ptr[SamplePlayer], Ptr[AudioSample], Unit] = struct._3
      def setSample_=(value: CFuncPtr2[Ptr[SamplePlayer], Ptr[AudioSample], Unit]): Unit = !struct.at3 = value
      def play : CFuncPtr3[Ptr[SamplePlayer], CInt, Float, CInt] = struct._4
      def play_=(value: CFuncPtr3[Ptr[SamplePlayer], CInt, Float, CInt]): Unit = !struct.at4 = value
      def isPlaying : CFuncPtr1[Ptr[SamplePlayer], CInt] = struct._5
      def isPlaying_=(value: CFuncPtr1[Ptr[SamplePlayer], CInt]): Unit = !struct.at5 = value
      def stop : CFuncPtr1[Ptr[SamplePlayer], Unit] = struct._6
      def stop_=(value: CFuncPtr1[Ptr[SamplePlayer], Unit]): Unit = !struct.at6 = value
      def setVolume : CFuncPtr3[Ptr[SamplePlayer], Float, Float, Unit] = struct._7
      def setVolume_=(value: CFuncPtr3[Ptr[SamplePlayer], Float, Float, Unit]): Unit = !struct.at7 = value
      def getVolume : CFuncPtr3[Ptr[SamplePlayer], Ptr[Float], Ptr[Float], Unit] = struct._8
      def getVolume_=(value: CFuncPtr3[Ptr[SamplePlayer], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at8 = value
      def getLength : CFuncPtr1[Ptr[SamplePlayer], Float] = struct._9
      def getLength_=(value: CFuncPtr1[Ptr[SamplePlayer], Float]): Unit = !struct.at9 = value
      def setOffset : CFuncPtr2[Ptr[SamplePlayer], Float, Unit] = struct._10
      def setOffset_=(value: CFuncPtr2[Ptr[SamplePlayer], Float, Unit]): Unit = !struct.at10 = value
      def setRate : CFuncPtr2[Ptr[SamplePlayer], Float, Unit] = struct._11
      def setRate_=(value: CFuncPtr2[Ptr[SamplePlayer], Float, Unit]): Unit = !struct.at11 = value
      def setPlayRange : CFuncPtr3[Ptr[SamplePlayer], CInt, CInt, Unit] = struct._12
      def setPlayRange_=(value: CFuncPtr3[Ptr[SamplePlayer], CInt, CInt, Unit]): Unit = !struct.at12 = value
      def setFinishCallback : CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit] = struct._13
      def setFinishCallback_=(value: CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit]): Unit = !struct.at13 = value
      def setLoopCallback : CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit] = struct._14
      def setLoopCallback_=(value: CFuncPtr2[Ptr[SamplePlayer], sndCallbackProc, Unit]): Unit = !struct.at14 = value
      def getOffset : CFuncPtr1[Ptr[SamplePlayer], Float] = struct._15
      def getOffset_=(value: CFuncPtr1[Ptr[SamplePlayer], Float]): Unit = !struct.at15 = value
      def getRate : CFuncPtr1[Ptr[SamplePlayer], Float] = struct._16
      def getRate_=(value: CFuncPtr1[Ptr[SamplePlayer], Float]): Unit = !struct.at16 = value
      def setPaused : CFuncPtr2[Ptr[SamplePlayer], CInt, Unit] = struct._17
      def setPaused_=(value: CFuncPtr2[Ptr[SamplePlayer], CInt, Unit]): Unit = !struct.at17 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_sequence = CStruct19[CFuncPtr0[Ptr[SoundSequence]], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr2[Ptr[SoundSequence], Ptr[CUnsignedChar], CInt], CFuncPtr1[Ptr[SoundSequence], uint32_t], CFuncPtr2[Ptr[SoundSequence], uint32_t, Unit], CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr2[Ptr[SoundSequence], Float, Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr1[Ptr[SoundSequence], Ptr[SequenceTrack]], CFuncPtr2[Ptr[SoundSequence], CUnsignedInt, Ptr[SequenceTrack]], CFuncPtr3[Ptr[SoundSequence], Ptr[SequenceTrack], CUnsignedInt, Unit], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr1[Ptr[SoundSequence], uint32_t], CFuncPtr3[Ptr[SoundSequence], SequenceFinishedCallback, Ptr[Byte], Unit], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr2[Ptr[SoundSequence], Ptr[CInt], CInt], CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit]]
  object playdate_sound_sequence:
    given _tag: Tag[playdate_sound_sequence] = Tag.materializeCStruct19Tag[CFuncPtr0[Ptr[SoundSequence]], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr2[Ptr[SoundSequence], Ptr[CUnsignedChar], CInt], CFuncPtr1[Ptr[SoundSequence], uint32_t], CFuncPtr2[Ptr[SoundSequence], uint32_t, Unit], CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr2[Ptr[SoundSequence], Float, Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr1[Ptr[SoundSequence], Ptr[SequenceTrack]], CFuncPtr2[Ptr[SoundSequence], CUnsignedInt, Ptr[SequenceTrack]], CFuncPtr3[Ptr[SoundSequence], Ptr[SequenceTrack], CUnsignedInt, Unit], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr1[Ptr[SoundSequence], CInt], CFuncPtr1[Ptr[SoundSequence], uint32_t], CFuncPtr3[Ptr[SoundSequence], SequenceFinishedCallback, Ptr[Byte], Unit], CFuncPtr1[Ptr[SoundSequence], Unit], CFuncPtr2[Ptr[SoundSequence], Ptr[CInt], CInt], CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_sequence] = scala.scalanative.unsafe.alloc[playdate_sound_sequence](1)
    def apply(newSequence : CFuncPtr0[Ptr[SoundSequence]], freeSequence : CFuncPtr1[Ptr[SoundSequence], Unit], loadMidiFile : CFuncPtr2[Ptr[SoundSequence], Ptr[CUnsignedChar], CInt], getTime : CFuncPtr1[Ptr[SoundSequence], uint32_t], setTime : CFuncPtr2[Ptr[SoundSequence], uint32_t, Unit], setLoops : CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit], getTempo : CFuncPtr1[Ptr[SoundSequence], CInt], setTempo : CFuncPtr2[Ptr[SoundSequence], Float, Unit], getTrackCount : CFuncPtr1[Ptr[SoundSequence], CInt], addTrack : CFuncPtr1[Ptr[SoundSequence], Ptr[SequenceTrack]], getTrackAtIndex : CFuncPtr2[Ptr[SoundSequence], CUnsignedInt, Ptr[SequenceTrack]], setTrackAtIndex : CFuncPtr3[Ptr[SoundSequence], Ptr[SequenceTrack], CUnsignedInt, Unit], allNotesOff : CFuncPtr1[Ptr[SoundSequence], Unit], isPlaying : CFuncPtr1[Ptr[SoundSequence], CInt], getLength : CFuncPtr1[Ptr[SoundSequence], uint32_t], play : CFuncPtr3[Ptr[SoundSequence], SequenceFinishedCallback, Ptr[Byte], Unit], stop : CFuncPtr1[Ptr[SoundSequence], Unit], getCurrentStep : CFuncPtr2[Ptr[SoundSequence], Ptr[CInt], CInt], setCurrentStep : CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit])(using Zone): Ptr[playdate_sound_sequence] =
      val ____ptr = apply()
      (!____ptr).newSequence = newSequence
      (!____ptr).freeSequence = freeSequence
      (!____ptr).loadMidiFile = loadMidiFile
      (!____ptr).getTime = getTime
      (!____ptr).setTime = setTime
      (!____ptr).setLoops = setLoops
      (!____ptr).getTempo = getTempo
      (!____ptr).setTempo = setTempo
      (!____ptr).getTrackCount = getTrackCount
      (!____ptr).addTrack = addTrack
      (!____ptr).getTrackAtIndex = getTrackAtIndex
      (!____ptr).setTrackAtIndex = setTrackAtIndex
      (!____ptr).allNotesOff = allNotesOff
      (!____ptr).isPlaying = isPlaying
      (!____ptr).getLength = getLength
      (!____ptr).play = play
      (!____ptr).stop = stop
      (!____ptr).getCurrentStep = getCurrentStep
      (!____ptr).setCurrentStep = setCurrentStep
      ____ptr
    extension (struct: playdate_sound_sequence)
      def newSequence : CFuncPtr0[Ptr[SoundSequence]] = struct._1
      def newSequence_=(value: CFuncPtr0[Ptr[SoundSequence]]): Unit = !struct.at1 = value
      def freeSequence : CFuncPtr1[Ptr[SoundSequence], Unit] = struct._2
      def freeSequence_=(value: CFuncPtr1[Ptr[SoundSequence], Unit]): Unit = !struct.at2 = value
      def loadMidiFile : CFuncPtr2[Ptr[SoundSequence], Ptr[CUnsignedChar], CInt] = struct._3
      def loadMidiFile_=(value: CFuncPtr2[Ptr[SoundSequence], Ptr[CUnsignedChar], CInt]): Unit = !struct.at3 = value
      def getTime : CFuncPtr1[Ptr[SoundSequence], uint32_t] = struct._4
      def getTime_=(value: CFuncPtr1[Ptr[SoundSequence], uint32_t]): Unit = !struct.at4 = value
      def setTime : CFuncPtr2[Ptr[SoundSequence], uint32_t, Unit] = struct._5
      def setTime_=(value: CFuncPtr2[Ptr[SoundSequence], uint32_t, Unit]): Unit = !struct.at5 = value
      def setLoops : CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit] = struct._6
      def setLoops_=(value: CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit]): Unit = !struct.at6 = value
      def getTempo : CFuncPtr1[Ptr[SoundSequence], CInt] = struct._7
      def getTempo_=(value: CFuncPtr1[Ptr[SoundSequence], CInt]): Unit = !struct.at7 = value
      def setTempo : CFuncPtr2[Ptr[SoundSequence], Float, Unit] = struct._8
      def setTempo_=(value: CFuncPtr2[Ptr[SoundSequence], Float, Unit]): Unit = !struct.at8 = value
      def getTrackCount : CFuncPtr1[Ptr[SoundSequence], CInt] = struct._9
      def getTrackCount_=(value: CFuncPtr1[Ptr[SoundSequence], CInt]): Unit = !struct.at9 = value
      def addTrack : CFuncPtr1[Ptr[SoundSequence], Ptr[SequenceTrack]] = struct._10
      def addTrack_=(value: CFuncPtr1[Ptr[SoundSequence], Ptr[SequenceTrack]]): Unit = !struct.at10 = value
      def getTrackAtIndex : CFuncPtr2[Ptr[SoundSequence], CUnsignedInt, Ptr[SequenceTrack]] = struct._11
      def getTrackAtIndex_=(value: CFuncPtr2[Ptr[SoundSequence], CUnsignedInt, Ptr[SequenceTrack]]): Unit = !struct.at11 = value
      def setTrackAtIndex : CFuncPtr3[Ptr[SoundSequence], Ptr[SequenceTrack], CUnsignedInt, Unit] = struct._12
      def setTrackAtIndex_=(value: CFuncPtr3[Ptr[SoundSequence], Ptr[SequenceTrack], CUnsignedInt, Unit]): Unit = !struct.at12 = value
      def allNotesOff : CFuncPtr1[Ptr[SoundSequence], Unit] = struct._13
      def allNotesOff_=(value: CFuncPtr1[Ptr[SoundSequence], Unit]): Unit = !struct.at13 = value
      def isPlaying : CFuncPtr1[Ptr[SoundSequence], CInt] = struct._14
      def isPlaying_=(value: CFuncPtr1[Ptr[SoundSequence], CInt]): Unit = !struct.at14 = value
      def getLength : CFuncPtr1[Ptr[SoundSequence], uint32_t] = struct._15
      def getLength_=(value: CFuncPtr1[Ptr[SoundSequence], uint32_t]): Unit = !struct.at15 = value
      def play : CFuncPtr3[Ptr[SoundSequence], SequenceFinishedCallback, Ptr[Byte], Unit] = struct._16
      def play_=(value: CFuncPtr3[Ptr[SoundSequence], SequenceFinishedCallback, Ptr[Byte], Unit]): Unit = !struct.at16 = value
      def stop : CFuncPtr1[Ptr[SoundSequence], Unit] = struct._17
      def stop_=(value: CFuncPtr1[Ptr[SoundSequence], Unit]): Unit = !struct.at17 = value
      def getCurrentStep : CFuncPtr2[Ptr[SoundSequence], Ptr[CInt], CInt] = struct._18
      def getCurrentStep_=(value: CFuncPtr2[Ptr[SoundSequence], Ptr[CInt], CInt]): Unit = !struct.at18 = value
      def setCurrentStep : CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit] = struct._19
      def setCurrentStep_=(value: CFuncPtr4[Ptr[SoundSequence], CInt, CInt, CInt, Unit]): Unit = !struct.at19 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_signal = CStruct5[CFuncPtr5[signalStepFunc, signalNoteOnFunc, signalNoteOffFunc, signalDeallocFunc, Ptr[Byte], Ptr[PDSynthSignal]], CFuncPtr1[Ptr[PDSynthSignal], Unit], CFuncPtr1[Ptr[PDSynthSignal], Float], CFuncPtr2[Ptr[PDSynthSignal], Float, Unit], CFuncPtr2[Ptr[PDSynthSignal], Float, Unit]]
  object playdate_sound_signal:
    given _tag: Tag[playdate_sound_signal] = Tag.materializeCStruct5Tag[CFuncPtr5[signalStepFunc, signalNoteOnFunc, signalNoteOffFunc, signalDeallocFunc, Ptr[Byte], Ptr[PDSynthSignal]], CFuncPtr1[Ptr[PDSynthSignal], Unit], CFuncPtr1[Ptr[PDSynthSignal], Float], CFuncPtr2[Ptr[PDSynthSignal], Float, Unit], CFuncPtr2[Ptr[PDSynthSignal], Float, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_signal] = scala.scalanative.unsafe.alloc[playdate_sound_signal](1)
    def apply(newSignal : CFuncPtr5[signalStepFunc, signalNoteOnFunc, signalNoteOffFunc, signalDeallocFunc, Ptr[Byte], Ptr[PDSynthSignal]], freeSignal : CFuncPtr1[Ptr[PDSynthSignal], Unit], getValue : CFuncPtr1[Ptr[PDSynthSignal], Float], setValueScale : CFuncPtr2[Ptr[PDSynthSignal], Float, Unit], setValueOffset : CFuncPtr2[Ptr[PDSynthSignal], Float, Unit])(using Zone): Ptr[playdate_sound_signal] =
      val ____ptr = apply()
      (!____ptr).newSignal = newSignal
      (!____ptr).freeSignal = freeSignal
      (!____ptr).getValue = getValue
      (!____ptr).setValueScale = setValueScale
      (!____ptr).setValueOffset = setValueOffset
      ____ptr
    extension (struct: playdate_sound_signal)
      def newSignal : CFuncPtr5[signalStepFunc, signalNoteOnFunc, signalNoteOffFunc, signalDeallocFunc, Ptr[Byte], Ptr[PDSynthSignal]] = struct._1
      def newSignal_=(value: CFuncPtr5[signalStepFunc, signalNoteOnFunc, signalNoteOffFunc, signalDeallocFunc, Ptr[Byte], Ptr[PDSynthSignal]]): Unit = !struct.at1 = value
      def freeSignal : CFuncPtr1[Ptr[PDSynthSignal], Unit] = struct._2
      def freeSignal_=(value: CFuncPtr1[Ptr[PDSynthSignal], Unit]): Unit = !struct.at2 = value
      def getValue : CFuncPtr1[Ptr[PDSynthSignal], Float] = struct._3
      def getValue_=(value: CFuncPtr1[Ptr[PDSynthSignal], Float]): Unit = !struct.at3 = value
      def setValueScale : CFuncPtr2[Ptr[PDSynthSignal], Float, Unit] = struct._4
      def setValueScale_=(value: CFuncPtr2[Ptr[PDSynthSignal], Float, Unit]): Unit = !struct.at4 = value
      def setValueOffset : CFuncPtr2[Ptr[PDSynthSignal], Float, Unit] = struct._5
      def setValueOffset_=(value: CFuncPtr2[Ptr[PDSynthSignal], Float, Unit]): Unit = !struct.at5 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_source = CStruct4[CFuncPtr3[Ptr[SoundSource], Float, Float, Unit], CFuncPtr3[Ptr[SoundSource], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[SoundSource], CInt], CFuncPtr2[Ptr[SoundSource], sndCallbackProc, Unit]]
  object playdate_sound_source:
    given _tag: Tag[playdate_sound_source] = Tag.materializeCStruct4Tag[CFuncPtr3[Ptr[SoundSource], Float, Float, Unit], CFuncPtr3[Ptr[SoundSource], Ptr[Float], Ptr[Float], Unit], CFuncPtr1[Ptr[SoundSource], CInt], CFuncPtr2[Ptr[SoundSource], sndCallbackProc, Unit]]
    def apply()(using Zone): Ptr[playdate_sound_source] = scala.scalanative.unsafe.alloc[playdate_sound_source](1)
    def apply(setVolume : CFuncPtr3[Ptr[SoundSource], Float, Float, Unit], getVolume : CFuncPtr3[Ptr[SoundSource], Ptr[Float], Ptr[Float], Unit], isPlaying : CFuncPtr1[Ptr[SoundSource], CInt], setFinishCallback : CFuncPtr2[Ptr[SoundSource], sndCallbackProc, Unit])(using Zone): Ptr[playdate_sound_source] =
      val ____ptr = apply()
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).isPlaying = isPlaying
      (!____ptr).setFinishCallback = setFinishCallback
      ____ptr
    extension (struct: playdate_sound_source)
      def setVolume : CFuncPtr3[Ptr[SoundSource], Float, Float, Unit] = struct._1
      def setVolume_=(value: CFuncPtr3[Ptr[SoundSource], Float, Float, Unit]): Unit = !struct.at1 = value
      def getVolume : CFuncPtr3[Ptr[SoundSource], Ptr[Float], Ptr[Float], Unit] = struct._2
      def getVolume_=(value: CFuncPtr3[Ptr[SoundSource], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at2 = value
      def isPlaying : CFuncPtr1[Ptr[SoundSource], CInt] = struct._3
      def isPlaying_=(value: CFuncPtr1[Ptr[SoundSource], CInt]): Unit = !struct.at3 = value
      def setFinishCallback : CFuncPtr2[Ptr[SoundSource], sndCallbackProc, Unit] = struct._4
      def setFinishCallback_=(value: CFuncPtr2[Ptr[SoundSource], sndCallbackProc, Unit]): Unit = !struct.at4 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_synth = CArray[CChar, Nat.Digit3[Nat._1, Nat._0, Nat._8]]
  object playdate_sound_synth:
    given _tag: Tag[playdate_sound_synth] = Tag.CArray[CChar, Nat.Digit3[Nat._1, Nat._0, Nat._8]](Tag.Byte, Tag.Digit3[Nat._1, Nat._0, Nat._8](Tag.Nat1, Tag.Nat0, Tag.Nat8))
    def apply()(using Zone): Ptr[playdate_sound_synth] = scala.scalanative.unsafe.alloc[playdate_sound_synth](1)
    def apply(newSynth : CFuncPtr0[Ptr[PDSynth]], freeSynth : CFuncPtr1[Ptr[PDSynth], Unit], setWaveform : CFuncPtr2[Ptr[PDSynth], SoundWaveform, Unit], setGenerator : CFuncPtr8[Ptr[PDSynth], CInt, synthRenderFunc, synthNoteOnFunc, synthReleaseFunc, synthSetParameterFunc, synthDeallocFunc, Ptr[Byte], Unit], setSample : CFuncPtr4[Ptr[PDSynth], Ptr[AudioSample], uint32_t, uint32_t, Unit], setAttackTime : CFuncPtr2[Ptr[PDSynth], Float, Unit], setDecayTime : CFuncPtr2[Ptr[PDSynth], Float, Unit], setSustainLevel : CFuncPtr2[Ptr[PDSynth], Float, Unit], setReleaseTime : CFuncPtr2[Ptr[PDSynth], Float, Unit], setTranspose : CFuncPtr2[Ptr[PDSynth], Float, Unit], setFrequencyModulator : CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit], getFrequencyModulator : CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]], setAmplitudeModulator : CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit], getAmplitudeModulator : CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]], getParameterCount : CFuncPtr1[Ptr[PDSynth], CInt], setParameter : CFuncPtr3[Ptr[PDSynth], CInt, Float, CInt], setParameterModulator : CFuncPtr3[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue], Unit], getParameterModulator : CFuncPtr2[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue]], playNote : CFuncPtr5[Ptr[PDSynth], Float, Float, Float, uint32_t, Unit], playMIDINote : CFuncPtr5[Ptr[PDSynth], MIDINote, Float, Float, uint32_t, Unit], noteOff : CFuncPtr2[Ptr[PDSynth], uint32_t, Unit], stop : CFuncPtr1[Ptr[PDSynth], Unit], setVolume : CFuncPtr3[Ptr[PDSynth], Float, Float, Unit], getVolume : CFuncPtr3[Ptr[PDSynth], Ptr[Float], Ptr[Float], Unit], isPlaying : CFuncPtr1[Ptr[PDSynth], CInt], getEnvelope : CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthEnvelope]], setWavetable : CFuncPtr5[Ptr[PDSynth], Ptr[AudioSample], CInt, CInt, CInt, CInt])(using Zone): Ptr[playdate_sound_synth] =
      val ____ptr = apply()
      (!____ptr).newSynth = newSynth
      (!____ptr).freeSynth = freeSynth
      (!____ptr).setWaveform = setWaveform
      (!____ptr).setGenerator = setGenerator
      (!____ptr).setSample = setSample
      (!____ptr).setAttackTime = setAttackTime
      (!____ptr).setDecayTime = setDecayTime
      (!____ptr).setSustainLevel = setSustainLevel
      (!____ptr).setReleaseTime = setReleaseTime
      (!____ptr).setTranspose = setTranspose
      (!____ptr).setFrequencyModulator = setFrequencyModulator
      (!____ptr).getFrequencyModulator = getFrequencyModulator
      (!____ptr).setAmplitudeModulator = setAmplitudeModulator
      (!____ptr).getAmplitudeModulator = getAmplitudeModulator
      (!____ptr).getParameterCount = getParameterCount
      (!____ptr).setParameter = setParameter
      (!____ptr).setParameterModulator = setParameterModulator
      (!____ptr).getParameterModulator = getParameterModulator
      (!____ptr).playNote = playNote
      (!____ptr).playMIDINote = playMIDINote
      (!____ptr).noteOff = noteOff
      (!____ptr).stop = stop
      (!____ptr).setVolume = setVolume
      (!____ptr).getVolume = getVolume
      (!____ptr).isPlaying = isPlaying
      (!____ptr).getEnvelope = getEnvelope
      (!____ptr).setWavetable = setWavetable
      ____ptr
    extension (struct: playdate_sound_synth)
      def newSynth: CFuncPtr0[Ptr[PDSynth]] = !struct.at(0).asInstanceOf[Ptr[CFuncPtr0[Ptr[PDSynth]]]]
      def newSynth_=(value: CFuncPtr0[Ptr[PDSynth]]): Unit = !struct.at(0).asInstanceOf[Ptr[CFuncPtr0[Ptr[PDSynth]]]] = value
      def freeSynth: CFuncPtr1[Ptr[PDSynth], Unit] = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Unit]]]
      def freeSynth_=(value: CFuncPtr1[Ptr[PDSynth], Unit]): Unit = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Unit]]] = value
      def setWaveform: CFuncPtr2[Ptr[PDSynth], SoundWaveform, Unit] = !struct.at(8).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], SoundWaveform, Unit]]]
      def setWaveform_=(value: CFuncPtr2[Ptr[PDSynth], SoundWaveform, Unit]): Unit = !struct.at(8).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], SoundWaveform, Unit]]] = value
      def setGenerator: CFuncPtr8[Ptr[PDSynth], CInt, synthRenderFunc, synthNoteOnFunc, synthReleaseFunc, synthSetParameterFunc, synthDeallocFunc, Ptr[Byte], Unit] = !struct.at(12).asInstanceOf[Ptr[CFuncPtr8[Ptr[PDSynth], CInt, synthRenderFunc, synthNoteOnFunc, synthReleaseFunc, synthSetParameterFunc, synthDeallocFunc, Ptr[Byte], Unit]]]
      def setGenerator_=(value: CFuncPtr8[Ptr[PDSynth], CInt, synthRenderFunc, synthNoteOnFunc, synthReleaseFunc, synthSetParameterFunc, synthDeallocFunc, Ptr[Byte], Unit]): Unit = !struct.at(12).asInstanceOf[Ptr[CFuncPtr8[Ptr[PDSynth], CInt, synthRenderFunc, synthNoteOnFunc, synthReleaseFunc, synthSetParameterFunc, synthDeallocFunc, Ptr[Byte], Unit]]] = value
      def setSample: CFuncPtr4[Ptr[PDSynth], Ptr[AudioSample], uint32_t, uint32_t, Unit] = !struct.at(16).asInstanceOf[Ptr[CFuncPtr4[Ptr[PDSynth], Ptr[AudioSample], uint32_t, uint32_t, Unit]]]
      def setSample_=(value: CFuncPtr4[Ptr[PDSynth], Ptr[AudioSample], uint32_t, uint32_t, Unit]): Unit = !struct.at(16).asInstanceOf[Ptr[CFuncPtr4[Ptr[PDSynth], Ptr[AudioSample], uint32_t, uint32_t, Unit]]] = value
      def setAttackTime: CFuncPtr2[Ptr[PDSynth], Float, Unit] = !struct.at(20).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]]
      def setAttackTime_=(value: CFuncPtr2[Ptr[PDSynth], Float, Unit]): Unit = !struct.at(20).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]] = value
      def setDecayTime: CFuncPtr2[Ptr[PDSynth], Float, Unit] = !struct.at(24).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]]
      def setDecayTime_=(value: CFuncPtr2[Ptr[PDSynth], Float, Unit]): Unit = !struct.at(24).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]] = value
      def setSustainLevel: CFuncPtr2[Ptr[PDSynth], Float, Unit] = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]]
      def setSustainLevel_=(value: CFuncPtr2[Ptr[PDSynth], Float, Unit]): Unit = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]] = value
      def setReleaseTime: CFuncPtr2[Ptr[PDSynth], Float, Unit] = !struct.at(32).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]]
      def setReleaseTime_=(value: CFuncPtr2[Ptr[PDSynth], Float, Unit]): Unit = !struct.at(32).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]] = value
      def setTranspose: CFuncPtr2[Ptr[PDSynth], Float, Unit] = !struct.at(36).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]]
      def setTranspose_=(value: CFuncPtr2[Ptr[PDSynth], Float, Unit]): Unit = !struct.at(36).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Float, Unit]]] = value
      def setFrequencyModulator: CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit] = !struct.at(40).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]]]
      def setFrequencyModulator_=(value: CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at(40).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]]] = value
      def getFrequencyModulator: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]] = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]]]
      def getFrequencyModulator_=(value: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]): Unit = !struct.at(44).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]]] = value
      def setAmplitudeModulator: CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit] = !struct.at(48).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]]]
      def setAmplitudeModulator_=(value: CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at(48).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], Ptr[PDSynthSignalValue], Unit]]] = value
      def getAmplitudeModulator: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]]]
      def getAmplitudeModulator_=(value: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthSignalValue]]]] = value
      def getParameterCount: CFuncPtr1[Ptr[PDSynth], CInt] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], CInt]]]
      def getParameterCount_=(value: CFuncPtr1[Ptr[PDSynth], CInt]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], CInt]]] = value
      def setParameter: CFuncPtr3[Ptr[PDSynth], CInt, Float, CInt] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], CInt, Float, CInt]]]
      def setParameter_=(value: CFuncPtr3[Ptr[PDSynth], CInt, Float, CInt]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], CInt, Float, CInt]]] = value
      def setParameterModulator: CFuncPtr3[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue], Unit] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue], Unit]]]
      def setParameterModulator_=(value: CFuncPtr3[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue], Unit]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue], Unit]]] = value
      def getParameterModulator: CFuncPtr2[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue]] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue]]]]
      def getParameterModulator_=(value: CFuncPtr2[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue]]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], CInt, Ptr[PDSynthSignalValue]]]] = value
      def playNote: CFuncPtr5[Ptr[PDSynth], Float, Float, Float, uint32_t, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], Float, Float, Float, uint32_t, Unit]]]
      def playNote_=(value: CFuncPtr5[Ptr[PDSynth], Float, Float, Float, uint32_t, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], Float, Float, Float, uint32_t, Unit]]] = value
      def playMIDINote: CFuncPtr5[Ptr[PDSynth], MIDINote, Float, Float, uint32_t, Unit] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], MIDINote, Float, Float, uint32_t, Unit]]]
      def playMIDINote_=(value: CFuncPtr5[Ptr[PDSynth], MIDINote, Float, Float, uint32_t, Unit]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], MIDINote, Float, Float, uint32_t, Unit]]] = value
      def noteOff: CFuncPtr2[Ptr[PDSynth], uint32_t, Unit] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], uint32_t, Unit]]]
      def noteOff_=(value: CFuncPtr2[Ptr[PDSynth], uint32_t, Unit]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDSynth], uint32_t, Unit]]] = value
      def stop: CFuncPtr1[Ptr[PDSynth], Unit] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Unit]]]
      def stop_=(value: CFuncPtr1[Ptr[PDSynth], Unit]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Unit]]] = value
      def setVolume: CFuncPtr3[Ptr[PDSynth], Float, Float, Unit] = !struct.at(88).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], Float, Float, Unit]]]
      def setVolume_=(value: CFuncPtr3[Ptr[PDSynth], Float, Float, Unit]): Unit = !struct.at(88).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], Float, Float, Unit]]] = value
      def getVolume: CFuncPtr3[Ptr[PDSynth], Ptr[Float], Ptr[Float], Unit] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], Ptr[Float], Ptr[Float], Unit]]]
      def getVolume_=(value: CFuncPtr3[Ptr[PDSynth], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDSynth], Ptr[Float], Ptr[Float], Unit]]] = value
      def isPlaying: CFuncPtr1[Ptr[PDSynth], CInt] = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], CInt]]]
      def isPlaying_=(value: CFuncPtr1[Ptr[PDSynth], CInt]): Unit = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], CInt]]] = value
      def getEnvelope: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthEnvelope]] = !struct.at(100).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthEnvelope]]]]
      def getEnvelope_=(value: CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthEnvelope]]): Unit = !struct.at(100).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDSynth], Ptr[PDSynthEnvelope]]]] = value
      def setWavetable: CFuncPtr5[Ptr[PDSynth], Ptr[AudioSample], CInt, CInt, CInt, CInt] = !struct.at(104).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], Ptr[AudioSample], CInt, CInt, CInt, CInt]]]
      def setWavetable_=(value: CFuncPtr5[Ptr[PDSynth], Ptr[AudioSample], CInt, CInt, CInt, CInt]): Unit = !struct.at(104).asInstanceOf[Ptr[CFuncPtr5[Ptr[PDSynth], Ptr[AudioSample], CInt, CInt, CInt, CInt]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  opaque type playdate_sound_track = CStruct17[CFuncPtr0[Ptr[SequenceTrack]], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr2[Ptr[SequenceTrack], Ptr[PDSynthInstrument], Unit], CFuncPtr1[Ptr[SequenceTrack], Ptr[PDSynthInstrument]], CFuncPtr5[Ptr[SequenceTrack], uint32_t, uint32_t, MIDINote, Float, Unit], CFuncPtr3[Ptr[SequenceTrack], uint32_t, MIDINote, Unit], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr2[Ptr[SequenceTrack], CInt, Ptr[ControlSignal]], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr2[Ptr[SequenceTrack], CInt, Unit], CFuncPtr1[Ptr[SequenceTrack], uint32_t], CFuncPtr2[Ptr[SequenceTrack], uint32_t, CInt], CFuncPtr6[Ptr[SequenceTrack], CInt, Ptr[uint32_t], Ptr[uint32_t], Ptr[MIDINote], Ptr[Float], CInt], CFuncPtr3[Ptr[SequenceTrack], CInt, CInt, Ptr[ControlSignal]]]
  object playdate_sound_track:
    given _tag: Tag[playdate_sound_track] = Tag.materializeCStruct17Tag[CFuncPtr0[Ptr[SequenceTrack]], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr2[Ptr[SequenceTrack], Ptr[PDSynthInstrument], Unit], CFuncPtr1[Ptr[SequenceTrack], Ptr[PDSynthInstrument]], CFuncPtr5[Ptr[SequenceTrack], uint32_t, uint32_t, MIDINote, Float, Unit], CFuncPtr3[Ptr[SequenceTrack], uint32_t, MIDINote, Unit], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr2[Ptr[SequenceTrack], CInt, Ptr[ControlSignal]], CFuncPtr1[Ptr[SequenceTrack], Unit], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr1[Ptr[SequenceTrack], CInt], CFuncPtr2[Ptr[SequenceTrack], CInt, Unit], CFuncPtr1[Ptr[SequenceTrack], uint32_t], CFuncPtr2[Ptr[SequenceTrack], uint32_t, CInt], CFuncPtr6[Ptr[SequenceTrack], CInt, Ptr[uint32_t], Ptr[uint32_t], Ptr[MIDINote], Ptr[Float], CInt], CFuncPtr3[Ptr[SequenceTrack], CInt, CInt, Ptr[ControlSignal]]]
    def apply()(using Zone): Ptr[playdate_sound_track] = scala.scalanative.unsafe.alloc[playdate_sound_track](1)
    def apply(newTrack : CFuncPtr0[Ptr[SequenceTrack]], freeTrack : CFuncPtr1[Ptr[SequenceTrack], Unit], setInstrument : CFuncPtr2[Ptr[SequenceTrack], Ptr[PDSynthInstrument], Unit], getInstrument : CFuncPtr1[Ptr[SequenceTrack], Ptr[PDSynthInstrument]], addNoteEvent : CFuncPtr5[Ptr[SequenceTrack], uint32_t, uint32_t, MIDINote, Float, Unit], removeNoteEvent : CFuncPtr3[Ptr[SequenceTrack], uint32_t, MIDINote, Unit], clearNotes : CFuncPtr1[Ptr[SequenceTrack], Unit], getControlSignalCount : CFuncPtr1[Ptr[SequenceTrack], CInt], getControlSignal : CFuncPtr2[Ptr[SequenceTrack], CInt, Ptr[ControlSignal]], clearControlEvents : CFuncPtr1[Ptr[SequenceTrack], Unit], getPolyphony : CFuncPtr1[Ptr[SequenceTrack], CInt], activeVoiceCount : CFuncPtr1[Ptr[SequenceTrack], CInt], setMuted : CFuncPtr2[Ptr[SequenceTrack], CInt, Unit], getLength : CFuncPtr1[Ptr[SequenceTrack], uint32_t], getIndexForStep : CFuncPtr2[Ptr[SequenceTrack], uint32_t, CInt], getNoteAtIndex : CFuncPtr6[Ptr[SequenceTrack], CInt, Ptr[uint32_t], Ptr[uint32_t], Ptr[MIDINote], Ptr[Float], CInt], getSignalForController : CFuncPtr3[Ptr[SequenceTrack], CInt, CInt, Ptr[ControlSignal]])(using Zone): Ptr[playdate_sound_track] =
      val ____ptr = apply()
      (!____ptr).newTrack = newTrack
      (!____ptr).freeTrack = freeTrack
      (!____ptr).setInstrument = setInstrument
      (!____ptr).getInstrument = getInstrument
      (!____ptr).addNoteEvent = addNoteEvent
      (!____ptr).removeNoteEvent = removeNoteEvent
      (!____ptr).clearNotes = clearNotes
      (!____ptr).getControlSignalCount = getControlSignalCount
      (!____ptr).getControlSignal = getControlSignal
      (!____ptr).clearControlEvents = clearControlEvents
      (!____ptr).getPolyphony = getPolyphony
      (!____ptr).activeVoiceCount = activeVoiceCount
      (!____ptr).setMuted = setMuted
      (!____ptr).getLength = getLength
      (!____ptr).getIndexForStep = getIndexForStep
      (!____ptr).getNoteAtIndex = getNoteAtIndex
      (!____ptr).getSignalForController = getSignalForController
      ____ptr
    extension (struct: playdate_sound_track)
      def newTrack : CFuncPtr0[Ptr[SequenceTrack]] = struct._1
      def newTrack_=(value: CFuncPtr0[Ptr[SequenceTrack]]): Unit = !struct.at1 = value
      def freeTrack : CFuncPtr1[Ptr[SequenceTrack], Unit] = struct._2
      def freeTrack_=(value: CFuncPtr1[Ptr[SequenceTrack], Unit]): Unit = !struct.at2 = value
      def setInstrument : CFuncPtr2[Ptr[SequenceTrack], Ptr[PDSynthInstrument], Unit] = struct._3
      def setInstrument_=(value: CFuncPtr2[Ptr[SequenceTrack], Ptr[PDSynthInstrument], Unit]): Unit = !struct.at3 = value
      def getInstrument : CFuncPtr1[Ptr[SequenceTrack], Ptr[PDSynthInstrument]] = struct._4
      def getInstrument_=(value: CFuncPtr1[Ptr[SequenceTrack], Ptr[PDSynthInstrument]]): Unit = !struct.at4 = value
      def addNoteEvent : CFuncPtr5[Ptr[SequenceTrack], uint32_t, uint32_t, MIDINote, Float, Unit] = struct._5
      def addNoteEvent_=(value: CFuncPtr5[Ptr[SequenceTrack], uint32_t, uint32_t, MIDINote, Float, Unit]): Unit = !struct.at5 = value
      def removeNoteEvent : CFuncPtr3[Ptr[SequenceTrack], uint32_t, MIDINote, Unit] = struct._6
      def removeNoteEvent_=(value: CFuncPtr3[Ptr[SequenceTrack], uint32_t, MIDINote, Unit]): Unit = !struct.at6 = value
      def clearNotes : CFuncPtr1[Ptr[SequenceTrack], Unit] = struct._7
      def clearNotes_=(value: CFuncPtr1[Ptr[SequenceTrack], Unit]): Unit = !struct.at7 = value
      def getControlSignalCount : CFuncPtr1[Ptr[SequenceTrack], CInt] = struct._8
      def getControlSignalCount_=(value: CFuncPtr1[Ptr[SequenceTrack], CInt]): Unit = !struct.at8 = value
      def getControlSignal : CFuncPtr2[Ptr[SequenceTrack], CInt, Ptr[ControlSignal]] = struct._9
      def getControlSignal_=(value: CFuncPtr2[Ptr[SequenceTrack], CInt, Ptr[ControlSignal]]): Unit = !struct.at9 = value
      def clearControlEvents : CFuncPtr1[Ptr[SequenceTrack], Unit] = struct._10
      def clearControlEvents_=(value: CFuncPtr1[Ptr[SequenceTrack], Unit]): Unit = !struct.at10 = value
      def getPolyphony : CFuncPtr1[Ptr[SequenceTrack], CInt] = struct._11
      def getPolyphony_=(value: CFuncPtr1[Ptr[SequenceTrack], CInt]): Unit = !struct.at11 = value
      def activeVoiceCount : CFuncPtr1[Ptr[SequenceTrack], CInt] = struct._12
      def activeVoiceCount_=(value: CFuncPtr1[Ptr[SequenceTrack], CInt]): Unit = !struct.at12 = value
      def setMuted : CFuncPtr2[Ptr[SequenceTrack], CInt, Unit] = struct._13
      def setMuted_=(value: CFuncPtr2[Ptr[SequenceTrack], CInt, Unit]): Unit = !struct.at13 = value
      def getLength : CFuncPtr1[Ptr[SequenceTrack], uint32_t] = struct._14
      def getLength_=(value: CFuncPtr1[Ptr[SequenceTrack], uint32_t]): Unit = !struct.at14 = value
      def getIndexForStep : CFuncPtr2[Ptr[SequenceTrack], uint32_t, CInt] = struct._15
      def getIndexForStep_=(value: CFuncPtr2[Ptr[SequenceTrack], uint32_t, CInt]): Unit = !struct.at15 = value
      def getNoteAtIndex : CFuncPtr6[Ptr[SequenceTrack], CInt, Ptr[uint32_t], Ptr[uint32_t], Ptr[MIDINote], Ptr[Float], CInt] = struct._16
      def getNoteAtIndex_=(value: CFuncPtr6[Ptr[SequenceTrack], CInt, Ptr[uint32_t], Ptr[uint32_t], Ptr[MIDINote], Ptr[Float], CInt]): Unit = !struct.at16 = value
      def getSignalForController : CFuncPtr3[Ptr[SequenceTrack], CInt, CInt, Ptr[ControlSignal]] = struct._17
      def getSignalForController_=(value: CFuncPtr3[Ptr[SequenceTrack], CInt, CInt, Ptr[ControlSignal]]): Unit = !struct.at17 = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  opaque type playdate_sprite = CArray[CChar, Nat.Digit3[Nat._2, Nat._5, Nat._2]]
  object playdate_sprite:
    given _tag: Tag[playdate_sprite] = Tag.CArray[CChar, Nat.Digit3[Nat._2, Nat._5, Nat._2]](Tag.Byte, Tag.Digit3[Nat._2, Nat._5, Nat._2](Tag.Nat2, Tag.Nat5, Tag.Nat2))
    def apply()(using Zone): Ptr[playdate_sprite] = scala.scalanative.unsafe.alloc[playdate_sprite](1)
    def apply(setAlwaysRedraw : CFuncPtr1[CInt, Unit], addDirtyRect : CFuncPtr1[LCDRect, Unit], drawSprites : CFuncPtr0[Unit], updateAndDrawSprites : CFuncPtr0[Unit], newSprite : CFuncPtr0[Ptr[LCDSprite]], freeSprite : CFuncPtr1[Ptr[LCDSprite], Unit], copy : CFuncPtr1[Ptr[LCDSprite], Ptr[LCDSprite]], addSprite : CFuncPtr1[Ptr[LCDSprite], Unit], removeSprite : CFuncPtr1[Ptr[LCDSprite], Unit], removeSprites : CFuncPtr2[Ptr[Ptr[LCDSprite]], CInt, Unit], removeAllSprites : CFuncPtr0[Unit], getSpriteCount : CFuncPtr0[CInt], setBounds : CFuncPtr2[Ptr[LCDSprite], PDRect, Unit], getBounds : CFuncPtr1[Ptr[LCDSprite], PDRect], moveTo : CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit], moveBy : CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit], setImage : CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], LCDBitmapFlip, Unit], getImage : CFuncPtr1[Ptr[LCDSprite], Ptr[LCDBitmap]], setSize : CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit], setZIndex : CFuncPtr2[Ptr[LCDSprite], int16_t, Unit], getZIndex : CFuncPtr1[Ptr[LCDSprite], int16_t], setDrawMode : CFuncPtr2[Ptr[LCDSprite], LCDBitmapDrawMode, Unit], setImageFlip : CFuncPtr2[Ptr[LCDSprite], LCDBitmapFlip, Unit], getImageFlip : CFuncPtr1[Ptr[LCDSprite], LCDBitmapFlip], setStencil : CFuncPtr2[Ptr[LCDSprite], Ptr[LCDBitmap], Unit], setClipRect : CFuncPtr2[Ptr[LCDSprite], LCDRect, Unit], clearClipRect : CFuncPtr1[Ptr[LCDSprite], Unit], setClipRectsInRange : CFuncPtr3[LCDRect, CInt, CInt, Unit], clearClipRectsInRange : CFuncPtr2[CInt, CInt, Unit], setUpdatesEnabled : CFuncPtr2[Ptr[LCDSprite], CInt, Unit], updatesEnabled : CFuncPtr1[Ptr[LCDSprite], CInt], setCollisionsEnabled : CFuncPtr2[Ptr[LCDSprite], CInt, Unit], collisionsEnabled : CFuncPtr1[Ptr[LCDSprite], CInt], setVisible : CFuncPtr2[Ptr[LCDSprite], CInt, Unit], isVisible : CFuncPtr1[Ptr[LCDSprite], CInt], setOpaque : CFuncPtr2[Ptr[LCDSprite], CInt, Unit], markDirty : CFuncPtr1[Ptr[LCDSprite], Unit], setTag : CFuncPtr2[Ptr[LCDSprite], uint8_t, Unit], getTag : CFuncPtr1[Ptr[LCDSprite], uint8_t], setIgnoresDrawOffset : CFuncPtr2[Ptr[LCDSprite], CInt, Unit], setUpdateFunction : CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteUpdateFunction], Unit], setDrawFunction : CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteDrawFunction], Unit], getPosition : CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit], resetCollisionWorld : CFuncPtr0[Unit], setCollideRect : CFuncPtr2[Ptr[LCDSprite], PDRect, Unit], getCollideRect : CFuncPtr1[Ptr[LCDSprite], PDRect], clearCollideRect : CFuncPtr1[Ptr[LCDSprite], Unit], setCollisionResponseFunction : CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteCollisionFilterProc], Unit], checkCollisions : CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]], moveWithCollisions : CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]], querySpritesAtPoint : CFuncPtr3[Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]], querySpritesInRect : CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]], querySpritesAlongLine : CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]], querySpriteInfoAlongLine : CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[SpriteQueryInfo]], overlappingSprites : CFuncPtr2[Ptr[LCDSprite], Ptr[CInt], Ptr[Ptr[LCDSprite]]], allOverlappingSprites : CFuncPtr1[Ptr[CInt], Ptr[Ptr[LCDSprite]]], setStencilPattern : CFuncPtr2[Ptr[LCDSprite], CArray[uint8_t, Nat._8], Unit], clearStencil : CFuncPtr1[Ptr[LCDSprite], Unit], setUserdata : CFuncPtr2[Ptr[LCDSprite], Ptr[Byte], Unit], getUserdata : CFuncPtr1[Ptr[LCDSprite], Ptr[Byte]], setStencilImage : CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], CInt, Unit], setCenter : CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit], getCenter : CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit])(using Zone): Ptr[playdate_sprite] =
      val ____ptr = apply()
      (!____ptr).setAlwaysRedraw = setAlwaysRedraw
      (!____ptr).addDirtyRect = addDirtyRect
      (!____ptr).drawSprites = drawSprites
      (!____ptr).updateAndDrawSprites = updateAndDrawSprites
      (!____ptr).newSprite = newSprite
      (!____ptr).freeSprite = freeSprite
      (!____ptr).copy = copy
      (!____ptr).addSprite = addSprite
      (!____ptr).removeSprite = removeSprite
      (!____ptr).removeSprites = removeSprites
      (!____ptr).removeAllSprites = removeAllSprites
      (!____ptr).getSpriteCount = getSpriteCount
      (!____ptr).setBounds = setBounds
      (!____ptr).getBounds = getBounds
      (!____ptr).moveTo = moveTo
      (!____ptr).moveBy = moveBy
      (!____ptr).setImage = setImage
      (!____ptr).getImage = getImage
      (!____ptr).setSize = setSize
      (!____ptr).setZIndex = setZIndex
      (!____ptr).getZIndex = getZIndex
      (!____ptr).setDrawMode = setDrawMode
      (!____ptr).setImageFlip = setImageFlip
      (!____ptr).getImageFlip = getImageFlip
      (!____ptr).setStencil = setStencil
      (!____ptr).setClipRect = setClipRect
      (!____ptr).clearClipRect = clearClipRect
      (!____ptr).setClipRectsInRange = setClipRectsInRange
      (!____ptr).clearClipRectsInRange = clearClipRectsInRange
      (!____ptr).setUpdatesEnabled = setUpdatesEnabled
      (!____ptr).updatesEnabled = updatesEnabled
      (!____ptr).setCollisionsEnabled = setCollisionsEnabled
      (!____ptr).collisionsEnabled = collisionsEnabled
      (!____ptr).setVisible = setVisible
      (!____ptr).isVisible = isVisible
      (!____ptr).setOpaque = setOpaque
      (!____ptr).markDirty = markDirty
      (!____ptr).setTag = setTag
      (!____ptr).getTag = getTag
      (!____ptr).setIgnoresDrawOffset = setIgnoresDrawOffset
      (!____ptr).setUpdateFunction = setUpdateFunction
      (!____ptr).setDrawFunction = setDrawFunction
      (!____ptr).getPosition = getPosition
      (!____ptr).resetCollisionWorld = resetCollisionWorld
      (!____ptr).setCollideRect = setCollideRect
      (!____ptr).getCollideRect = getCollideRect
      (!____ptr).clearCollideRect = clearCollideRect
      (!____ptr).setCollisionResponseFunction = setCollisionResponseFunction
      (!____ptr).checkCollisions = checkCollisions
      (!____ptr).moveWithCollisions = moveWithCollisions
      (!____ptr).querySpritesAtPoint = querySpritesAtPoint
      (!____ptr).querySpritesInRect = querySpritesInRect
      (!____ptr).querySpritesAlongLine = querySpritesAlongLine
      (!____ptr).querySpriteInfoAlongLine = querySpriteInfoAlongLine
      (!____ptr).overlappingSprites = overlappingSprites
      (!____ptr).allOverlappingSprites = allOverlappingSprites
      (!____ptr).setStencilPattern = setStencilPattern
      (!____ptr).clearStencil = clearStencil
      (!____ptr).setUserdata = setUserdata
      (!____ptr).getUserdata = getUserdata
      (!____ptr).setStencilImage = setStencilImage
      (!____ptr).setCenter = setCenter
      (!____ptr).getCenter = getCenter
      ____ptr
    extension (struct: playdate_sprite)
      def setAlwaysRedraw: CFuncPtr1[CInt, Unit] = !struct.at(0).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def setAlwaysRedraw_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(0).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def addDirtyRect: CFuncPtr1[LCDRect, Unit] = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[LCDRect, Unit]]]
      def addDirtyRect_=(value: CFuncPtr1[LCDRect, Unit]): Unit = !struct.at(4).asInstanceOf[Ptr[CFuncPtr1[LCDRect, Unit]]] = value
      def drawSprites: CFuncPtr0[Unit] = !struct.at(8).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def drawSprites_=(value: CFuncPtr0[Unit]): Unit = !struct.at(8).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def updateAndDrawSprites: CFuncPtr0[Unit] = !struct.at(12).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def updateAndDrawSprites_=(value: CFuncPtr0[Unit]): Unit = !struct.at(12).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def newSprite: CFuncPtr0[Ptr[LCDSprite]] = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDSprite]]]]
      def newSprite_=(value: CFuncPtr0[Ptr[LCDSprite]]): Unit = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[Ptr[LCDSprite]]]] = value
      def freeSprite: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(20).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def freeSprite_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(20).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def copy: CFuncPtr1[Ptr[LCDSprite], Ptr[LCDSprite]] = !struct.at(24).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[LCDSprite]]]]
      def copy_=(value: CFuncPtr1[Ptr[LCDSprite], Ptr[LCDSprite]]): Unit = !struct.at(24).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[LCDSprite]]]] = value
      def addSprite: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(28).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def addSprite_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(28).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def removeSprite: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def removeSprite_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(32).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def removeSprites: CFuncPtr2[Ptr[Ptr[LCDSprite]], CInt, Unit] = !struct.at(36).asInstanceOf[Ptr[CFuncPtr2[Ptr[Ptr[LCDSprite]], CInt, Unit]]]
      def removeSprites_=(value: CFuncPtr2[Ptr[Ptr[LCDSprite]], CInt, Unit]): Unit = !struct.at(36).asInstanceOf[Ptr[CFuncPtr2[Ptr[Ptr[LCDSprite]], CInt, Unit]]] = value
      def removeAllSprites: CFuncPtr0[Unit] = !struct.at(40).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def removeAllSprites_=(value: CFuncPtr0[Unit]): Unit = !struct.at(40).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def getSpriteCount: CFuncPtr0[CInt] = !struct.at(44).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def getSpriteCount_=(value: CFuncPtr0[CInt]): Unit = !struct.at(44).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def setBounds: CFuncPtr2[Ptr[LCDSprite], PDRect, Unit] = !struct.at(48).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]]]
      def setBounds_=(value: CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]): Unit = !struct.at(48).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]]] = value
      def getBounds: CFuncPtr1[Ptr[LCDSprite], PDRect] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], PDRect]]]
      def getBounds_=(value: CFuncPtr1[Ptr[LCDSprite], PDRect]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], PDRect]]] = value
      def moveTo: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]]
      def moveTo_=(value: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]] = value
      def moveBy: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]]
      def moveBy_=(value: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]] = value
      def setImage: CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], LCDBitmapFlip, Unit] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], LCDBitmapFlip, Unit]]]
      def setImage_=(value: CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], LCDBitmapFlip, Unit]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], LCDBitmapFlip, Unit]]] = value
      def getImage: CFuncPtr1[Ptr[LCDSprite], Ptr[LCDBitmap]] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[LCDBitmap]]]]
      def getImage_=(value: CFuncPtr1[Ptr[LCDSprite], Ptr[LCDBitmap]]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[LCDBitmap]]]] = value
      def setSize: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]]
      def setSize_=(value: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]] = value
      def setZIndex: CFuncPtr2[Ptr[LCDSprite], int16_t, Unit] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], int16_t, Unit]]]
      def setZIndex_=(value: CFuncPtr2[Ptr[LCDSprite], int16_t, Unit]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], int16_t, Unit]]] = value
      def getZIndex: CFuncPtr1[Ptr[LCDSprite], int16_t] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], int16_t]]]
      def getZIndex_=(value: CFuncPtr1[Ptr[LCDSprite], int16_t]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], int16_t]]] = value
      def setDrawMode: CFuncPtr2[Ptr[LCDSprite], LCDBitmapDrawMode, Unit] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDBitmapDrawMode, Unit]]]
      def setDrawMode_=(value: CFuncPtr2[Ptr[LCDSprite], LCDBitmapDrawMode, Unit]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDBitmapDrawMode, Unit]]] = value
      def setImageFlip: CFuncPtr2[Ptr[LCDSprite], LCDBitmapFlip, Unit] = !struct.at(88).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDBitmapFlip, Unit]]]
      def setImageFlip_=(value: CFuncPtr2[Ptr[LCDSprite], LCDBitmapFlip, Unit]): Unit = !struct.at(88).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDBitmapFlip, Unit]]] = value
      def getImageFlip: CFuncPtr1[Ptr[LCDSprite], LCDBitmapFlip] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], LCDBitmapFlip]]]
      def getImageFlip_=(value: CFuncPtr1[Ptr[LCDSprite], LCDBitmapFlip]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], LCDBitmapFlip]]] = value
      def setStencil: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDBitmap], Unit] = !struct.at(96).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDBitmap], Unit]]]
      def setStencil_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDBitmap], Unit]): Unit = !struct.at(96).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDBitmap], Unit]]] = value
      def setClipRect: CFuncPtr2[Ptr[LCDSprite], LCDRect, Unit] = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDRect, Unit]]]
      def setClipRect_=(value: CFuncPtr2[Ptr[LCDSprite], LCDRect, Unit]): Unit = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], LCDRect, Unit]]] = value
      def clearClipRect: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def clearClipRect_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def setClipRectsInRange: CFuncPtr3[LCDRect, CInt, CInt, Unit] = !struct.at(108).asInstanceOf[Ptr[CFuncPtr3[LCDRect, CInt, CInt, Unit]]]
      def setClipRectsInRange_=(value: CFuncPtr3[LCDRect, CInt, CInt, Unit]): Unit = !struct.at(108).asInstanceOf[Ptr[CFuncPtr3[LCDRect, CInt, CInt, Unit]]] = value
      def clearClipRectsInRange: CFuncPtr2[CInt, CInt, Unit] = !struct.at(112).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]]
      def clearClipRectsInRange_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at(112).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]] = value
      def setUpdatesEnabled: CFuncPtr2[Ptr[LCDSprite], CInt, Unit] = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]]
      def setUpdatesEnabled_=(value: CFuncPtr2[Ptr[LCDSprite], CInt, Unit]): Unit = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]] = value
      def updatesEnabled: CFuncPtr1[Ptr[LCDSprite], CInt] = !struct.at(120).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]]
      def updatesEnabled_=(value: CFuncPtr1[Ptr[LCDSprite], CInt]): Unit = !struct.at(120).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]] = value
      def setCollisionsEnabled: CFuncPtr2[Ptr[LCDSprite], CInt, Unit] = !struct.at(124).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]]
      def setCollisionsEnabled_=(value: CFuncPtr2[Ptr[LCDSprite], CInt, Unit]): Unit = !struct.at(124).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]] = value
      def collisionsEnabled: CFuncPtr1[Ptr[LCDSprite], CInt] = !struct.at(128).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]]
      def collisionsEnabled_=(value: CFuncPtr1[Ptr[LCDSprite], CInt]): Unit = !struct.at(128).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]] = value
      def setVisible: CFuncPtr2[Ptr[LCDSprite], CInt, Unit] = !struct.at(132).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]]
      def setVisible_=(value: CFuncPtr2[Ptr[LCDSprite], CInt, Unit]): Unit = !struct.at(132).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]] = value
      def isVisible: CFuncPtr1[Ptr[LCDSprite], CInt] = !struct.at(136).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]]
      def isVisible_=(value: CFuncPtr1[Ptr[LCDSprite], CInt]): Unit = !struct.at(136).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], CInt]]] = value
      def setOpaque: CFuncPtr2[Ptr[LCDSprite], CInt, Unit] = !struct.at(140).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]]
      def setOpaque_=(value: CFuncPtr2[Ptr[LCDSprite], CInt, Unit]): Unit = !struct.at(140).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]] = value
      def markDirty: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(144).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def markDirty_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(144).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def setTag: CFuncPtr2[Ptr[LCDSprite], uint8_t, Unit] = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], uint8_t, Unit]]]
      def setTag_=(value: CFuncPtr2[Ptr[LCDSprite], uint8_t, Unit]): Unit = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], uint8_t, Unit]]] = value
      def getTag: CFuncPtr1[Ptr[LCDSprite], uint8_t] = !struct.at(152).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], uint8_t]]]
      def getTag_=(value: CFuncPtr1[Ptr[LCDSprite], uint8_t]): Unit = !struct.at(152).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], uint8_t]]] = value
      def setIgnoresDrawOffset: CFuncPtr2[Ptr[LCDSprite], CInt, Unit] = !struct.at(156).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]]
      def setIgnoresDrawOffset_=(value: CFuncPtr2[Ptr[LCDSprite], CInt, Unit]): Unit = !struct.at(156).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CInt, Unit]]] = value
      def setUpdateFunction: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteUpdateFunction], Unit] = !struct.at(160).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteUpdateFunction], Unit]]]
      def setUpdateFunction_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteUpdateFunction], Unit]): Unit = !struct.at(160).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteUpdateFunction], Unit]]] = value
      def setDrawFunction: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteDrawFunction], Unit] = !struct.at(164).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteDrawFunction], Unit]]]
      def setDrawFunction_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteDrawFunction], Unit]): Unit = !struct.at(164).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteDrawFunction], Unit]]] = value
      def getPosition: CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit] = !struct.at(168).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]]]
      def getPosition_=(value: CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at(168).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]]] = value
      def resetCollisionWorld: CFuncPtr0[Unit] = !struct.at(172).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def resetCollisionWorld_=(value: CFuncPtr0[Unit]): Unit = !struct.at(172).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def setCollideRect: CFuncPtr2[Ptr[LCDSprite], PDRect, Unit] = !struct.at(176).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]]]
      def setCollideRect_=(value: CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]): Unit = !struct.at(176).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], PDRect, Unit]]] = value
      def getCollideRect: CFuncPtr1[Ptr[LCDSprite], PDRect] = !struct.at(180).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], PDRect]]]
      def getCollideRect_=(value: CFuncPtr1[Ptr[LCDSprite], PDRect]): Unit = !struct.at(180).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], PDRect]]] = value
      def clearCollideRect: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(184).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def clearCollideRect_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(184).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def setCollisionResponseFunction: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteCollisionFilterProc], Unit] = !struct.at(188).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteCollisionFilterProc], Unit]]]
      def setCollisionResponseFunction_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteCollisionFilterProc], Unit]): Unit = !struct.at(188).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[LCDSpriteCollisionFilterProc], Unit]]] = value
      def checkCollisions: CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]] = !struct.at(192).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]]]
      def checkCollisions_=(value: CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]): Unit = !struct.at(192).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]]] = value
      def moveWithCollisions: CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]] = !struct.at(196).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]]]
      def moveWithCollisions_=(value: CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]): Unit = !struct.at(196).asInstanceOf[Ptr[CFuncPtr6[Ptr[LCDSprite], Float, Float, Ptr[Float], Ptr[Float], Ptr[CInt], Ptr[SpriteCollisionInfo]]]] = value
      def querySpritesAtPoint: CFuncPtr3[Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]] = !struct.at(200).asInstanceOf[Ptr[CFuncPtr3[Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]]
      def querySpritesAtPoint_=(value: CFuncPtr3[Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]): Unit = !struct.at(200).asInstanceOf[Ptr[CFuncPtr3[Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]] = value
      def querySpritesInRect: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]] = !struct.at(204).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]]
      def querySpritesInRect_=(value: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]): Unit = !struct.at(204).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]] = value
      def querySpritesAlongLine: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]] = !struct.at(208).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]]
      def querySpritesAlongLine_=(value: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]): Unit = !struct.at(208).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[Ptr[LCDSprite]]]]] = value
      def querySpriteInfoAlongLine: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[SpriteQueryInfo]] = !struct.at(212).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[SpriteQueryInfo]]]]
      def querySpriteInfoAlongLine_=(value: CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[SpriteQueryInfo]]): Unit = !struct.at(212).asInstanceOf[Ptr[CFuncPtr5[Float, Float, Float, Float, Ptr[CInt], Ptr[SpriteQueryInfo]]]] = value
      def overlappingSprites: CFuncPtr2[Ptr[LCDSprite], Ptr[CInt], Ptr[Ptr[LCDSprite]]] = !struct.at(216).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[CInt], Ptr[Ptr[LCDSprite]]]]]
      def overlappingSprites_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[CInt], Ptr[Ptr[LCDSprite]]]): Unit = !struct.at(216).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[CInt], Ptr[Ptr[LCDSprite]]]]] = value
      def allOverlappingSprites: CFuncPtr1[Ptr[CInt], Ptr[Ptr[LCDSprite]]] = !struct.at(220).asInstanceOf[Ptr[CFuncPtr1[Ptr[CInt], Ptr[Ptr[LCDSprite]]]]]
      def allOverlappingSprites_=(value: CFuncPtr1[Ptr[CInt], Ptr[Ptr[LCDSprite]]]): Unit = !struct.at(220).asInstanceOf[Ptr[CFuncPtr1[Ptr[CInt], Ptr[Ptr[LCDSprite]]]]] = value
      def setStencilPattern: CFuncPtr2[Ptr[LCDSprite], CArray[uint8_t, Nat._8], Unit] = !struct.at(224).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CArray[uint8_t, Nat._8], Unit]]]
      def setStencilPattern_=(value: CFuncPtr2[Ptr[LCDSprite], CArray[uint8_t, Nat._8], Unit]): Unit = !struct.at(224).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], CArray[uint8_t, Nat._8], Unit]]] = value
      def clearStencil: CFuncPtr1[Ptr[LCDSprite], Unit] = !struct.at(228).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]]
      def clearStencil_=(value: CFuncPtr1[Ptr[LCDSprite], Unit]): Unit = !struct.at(228).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Unit]]] = value
      def setUserdata: CFuncPtr2[Ptr[LCDSprite], Ptr[Byte], Unit] = !struct.at(232).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[Byte], Unit]]]
      def setUserdata_=(value: CFuncPtr2[Ptr[LCDSprite], Ptr[Byte], Unit]): Unit = !struct.at(232).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDSprite], Ptr[Byte], Unit]]] = value
      def getUserdata: CFuncPtr1[Ptr[LCDSprite], Ptr[Byte]] = !struct.at(236).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[Byte]]]]
      def getUserdata_=(value: CFuncPtr1[Ptr[LCDSprite], Ptr[Byte]]): Unit = !struct.at(236).asInstanceOf[Ptr[CFuncPtr1[Ptr[LCDSprite], Ptr[Byte]]]] = value
      def setStencilImage: CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], CInt, Unit] = !struct.at(240).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], CInt, Unit]]]
      def setStencilImage_=(value: CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], CInt, Unit]): Unit = !struct.at(240).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[LCDBitmap], CInt, Unit]]] = value
      def setCenter: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit] = !struct.at(244).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]]
      def setCenter_=(value: CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]): Unit = !struct.at(244).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Float, Float, Unit]]] = value
      def getCenter: CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit] = !struct.at(248).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]]]
      def getCenter_=(value: CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at(248).asInstanceOf[Ptr[CFuncPtr3[Ptr[LCDSprite], Ptr[Float], Ptr[Float], Unit]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sys.h
  */
  opaque type playdate_sys = CArray[CChar, Nat.Digit3[Nat._1, Nat._6, Nat._0]]
  object playdate_sys:
    given _tag: Tag[playdate_sys] = Tag.CArray[CChar, Nat.Digit3[Nat._1, Nat._6, Nat._0]](Tag.Byte, Tag.Digit3[Nat._1, Nat._6, Nat._0](Tag.Nat1, Tag.Nat6, Tag.Nat0))
    def apply()(using Zone): Ptr[playdate_sys] = scala.scalanative.unsafe.alloc[playdate_sys](1)
    def apply(realloc : CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]], formatString : CFuncPtr2[Ptr[Ptr[CUnsignedChar]], Ptr[CUnsignedChar], CInt], logToConsole : CFuncPtr1[Ptr[CUnsignedChar], Unit], error : CFuncPtr1[Ptr[CUnsignedChar], Unit], getLanguage : CFuncPtr0[PDLanguage], getCurrentTimeMilliseconds : CFuncPtr0[CUnsignedInt], getSecondsSinceEpoch : CFuncPtr1[Ptr[CUnsignedInt], CUnsignedInt], drawFPS : CFuncPtr2[CInt, CInt, Unit], setUpdateCallback : CFuncPtr2[Ptr[PDCallbackFunction], Ptr[Byte], Unit], getButtonState : CFuncPtr3[Ptr[PDButtons], Ptr[PDButtons], Ptr[PDButtons], Unit], setPeripheralsEnabled : CFuncPtr1[PDPeripherals, Unit], getAccelerometer : CFuncPtr3[Ptr[Float], Ptr[Float], Ptr[Float], Unit], getCrankChange : CFuncPtr0[Float], getCrankAngle : CFuncPtr0[Float], isCrankDocked : CFuncPtr0[CInt], setCrankSoundsDisabled : CFuncPtr1[CInt, CInt], getFlipped : CFuncPtr0[CInt], setAutoLockDisabled : CFuncPtr1[CInt, Unit], setMenuImage : CFuncPtr2[Ptr[LCDBitmap], CInt, Unit], addMenuItem : CFuncPtr3[Ptr[CUnsignedChar], Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]], addCheckmarkMenuItem : CFuncPtr4[Ptr[CUnsignedChar], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]], addOptionsMenuItem : CFuncPtr5[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]], removeAllMenuItems : CFuncPtr0[Unit], removeMenuItem : CFuncPtr1[Ptr[PDMenuItem], Unit], getMenuItemValue : CFuncPtr1[Ptr[PDMenuItem], CInt], setMenuItemValue : CFuncPtr2[Ptr[PDMenuItem], CInt, Unit], getMenuItemTitle : CFuncPtr1[Ptr[PDMenuItem], Ptr[CUnsignedChar]], setMenuItemTitle : CFuncPtr2[Ptr[PDMenuItem], Ptr[CUnsignedChar], Unit], getMenuItemUserdata : CFuncPtr1[Ptr[PDMenuItem], Ptr[Byte]], setMenuItemUserdata : CFuncPtr2[Ptr[PDMenuItem], Ptr[Byte], Unit], getReduceFlashing : CFuncPtr0[CInt], getElapsedTime : CFuncPtr0[Float], resetElapsedTime : CFuncPtr0[Unit], getBatteryPercentage : CFuncPtr0[Float], getBatteryVoltage : CFuncPtr0[Float], getTimezoneOffset : CFuncPtr0[int32_t], shouldDisplay24HourTime : CFuncPtr0[CInt], convertEpochToDateTime : CFuncPtr2[uint32_t, Ptr[PDDateTime], Unit], convertDateTimeToEpoch : CFuncPtr1[Ptr[PDDateTime], uint32_t], clearICache : CFuncPtr0[Unit])(using Zone): Ptr[playdate_sys] =
      val ____ptr = apply()
      (!____ptr).realloc = realloc
      (!____ptr).formatString = formatString
      (!____ptr).logToConsole = logToConsole
      (!____ptr).error = error
      (!____ptr).getLanguage = getLanguage
      (!____ptr).getCurrentTimeMilliseconds = getCurrentTimeMilliseconds
      (!____ptr).getSecondsSinceEpoch = getSecondsSinceEpoch
      (!____ptr).drawFPS = drawFPS
      (!____ptr).setUpdateCallback = setUpdateCallback
      (!____ptr).getButtonState = getButtonState
      (!____ptr).setPeripheralsEnabled = setPeripheralsEnabled
      (!____ptr).getAccelerometer = getAccelerometer
      (!____ptr).getCrankChange = getCrankChange
      (!____ptr).getCrankAngle = getCrankAngle
      (!____ptr).isCrankDocked = isCrankDocked
      (!____ptr).setCrankSoundsDisabled = setCrankSoundsDisabled
      (!____ptr).getFlipped = getFlipped
      (!____ptr).setAutoLockDisabled = setAutoLockDisabled
      (!____ptr).setMenuImage = setMenuImage
      (!____ptr).addMenuItem = addMenuItem
      (!____ptr).addCheckmarkMenuItem = addCheckmarkMenuItem
      (!____ptr).addOptionsMenuItem = addOptionsMenuItem
      (!____ptr).removeAllMenuItems = removeAllMenuItems
      (!____ptr).removeMenuItem = removeMenuItem
      (!____ptr).getMenuItemValue = getMenuItemValue
      (!____ptr).setMenuItemValue = setMenuItemValue
      (!____ptr).getMenuItemTitle = getMenuItemTitle
      (!____ptr).setMenuItemTitle = setMenuItemTitle
      (!____ptr).getMenuItemUserdata = getMenuItemUserdata
      (!____ptr).setMenuItemUserdata = setMenuItemUserdata
      (!____ptr).getReduceFlashing = getReduceFlashing
      (!____ptr).getElapsedTime = getElapsedTime
      (!____ptr).resetElapsedTime = resetElapsedTime
      (!____ptr).getBatteryPercentage = getBatteryPercentage
      (!____ptr).getBatteryVoltage = getBatteryVoltage
      (!____ptr).getTimezoneOffset = getTimezoneOffset
      (!____ptr).shouldDisplay24HourTime = shouldDisplay24HourTime
      (!____ptr).convertEpochToDateTime = convertEpochToDateTime
      (!____ptr).convertDateTimeToEpoch = convertDateTimeToEpoch
      (!____ptr).clearICache = clearICache
      ____ptr
    extension (struct: playdate_sys)
      def realloc: CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]] = !struct.at(0).asInstanceOf[Ptr[CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]]]
      def realloc_=(value: CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]): Unit = !struct.at(0).asInstanceOf[Ptr[CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]]] = value
      def formatString: CFuncPtr2[Ptr[Ptr[CUnsignedChar]], Ptr[CUnsignedChar], CInt] = !struct.at(4).asInstanceOf[Ptr[CFuncPtr2[Ptr[Ptr[CUnsignedChar]], Ptr[CUnsignedChar], CInt]]]
      def formatString_=(value: CFuncPtr2[Ptr[Ptr[CUnsignedChar]], Ptr[CUnsignedChar], CInt]): Unit = !struct.at(4).asInstanceOf[Ptr[CFuncPtr2[Ptr[Ptr[CUnsignedChar]], Ptr[CUnsignedChar], CInt]]] = value
      def logToConsole: CFuncPtr1[Ptr[CUnsignedChar], Unit] = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]]
      def logToConsole_=(value: CFuncPtr1[Ptr[CUnsignedChar], Unit]): Unit = !struct.at(8).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]] = value
      def error: CFuncPtr1[Ptr[CUnsignedChar], Unit] = !struct.at(12).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]]
      def error_=(value: CFuncPtr1[Ptr[CUnsignedChar], Unit]): Unit = !struct.at(12).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedChar], Unit]]] = value
      def getLanguage: CFuncPtr0[PDLanguage] = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[PDLanguage]]]
      def getLanguage_=(value: CFuncPtr0[PDLanguage]): Unit = !struct.at(16).asInstanceOf[Ptr[CFuncPtr0[PDLanguage]]] = value
      def getCurrentTimeMilliseconds: CFuncPtr0[CUnsignedInt] = !struct.at(20).asInstanceOf[Ptr[CFuncPtr0[CUnsignedInt]]]
      def getCurrentTimeMilliseconds_=(value: CFuncPtr0[CUnsignedInt]): Unit = !struct.at(20).asInstanceOf[Ptr[CFuncPtr0[CUnsignedInt]]] = value
      def getSecondsSinceEpoch: CFuncPtr1[Ptr[CUnsignedInt], CUnsignedInt] = !struct.at(24).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedInt], CUnsignedInt]]]
      def getSecondsSinceEpoch_=(value: CFuncPtr1[Ptr[CUnsignedInt], CUnsignedInt]): Unit = !struct.at(24).asInstanceOf[Ptr[CFuncPtr1[Ptr[CUnsignedInt], CUnsignedInt]]] = value
      def drawFPS: CFuncPtr2[CInt, CInt, Unit] = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]]
      def drawFPS_=(value: CFuncPtr2[CInt, CInt, Unit]): Unit = !struct.at(28).asInstanceOf[Ptr[CFuncPtr2[CInt, CInt, Unit]]] = value
      def setUpdateCallback: CFuncPtr2[Ptr[PDCallbackFunction], Ptr[Byte], Unit] = !struct.at(32).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDCallbackFunction], Ptr[Byte], Unit]]]
      def setUpdateCallback_=(value: CFuncPtr2[Ptr[PDCallbackFunction], Ptr[Byte], Unit]): Unit = !struct.at(32).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDCallbackFunction], Ptr[Byte], Unit]]] = value
      def getButtonState: CFuncPtr3[Ptr[PDButtons], Ptr[PDButtons], Ptr[PDButtons], Unit] = !struct.at(36).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDButtons], Ptr[PDButtons], Ptr[PDButtons], Unit]]]
      def getButtonState_=(value: CFuncPtr3[Ptr[PDButtons], Ptr[PDButtons], Ptr[PDButtons], Unit]): Unit = !struct.at(36).asInstanceOf[Ptr[CFuncPtr3[Ptr[PDButtons], Ptr[PDButtons], Ptr[PDButtons], Unit]]] = value
      def setPeripheralsEnabled: CFuncPtr1[PDPeripherals, Unit] = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[PDPeripherals, Unit]]]
      def setPeripheralsEnabled_=(value: CFuncPtr1[PDPeripherals, Unit]): Unit = !struct.at(40).asInstanceOf[Ptr[CFuncPtr1[PDPeripherals, Unit]]] = value
      def getAccelerometer: CFuncPtr3[Ptr[Float], Ptr[Float], Ptr[Float], Unit] = !struct.at(44).asInstanceOf[Ptr[CFuncPtr3[Ptr[Float], Ptr[Float], Ptr[Float], Unit]]]
      def getAccelerometer_=(value: CFuncPtr3[Ptr[Float], Ptr[Float], Ptr[Float], Unit]): Unit = !struct.at(44).asInstanceOf[Ptr[CFuncPtr3[Ptr[Float], Ptr[Float], Ptr[Float], Unit]]] = value
      def getCrankChange: CFuncPtr0[Float] = !struct.at(48).asInstanceOf[Ptr[CFuncPtr0[Float]]]
      def getCrankChange_=(value: CFuncPtr0[Float]): Unit = !struct.at(48).asInstanceOf[Ptr[CFuncPtr0[Float]]] = value
      def getCrankAngle: CFuncPtr0[Float] = !struct.at(52).asInstanceOf[Ptr[CFuncPtr0[Float]]]
      def getCrankAngle_=(value: CFuncPtr0[Float]): Unit = !struct.at(52).asInstanceOf[Ptr[CFuncPtr0[Float]]] = value
      def isCrankDocked: CFuncPtr0[CInt] = !struct.at(56).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def isCrankDocked_=(value: CFuncPtr0[CInt]): Unit = !struct.at(56).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def setCrankSoundsDisabled: CFuncPtr1[CInt, CInt] = !struct.at(60).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]]
      def setCrankSoundsDisabled_=(value: CFuncPtr1[CInt, CInt]): Unit = !struct.at(60).asInstanceOf[Ptr[CFuncPtr1[CInt, CInt]]] = value
      def getFlipped: CFuncPtr0[CInt] = !struct.at(64).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def getFlipped_=(value: CFuncPtr0[CInt]): Unit = !struct.at(64).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def setAutoLockDisabled: CFuncPtr1[CInt, Unit] = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]]
      def setAutoLockDisabled_=(value: CFuncPtr1[CInt, Unit]): Unit = !struct.at(68).asInstanceOf[Ptr[CFuncPtr1[CInt, Unit]]] = value
      def setMenuImage: CFuncPtr2[Ptr[LCDBitmap], CInt, Unit] = !struct.at(72).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]]]
      def setMenuImage_=(value: CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]): Unit = !struct.at(72).asInstanceOf[Ptr[CFuncPtr2[Ptr[LCDBitmap], CInt, Unit]]] = value
      def addMenuItem: CFuncPtr3[Ptr[CUnsignedChar], Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]] = !struct.at(76).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]]
      def addMenuItem_=(value: CFuncPtr3[Ptr[CUnsignedChar], Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]): Unit = !struct.at(76).asInstanceOf[Ptr[CFuncPtr3[Ptr[CUnsignedChar], Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]] = value
      def addCheckmarkMenuItem: CFuncPtr4[Ptr[CUnsignedChar], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]] = !struct.at(80).asInstanceOf[Ptr[CFuncPtr4[Ptr[CUnsignedChar], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]]
      def addCheckmarkMenuItem_=(value: CFuncPtr4[Ptr[CUnsignedChar], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]): Unit = !struct.at(80).asInstanceOf[Ptr[CFuncPtr4[Ptr[CUnsignedChar], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]] = value
      def addOptionsMenuItem: CFuncPtr5[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]] = !struct.at(84).asInstanceOf[Ptr[CFuncPtr5[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]]
      def addOptionsMenuItem_=(value: CFuncPtr5[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]): Unit = !struct.at(84).asInstanceOf[Ptr[CFuncPtr5[Ptr[CUnsignedChar], Ptr[Ptr[CUnsignedChar]], CInt, Ptr[PDMenuItemCallbackFunction], Ptr[Byte], Ptr[PDMenuItem]]]] = value
      def removeAllMenuItems: CFuncPtr0[Unit] = !struct.at(88).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def removeAllMenuItems_=(value: CFuncPtr0[Unit]): Unit = !struct.at(88).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def removeMenuItem: CFuncPtr1[Ptr[PDMenuItem], Unit] = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Unit]]]
      def removeMenuItem_=(value: CFuncPtr1[Ptr[PDMenuItem], Unit]): Unit = !struct.at(92).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Unit]]] = value
      def getMenuItemValue: CFuncPtr1[Ptr[PDMenuItem], CInt] = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], CInt]]]
      def getMenuItemValue_=(value: CFuncPtr1[Ptr[PDMenuItem], CInt]): Unit = !struct.at(96).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], CInt]]] = value
      def setMenuItemValue: CFuncPtr2[Ptr[PDMenuItem], CInt, Unit] = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], CInt, Unit]]]
      def setMenuItemValue_=(value: CFuncPtr2[Ptr[PDMenuItem], CInt, Unit]): Unit = !struct.at(100).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], CInt, Unit]]] = value
      def getMenuItemTitle: CFuncPtr1[Ptr[PDMenuItem], Ptr[CUnsignedChar]] = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Ptr[CUnsignedChar]]]]
      def getMenuItemTitle_=(value: CFuncPtr1[Ptr[PDMenuItem], Ptr[CUnsignedChar]]): Unit = !struct.at(104).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Ptr[CUnsignedChar]]]] = value
      def setMenuItemTitle: CFuncPtr2[Ptr[PDMenuItem], Ptr[CUnsignedChar], Unit] = !struct.at(108).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], Ptr[CUnsignedChar], Unit]]]
      def setMenuItemTitle_=(value: CFuncPtr2[Ptr[PDMenuItem], Ptr[CUnsignedChar], Unit]): Unit = !struct.at(108).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], Ptr[CUnsignedChar], Unit]]] = value
      def getMenuItemUserdata: CFuncPtr1[Ptr[PDMenuItem], Ptr[Byte]] = !struct.at(112).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Ptr[Byte]]]]
      def getMenuItemUserdata_=(value: CFuncPtr1[Ptr[PDMenuItem], Ptr[Byte]]): Unit = !struct.at(112).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDMenuItem], Ptr[Byte]]]] = value
      def setMenuItemUserdata: CFuncPtr2[Ptr[PDMenuItem], Ptr[Byte], Unit] = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], Ptr[Byte], Unit]]]
      def setMenuItemUserdata_=(value: CFuncPtr2[Ptr[PDMenuItem], Ptr[Byte], Unit]): Unit = !struct.at(116).asInstanceOf[Ptr[CFuncPtr2[Ptr[PDMenuItem], Ptr[Byte], Unit]]] = value
      def getReduceFlashing: CFuncPtr0[CInt] = !struct.at(120).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def getReduceFlashing_=(value: CFuncPtr0[CInt]): Unit = !struct.at(120).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def getElapsedTime: CFuncPtr0[Float] = !struct.at(124).asInstanceOf[Ptr[CFuncPtr0[Float]]]
      def getElapsedTime_=(value: CFuncPtr0[Float]): Unit = !struct.at(124).asInstanceOf[Ptr[CFuncPtr0[Float]]] = value
      def resetElapsedTime: CFuncPtr0[Unit] = !struct.at(128).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def resetElapsedTime_=(value: CFuncPtr0[Unit]): Unit = !struct.at(128).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value
      def getBatteryPercentage: CFuncPtr0[Float] = !struct.at(132).asInstanceOf[Ptr[CFuncPtr0[Float]]]
      def getBatteryPercentage_=(value: CFuncPtr0[Float]): Unit = !struct.at(132).asInstanceOf[Ptr[CFuncPtr0[Float]]] = value
      def getBatteryVoltage: CFuncPtr0[Float] = !struct.at(136).asInstanceOf[Ptr[CFuncPtr0[Float]]]
      def getBatteryVoltage_=(value: CFuncPtr0[Float]): Unit = !struct.at(136).asInstanceOf[Ptr[CFuncPtr0[Float]]] = value
      def getTimezoneOffset: CFuncPtr0[int32_t] = !struct.at(140).asInstanceOf[Ptr[CFuncPtr0[int32_t]]]
      def getTimezoneOffset_=(value: CFuncPtr0[int32_t]): Unit = !struct.at(140).asInstanceOf[Ptr[CFuncPtr0[int32_t]]] = value
      def shouldDisplay24HourTime: CFuncPtr0[CInt] = !struct.at(144).asInstanceOf[Ptr[CFuncPtr0[CInt]]]
      def shouldDisplay24HourTime_=(value: CFuncPtr0[CInt]): Unit = !struct.at(144).asInstanceOf[Ptr[CFuncPtr0[CInt]]] = value
      def convertEpochToDateTime: CFuncPtr2[uint32_t, Ptr[PDDateTime], Unit] = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[uint32_t, Ptr[PDDateTime], Unit]]]
      def convertEpochToDateTime_=(value: CFuncPtr2[uint32_t, Ptr[PDDateTime], Unit]): Unit = !struct.at(148).asInstanceOf[Ptr[CFuncPtr2[uint32_t, Ptr[PDDateTime], Unit]]] = value
      def convertDateTimeToEpoch: CFuncPtr1[Ptr[PDDateTime], uint32_t] = !struct.at(152).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDDateTime], uint32_t]]]
      def convertDateTimeToEpoch_=(value: CFuncPtr1[Ptr[PDDateTime], uint32_t]): Unit = !struct.at(152).asInstanceOf[Ptr[CFuncPtr1[Ptr[PDDateTime], uint32_t]]] = value
      def clearICache: CFuncPtr0[Unit] = !struct.at(156).asInstanceOf[Ptr[CFuncPtr0[Unit]]]
      def clearICache_=(value: CFuncPtr0[Unit]): Unit = !struct.at(156).asInstanceOf[Ptr[CFuncPtr0[Unit]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  opaque type playdate_video = CStruct8[CFuncPtr1[Ptr[CUnsignedChar], Ptr[LCDVideoPlayer]], CFuncPtr1[Ptr[LCDVideoPlayer], Unit], CFuncPtr2[Ptr[LCDVideoPlayer], Ptr[LCDBitmap], CInt], CFuncPtr1[Ptr[LCDVideoPlayer], Unit], CFuncPtr2[Ptr[LCDVideoPlayer], CInt, CInt], CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[CUnsignedChar]], CFuncPtr6[Ptr[LCDVideoPlayer], Ptr[CInt], Ptr[CInt], Ptr[Float], Ptr[CInt], Ptr[CInt], Unit], CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[LCDBitmap]]]
  object playdate_video:
    given _tag: Tag[playdate_video] = Tag.materializeCStruct8Tag[CFuncPtr1[Ptr[CUnsignedChar], Ptr[LCDVideoPlayer]], CFuncPtr1[Ptr[LCDVideoPlayer], Unit], CFuncPtr2[Ptr[LCDVideoPlayer], Ptr[LCDBitmap], CInt], CFuncPtr1[Ptr[LCDVideoPlayer], Unit], CFuncPtr2[Ptr[LCDVideoPlayer], CInt, CInt], CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[CUnsignedChar]], CFuncPtr6[Ptr[LCDVideoPlayer], Ptr[CInt], Ptr[CInt], Ptr[Float], Ptr[CInt], Ptr[CInt], Unit], CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[LCDBitmap]]]
    def apply()(using Zone): Ptr[playdate_video] = scala.scalanative.unsafe.alloc[playdate_video](1)
    def apply(loadVideo : CFuncPtr1[Ptr[CUnsignedChar], Ptr[LCDVideoPlayer]], freePlayer : CFuncPtr1[Ptr[LCDVideoPlayer], Unit], setContext : CFuncPtr2[Ptr[LCDVideoPlayer], Ptr[LCDBitmap], CInt], useScreenContext : CFuncPtr1[Ptr[LCDVideoPlayer], Unit], renderFrame : CFuncPtr2[Ptr[LCDVideoPlayer], CInt, CInt], getError : CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[CUnsignedChar]], getInfo : CFuncPtr6[Ptr[LCDVideoPlayer], Ptr[CInt], Ptr[CInt], Ptr[Float], Ptr[CInt], Ptr[CInt], Unit], getContext : CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[LCDBitmap]])(using Zone): Ptr[playdate_video] =
      val ____ptr = apply()
      (!____ptr).loadVideo = loadVideo
      (!____ptr).freePlayer = freePlayer
      (!____ptr).setContext = setContext
      (!____ptr).useScreenContext = useScreenContext
      (!____ptr).renderFrame = renderFrame
      (!____ptr).getError = getError
      (!____ptr).getInfo = getInfo
      (!____ptr).getContext = getContext
      ____ptr
    extension (struct: playdate_video)
      def loadVideo : CFuncPtr1[Ptr[CUnsignedChar], Ptr[LCDVideoPlayer]] = struct._1
      def loadVideo_=(value: CFuncPtr1[Ptr[CUnsignedChar], Ptr[LCDVideoPlayer]]): Unit = !struct.at1 = value
      def freePlayer : CFuncPtr1[Ptr[LCDVideoPlayer], Unit] = struct._2
      def freePlayer_=(value: CFuncPtr1[Ptr[LCDVideoPlayer], Unit]): Unit = !struct.at2 = value
      def setContext : CFuncPtr2[Ptr[LCDVideoPlayer], Ptr[LCDBitmap], CInt] = struct._3
      def setContext_=(value: CFuncPtr2[Ptr[LCDVideoPlayer], Ptr[LCDBitmap], CInt]): Unit = !struct.at3 = value
      def useScreenContext : CFuncPtr1[Ptr[LCDVideoPlayer], Unit] = struct._4
      def useScreenContext_=(value: CFuncPtr1[Ptr[LCDVideoPlayer], Unit]): Unit = !struct.at4 = value
      def renderFrame : CFuncPtr2[Ptr[LCDVideoPlayer], CInt, CInt] = struct._5
      def renderFrame_=(value: CFuncPtr2[Ptr[LCDVideoPlayer], CInt, CInt]): Unit = !struct.at5 = value
      def getError : CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[CUnsignedChar]] = struct._6
      def getError_=(value: CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[CUnsignedChar]]): Unit = !struct.at6 = value
      def getInfo : CFuncPtr6[Ptr[LCDVideoPlayer], Ptr[CInt], Ptr[CInt], Ptr[Float], Ptr[CInt], Ptr[CInt], Unit] = struct._7
      def getInfo_=(value: CFuncPtr6[Ptr[LCDVideoPlayer], Ptr[CInt], Ptr[CInt], Ptr[Float], Ptr[CInt], Ptr[CInt], Unit]): Unit = !struct.at7 = value
      def getContext : CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[LCDBitmap]] = struct._8
      def getContext_=(value: CFuncPtr1[Ptr[LCDVideoPlayer], Ptr[LCDBitmap]]): Unit = !struct.at8 = value

object unions:
  import _root_.pdapi.enumerations.*
  import _root_.pdapi.predef.*
  import _root_.pdapi.aliases.*
  import _root_.pdapi.structs.*
  import _root_.pdapi.unions.*
  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  opaque type json_value_data = CArray[Byte, Nat._4]
  object json_value_data:
    given _tag: Tag[json_value_data] = Tag.CArray[CChar, Nat._4](Tag.Byte, Tag.Nat4)
    def apply()(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      ___ptr
    @scala.annotation.targetName("apply_intval")
    def apply(intval: CInt)(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[CInt]].update(0, intval)
      ___ptr
    @scala.annotation.targetName("apply_floatval")
    def apply(floatval: Float)(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Float]].update(0, floatval)
      ___ptr
    @scala.annotation.targetName("apply_stringval")
    def apply(stringval: Ptr[CUnsignedChar])(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]].update(0, stringval)
      ___ptr
    @scala.annotation.targetName("apply_arrayval")
    def apply(arrayval: Ptr[Byte])(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Ptr[Byte]]].update(0, arrayval)
      ___ptr
    @scala.annotation.targetName("apply_tableval")
    def apply(tableval: Ptr[Byte])(using Zone): Ptr[json_value_data] =
      val ___ptr = alloc[json_value_data](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Ptr[Byte]]].update(0, tableval)
      ___ptr
    extension (struct: json_value_data)
      def intval : CInt = !struct.at(0).asInstanceOf[Ptr[CInt]]
      def intval_=(value: CInt): Unit = !struct.at(0).asInstanceOf[Ptr[CInt]] = value
      def floatval : Float = !struct.at(0).asInstanceOf[Ptr[Float]]
      def floatval_=(value: Float): Unit = !struct.at(0).asInstanceOf[Ptr[Float]] = value
      def stringval : Ptr[CUnsignedChar] = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]]
      def stringval_=(value: Ptr[CUnsignedChar]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]] = value
      def arrayval : Ptr[Byte] = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]]
      def arrayval_=(value: Ptr[Byte]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]] = value
      def tableval : Ptr[Byte] = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]]
      def tableval_=(value: Ptr[Byte]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[Byte]]] = value

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_lua.h
  */
  opaque type lua_value_v = CArray[Byte, Nat._4]
  object lua_value_v:
    given _tag: Tag[lua_value_v] = Tag.CArray[CChar, Nat._4](Tag.Byte, Tag.Nat4)
    def apply()(using Zone): Ptr[lua_value_v] =
      val ___ptr = alloc[lua_value_v](1)
      ___ptr
    @scala.annotation.targetName("apply_intval")
    def apply(intval: CUnsignedInt)(using Zone): Ptr[lua_value_v] =
      val ___ptr = alloc[lua_value_v](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[CUnsignedInt]].update(0, intval)
      ___ptr
    @scala.annotation.targetName("apply_floatval")
    def apply(floatval: Float)(using Zone): Ptr[lua_value_v] =
      val ___ptr = alloc[lua_value_v](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Float]].update(0, floatval)
      ___ptr
    @scala.annotation.targetName("apply_strval")
    def apply(strval: Ptr[CUnsignedChar])(using Zone): Ptr[lua_value_v] =
      val ___ptr = alloc[lua_value_v](1)
      val un = !___ptr
      un.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]].update(0, strval)
      ___ptr
    extension (struct: lua_value_v)
      def intval : CUnsignedInt = !struct.at(0).asInstanceOf[Ptr[CUnsignedInt]]
      def intval_=(value: CUnsignedInt): Unit = !struct.at(0).asInstanceOf[Ptr[CUnsignedInt]] = value
      def floatval : Float = !struct.at(0).asInstanceOf[Ptr[Float]]
      def floatval_=(value: Float): Unit = !struct.at(0).asInstanceOf[Ptr[Float]] = value
      def strval : Ptr[CUnsignedChar] = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]]
      def strval_=(value: Ptr[CUnsignedChar]): Unit = !struct.at(0).asInstanceOf[Ptr[Ptr[CUnsignedChar]]] = value


@extern
private[pdapi] object extern_functions:
  import _root_.pdapi.enumerations.*
  import _root_.pdapi.predef.*
  import _root_.pdapi.aliases.*
  import _root_.pdapi.structs.*
  import _root_.pdapi.unions.*
  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  def SoundFormat_bytesPerFrame(fmt : SoundFormat): uint32_t = extern

  private[pdapi] def __sn_wrap_pdapi_LCDMakeRect(x : CInt, y : CInt, width : CInt, height : CInt, __return : Ptr[LCDRect]): Unit = extern

  private[pdapi] def __sn_wrap_pdapi_LCDRect_translate(r : Ptr[LCDRect], dx : CInt, dy : CInt, __return : Ptr[LCDRect]): Unit = extern

  private[pdapi] def __sn_wrap_pdapi_PDRectMake(x : Float, y : Float, width : Float, height : Float, __return : Ptr[PDRect]): Unit = extern

  private[pdapi] def __sn_wrap_pdapi_json_boolValue(value : Ptr[json_value]): CInt = extern

  private[pdapi] def __sn_wrap_pdapi_json_floatValue(value : Ptr[json_value]): Float = extern

  private[pdapi] def __sn_wrap_pdapi_json_intValue(value : Ptr[json_value]): CInt = extern

  private[pdapi] def __sn_wrap_pdapi_json_stringValue(value : Ptr[json_value]): Ptr[CUnsignedChar] = extern

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api.h
  */
  def eventHandler(playdate : Ptr[PlaydateAPI], event : PDSystemEvent, arg : uint32_t): CInt = extern

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_setArrayDecode(decoder : Ptr[json_decoder], willDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit], didDecodeArrayValue : CFuncPtr3[Ptr[json_decoder], CInt, json_value, Unit], didDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]]): Unit = extern

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_setTableDecode(decoder : Ptr[json_decoder], willDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Unit], didDecodeTableValue : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value, Unit], didDecodeSublist : CFuncPtr3[Ptr[json_decoder], Ptr[CUnsignedChar], json_value_type, Ptr[Byte]]): Unit = extern

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  def pd_frequencyToNote(f : Float): MIDINote = extern

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sound.h
  */
  def pd_noteToFrequency(n : MIDINote): Float = extern


object functions:
  import _root_.pdapi.enumerations.*
  import _root_.pdapi.predef.*
  import _root_.pdapi.aliases.*
  import _root_.pdapi.structs.*
  import _root_.pdapi.unions.*
  import extern_functions.*
  export extern_functions.*

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  def LCDMakeRect(x : CInt, y : CInt, width : CInt, height : CInt)(using Zone): LCDRect =
    val __ptr_0: Ptr[LCDRect] = alloc[LCDRect](1)
    __sn_wrap_pdapi_LCDMakeRect(x, y, width, height, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  def LCDMakeRect(x : CInt, y : CInt, width : CInt, height : CInt)(__return : Ptr[LCDRect]): Unit =
    __sn_wrap_pdapi_LCDMakeRect(x, y, width, height, __return)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  def LCDRect_translate(r : Ptr[LCDRect], dx : CInt, dy : CInt)(__return : Ptr[LCDRect]): Unit =
    __sn_wrap_pdapi_LCDRect_translate(r, dx, dy, __return)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  def LCDRect_translate(r : Ptr[LCDRect], dx : CInt, dy : CInt)(using Zone): LCDRect =
    val __ptr_0: Ptr[LCDRect] = alloc[LCDRect](1)
    __sn_wrap_pdapi_LCDRect_translate(r, dx, dy, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_gfx.h
  */
  def LCDRect_translate(r : LCDRect, dx : CInt, dy : CInt)(using Zone): LCDRect =
    val __ptr_0: Ptr[LCDRect] = alloc[LCDRect](2)
    !(__ptr_0 + 0) = r
    __sn_wrap_pdapi_LCDRect_translate((__ptr_0 + 0), dx, dy, (__ptr_0 + 1))
    !(__ptr_0 + 1)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  def PDRectMake(x : Float, y : Float, width : Float, height : Float)(__return : Ptr[PDRect]): Unit =
    __sn_wrap_pdapi_PDRectMake(x, y, width, height, __return)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_sprite.h
  */
  def PDRectMake(x : Float, y : Float, width : Float, height : Float)(using Zone): PDRect =
    val __ptr_0: Ptr[PDRect] = alloc[PDRect](1)
    __sn_wrap_pdapi_PDRectMake(x, y, width, height, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_boolValue(value : json_value)(using Zone): CInt =
    val __ptr_0: Ptr[json_value] = alloc[json_value](1)
    !(__ptr_0 + 0) = value
    __sn_wrap_pdapi_json_boolValue((__ptr_0 + 0))

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_boolValue(value : Ptr[json_value]): CInt =
    __sn_wrap_pdapi_json_boolValue(value)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_floatValue(value : json_value)(using Zone): Float =
    val __ptr_0: Ptr[json_value] = alloc[json_value](1)
    !(__ptr_0 + 0) = value
    __sn_wrap_pdapi_json_floatValue((__ptr_0 + 0))

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_floatValue(value : Ptr[json_value]): Float =
    __sn_wrap_pdapi_json_floatValue(value)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_intValue(value : Ptr[json_value]): CInt =
    __sn_wrap_pdapi_json_intValue(value)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_intValue(value : json_value)(using Zone): CInt =
    val __ptr_0: Ptr[json_value] = alloc[json_value](1)
    !(__ptr_0 + 0) = value
    __sn_wrap_pdapi_json_intValue((__ptr_0 + 0))

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_stringValue(value : Ptr[json_value]): Ptr[CUnsignedChar] =
    __sn_wrap_pdapi_json_stringValue(value)

  /**
   * [bindgen] header: /Users/kubukoz/Developer/PlaydateSDK/C_API/pd_api/pd_api_json.h
  */
  def json_stringValue(value : json_value)(using Zone): Ptr[CUnsignedChar] =
    val __ptr_0: Ptr[json_value] = alloc[json_value](1)
    !(__ptr_0 + 0) = value
    __sn_wrap_pdapi_json_stringValue((__ptr_0 + 0))

object types:
  export _root_.pdapi.structs.*
  export _root_.pdapi.aliases.*
  export _root_.pdapi.unions.*
  export _root_.pdapi.enumerations.*

object all:
  export _root_.pdapi.enumerations.FileOptions
  export _root_.pdapi.enumerations.LCDBitmapDrawMode
  export _root_.pdapi.enumerations.LCDBitmapFlip
  export _root_.pdapi.enumerations.LCDFontLanguage
  export _root_.pdapi.enumerations.LCDLineCapStyle
  export _root_.pdapi.enumerations.LCDPolygonFillRule
  export _root_.pdapi.enumerations.LCDSolidColor
  export _root_.pdapi.enumerations.LFOType
  export _root_.pdapi.enumerations.LuaType
  export _root_.pdapi.enumerations.PDButtons
  export _root_.pdapi.enumerations.PDLanguage
  export _root_.pdapi.enumerations.PDPeripherals
  export _root_.pdapi.enumerations.PDStringEncoding
  export _root_.pdapi.enumerations.PDSystemEvent
  export _root_.pdapi.enumerations.SoundFormat
  export _root_.pdapi.enumerations.SoundWaveform
  export _root_.pdapi.enumerations.SpriteCollisionResponseType
  export _root_.pdapi.enumerations.TwoPoleFilterType
  export _root_.pdapi.enumerations.json_value_type
  export _root_.pdapi.enumerations.l_valtype
  export _root_.pdapi.aliases.AddScoreCallback
  export _root_.pdapi.aliases.AudioSourceFunction
  export _root_.pdapi.aliases.BoardsListCallback
  export _root_.pdapi.aliases.LCDColor
  export _root_.pdapi.aliases.LCDPattern
  export _root_.pdapi.aliases.LCDSpriteCollisionFilterProc
  export _root_.pdapi.aliases.LCDSpriteDrawFunction
  export _root_.pdapi.aliases.LCDSpriteUpdateFunction
  export _root_.pdapi.aliases.MIDINote
  export _root_.pdapi.aliases.PDCallbackFunction
  export _root_.pdapi.aliases.PDMenuItemCallbackFunction
  export _root_.pdapi.aliases.PersonalBestCallback
  export _root_.pdapi.aliases.RecordCallback
  export _root_.pdapi.aliases.SDFile
  export _root_.pdapi.aliases.ScoresCallback
  export _root_.pdapi.aliases.SequenceFinishedCallback
  export _root_.pdapi.aliases.effectProc
  export _root_.pdapi.aliases.int16_t
  export _root_.pdapi.aliases.int32_t
  export _root_.pdapi.aliases.lua_CFunction
  export _root_.pdapi.aliases.lua_State
  export _root_.pdapi.aliases.signalDeallocFunc
  export _root_.pdapi.aliases.signalNoteOffFunc
  export _root_.pdapi.aliases.signalNoteOnFunc
  export _root_.pdapi.aliases.signalStepFunc
  export _root_.pdapi.aliases.size_t
  export _root_.pdapi.aliases.sndCallbackProc
  export _root_.pdapi.aliases.synthDeallocFunc
  export _root_.pdapi.aliases.synthNoteOnFunc
  export _root_.pdapi.aliases.synthReleaseFunc
  export _root_.pdapi.aliases.synthRenderFunc
  export _root_.pdapi.aliases.synthSetParameterFunc
  export _root_.pdapi.aliases.uint16_t
  export _root_.pdapi.aliases.uint32_t
  export _root_.pdapi.aliases.uint8_t
  export _root_.pdapi.aliases.uintptr_t
  export _root_.pdapi.aliases.writeFunc
  export _root_.pdapi.structs.AudioSample
  export _root_.pdapi.structs.BitCrusher
  export _root_.pdapi.structs.CWCollisionInfo
  export _root_.pdapi.structs.CWItemInfo
  export _root_.pdapi.structs.CollisionPoint
  export _root_.pdapi.structs.CollisionVector
  export _root_.pdapi.structs.ControlSignal
  export _root_.pdapi.structs.DelayLine
  export _root_.pdapi.structs.DelayLineTap
  export _root_.pdapi.structs.FilePlayer
  export _root_.pdapi.structs.FileStat
  export _root_.pdapi.structs.LCDBitmap
  export _root_.pdapi.structs.LCDBitmapTable
  export _root_.pdapi.structs.LCDFont
  export _root_.pdapi.structs.LCDFontData
  export _root_.pdapi.structs.LCDFontGlyph
  export _root_.pdapi.structs.LCDFontPage
  export _root_.pdapi.structs.LCDRect
  export _root_.pdapi.structs.LCDSprite
  export _root_.pdapi.structs.LCDVideoPlayer
  export _root_.pdapi.structs.LuaUDObject
  export _root_.pdapi.structs.OnePoleFilter
  export _root_.pdapi.structs.Overdrive
  export _root_.pdapi.structs.PDBoard
  export _root_.pdapi.structs.PDBoardsList
  export _root_.pdapi.structs.PDDateTime
  export _root_.pdapi.structs.PDMenuItem
  export _root_.pdapi.structs.PDRect
  export _root_.pdapi.structs.PDScore
  export _root_.pdapi.structs.PDScoresList
  export _root_.pdapi.structs.PDSynth
  export _root_.pdapi.structs.PDSynthEnvelope
  export _root_.pdapi.structs.PDSynthInstrument
  export _root_.pdapi.structs.PDSynthLFO
  export _root_.pdapi.structs.PDSynthSignal
  export _root_.pdapi.structs.PDSynthSignalValue
  export _root_.pdapi.structs.PlaydateAPI
  export _root_.pdapi.structs.RingModulator
  export _root_.pdapi.structs.SamplePlayer
  export _root_.pdapi.structs.SequenceTrack
  export _root_.pdapi.structs.SoundChannel
  export _root_.pdapi.structs.SoundEffect
  export _root_.pdapi.structs.SoundSequence
  export _root_.pdapi.structs.SoundSource
  export _root_.pdapi.structs.SpriteCollisionInfo
  export _root_.pdapi.structs.SpriteQueryInfo
  export _root_.pdapi.structs.TwoPoleFilter
  export _root_.pdapi.structs.json_decoder
  export _root_.pdapi.structs.json_encoder
  export _root_.pdapi.structs.json_reader
  export _root_.pdapi.structs.json_value
  export _root_.pdapi.structs.lua_reg
  export _root_.pdapi.structs.lua_val
  export _root_.pdapi.structs.playdate_control_signal
  export _root_.pdapi.structs.playdate_display
  export _root_.pdapi.structs.playdate_file
  export _root_.pdapi.structs.playdate_graphics
  export _root_.pdapi.structs.playdate_json
  export _root_.pdapi.structs.playdate_lua
  export _root_.pdapi.structs.playdate_scoreboards
  export _root_.pdapi.structs.playdate_sound
  export _root_.pdapi.structs.playdate_sound_channel
  export _root_.pdapi.structs.playdate_sound_effect
  export _root_.pdapi.structs.playdate_sound_effect_bitcrusher
  export _root_.pdapi.structs.playdate_sound_effect_delayline
  export _root_.pdapi.structs.playdate_sound_effect_onepolefilter
  export _root_.pdapi.structs.playdate_sound_effect_overdrive
  export _root_.pdapi.structs.playdate_sound_effect_ringmodulator
  export _root_.pdapi.structs.playdate_sound_effect_twopolefilter
  export _root_.pdapi.structs.playdate_sound_envelope
  export _root_.pdapi.structs.playdate_sound_fileplayer
  export _root_.pdapi.structs.playdate_sound_instrument
  export _root_.pdapi.structs.playdate_sound_lfo
  export _root_.pdapi.structs.playdate_sound_sample
  export _root_.pdapi.structs.playdate_sound_sampleplayer
  export _root_.pdapi.structs.playdate_sound_sequence
  export _root_.pdapi.structs.playdate_sound_signal
  export _root_.pdapi.structs.playdate_sound_source
  export _root_.pdapi.structs.playdate_sound_synth
  export _root_.pdapi.structs.playdate_sound_track
  export _root_.pdapi.structs.playdate_sprite
  export _root_.pdapi.structs.playdate_sys
  export _root_.pdapi.structs.playdate_video
  export _root_.pdapi.unions.json_value_data
  export _root_.pdapi.unions.lua_value_v
  export _root_.pdapi.functions.SoundFormat_bytesPerFrame
  export _root_.pdapi.functions.eventHandler
  export _root_.pdapi.functions.json_setArrayDecode
  export _root_.pdapi.functions.json_setTableDecode
  export _root_.pdapi.functions.pd_frequencyToNote
  export _root_.pdapi.functions.pd_noteToFrequency
  export _root_.pdapi.functions.LCDMakeRect
  export _root_.pdapi.functions.LCDRect_translate
  export _root_.pdapi.functions.PDRectMake
  export _root_.pdapi.functions.json_boolValue
  export _root_.pdapi.functions.json_floatValue
  export _root_.pdapi.functions.json_intValue
  export _root_.pdapi.functions.json_stringValue

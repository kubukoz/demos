//> using lib "org.scodec::scodec-core:2.2.0"
//> using lib "org.scodec::scodec-cats:1.1.0"
//> using plugin "org.polyvariant:::better-tostring:0.3.16"
//> using lib "co.fs2::fs2-io:3.2.12"
//> using lib "com.lihaoyi::pprint:0.7.3"
//> using scala "3.1.3"
import fs2.io.file.Files
import cats.effect.IO
import fs2.io.file.Path
import cats.effect.IOApp
import scodec.bits.ByteVector
import scodec.bits._
import scodec.Codec
import cats.implicits._
import scodec.interop.cats._
import java.nio.charset.StandardCharsets
import cats.effect.ExitCode
import scodec.Err

case class ClassFile(
  minorVersion: Int,
  majorVersion: Int,
  constants: List[Constant],
  accessFlags: Set[ClassAccessFlag],
  thisClass: ConstantIndex,
  superClass: ConstantIndex,
  interfaces: List[ConstantIndex],
  fields: List[FieldInfo],
  methods: List[MethodInfo],
  attributes: List[AttributeInfo],
)

case class ConstantIndex(value: Int)

enum Constant {
  case Class(nameIndex: ConstantIndex)
  case FieldRef(classIndex: ConstantIndex, nameAndTypeIndex: ConstantIndex)
  case MethodRef(classIndex: ConstantIndex, nameAndTypeIndex: ConstantIndex)
  case InterfaceMethodRef(classIndex: ConstantIndex, nameAndTypeIndex: ConstantIndex)
  case StringRef(stringIndex: ConstantIndex)
  case IntConstant(bytes: ByteVector)
  case FloatConstant(bytes: ByteVector)
  case LongConstant(highBytes: ByteVector, lowBytes: ByteVector)
  case DoubleConstant(highBytes: ByteVector, lowBytes: ByteVector)
  case NameAndType(nameIndex: ConstantIndex, descriptorIndex: ConstantIndex)
  case Utf8(bytes: String)
  case MethodHandle(referenceType: MethodReferenceKind, referenceIndex: ConstantIndex)
  case MethodType(descriptorIndex: ConstantIndex)
  case InvokeDynamic(bootstrapMethodAttrIndex: Int, nameAndTypeIndex: ConstantIndex)
}

enum ClassAccessFlag {
  case Public, Final, Super, Interface, Abstract, Synthetic, Annotation, Enum
}

enum FieldAccessFlag {
  case Public, Private, Protected, Static, Final, Volatile, Transient, Synthetic, Enum
}

enum MethodAccessFlag {
  case Public, Private, Protected, Static, Final, Synchronized, Bridge, Varargs, Native, Abstract,
    Strict, Synthetic
}

enum MethodReferenceKind {
  case GetField, GetStatic, PutField, PutStatic, InvokeVirtual, InvokeStatic, InvokeSpecial,
    NewInvokeSpecial, InvokeInterface
}

case class FieldInfo(
  accessFlags: Set[FieldAccessFlag],
  nameIndex: ConstantIndex,
  descriptorIndex: ConstantIndex,
  attributes: List[AttributeInfo],
)

case class AttributeInfo(
  nameIndex: ConstantIndex,
  info: ByteVector,
)

case class MethodInfo(
  accessFlags: Set[MethodAccessFlag],
  nameIndex: ConstantIndex,
  descriptorIndex: ConstantIndex,
  attributes: List[AttributeInfo],
)

object Main extends IOApp {

  def masked[A](range: Codec[Int], valuesWithMasks: Map[A, Int]): Codec[Set[A]] =
    range
      .imap { v =>
        valuesWithMasks.collect {
          case (flag, mask) if (mask & v) == mask => flag
        }.toSet
      } { flags =>
        flags.map(flag => valuesWithMasks(flag)).foldLeft(0)(_ | _)
      }

  val c = {

    import scodec.codecs._

    val u1 = byte
    val u1Int = uint(8)
    val u2 = uint(16)
    val u4 = ulong(32)

    val constantPoolIndex = u2.as[ConstantIndex]

    val fieldRefCommon =
      ("class index" | constantPoolIndex) ::
        ("name and type index" | constantPoolIndex)

    val methodRef = fieldRefCommon.as[Constant.MethodRef]
    val fieldRef = fieldRefCommon.as[Constant.FieldRef]
    val interfaceMethodRef = fieldRefCommon.as[Constant.InterfaceMethodRef]

    val nameAndType =
      (("name index" | constantPoolIndex) :: ("descriptor index" | constantPoolIndex))
        .as[Constant.NameAndType]

    val classConstant = ("name index" | constantPoolIndex).as[Constant.Class]

    val utf8Constant = vectorOfN("length" | u2, u1)
      .xmap(bytes => new String(bytes.toArray), _.getBytes(StandardCharsets.UTF_8).toVector)
      .as[Constant.Utf8]

    val stringConstant = ("string index" | constantPoolIndex).as[Constant.StringRef]

    val numeric = "bytes" | bytes(4)
    val bigNumeric = ("high bytes" | bytes(4)) :: ("low bytes" | bytes(4))

    val intConstant = numeric.as[Constant.IntConstant]
    val floatConstant = numeric.as[Constant.FloatConstant]
    val longConstant = bigNumeric.as[Constant.LongConstant]
    val doubleConstant = bigNumeric.as[Constant.DoubleConstant]

    val methodType = ("descriptor index" | constantPoolIndex).as[Constant.MethodType]
    val methodHandle =
      (("reference kind" | mappedEnum(
        u1Int,
        MethodReferenceKind.values.map(k => k -> k.ordinal).toMap,
      )) ::
        ("reference index" | constantPoolIndex))
        .as[Constant.MethodHandle]

    val invokeDynamic =
      (("bootstrap method attr index" | u2) ::
        ("name and type index" | constantPoolIndex))
        .as[Constant.InvokeDynamic]

    val constantEntry =
      "constant pool entry" |
        discriminated[Constant]
          .by(u1)
          .typecase(7, classConstant)
          .typecase(9, fieldRef)
          .typecase(10, methodRef)
          .typecase(11, interfaceMethodRef)
          .typecase(8, stringConstant)
          .typecase(3, intConstant)
          .typecase(4, floatConstant)
          // .typecase(5, longConstant)
          // .typecase(6, doubleConstant)
          .typecase(12, nameAndType)
          .typecase(1, utf8Constant)
          .typecase(15, methodHandle)
          .typecase(16, methodType)
          .typecase(18, invokeDynamic)

    val classAccessFlags = {
      import ClassAccessFlag._
      "access flags" | masked(
        u2,
        Map(
          Public -> 0x0001,
          Final -> 0x0010,
          Super -> 0x0020,
          Interface -> 0x0200,
          Abstract -> 0x0400,
          Synthetic -> 0x1000,
          Annotation -> 0x2000,
          Enum -> 0x4000,
        ),
      )
    }

    val fieldAccessFlags = {
      import FieldAccessFlag._
      "access flags" | masked(
        u2,
        Map(
          Public -> 0x0001,
          Private -> 0x0002,
          Protected -> 0x0004,
          Static -> 0x0008,
          Final -> 0x0010,
          Volatile -> 0x0040,
          Transient -> 0x0080,
          Synthetic -> 0x1000,
          Enum -> 0x4000,
        ),
      )
    }

    val methodAccessFlags = {
      import MethodAccessFlag._
      "access flags" | masked(
        u2,
        Map(
          Public -> 0x0001,
          Private -> 0x0002,
          Protected -> 0x0004,
          Static -> 0x0008,
          Final -> 0x0010,
          Synchronized -> 0x0020,
          Bridge -> 0x0040,
          Varargs -> 0x0080,
          Native -> 0x0100,
          Abstract -> 0x0400,
          Strict -> 0x0800,
          Synthetic -> 0x1000,
        ),
      )
    }

    val attribute =
      "attribute" | (
        ("name index" | constantPoolIndex) ::
          variableSizeBytesLong(
            "attribute length" | u4,
            "info" | vector(u1),
          ).imap(ByteVector(_))(_.toArray.toVector)
      ).as[AttributeInfo]

    val attributes = "attributes" | listOfN("attributes count" | u2, attribute)

    val fieldInfo =
      "field info" |
        (
          "access flags" | fieldAccessFlags ::
            ("name index" | constantPoolIndex) ::
            ("descriptor index" | constantPoolIndex) ::
            attributes
        ).as[FieldInfo]

    val methodInfo =
      "method info" | (
        ("access flags" | methodAccessFlags) ::
          ("name index" | constantPoolIndex) ::
          ("descriptor index" | constantPoolIndex) ::
          attributes
      ).as[MethodInfo]

    (
      ("magic number " | constant(hex"CAFEBABE")) ::
        ("minor version" | u2) ::
        ("major version" | u2) ::
        listOfN(
          "constant pool count" | u2.imap(_ - 1)(_ + 1),
          constantEntry,
        ) ::
        classAccessFlags ::
        ("this class" | constantPoolIndex) ::
        ("super class" | constantPoolIndex) ::
        listOfN(
          "interface count" | u2,
          "interface index" | constantPoolIndex,
        ) ::
        listOfN(
          "field count" | u2,
          fieldInfo,
        ) ::
        listOfN(
          "method count" | u2,
          methodInfo,
        ) ::
        attributes
    ).dropUnits.as[ClassFile]
  }

  def run(args: List[String]): IO[ExitCode] = Files[IO]
    .readAll(Path(args(0)))
    .compile
    .toVector
    .map { bytes =>
      val bits = ByteVector(bytes).bits

      c.decode(bits)
        .map(_.value)
    }
    .map(_.map(pprint(_)))
    .flatMap(IO.println(_))
    .as(ExitCode.Success)

}

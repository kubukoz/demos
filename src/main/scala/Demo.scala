import com.google.protobuf.CodedOutputStream

import scalapb.LiteParser
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Primitive
import smithy4s.schema.Primitive.PInt
import smithy4s.schema.Primitive.PString
import smithy4s.schema.SchemaField
import smithy4s.schema.SchemaVisitor

import java.io.ByteArrayOutputStream
import java.util.Base64
import com.google.protobuf.struct.Value
import tutorial.{addressbook => pb}
import _root_.{tutorial => sm}
import smithy4s.schema.CollectionTag
import cats.implicits._
import scalapb.GeneratedMessage

object Demo extends App {

  def parityTest[A <: GeneratedMessage, B: Schema](a: A, b: B): Unit = {

    val p1 = {
      val baos = new ByteArrayOutputStream(CodedOutputStream.DEFAULT_BUFFER_SIZE)

      val bufferSize = LiteParser.preferredCodedOutputStreamBufferSize(a.serializedSize)
      val cos: CodedOutputStream = CodedOutputStream.newInstance(baos, bufferSize)

      a.writeTo(cos)
      cos.flush()

      val result = baos.toByteArray()

      import sys.process._
      println("xxd".#<(new java.io.ByteArrayInputStream(result)).!!)
      result
    }

    val p2 = {
      val writer = ProtobufSerializer.derive[B]

      val result = writer.serialize(b)
      import sys.process._
      println("xxd".#<(new java.io.ByteArrayInputStream(result)).!!)

      result
    }

    require(p1.toList == p2.toList, "p1 != p2")
  }

  parityTest(
    pb.Person(
      name = "John Doe",
      id = 42,
      email = Some("kubukoz@gmail.com"),
      phones = List(pb.Phone("123")),
    ),
    sm.Person(
      name = "John Doe",
      id = 42,
      email = Some("kubukoz@gmail.com"),
      phones = List(sm.Phone("123")),
    ),
  )

  parityTest(
    pb.Person(
      name = "John Doe",
      id = 42,
      email = Some("kubukoz@gmail.com"),
      phones = Nil,
    ),
    sm.Person(
      name = "John Doe",
      id = 42,
      email = Some("kubukoz@gmail.com"),
      phones = Nil,
    ),
  )

  parityTest(
    pb.Person(
      name = "John Doe",
      id = 42,
      email = None,
      phones = Nil,
    ),
    sm.Person(
      name = "John Doe",
      id = 42,
      email = None,
      phones = Nil,
    ),
  )

}

trait ProtobufSerializer[A] {
  def serialize(a: A): Array[Byte]
}

object ProtobufSerializer {

  def derive[A: Schema]: ProtobufSerializer[A] = {
    val writer = implicitly[Schema[A]].compile(ProtobufWriterVisitor)

    a => {
      val baos = new ByteArrayOutputStream(CodedOutputStream.DEFAULT_BUFFER_SIZE)

      val bufferSize = LiteParser.preferredCodedOutputStreamBufferSize(
        writer.serializedSize(a)
      )

      val cos: CodedOutputStream = CodedOutputStream.newInstance(baos, bufferSize)

      writer.writeTo(cos, a)
      cos.flush()
      baos.toByteArray()
    }
  }

}

trait ProtobufWriter[A] { self =>
  def writeTo(cos: CodedOutputStream, a: A): Unit
  def serializedSize(a: A): Int

  def contramap[B](f: B => A): ProtobufWriter[B] =
    new ProtobufWriter[B] {
      override def writeTo(cos: CodedOutputStream, a: B): Unit = self.writeTo(cos, f(a))
      override def serializedSize(b: B): Int = self.serializedSize(f(b))
    }

}

object ProtobufWriter {

  def instance[A](f: (CodedOutputStream, A) => Unit, size: A => Int): ProtobufWriter[A] =
    new ProtobufWriter[A] {
      override def writeTo(cos: CodedOutputStream, a: A): Unit = f(cos, a)
      override def serializedSize(a: A): Int = size(a)
    }

}

object ProtobufWriterVisitor extends SchemaVisitor.Default[ProtobufWriter] {
  override def default[A]: ProtobufWriter[A] = sys.error("unsupported schema")

  override def collection[C[_], A](
    shapeId: ShapeId,
    hints: Hints,
    tag: CollectionTag[C],
    member: Schema[A],
  ): ProtobufWriter[C[A]] = {
    val memberInstance = member.compile(this)

    val fieldNumber = hints.get(sm.ProtobufIndex).getOrElse(sys.error("no index found")).value

    tag match {
      case CollectionTag.ListTag =>
        ProtobufWriter.instance(
          (cos, v) =>
            v.foreach { item =>
              cos.writeTag(
                fieldNumber,
                2, /* why 2??????? seems to be a magic number in scalapb too */
              )
              cos.writeUInt32NoTag(memberInstance.serializedSize(item))
              memberInstance.writeTo(cos, item)
            },
          list =>
            list.foldMap { item =>
              val sz = memberInstance.serializedSize(item)

              1 + CodedOutputStream.computeUInt32SizeNoTag(sz) + sz
            },
        )
    }
  }

  override def primitive[P](
    shapeId: ShapeId,
    hints: Hints,
    tag: Primitive[P],
  ): ProtobufWriter[P] = {
    val fieldNumber = hints.get(sm.ProtobufIndex).getOrElse(sys.error("no index found")).value

    tag match {
      case PString =>
        ProtobufWriter.instance(
          _.writeString(fieldNumber, _),
          CodedOutputStream.computeStringSize(fieldNumber, _),
        )
      case PInt =>
        ProtobufWriter.instance(
          _.writeInt32(fieldNumber, _),
          CodedOutputStream.computeInt32Size(fieldNumber, _),
        )
    }
  }

  override def struct[S](
    shapeId: ShapeId,
    hints: Hints,
    fields: Vector[SchemaField[S, _]],
    make: IndexedSeq[Any] => S,
  ): ProtobufWriter[S] = {

    def handleField[A](
      f: smithy4s.schema.Field[ProtobufWriter, S, A]
    ): ProtobufWriter[S] = f
      .instanceA(new smithy4s.schema.Field.ToOptional[ProtobufWriter] {
        override def apply[B](
          f: ProtobufWriter[B]
        ): ProtobufWriter[Option[B]] = ProtobufWriter.instance[Option[B]](
          {
            case (cos, Some(v)) => f.writeTo(cos, v)
            case (_, None)      => ()
          },
          _.fold(0)(f.serializedSize),
        )
      })
      .contramap(f.get)

    val instances: Vector[ProtobufWriter[S]] = fields
      .sortBy(_.instance.hints.get(sm.ProtobufIndex).getOrElse(sys.error("no index found")).value)
      .map(_.mapK(this))
      .map(handleField(_))

    // so here are a couple things wrong with this.
    // first of all, instances writing their values need to be ordered by their ACTUAL protobuf index.

    ProtobufWriter.instance[S](
      (cos, v) => instances.foreach(_.writeTo(cos, v)),
      v => instances.foldLeft(0) { case (acc, f) => acc + f.serializedSize(v) },
    )
  }

}

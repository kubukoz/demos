//> using dep io.circe::circe-parser:0.14.10
import util.chaining.*

enum Document {
  private case DObject(values: Map[String, Document])
  private case DList(values: List[Document])
  private case DString(value: String)

  def visit[A](v: DocumentVisitor[A]): A =
    this match {
      case Document.DObject(values) => v.visitObject(values)
      case Document.DList(values)   => v.visitList(values)
      case Document.DString(value)  => v.visitString(value)
    }

}

trait DocumentVisitor[A] {
  def visit(document: Document): A = document.visit(this)

  def visitObject(values: Map[String, Document]): A
  def visitList(values: List[Document]): A
  def visitString(value: String): A
}

object Document {

  def obj(values: (String, Document)*): Document = Document.DObject(values.toMap)
  def list(values: Document*): Document = Document.DList(values.toList)
  def string(value: String): Document = Document.DString(value)

  private object Compiler extends SchemaVisitor[DocumentEncoder] {
    def visitDocument: DocumentEncoder[Document] = identity(_)

    def visitList[T](member: Schema[T]): DocumentEncoder[List[T]] = {
      val underlying = visit(member)

      list => Document.list(list.map(underlying.encode)*)
    }

    val visitString: DocumentEncoder[String] = Document.string(_)

    def visitStruct[T](fields: List[Field[T, ?]], make: List[Any] => T): DocumentEncoder[T] = {
      val instances = fields.map { field =>
        val encoder = visit(field.schema)
        field.label -> encoder.contramap(field.get)
      }

      s => {
        val values = instances.map { (label, encoder) =>
          label -> encoder.encode(s)
        }

        Document.obj(values*)
      }
    }

  }

  def encode[A: Schema](a: A): Document = Compiler.visit(Schema[A]).encode(a)
}

trait DocumentEncoder[A] {
  def encode(a: A): Document
  def contramap[B](f: B => A): DocumentEncoder[B] = b => encode(f(b))
}

enum Schema[A] {
  case SStruct(fields: List[Field[A, ?]], make: List[Any] => A) extends Schema[A]
  case SList(schema: Schema[A]) extends Schema[List[A]]
  case SString extends Schema[String]
  case SDocument extends Schema[Document]
}

trait SchemaVisitor[F[_]] {

  def visit[T](schema: Schema[T]): F[T] =
    schema match {
      case Schema.SStruct(fields, make) => visitStruct(fields, make)
      case Schema.SList(schema)         => visitList(schema)
      case Schema.SString               => visitString
      case Schema.SDocument             => visitDocument
    }

  def visitStruct[T](fields: List[Field[T, ?]], make: List[Any] => T): F[T]

  def visitList[T](member: Schema[T]): F[List[T]]

  def visitString: F[String]

  def visitDocument: F[Document]
}

object Schema {
  def apply[A](using schema: Schema[A]): Schema[A] = schema
}

case class Field[S, A](
  get: S => A,
  label: String,
  schema: Schema[A],
)

trait JsonEncoder[A] {
  def encode(a: A): StringBuilder => Unit
  def contramap[B](f: B => A): JsonEncoder[B] = b => encode(f(b))
}

object JsonEncoder {

  private object Compiler extends SchemaVisitor[JsonEncoder] {

    def visitList[T](member: Schema[T]): JsonEncoder[List[T]] = {
      val underlying = visit(member)

      list =>
        sb => {
          sb.append("[")
          var isFirst = true
          list.foreach { item =>
            if (isFirst)
              isFirst = false
            else
              sb.append(",")
            underlying.encode(item)(sb)
          }
          sb.append("]")
        }
    }

    val visitString: JsonEncoder[String] = v => _.append("\"").append(v).append("\"")

    def visitStruct[T](fields: List[Field[T, ?]], make: List[Any] => T): JsonEncoder[T] = {
      val instances = fields.map { field =>
        val encoder = visit(field.schema)
        field.label -> encoder.contramap(field.get)
      }

      s => { sb =>
        sb.append("{")
        var isFirst = true
        instances.foreach { (label, encoder) =>
          if (isFirst)
            isFirst = false
          else
            sb.append(",")
          sb.append("\"").append(label).append("\":")
          encoder.encode(s)(sb)
        }
        sb.append("}")
      }
    }

    val visitDocument: JsonEncoder[Document] = {
      val visitor =
        new DocumentVisitor[StringBuilder => Unit] {
          def visitString(value: String): StringBuilder => Unit =
            _.append("\"").append(value).append("\"")

          def visitList(values: List[Document]): StringBuilder => Unit = { sb =>
            sb.append("[")
            var isFirst = true
            values.foreach { value =>
              if (isFirst)
                isFirst = false
              else
                sb.append(",")
              this.visit(value)(sb)
            }
            sb.append("]")
          }

          def visitObject(values: Map[String, Document]): StringBuilder => Unit = { sb =>
            sb.append("{")
            var isFirst = true
            values.foreach { (label, value) =>
              if (isFirst)
                isFirst = false
              else
                sb.append(",")
              sb.append("\"").append(label).append("\":")
              this.visit(value)(sb)
            }
            sb.append("}")
          }
        }

      visitor.visit(_)
    }

  }

  def encode[A: Schema](
    a: A
  ): String = new StringBuilder().tap(Compiler.visit(Schema[A]).encode(a)).toString()

}

JsonEncoder.encode("foo")(
  using Schema.SString
)

JsonEncoder.encode(List("foo", "bar"))(
  using Schema.SList(Schema.SString)
)

case class Friend(name: String, data: Document)

object Friend {

  given Schema[Friend] = Schema.SStruct(
    List(
      Field(_.name, "name", Schema.SString),
      Field(_.data, "data", Schema.SDocument),
    ),
    { case List(name: String, doc: Document) => Friend(name, doc) },
  )

}

case class User(
  name: String,
  friends: List[Friend],
)

object User {

  given Schema[User] = Schema.SStruct(
    List(
      Field(_.name, "name", Schema.SString),
      Field(_.friends, "friends", Schema.SList(Schema[Friend])),
    ),
    { case List(name: String, friends: List[Friend]) => User(name, friends) },
  )

}

case class UsersUntyped(usersUntyped: List[Document])

object UsersUntyped {

  given Schema[UsersUntyped] = Schema.SStruct(
    List(
      Field(_.usersUntyped, "usersUntyped", Schema.SList(Schema.SDocument))
    ),
    { case List(usersUntyped: List[Document]) => UsersUntyped(usersUntyped) },
  )

}

val user = User(
  "John",
  List(
    Friend(
      "Alice",
      Document.obj(
        "k" -> Document.list(
          Document.string("vvv")
        )
      ),
    ),
    Friend("Bob", Document.string("aaa")),
  ),
)

io.circe.parser.parse(JsonEncoder.encode(user)).toTry.get

io.circe.parser.parse(JsonEncoder.encode(UsersUntyped(List(Document.encode(user))))).toTry.get

//> using scala "2.13.7"
import dynosaur.Schema
import smithy4s.schema
import smithy4s.SchemaVisitor

object dynosaur {
  trait Schema
}

object smithy4s {
  object schema {
    trait A
  }

  trait SchemaVisitor[A] {
    def list(schema: smithy4s.schema.A): Unit
  }

}

// object interop extends SchemaVisitor[Schema]

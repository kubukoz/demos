package demo

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.union

sealed trait MyEvent extends scala.Product with scala.Serializable {
  @inline final def widen: MyEvent = this
}
object MyEvent extends ShapeTag.Companion[MyEvent] {
  val id: ShapeId = ShapeId("demo", "MyEvent")

  val hints: Hints = Hints(
    demo.EventUnion(),
  )

  case class E1Case(e1: Event1) extends MyEvent
  case class E2Case(e2: Event2) extends MyEvent
  case class E3Case(e3: Event3) extends MyEvent

  object E1Case {
    val hints: Hints = Hints.empty
    val schema: Schema[E1Case] = bijection(Event1.schema.addHints(hints), E1Case(_), _.e1)
    val alt = schema.oneOf[MyEvent]("e1")
  }
  object E2Case {
    val hints: Hints = Hints.empty
    val schema: Schema[E2Case] = bijection(Event2.schema.addHints(hints), E2Case(_), _.e2)
    val alt = schema.oneOf[MyEvent]("e2")
  }
  object E3Case {
    val hints: Hints = Hints.empty
    val schema: Schema[E3Case] = bijection(Event3.schema.addHints(hints), E3Case(_), _.e3)
    val alt = schema.oneOf[MyEvent]("e3")
  }

  implicit val schema: Schema[MyEvent] = union(
    E1Case.alt,
    E2Case.alt,
    E3Case.alt,
  ){
    case c: E1Case => E1Case.alt(c)
    case c: E2Case => E2Case.alt(c)
    case c: E3Case => E3Case.alt(c)
  }.withId(id).addHints(hints)
}